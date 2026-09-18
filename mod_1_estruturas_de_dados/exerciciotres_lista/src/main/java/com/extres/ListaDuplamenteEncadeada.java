package com.extres;

public class ListaDuplamenteEncadeada {

    private ElementoDuplamenteEncadeado primeiro;

    private ElementoDuplamenteEncadeado ultimo;

    public ListaDuplamenteEncadeada() {
        this.primeiro = null;
        this.ultimo = null;
    }

    /**
     * Adiciona um elemento no final da lista.
     * @param valor
     */
    public void push(Integer valor) {
        ElementoDuplamenteEncadeado novoElemento = new ElementoDuplamenteEncadeado(valor);
        if (primeiro == null) {
            primeiro = novoElemento;
            ultimo = novoElemento;
        } else {
            ultimo.setProximo(novoElemento);
            novoElemento.setAnterior(ultimo);
            ultimo = novoElemento;
        }
    }

    /**
     * Remove e retorna o elemento do final da lista.
     * @return
     */
    public Integer pop() {
        if (ultimo == null) {
            System.out.println("Lista vazia.");
            return null;
        }
        Integer valorRemovido = ultimo.getValor();
        if (ultimo == primeiro) {
            primeiro = null;
            ultimo = null;
        } else {
            ultimo = ultimo.getAnterior();
            ultimo.setProximo(null);
        }
        return valorRemovido;
    }

    /**
     * Adiciona um elemento no início da lista.
     * @param valor
     */
    public void insert(Integer valor) {
        ElementoDuplamenteEncadeado novoElemento = new ElementoDuplamenteEncadeado(valor);
        if (primeiro == null) {
            primeiro = novoElemento;
            ultimo = novoElemento;
        } else {
            novoElemento.setProximo(primeiro);
            primeiro.setAnterior(novoElemento);
            primeiro = novoElemento;
        }
    }

    /**
     * Remove e retorna o elemento do início da lista.
     * @return
     */
    public Integer remove() {
        if (primeiro == null) {
            System.out.println("Lista vazia.");
            return null;
        }
        Integer valorRemovido = primeiro.getValor();
        if (primeiro == ultimo) {
            primeiro = null;
            ultimo = null;
        } else {
            primeiro = primeiro.getProximo();
            primeiro.setAnterior(null);
        }
        return valorRemovido;
    }

    /**
     * Retorna o tamanho da lista.
     * @return
     */
    public Integer size() {
        int tamanho = 0;
        ElementoDuplamenteEncadeado atual = primeiro;
        while (atual != null) {
            tamanho++;
            atual = atual.getProximo();
        }
        return tamanho;
    }

    /**
     * Retorna o elemento na posição especificada.
     * @param index
     * @return
     */
    public Integer elementAt(int index) {
        if (primeiro == null) {
            System.out.println("Lista vazia.");
            return null;
        }
        ElementoDuplamenteEncadeado atual = primeiro;
        int contador = 0;
        while (atual != null) {
            if (contador == index) {
                return atual.getValor();
            }
            contador++;
            atual = atual.getProximo();
        }
        System.out.println("Índice inválido.");
        return null;
    }

    /**
     * Imprime os elementos da lista.
     */
    public void printList() {
        if (primeiro == null) {
            System.out.println("Lista vazia.");
            return;
        }
        ElementoDuplamenteEncadeado atual = primeiro;
        System.out.print("Lista: ");
        while (atual != null) {
            System.out.print(atual.getValor() + " ");
            atual = atual.getProximo();
        }
        System.out.println();
    }

}
