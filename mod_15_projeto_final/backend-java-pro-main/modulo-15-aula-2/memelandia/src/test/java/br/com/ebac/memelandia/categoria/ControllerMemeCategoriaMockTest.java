package br.com.ebac.memelandia.categoria;

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

import br.com.ebac.memelandia.controllers.ControllerMemeCategoria;
import br.com.ebac.memelandia.entities.db1.CategoriaMeme;
import br.com.ebac.memelandia.services.ServicoMemeCategoria;

/**
 * [Mocking Test] Testes unitários da camada Web (endpoints REST) do serviço de Categorias de Meme (MySQL / db1).
 *
 * Controller testado: {@link ControllerMemeCategoria}
 * Rota base: /memelandia
 *
 * Utiliza @WebMvcTest para isolar o controller e @MockBean para simular
 * o {@link ServicoMemeCategoria} sem depender do banco de dados MySQL.
 */
@WebMvcTest(ControllerMemeCategoria.class)
@DisplayName("[Mocking Test] Serviço de Categorias (MySQL) - Endpoints (/memelandia)")
class ControllerMemeCategoriaMockTest {

    // Cliente de testes HTTP do Spring MVC
    @Autowired
    private MockMvc mockMvc;

    // Conversor JSON utilizado para serializar o corpo das requisições POST
    @Autowired
    private ObjectMapper objectMapper;

    // Mock da camada de serviço de categorias
    @MockBean
    private ServicoMemeCategoria servicoMemeCategoria;

    /**
     * Método auxiliar para montar uma CategoriaMeme com a referência String do usuário (ID do MongoDB).
     */
    private CategoriaMeme criarCategoriaExemplo(Long id, String nome, String descricao) {
        CategoriaMeme categoria = new CategoriaMeme();
        categoria.setId(id);
        categoria.setNome(nome);
        categoria.setDescricao(descricao);
        categoria.setDataCadastro(Date.valueOf("2026-01-20"));
        categoria.setUsuario("65a1b2c3d4e5f60001000001");
        return categoria;
    }

    /**
     * Endpoint: GET /memelandia/categorias
     * Objetivo: Verificar se a listagem de categorias retorna HTTP 200 e os dados esperados.
     */
    @Test
    @DisplayName("[Mocking Test] GET /memelandia/categorias - Deve retornar lista de categorias com status 200")
    void deveListarTodasCategoriasComSucesso() throws Exception {
        // Arrange: define duas categorias simuladas que serão retornadas pelo serviço
        CategoriaMeme cat1 = criarCategoriaExemplo(1L, "Programação", "Memes sobre desenvolvimento");
        CategoriaMeme cat2 = criarCategoriaExemplo(2L, "Java", "Memes sobre JVM e Spring");
        when(servicoMemeCategoria.listaTodasCategorias()).thenReturn(List.of(cat1, cat2));

        // Act & Assert: realiza GET /memelandia/categorias e valida status e payload JSON
        mockMvc.perform(get("/memelandia/categorias")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("Programação")))
                .andExpect(jsonPath("$[0].descricao", is("Memes sobre desenvolvimento")))
                .andExpect(jsonPath("$[0].usuario", is("65a1b2c3d4e5f60001000001")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nome", is("Java")));

        // Confirma que o serviço foi consultado uma única vez
        verify(servicoMemeCategoria, times(1)).listaTodasCategorias();
    }

    /**
     * Endpoint: GET /memelandia/categorias
     * Objetivo: Verificar se o endpoint retorna lista vazia com status 200 quando não há categorias.
     */
    @Test
    @DisplayName("[Mocking Test] GET /memelandia/categorias - Deve retornar lista vazia quando não houver categorias")
    void deveRetornarListaVaziaQuandoNaoExistiremCategorias() throws Exception {
        // Arrange: simula ausência de registros no banco
        when(servicoMemeCategoria.listaTodasCategorias()).thenReturn(Collections.emptyList());

        // Act & Assert: verifica HTTP 200 e array JSON vazio
        mockMvc.perform(get("/memelandia/categorias")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(servicoMemeCategoria, times(1)).listaTodasCategorias();
    }

    /**
     * Endpoint: POST /memelandia/categorias
     * Objetivo: Verificar se a criação de uma nova categoria via JSON retorna HTTP 200 e a categoria salva.
     */
    @Test
    @DisplayName("[Mocking Test] POST /memelandia/categorias - Deve cadastrar uma nova categoria com status 200")
    void deveCriarNovaCategoriaComSucesso() throws Exception {
        // Arrange: cria objeto de entrada e o resultado esperado após persistência
        CategoriaMeme entrada = criarCategoriaExemplo(null, "Banco de Dados", "Memes sobre SQL");
        CategoriaMeme salva = criarCategoriaExemplo(5L, "Banco de Dados", "Memes sobre SQL");
        when(servicoMemeCategoria.novaCategoriaMeme(any(CategoriaMeme.class))).thenReturn(salva);

        // Act & Assert: envia POST /memelandia/categorias e valida os campos retornados
        mockMvc.perform(post("/memelandia/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.nome", is("Banco de Dados")))
                .andExpect(jsonPath("$.descricao", is("Memes sobre SQL")))
                .andExpect(jsonPath("$.dataCadastro", is("2026-01-20")))
                .andExpect(jsonPath("$.usuario", is("65a1b2c3d4e5f60001000001")));

        // Garante que o método de persistência do serviço foi chamado
        verify(servicoMemeCategoria, times(1)).novaCategoriaMeme(any(CategoriaMeme.class));
    }
}
