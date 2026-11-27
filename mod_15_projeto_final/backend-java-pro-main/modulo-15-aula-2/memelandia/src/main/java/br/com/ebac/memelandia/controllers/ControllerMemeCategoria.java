package br.com.ebac.memelandia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ebac.memelandia.entities.db1.CategoriaMeme;
import br.com.ebac.memelandia.services.ServicoMemeCategoria;

@RestController
@RequestMapping("/memelandia")
public class ControllerMemeCategoria {

    @Autowired
    private ServicoMemeCategoria servicoMemelandia;

    @GetMapping("/categorias")
    public List<CategoriaMeme> buscaCategorias() {
        return servicoMemelandia.listaTodasCategorias();
    }

    @PostMapping("/categorias")
    public CategoriaMeme novaCategoria(@RequestBody CategoriaMeme categoriaMeme) {
        return servicoMemelandia.novaCategoriaMeme(categoriaMeme);
    }

}
