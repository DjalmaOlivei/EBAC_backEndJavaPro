package br.com.mod.nove.animals_service.repositorios;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mod.nove.animals_service.entidades.Cachorro;

public interface CachorroRepository extends JpaRepository<Cachorro, Integer>{

@Query ("SELECT a FROM Cachorro a WHERE a.dataSaida IS NULL ORDER BY a.dataEntrada ASC")
    List<Cachorro> findAdopted();

@Query ("SELECT a FROM Cachorro a WHERE a.dataSaida IS NOT NULL ORDER BY a.dataEntrada ASC")
    List<Cachorro> findNotAdopted();

    @Query("SELECT c.resgatador, COUNT(c) AS cachorros_resgatados FROM Cachorro c "
            + "WHERE c.dataEntrada BETWEEN :dataInicio AND :dataFim "
            + "GROUP BY c.resgatador ORDER BY cachorros_resgatados DESC")
    List<Object[]> rankingResgatadores(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);

}
