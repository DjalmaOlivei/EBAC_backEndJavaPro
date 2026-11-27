package br.com.ebac.memelandia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ebac.memelandia.entities.db1.CategoriaMeme;
import br.com.ebac.memelandia.repositories.db1.RepositorioCategoriaMeme;

@Component
public class ServicoMemeCategoria {
    

    @Autowired
    private RepositorioCategoriaMeme repositorioCategoriaMeme;

    public CategoriaMeme novaCategoriaMeme(CategoriaMeme categoriaMeme) {
        return repositorioCategoriaMeme.save(categoriaMeme);
    }

    public List<CategoriaMeme> listaTodasCategorias() {
        return repositorioCategoriaMeme.findAll();
    }
}
