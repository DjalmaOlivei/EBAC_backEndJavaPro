package br.com.ebac.memelandia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ebac.memelandia.entities.db1.Meme;
import br.com.ebac.memelandia.repositories.db1.RepositorioMeme;

@Component 
public class ServicoMeme {

    @Autowired
    private RepositorioMeme repositorioMeme;

    public Meme novoMeme(Meme meme) {
        return repositorioMeme.save(meme);
    }

    public List<Meme> listaTodosMemes() {
        return repositorioMeme.findAll();
    }

}
