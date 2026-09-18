
import java.util.Arrays;

public class fatoraTopDown {

    
    private long[] resultado;
    private int numero;

    private fatoraTopDown(int numero) {
        this.numero = numero;
        this.resultado = new long[numero + 1];
        Arrays.fill(resultado, -1);
    }

    /**
     * Fatora um número natural metodo TopDown!!!
     * @param numero
     * @return
     */
    public static long of(int numero) {
        if (numero < 0) throw new IllegalArgumentException("Número não pode ser negativo");
        else if (numero == 0) return 1;
        else if (numero == 1) return 1;
        else {
            fatoraTopDown f = new fatoraTopDown(numero);
            return f.fatorar(numero);
        }
        
    }
    
    private long fatorar(int n){

        if (resultado[n] != -1 ) return resultado[n];
        if (n == 0 ) return 1;
        try{
            //resultado[n] = n * fatorar(n-1);
            resultado[n] = Math.multiplyExact(n, this.fatorar(n-1));
        }catch(ArithmeticException e ){
            throw new ArithmeticException("Número grande demais para ser expressado por um primitivo long!!! ");
        }
        
        return resultado[n];

    }


}
