package com.ex.um;

public class Elemento {

    private Integer valor;

    private Elemento anterior;

    public Elemento(Integer valor) {
        this.valor = valor;
    }

    /**
     * Verifica se o elemento tem um antecessor.
     * @return
     */
    public boolean temAnterior() {
        return anterior != null;
    }

    /**
     * Retorna o elemento antecessor.
     * @return
     */
    public Elemento getAnterior() {
        return anterior;
    }

    /**
     * Define o elemento antecessor.
     * @param anterior
     */
    public void setAnterior(Elemento anterior) {
        this.anterior = anterior;
    }

    /**
     * Retorna o valor do elemento.
     * @return
     */
    public Integer getValor() {
        return valor;
    }

}
