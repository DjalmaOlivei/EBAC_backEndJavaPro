package com.excinco;

public class TabelaHash {

    private static final int limiteItens = 10;
    /*
    explicação de porque número primo utilizadona função hash
    utilizado um número primo por sua caracteristica de ser divisível somente por ele,
    mesmo assim evitando uma repetição de calculo de modulo que resulte em 0.
    */
    private static final int numeroHash = 7;
    private final Elemento[] tabela = new Elemento[limiteItens];
    private int tamanho;

    /**
     * Insere uma chave e um valor inteiro.
     * Se a chave ja existir, apenas atualiza o valor.
     */
    public void inserir(Integer chave, Integer valor) {
        validarChave(chave);

        Elemento entrada = localizar(chave);
        if (entrada != null) {
            entrada.setValor(valor);
            return;
        }

        if (tamanho == limiteItens) {
            throw new IllegalArgumentException("A tabela hash ja atingiu o limite de 10 itens.");
        }

        int indice = funcaoHash(chave);
        Elemento novaEntrada = new Elemento(chave, valor);
        novaEntrada.setProxima(tabela[indice]);
        tabela[indice] = novaEntrada;
        tamanho++;
    }

    /**
     * Busca o valor associado a uma chave.
     * @return o valor encontrado ou {@code null} quando a chave nao existe
     */
    public Integer buscar(Integer chave) {
        validarChave(chave);
        Elemento entrada = localizar(chave);
        return entrada == null ? null : entrada.getValor();
    }

    /**
     * Remove uma chave da cadeia correspondente.
     * @return o valor removido ou {@code null} quando a chave nao existe
     */
    public Integer remover(Integer chave) {
        validarChave(chave);

        int indice = funcaoHash(chave);
        Elemento atual = tabela[indice];
        Elemento anterior = null;

        while (atual != null) {
            if (atual.getChave().equals(chave)) {
                if (anterior == null) {
                    tabela[indice] = atual.getProxima();
                } else {
                    anterior.setProxima(atual.getProxima());
                }
                tamanho--;
                return atual.getValor();
            }
            anterior = atual;
            atual = atual.getProxima();
        }
        return null;
    }

    /** 
     * Informa se uma chave esta armazenada na tabela. 
     * @param chave a chave a ser verificada
     * @return {@code true} se a chave estiver armazenada, {@code false} caso contrario
     */
    public boolean contem(Integer chave) {
        validarChave(chave);
        return localizar(chave) != null;
    }

    /**
     * Calcula o indice usando o resto da divisao por um numero primo.
     * {@code floorMod} mantem o indice positivo para chaves negativas.
     */
    public int funcaoHash(Integer chave) {
        validarChave(chave);
        return Math.floorMod(chave, numeroHash);
    }

    /** 
     * Retorna a quantidade atual de itens armazenados. 
     */
    public int tamanho() {
        return tamanho;
    }

    /** 
     * Informa se os 10 espacos disponiveis ja foram ocupados. 
     */
    public boolean estaCheia() {
        return tamanho == limiteItens;
    }

    /** 
     * Remove todos os pares e deixa a tabela vazia novamente. 
     */
    public void limpar() {
        for (int indice = 0; indice < tabela.length; indice++) {
            tabela[indice] = null;
        }
        tamanho = 0;
    }

    /** 
     * Procura uma entrada dentro da cadeia da chave. 
     * */
    private Elemento localizar(Integer chave) {
        int indice = funcaoHash(chave);
        Elemento atual = tabela[indice];

        while (atual != null) {
            if (atual.getChave().equals(chave)) {
                return atual;
            }
            atual = atual.getProxima();
        }
        return null;
    }

    /** 
     * Impede que {@code null} seja usado como chave. 
     */
    private void validarChave(Integer chave) {
        if (chave == null) {
            throw new IllegalArgumentException("A chave nao pode ser nula.");
        }
    }

}
