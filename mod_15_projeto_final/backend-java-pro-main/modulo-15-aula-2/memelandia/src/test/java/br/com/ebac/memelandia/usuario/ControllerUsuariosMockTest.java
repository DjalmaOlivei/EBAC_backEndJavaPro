package br.com.ebac.memelandia.usuario;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.ebac.memelandia.controllers.ControllerUsuarios;
import br.com.ebac.memelandia.entities.db2.Usuario;
import br.com.ebac.memelandia.services.ServicoUsuario;

/**
 * [Mocking Test] Testes unitários da camada Web (endpoints REST) do serviço de Usuários (MongoDB / db2).
 *
 * Controller testado: {@link ControllerUsuarios}
 * Rota base: /usuario
 *
 * Utiliza @WebMvcTest para carregar apenas o contexto MVC deste controller
 * e @MockBean para simular o comportamento de {@link ServicoUsuario} sem acessar o MongoDB.
 */
@WebMvcTest(ControllerUsuarios.class)
@DisplayName("[Mocking Test] Serviço de Usuários (MongoDB) - Endpoints (/usuario)")
class ControllerUsuariosMockTest {

    // Simula requisições HTTP para os endpoints do controller sem subir um servidor real
    @Autowired
    private MockMvc mockMvc;

    // Serializa objetos Java para JSON e vice-versa utilizando a configuração padrão do Spring
    @Autowired
    private ObjectMapper objectMapper;

    // Mock da camada de serviço injetada no ControllerUsuarios
    @MockBean
    private ServicoUsuario servicoUsuario;

    /**
     * Método auxiliar para construir um documento Usuario válido (ID String do MongoDB).
     */
    private Usuario criarUsuarioExemplo(String id, String nome, String email) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setDataCadastro(Date.from(LocalDate.of(2026, 1, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
        return usuario;
    }

    /**
     * Endpoint: GET /usuario/usuarios
     * Objetivo: Verificar se a listagem retorna HTTP 200 e o JSON com os usuários do MongoDB.
     */
    @Test
    @DisplayName("[Mocking Test] GET /usuario/usuarios - Deve retornar lista de usuários com status 200")
    void deveListarTodosUsuariosComSucesso() throws Exception {
        // Arrange: prepara a lista simulada que o serviço deve devolver
        Usuario usuario1 = criarUsuarioExemplo("65a1b2c3d4e5f60001000001", "Maria Silva", "maria@ebac.com.br");
        Usuario usuario2 = criarUsuarioExemplo("65a1b2c3d4e5f60001000002", "João Souza", "joao@ebac.com.br");
        when(servicoUsuario.listaTodosUsuarios()).thenReturn(List.of(usuario1, usuario2));

        // Act & Assert: executa GET /usuario/usuarios e valida status e campos do JSON
        mockMvc.perform(get("/usuario/usuarios")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("65a1b2c3d4e5f60001000001")))
                .andExpect(jsonPath("$[0].nome", is("Maria Silva")))
                .andExpect(jsonPath("$[0].email", is("maria@ebac.com.br")))
                .andExpect(jsonPath("$[1].id", is("65a1b2c3d4e5f60001000002")))
                .andExpect(jsonPath("$[1].nome", is("João Souza")))
                .andExpect(jsonPath("$[1].email", is("joao@ebac.com.br")));

        // Garante que o controller delegou a consulta para o serviço exatamente uma vez
        verify(servicoUsuario, times(1)).listaTodosUsuarios();
    }

    /**
     * Endpoint: GET /usuario/usuarios
     * Objetivo: Verificar se, quando não há usuários cadastrados, o endpoint retorna HTTP 200 e lista vazia.
     */
    @Test
    @DisplayName("[Mocking Test] GET /usuario/usuarios - Deve retornar lista vazia quando não houver usuários")
    void deveRetornarListaVaziaQuandoNaoExistiremUsuarios() throws Exception {
        // Arrange: simula retorno vazio do serviço
        when(servicoUsuario.listaTodosUsuarios()).thenReturn(Collections.emptyList());

        // Act & Assert: valida que o array JSON retornado tem tamanho 0
        mockMvc.perform(get("/usuario/usuarios")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(servicoUsuario, times(1)).listaTodosUsuarios();
    }

    /**
     * Endpoint: POST /usuario/usuarios
     * Objetivo: Verificar se o cadastro de um novo usuário via JSON retorna HTTP 200 e o usuário salvo com ID String.
     */
    @Test
    @DisplayName("[Mocking Test] POST /usuario/usuarios - Deve cadastrar um novo usuário com status 200")
    void deveCriarNovoUsuarioComSucesso() throws Exception {
        // Arrange: payload de entrada (sem ID) e documento salvo esperado (com ObjectId String gerado)
        Usuario entrada = criarUsuarioExemplo(null, "Ana Costa", "ana@ebac.com.br");
        Usuario salvo = criarUsuarioExemplo("65a1b2c3d4e5f60001000010", "Ana Costa", "ana@ebac.com.br");
        when(servicoUsuario.novoUsuario(any(Usuario.class))).thenReturn(salvo);

        // Act & Assert: envia POST com JSON no corpo e valida o usuário retornado na resposta
        mockMvc.perform(post("/usuario/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("65a1b2c3d4e5f60001000010")))
                .andExpect(jsonPath("$.nome", is("Ana Costa")))
                .andExpect(jsonPath("$.email", is("ana@ebac.com.br")))
                .andExpect(jsonPath("$.dataCadastro", notNullValue()));

        // Confirma que o serviço de criação foi acionado
        verify(servicoUsuario, times(1)).novoUsuario(any(Usuario.class));
    }
}
