package com.exdois;

public class Fila {

    private Elemento inicio;
    private Elemento fim;

    /**
     * Adiciona um elemento no final da fila.
     * @param valor
     */
    public void enqueue(Integer valor) {
        Elemento novoElemento = new Elemento(valor);
        if (fim != null) {
            fim.setProximo(novoElemento);
        }
        fim = novoElemento;
        if (inicio == null) {
            inicio = novoElemento;
        }
    }

    /**
     * Remove e retorna o elemento do início da fila.
     * @return
     */
    public Elemento dequeue() {
        if (inicio == null) {
            return null; // Fila vazia
        }
        Elemento elemento = inicio;
        inicio = inicio.getProximo();
        if (inicio == null) {
            fim = null; // Fila ficou vazia
        }
        return elemento;
    }

    /**
     * Retorna o elemento do início da fila sem removê-lo.
     * @return
     */
    public Integer rear() {
        if (fim == null) {
            return null; // Fila vazia
        }
        return fim.getValor();
    }

    /**
     * Retorna o elemento do início da fila sem removê-lo.
     * @return
     */
    public Integer front() {
        if (inicio == null) {
            return null; // Fila vazia
        }
        return inicio.getValor();
    }


    /**
    * Retorna o tamanho da fila.
    * @return
    */
    public Integer size() {
        int tamanho = 0;
        Elemento atual = inicio;
        while (atual != null) {
            tamanho++;
            atual = atual.getProximo();
        }
        return tamanho;
    }

    /**
     * Verifica se a fila está vazia.
     * @return
     */
    public boolean isEmpty() {
        return inicio == null;
    }

}
