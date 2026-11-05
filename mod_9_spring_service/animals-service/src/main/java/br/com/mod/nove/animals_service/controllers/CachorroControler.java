package br.com.mod.nove.animals_service.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mod.nove.animals_service.entidades.Cachorro;
import br.com.mod.nove.animals_service.repositorios.CachorroRepository;



@RestController 
@RequestMapping ("/cachorros")
public class CachorroControler {

    private CachorroRepository repository;
    
    public CachorroControler(CachorroRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/listar")
    private List<Cachorro> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public Cachorro postMethodName(@RequestBody Cachorro animal) {
        //TODO: process POST request
        
        return repository.save(animal);
    }

    @GetMapping("/para-adocao")
    public List<Cachorro> getMethodName() {
        return repository.findAdopted();
    }

    @GetMapping("/adotados")
    public List<Cachorro> getNotAdopted() {
        return repository.findNotAdopted();
    }

    @GetMapping("/ranking-resgatadores")
    public List<Object[]> getRankingResgatadores(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return repository.rankingResgatadores(Date.valueOf(dataInicio), Date.valueOf(dataFim));
    } 
    


}
