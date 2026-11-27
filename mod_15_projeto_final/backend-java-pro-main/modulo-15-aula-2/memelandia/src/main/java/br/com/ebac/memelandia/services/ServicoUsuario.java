package br.com.ebac.memelandia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ebac.memelandia.entities.db2.Usuario;
import br.com.ebac.memelandia.repositories.db2.RepositorioUsuario;

@Component 
public class ServicoUsuario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    public Usuario novoUsuario(Usuario usuario) {
        return repositorioUsuario.save(usuario);
    }

    public List<Usuario> listaTodosUsuarios() {
        return repositorioUsuario.findAll();
    }

}
