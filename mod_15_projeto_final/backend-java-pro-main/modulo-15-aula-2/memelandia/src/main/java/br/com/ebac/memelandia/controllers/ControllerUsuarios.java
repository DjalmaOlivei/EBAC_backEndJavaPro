package br.com.ebac.memelandia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ebac.memelandia.entities.db2.Usuario;
import br.com.ebac.memelandia.services.ServicoUsuario;

@RestController 
@RequestMapping ("/usuario")
public class ControllerUsuarios {

    @Autowired
    private ServicoUsuario servicoUsuario;

    @GetMapping("/usuarios")
    public List<Usuario> buscaUsuarios() {
        return servicoUsuario.listaTodosUsuarios();
    }

    @PostMapping("/usuarios")
    public Usuario novoUsuario(@RequestBody Usuario usuario) {
        return servicoUsuario.novoUsuario(usuario);
    }

}
