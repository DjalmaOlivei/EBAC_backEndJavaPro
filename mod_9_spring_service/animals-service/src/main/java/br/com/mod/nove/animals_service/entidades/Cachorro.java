package br.com.mod.nove.animals_service.entidades;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cachorro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    public Integer getId() {
        return id;
    }

    @Column (name = "nome_provisorio", nullable = false)
    private String nomeProvisorio;

    @Column (name = "idade_estimada", nullable = false)
    private Integer idadeEstimada;

    @Column (name = "raca", nullable = false)
    private String raca;

    @Column (name = "data_entrada", nullable = false)
    private Date dataEntrada;

    @Column (name = "data_saida", nullable = true)
    private Date dataSaida;

    @Column (name = "condicao_chegada", nullable = false)
    private String condicaoChegada;

    @Column (name = "nome_recebedor", nullable = true)
    private String nomeRecebedor;

    @Column (name = "data_obito", nullable = true)
    private Date dataObito;

    @Column (name = "porte", nullable = false)
    private String porte;

    @Column (name = "resgatador", nullable = false)
    private String resgatador;

    public String getResgatador() {
        return resgatador;
    }

    public void setResgatador(String resgatador) {
        this.resgatador = resgatador;
    }

    public String getNomeProvisorio() {
        return nomeProvisorio;
    }

    public void setNomeProvisorio(String nomeProvisorio) {
        this.nomeProvisorio = nomeProvisorio;
    }

    public Integer getIdadeEstimada() {
        return idadeEstimada;
    }

    public void setIdadeEstimada(Integer idadeEstimada) {
        this.idadeEstimada = idadeEstimada;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getCondicaoChegada() {
        return condicaoChegada;
    }

    public void setCondicaoChegada(String condicaoChegada) {
        this.condicaoChegada = condicaoChegada;
    }

    public String getNomeRecebedor() {
        return nomeRecebedor;
    }

    public void setNomeRecebedor(String nomeRecebedor) {
        this.nomeRecebedor = nomeRecebedor;
    }

    public Date getDataObito() {
        return dataObito;
    }

    public void setDataObito(Date dataObito) {
        this.dataObito = dataObito;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    
}
