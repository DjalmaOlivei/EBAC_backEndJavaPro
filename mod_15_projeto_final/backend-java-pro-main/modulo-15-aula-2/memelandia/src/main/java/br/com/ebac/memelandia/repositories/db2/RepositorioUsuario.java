package br.com.ebac.memelandia.repositories.db2;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.ebac.memelandia.entities.db2.Usuario;

public interface RepositorioUsuario extends MongoRepository<Usuario, String> {
}
