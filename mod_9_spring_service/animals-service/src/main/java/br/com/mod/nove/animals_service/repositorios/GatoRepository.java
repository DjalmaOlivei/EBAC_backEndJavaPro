package br.com.mod.nove.animals_service.repositorios;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mod.nove.animals_service.entidades.Gato;

public interface GatoRepository extends JpaRepository<Gato, Integer>{
    
@Query ("SELECT a FROM Gato a WHERE a.dataSaida IS NULL ORDER BY a.dataEntrada ASC")
    List<Gato> findAdopted();

@Query ("SELECT a FROM Gato a WHERE a.dataSaida IS NOT NULL ORDER BY a.dataEntrada ASC")
    List<Gato> findNotAdopted();

@Query("SELECT c.resgatador, COUNT(c) AS gatos_resgatados FROM Gato c "
            + "WHERE c.dataEntrada BETWEEN :dataInicio AND :dataFim "
            + "GROUP BY c.resgatador ORDER BY gatos_resgatados DESC")
    List<Object[]> rankingResgatadores(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);

}
