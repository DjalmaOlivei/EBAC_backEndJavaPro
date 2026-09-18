package com.excinco;

public class Elemento {
        private final Integer chave;
        private Integer valor;
        private Elemento proxima;

        public Elemento(Integer chave, Integer valor) {
            this.chave = chave;
            this.valor = valor;
        }

        public Integer getChave() {
            return chave;
        }

        public Integer getValor() {
            return valor;
        }

        public void setValor(Integer valor) {
            this.valor = valor;
        }

        public Elemento getProxima() {
            return proxima;
        }

        public void setProxima(Elemento proxima) {
            this.proxima = proxima;
        }
    }

