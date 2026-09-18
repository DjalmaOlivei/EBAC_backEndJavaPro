
public class fatoraBottonUp {

    
    
    private long[] resultado;
    private int numero;

    private fatoraBottonUp(int numero) {
        this.numero = numero;
        this.resultado = new long[numero + 1];
    }

    /**
     * Fatora um número natural metodo BottonUp!!!
     * @param numero
     * @return
     */
    public static long of(int numero) {
        if (numero < 0) throw new IllegalArgumentException("Número não pode ser negativo");
        else if (numero == 0) return 1;
        else if (numero == 1) return 1;
        else {
            fatoraBottonUp f = new fatoraBottonUp(numero);
            return f.fatorar(numero);
        }
        
    }
    
    private long fatorar(int n){

        resultado[0] = 1;
        resultado[1] = 1;

        try {
            for (int i = 2; i<= n; i++) resultado[i] = Math.multiplyExact(i, resultado[i - 1]);
        }
        catch (ArithmeticException e ) {
            throw new ArithmeticException("Número muito grande pra ser representado um uma variavel primitiva long!!!");
        }
        return resultado[n];
        
    }



}
