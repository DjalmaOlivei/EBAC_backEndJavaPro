public class fatora {

    private long resultado;
    private int numero;

    private fatora(int numero) {
        this.numero = numero;
        this.resultado = 1;
    }

    /**
     * Faz a fatoração de um número natural!!! 
     * @param numero
     * @return
     */
    public static long of(int numero) {
        if (numero < 0) {
            throw new IllegalArgumentException("Número não pode ser negativo");
        }else if (numero == 0) {
            return 1;
        }else {
            fatora f = new fatora(numero);
            return f.fatorar();
        }
        
    }
    
    private long fatorar(){
        if (this.numero == 1) {
            return this.resultado;
        } else {
            try {
                this.resultado = Math.multiplyExact(this.resultado, this.numero);
            } catch (ArithmeticException e) {
                throw new ArithmeticException("Overflow: o resultado da fatoração é muito grande para ser representado como um long.");
            }
            this.numero--;
            return fatorar();
        }
    }

}
