package com.excinco;

public class Main {
    public static void main(String[] args) {
        TabelaHash tabelaHash = new TabelaHash();

        // Inserindo pares chave-valor
        tabelaHash.inserir(1, 10);
        tabelaHash.inserir(2, 20);
        tabelaHash.inserir(3, 30);

        // Buscando valores
        System.out.println("Valor da chave 1: " + tabelaHash.buscar(1)); 
        System.out.println("Valor da chave 2: " + tabelaHash.buscar(2)); 
        System.out.println("Valor da chave 3: " + tabelaHash.buscar(3)); 

        // Atualizando valor da chave existente
        tabelaHash.inserir(2, 25);
        System.out.println("Valor atualizado da chave 2: " + tabelaHash.buscar(2)); 

        // Removendo uma chave
        Integer valorRemovido = tabelaHash.remover(1);
        System.out.println("Valor removido da chave 1: " + valorRemovido); 
        System.out.println("Valor da chave 1 após remoção: " + tabelaHash.buscar(1)); 

        // Verificando se a tabela está cheia
        System.out.println("A tabela está cheia? " + tabelaHash.estaCheia()); 

        // Limpando a tabela
        tabelaHash.limpar();
        System.out.println("Tamanho da tabela após limpeza: " + tabelaHash.tamanho());
    }
}