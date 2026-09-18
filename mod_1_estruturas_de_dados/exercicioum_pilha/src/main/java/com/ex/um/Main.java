package com.ex.um;


public class Main {
    public static void main(String[] args) {
        
        Pilha pilha = new Pilha();

        // Medindo o tempo de execução para adicionar elementos na pilha
        long inicioPush = System.nanoTime();

        // Adicionando elementos na pilha
        pilha.push(10);
        pilha.push(20);
        pilha.push(30);
        pilha.push(40);
        pilha.push(50);
        pilha.push(60);
        pilha.push(70);
        pilha.push(80);
        pilha.push(90);
        pilha.push(100);


        // Calculando o tempo decorrido em milissegundos
        long fimPush = System.nanoTime();
        double tempoDecorridoMillis = (fimPush - inicioPush) / 1_000_000.0;
        System.out.println("Tempo decorrido para adicionar elementos na pilha: " + tempoDecorridoMillis + " ms");

        System.out.print("Tamanho final da pilha : "+pilha.tamanho()+"\n");

        // Medindo o tempo de execução para remover elementos da pilha
        long inicioPop = System.nanoTime();

        // Removendo elementos da pilha
        while (!pilha.estaVazia()) {
            Elemento elemento = pilha.pop();
            //System.out.println("Elemento removido: " + elemento.getValor());
        }

        // Calculando o tempo decorrido em milissegundos
        long fimPop = System.nanoTime();
        double tempoDecorridoMillisPop = (fimPop - inicioPop) / 1_000_000.0;
        System.out.println("Tempo decorrido para remover elementos da pilha: " + tempoDecorridoMillisPop + " ms");

    }
}