package com.extres;

public class ElementoDuplamenteEncadeado {

    private Integer valor;

    private ElementoDuplamenteEncadeado proximo;

    private ElementoDuplamenteEncadeado anterior;

    public ElementoDuplamenteEncadeado(Integer valor) {
        this.valor = valor;
        this.proximo = null;
        this.anterior = null;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public ElementoDuplamenteEncadeado getProximo() {
        return proximo;
    }

    public void setProximo(ElementoDuplamenteEncadeado proximo) {
        this.proximo = proximo;
    }

    public ElementoDuplamenteEncadeado getAnterior() {
        return anterior;
    }

    public void setAnterior(ElementoDuplamenteEncadeado anterior) {
        this.anterior = anterior;
    }

}
