package br.com.ebac.memelandia.meme;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.ebac.memelandia.controllers.ControllerMeme;
import br.com.ebac.memelandia.entities.db1.CategoriaMeme;
import br.com.ebac.memelandia.entities.db1.Meme;
import br.com.ebac.memelandia.services.ServicoMeme;

/**
 * [Mocking Test] Testes unitários da camada Web (endpoints REST) do serviço de Memes (MySQL / db1).
 *
 * Controller testado: {@link ControllerMeme}
 * Rota base: /meme
 *
 * Utiliza @WebMvcTest para carregar apenas os componentes web deste controller
 * e @MockBean para simular o {@link ServicoMeme}.
 */
@WebMvcTest(ControllerMeme.class)
@DisplayName("[Mocking Test] Serviço de Memes (MySQL) - Endpoints (/meme)")
class ControllerMemeMockTest {

    // Executa chamadas HTTP simuladas contra o ControllerMeme
    @Autowired
    private MockMvc mockMvc;

    // Serializa o objeto Meme para JSON nas requisições POST
    @Autowired
    private ObjectMapper objectMapper;

    // Mock da camada de serviço de memes
    @MockBean
    private ServicoMeme servicoMeme;

    /**
     * Método auxiliar para construir um Meme completo (com categoria associada e ID String do usuário no MongoDB).
     */
    private Meme criarMemeExemplo(Long id, String nome, String descricao) {
        String usuarioId = "65a1b2c3d4e5f60001000001";

        CategoriaMeme categoria = new CategoriaMeme();
        categoria.setId(1L);
        categoria.setNome("Programação");
        categoria.setDescricao("Memes sobre desenvolvimento");
        categoria.setDataCadastro(Date.valueOf("2026-01-20"));
        categoria.setUsuario(usuarioId);

        Meme meme = new Meme();
        meme.setId(id);
        meme.setNome(nome);
        meme.setDescricao(descricao);
        meme.setDataCadastro(Date.valueOf("2026-02-01"));
        meme.setCategoriaMeme(categoria);
        meme.setUsuario(usuarioId);
        return meme;
    }

    /**
     * Endpoint: GET /meme/memes
     * Objetivo: Verificar se a listagem de memes retorna HTTP 200 e os memes cadastrados.
     */
    @Test
    @DisplayName("[Mocking Test] GET /meme/memes - Deve retornar lista de memes com status 200")
    void deveListarTodosMemesComSucesso() throws Exception {
        // Arrange: prepara os memes simulados retornados pelo serviço
        Meme meme1 = criarMemeExemplo(1L, "Funciona na minha máquina", "Clássico de deploy");
        Meme meme2 = criarMemeExemplo(2L, "NullPointerException", "Esqueceu de inicializar");
        when(servicoMeme.listaTodosMemes()).thenReturn(List.of(meme1, meme2));

        // Act & Assert: executa GET /meme/memes e valida status HTTP e estrutura JSON
        mockMvc.perform(get("/meme/memes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("Funciona na minha máquina")))
                .andExpect(jsonPath("$[0].descricao", is("Clássico de deploy")))
                .andExpect(jsonPath("$[0].categoriaMeme.nome", is("Programação")))
                .andExpect(jsonPath("$[0].usuario", is("65a1b2c3d4e5f60001000001")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nome", is("NullPointerException")));

        // Verifica que o serviço foi invocado exatamente uma vez
        verify(servicoMeme, times(1)).listaTodosMemes();
    }

    /**
     * Endpoint: GET /meme/memes
     * Objetivo: Verificar se o endpoint retorna lista vazia com status 200 quando não há memes.
     */
    @Test
    @DisplayName("[Mocking Test] GET /meme/memes - Deve retornar lista vazia quando não houver memes")
    void deveRetornarListaVaziaQuandoNaoExistiremMemes() throws Exception {
        // Arrange: simula retorno vazio do serviço
        when(servicoMeme.listaTodosMemes()).thenReturn(Collections.emptyList());

        // Act & Assert: confirma status 200 e array vazio
        mockMvc.perform(get("/meme/memes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(servicoMeme, times(1)).listaTodosMemes();
    }

    /**
     * Endpoint: POST /meme/memes
     * Objetivo: Verificar se o cadastro de um novo meme via JSON retorna HTTP 200 e o meme salvo.
     */
    @Test
    @DisplayName("[Mocking Test] POST /meme/memes - Deve cadastrar um novo meme com status 200")
    void deveCriarNovoMemeComSucesso() throws Exception {
        // Arrange: payload de entrada e objeto persistido simulado
        Meme entrada = criarMemeExemplo(null, "Café e Código", "Combustível de dev");
        Meme salvo = criarMemeExemplo(7L, "Café e Código", "Combustível de dev");
        when(servicoMeme.novoMeme(any(Meme.class))).thenReturn(salvo);

        // Act & Assert: envia POST /meme/memes com JSON e valida o retorno
        mockMvc.perform(post("/meme/memes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(7)))
                .andExpect(jsonPath("$.nome", is("Café e Código")))
                .andExpect(jsonPath("$.descricao", is("Combustível de dev")))
                .andExpect(jsonPath("$.dataCadastro", is("2026-02-01")))
                .andExpect(jsonPath("$.categoriaMeme.id", is(1)))
                .andExpect(jsonPath("$.usuario", is("65a1b2c3d4e5f60001000001")));

        // Confirma que o serviço de criação foi chamado uma vez
        verify(servicoMeme, times(1)).novoMeme(any(Meme.class));
    }
}
