package br.com.ebac.memelandia.repositories.db1;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ebac.memelandia.entities.db1.Meme;

public interface RepositorioMeme extends JpaRepository<Meme, Long> {
}
