package br.com.ebac.memelandia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ebac.memelandia.entities.db1.Meme;
import br.com.ebac.memelandia.services.ServicoMeme;

@RestController 
@RequestMapping ("/meme")
public class ControllerMeme {

    @Autowired 
    ServicoMeme servicoMeme;

    @GetMapping("/memes")
    public List<Meme> buscaMemes() {
        return servicoMeme.listaTodosMemes();
    }

    @PostMapping("/memes")
    public Meme novoMeme(@RequestBody Meme meme) {
        return servicoMeme.novoMeme(meme);
    }

}
