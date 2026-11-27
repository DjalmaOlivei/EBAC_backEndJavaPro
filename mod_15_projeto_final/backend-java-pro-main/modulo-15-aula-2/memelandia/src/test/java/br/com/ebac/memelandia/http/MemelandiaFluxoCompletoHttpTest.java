package br.com.ebac.memelandia.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ebac.memelandia.entities.db1.CategoriaMeme;
import br.com.ebac.memelandia.entities.db1.Meme;
import br.com.ebac.memelandia.entities.db2.Usuario;

/**
 * [HTTP Test] Teste de integração ponta a ponta (End-to-End) com requisições HTTP reais.
 *
 * Diferença em relação aos *MockTest:
 * - Os *MockTest usam @WebMvcTest + @MockBean e NÃO acessam bancos de dados reais.
 * - Este teste usa @SpringBootTest(webEnvironment = RANDOM_PORT), subindo o servidor
 *   Tomcat embutido, todos os controllers, services, repositories e conexões reais
 *   com MongoDB e MySQL.
 *
 * Pré-requisitos para executar este teste:
 * 1. Docker Compose ligado com MongoDB (porta 27017) e MySQL (porta 3306) saudáveis.
 * 2. Credenciais e configurações de Db1Config (MySQL) e Db2Config (MongoDB) válidas
 *    (no MongoDB, o usuário root "admin" autentica no authSource "admin").
 */
@Tag("http")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("[HTTP Test] Fluxo Completo End-to-End (MongoDB + MySQL via Docker Compose)")
class MemelandiaFluxoCompletoHttpTest {

    // Cliente HTTP real do Spring Boot Test, já apontando para a porta aleatória do servidor
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Cenário completo simulando um usuário utilizando a API do início ao fim:
     *
     * Passo 1: POST /usuario/usuarios     -> Grava um novo usuário no MongoDB (db2)
     * Passo 2: GET  /usuario/usuarios     -> Consulta o MongoDB e confirma que o usuário está listado
     * Passo 3: POST /memelandia/categorias -> Grava uma categoria no MySQL (db1) vinculada ao ID do usuário
     * Passo 4: GET  /memelandia/categorias -> Consulta o MySQL e confirma que a categoria está listada
     * Passo 5: POST /meme/memes           -> Grava um meme no MySQL (db1) vinculado à categoria e ao usuário
     * Passo 6: GET  /meme/memes           -> Consulta o MySQL e confirma que o meme está listado
     */
    @Test
    @DisplayName("[HTTP Test] Deve executar fluxo completo: criar e listar Usuário, Categoria e Meme via HTTP real")
    void deveExecutarFluxoCompletoViaHttp() {
        // Gera um sufixo aleatório para evitar conflito com o índice único de e-mail (@Indexed(unique = true)) no MongoDB
        String sufixoUnico = UUID.randomUUID().toString().substring(0, 8);

        // =====================================================================
        // PASSO 1: Cadastrar Usuário via HTTP POST no MongoDB (/usuario/usuarios)
        // =====================================================================
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Usuário HTTP " + sufixoUnico);
        novoUsuario.setEmail("http-" + sufixoUnico + "@memelandia.com");
        novoUsuario.setDataCadastro(java.util.Date.from(
                LocalDate.of(2026, 2, 10).atStartOfDay(ZoneOffset.UTC).toInstant()));

        ResponseEntity<Usuario> respostaUsuario = restTemplate.postForEntity(
                "/usuario/usuarios",
                novoUsuario,
                Usuario.class
        );

        // Valida HTTP 200 e garante que o MongoDB gerou o ObjectId (String) do usuário
        assertEquals(HttpStatus.OK, respostaUsuario.getStatusCode(),
                "POST /usuario/usuarios deve retornar HTTP 200");
        Usuario usuarioSalvo = respostaUsuario.getBody();
        assertNotNull(usuarioSalvo, "O corpo da resposta do usuário não deve ser nulo");
        assertNotNull(usuarioSalvo.getId(), "O ID do usuário (gerado pelo MongoDB) não deve ser nulo");

        // =====================================================================
        // PASSO 2: Listar Usuários via HTTP GET no MongoDB (/usuario/usuarios)
        // =====================================================================
        ResponseEntity<Usuario[]> listaUsuarios = restTemplate.getForEntity(
                "/usuario/usuarios",
                Usuario[].class
        );

        assertEquals(HttpStatus.OK, listaUsuarios.getStatusCode(),
                "GET /usuario/usuarios deve retornar HTTP 200");
        assertNotNull(listaUsuarios.getBody(), "A lista de usuários não deve ser nula");
        assertTrue(
                Arrays.stream(listaUsuarios.getBody())
                        .anyMatch(u -> usuarioSalvo.getId().equals(u.getId())),
                "O usuário recém-criado no MongoDB deve aparecer na listagem GET /usuario/usuarios"
        );

        // =====================================================================
        // PASSO 3: Cadastrar Categoria via HTTP POST no MySQL (/memelandia/categorias)
        // =====================================================================
        CategoriaMeme novaCategoria = new CategoriaMeme();
        novaCategoria.setNome("Categoria HTTP " + sufixoUnico);
        novaCategoria.setDescricao("Categoria criada no teste HTTP E2E");
        novaCategoria.setDataCadastro(Date.valueOf("2026-02-10"));
        // Vincula a categoria ao ID (String) do usuário salvo anteriormente no MongoDB
        novaCategoria.setUsuario(usuarioSalvo.getId());

        ResponseEntity<CategoriaMeme> respostaCategoria = restTemplate.postForEntity(
                "/memelandia/categorias",
                novaCategoria,
                CategoriaMeme.class
        );

        // Valida HTTP 200, geração do ID Long pelo MySQL e preservação do vínculo com o usuário
        assertEquals(HttpStatus.OK, respostaCategoria.getStatusCode(),
                "POST /memelandia/categorias deve retornar HTTP 200");
        CategoriaMeme categoriaSalva = respostaCategoria.getBody();
        assertNotNull(categoriaSalva, "O corpo da resposta da categoria não deve ser nulo");
        assertNotNull(categoriaSalva.getId(), "O ID da categoria (gerado pelo MySQL) não deve ser nulo");
        assertEquals(usuarioSalvo.getId(), categoriaSalva.getUsuario(),
                "O usuario_id salvo na categoria deve corresponder ao usuário criado no MongoDB");

        // =====================================================================
        // PASSO 4: Listar Categorias via HTTP GET no MySQL (/memelandia/categorias)
        // =====================================================================
        ResponseEntity<CategoriaMeme[]> listaCategorias = restTemplate.getForEntity(
                "/memelandia/categorias",
                CategoriaMeme[].class
        );

        assertEquals(HttpStatus.OK, listaCategorias.getStatusCode(),
                "GET /memelandia/categorias deve retornar HTTP 200");
        assertNotNull(listaCategorias.getBody(), "A lista de categorias não deve ser nula");
        assertTrue(
                Arrays.stream(listaCategorias.getBody())
                        .anyMatch(c -> categoriaSalva.getId().equals(c.getId())),
                "A categoria recém-criada no MySQL deve aparecer na listagem GET /memelandia/categorias"
        );

        // =====================================================================
        // PASSO 5: Cadastrar Meme via HTTP POST no MySQL (/meme/memes)
        // =====================================================================
        Meme novoMeme = new Meme();
        novoMeme.setNome("Meme HTTP " + sufixoUnico);
        novoMeme.setDescricao("Meme criado no teste HTTP E2E");
        novoMeme.setDataCadastro(Date.valueOf("2026-02-10"));
        // Associa o meme à categoria (MySQL) e ao usuário (MongoDB) criados nos passos anteriores
        novoMeme.setCategoriaMeme(categoriaSalva);
        novoMeme.setUsuario(usuarioSalvo.getId());

        ResponseEntity<Meme> respostaMeme = restTemplate.postForEntity(
                "/meme/memes",
                novoMeme,
                Meme.class
        );

        // Valida HTTP 200, ID gerado no MySQL e integridade das referências
        assertEquals(HttpStatus.OK, respostaMeme.getStatusCode(),
                "POST /meme/memes deve retornar HTTP 200");
        Meme memeSalvo = respostaMeme.getBody();
        assertNotNull(memeSalvo, "O corpo da resposta do meme não deve ser nulo");
        assertNotNull(memeSalvo.getId(), "O ID do meme (gerado pelo MySQL) não deve ser nulo");
        assertEquals(usuarioSalvo.getId(), memeSalvo.getUsuario(),
                "O usuario_id do meme deve corresponder ao usuário criado no MongoDB");
        assertNotNull(memeSalvo.getCategoriaMeme(), "A categoria associada ao meme não deve ser nula");
        assertEquals(categoriaSalva.getId(), memeSalvo.getCategoriaMeme().getId(),
                "A categoria do meme deve corresponder à categoria criada no MySQL");

        // =====================================================================
        // PASSO 6: Listar Memes via HTTP GET no MySQL (/meme/memes)
        // =====================================================================
        ResponseEntity<Meme[]> listaMemes = restTemplate.getForEntity(
                "/meme/memes",
                Meme[].class
        );

        assertEquals(HttpStatus.OK, listaMemes.getStatusCode(),
                "GET /meme/memes deve retornar HTTP 200");
        assertNotNull(listaMemes.getBody(), "A lista de memes não deve ser nula");
        assertTrue(
                Arrays.stream(listaMemes.getBody())
                        .anyMatch(m -> memeSalvo.getId().equals(m.getId())),
                "O meme recém-criado no MySQL deve aparecer na listagem GET /meme/memes"
        );
    }
}
