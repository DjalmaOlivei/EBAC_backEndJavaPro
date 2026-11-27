package br.com.mod.nove.animals_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.mod.nove.animals_service.repositorios.GatoRepository;

@SpringBootTest
class AnimalsServiceApplicationTests {

	@Autowired
	private GatoRepository gatoRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void catRescuerRankingUsesCatRecords() {
		var ranking = gatoRepository.rankingResgatadores(
				Date.valueOf("2026-08-20"),
				Date.valueOf("2026-08-31"));

		assertEquals(1, ranking.size());
		assertEquals("Isabela", ranking.get(0)[0]);
		assertEquals(1L, ranking.get(0)[1]);
	}

}
