package com.exdois;

public class Main {
    public static void main(String[] args) {

        Fila fila = new Fila();

        // Medindo o tempo de execução para adicionar elementos na fila
        long inicioEnqueue = System.nanoTime();


        // Adicionando elementos na fila
        fila.enqueue(10);
        fila.enqueue(20);
        fila.enqueue(30);
        fila.enqueue(40);
        fila.enqueue(50);
        fila.enqueue(60);
        fila.enqueue(70);
        fila.enqueue(80);
        fila.enqueue(90);
        fila.enqueue(100);


        // Calculando o tempo decorrido em milissegundos
        long fimEnqueue = System.nanoTime();
        double tempoDecorridoMillisEnqueue = (fimEnqueue - inicioEnqueue) / 1_000_000.0;
        System.out.println("Tempo decorrido para adicionar elementos na fila: " + tempoDecorridoMillisEnqueue + " ms");

        System.out.println("Tamanho da fila após adição: " + fila.size());

        // Medindo o tempo de execução para resgatar último elemento da fila
        long inicioRear = System.nanoTime();

        fila.rear();// 1
        fila.rear();// 2
        fila.rear();// 3
        fila.rear();// 4
        fila.rear();// 5
        fila.rear();// 6
        fila.rear();// 7
        fila.rear();// 8
        fila.rear();// 9
        fila.rear();// 10

        // Calculando o tempo decorrido em milissegundos de resgatar o último elemento da fila
        long fimRear = System.nanoTime();
        double tempoDecorridoMillisRear = (fimRear - inicioRear) / 1_000_000.0;
        System.out.println("Tempo decorrido para resgatar o último elemento da fila: " + tempoDecorridoMillisRear + " ms");

        // Medindo o tempo de execução para resgatar o primeiro elementos da fila
        long inicioFront = System.nanoTime();

        fila.front();// 1
        fila.front();// 2
        fila.front();// 3
        fila.front();// 4
        fila.front();// 5
        fila.front();// 6
        fila.front();// 7
        fila.front();// 8
        fila.front();// 9
        fila.front();// 10

        // Calculando o tempo decorrido em milissegundos de resgatar o primeiro elemento da fila
        long fimFront = System.nanoTime();
        double tempoDecorridoMillisFront = (fimFront - inicioFront) / 1_000_000.0;
        System.out.println("Tempo decorrido para resgatar o primeiro elemento da fila: " + tempoDecorridoMillisFront + " ms");


        // Medindo o tempo de execução para remover elementos da fila
        long inicioDequeue = System.nanoTime();

        // Removendo elementos da fila
        fila.dequeue();// 1
        fila.dequeue();// 2
        fila.dequeue();// 3
        fila.dequeue();// 4
        fila.dequeue();// 5
        fila.dequeue();// 6
        fila.dequeue();// 7
        fila.dequeue();// 8
        fila.dequeue();// 9
        fila.dequeue();// 10

        // Calculando o tempo decorrido em milissegundos de remover elementos da fila
        long fimDequeue = System.nanoTime();
        double tempoDecorridoMillisDequeue = (fimDequeue - inicioDequeue) / 1_000_000.0;
        System.out.println("Tempo decorrido para remover elementos da fila: " + tempoDecorridoMillisDequeue + " ms");

        // Verificando novamente se a fila está vazia
        if (fila.isEmpty()) {
            System.out.println("A fila está vazia.");
        } else {
            System.out.println("A fila não está vazia.");
        }
    
    }
}