package com.extres;


public class Main {
    public static void main(String[] args) {

        ListaDuplamenteEncadeada lista = new ListaDuplamenteEncadeada();

        // Medindo o tempo de execução para adicionar elementos à lista via push
        long inicioPush = System.nanoTime();

        // Adicionando elementos à lista
        lista.push(10);
        lista.push(20);
        lista.push(30);
        lista.push(40);
        lista.push(50);
        lista.push(60);
        lista.push(70);
        lista.push(80);
        lista.push(90);
        lista.push(100);
        
        // Calculando o tempo decorrido em milissegundos
        long fimPush = System.nanoTime();
        double tempoDecorridoMillisPush = (fimPush - inicioPush) / 1_000_000.0;
        System.out.println("Tempo decorrido para adicionar elementos à lista via push: " + tempoDecorridoMillisPush + " ms");

        System.out.println("Lista após adicionar elementos via push:"+ lista.size());

        // Medindo o tempo de execução para remover elementos da lista via pop
        long inicioPop = System.nanoTime();

        // Removendo elementos da lista
        lista.pop();// 1
        lista.pop();// 2 
        lista.pop();// 3
        lista.pop();// 4
        lista.pop();// 5
        lista.pop();// 6
        lista.pop();// 7
        lista.pop();// 8
        lista.pop();// 9
        lista.pop();// 10

        // Calculando o tempo decorrido em milissegundos
        long fimPop = System.nanoTime();
        double tempoDecorridoMillisPop = (fimPop - inicioPop) / 1_000_000.0;
        System.out.println("Tempo decorrido para remover elementos da lista via pop: " + tempoDecorridoMillisPop + " ms");
    
        // Medindo o tempo de execução para adicionar elementos à lista via insert
        long inicioInsert = System.nanoTime();

        // Adicionando elementos à lista
        lista.insert(10);
        lista.insert(20);
        lista.insert(30);
        lista.insert(40);
        lista.insert(50);
        lista.insert(60);
        lista.insert(70);
        lista.insert(80);
        lista.insert(90);
        lista.insert(100);

        // Calculando o tempo decorrido em milissegundos
        long fimInsert = System.nanoTime();
        double tempoDecorridoMillisInsert = (fimInsert - inicioInsert) / 1_000_000.0;
        System.out.println("Tempo decorrido para adicionar elementos à lista via insert: " + tempoDecorridoMillisInsert + " ms");

        System.out.println("Lista após adicionar elementos via insert:"+ lista.size());

        int tamanho = lista.size() - 1;
        // Medindo o tempo de execução para encontrar elementos da lista via elementAt
        long inicioElementAt = System.nanoTime();

        lista.elementAt(tamanho);// 1
        lista.elementAt(tamanho);// 2 
        lista.elementAt(tamanho);// 3
        lista.elementAt(tamanho);// 4
        lista.elementAt(tamanho);// 5
        lista.elementAt(tamanho);// 6
        lista.elementAt(tamanho);// 7
        lista.elementAt(tamanho);// 8
        lista.elementAt(tamanho);// 9
        lista.elementAt(tamanho);// 10

        // Calculando o tempo decorrido em milissegundos
        long fimElementAt = System.nanoTime();
        double tempoDecorridoMillisElementAt = (fimElementAt - inicioElementAt) / 1_000_000.0;
        System.out.println("Tempo decorrido para encontrar elementos na ultima posição da lista via elementAt: " + tempoDecorridoMillisElementAt + " ms");
        

        // Medindo o tempo de execução para remover elementos da lista via remove
        long inicioRemove = System.nanoTime();

        // Removendo elementos da lista
        lista.remove();// 1
        lista.remove();// 2 
        lista.remove();// 3
        lista.remove();// 4
        lista.remove();// 5
        lista.remove();// 6  
        lista.remove();// 7
        lista.remove();// 8
        lista.remove();// 9
        lista.remove();// 10

        // Calculando o tempo decorrido em milissegundos
        long fimRemove = System.nanoTime();
        double tempoDecorridoMillisRemove = (fimRemove - inicioRemove) / 1_000_000.0;
        System.out.println("Tempo decorrido para remover elementos da lista via remove: " + tempoDecorridoMillisRemove + " ms");

    }
}