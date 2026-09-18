package com.ex.um;

public class Pilha {

    private Elemento topo;

    /**
     * Adiciona um elemento no topo da pilha.
     * @param valor
     */
    public void push(Integer valor) {
        Elemento novoElemento = new Elemento(valor);
        if (topo != null) {
            novoElemento.setAnterior(topo);
        }
        topo = novoElemento;
    }

    /**
     * Remove e retorna o elemento do topo da pilha.
     * @return
     */
    public Elemento pop() {
        if (topo == null) {
            return null; // Pilha vazia
        }
        Elemento elemento = topo;
        topo = topo.getAnterior();
        return elemento;
    }

    public Integer tamanho() {
        int tamanho = 0;
        Elemento atual = topo;
        while (atual != null) {
            tamanho++;
            atual = atual.getAnterior();
        }
        return tamanho;
    }

    /**
     * Verifica se a pilha está vazia.
     * @return
     */
    public boolean estaVazia() {
        return topo == null;
    }

}
