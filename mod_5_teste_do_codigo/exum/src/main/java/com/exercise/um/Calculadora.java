package com.exercise.um;

public class Calculadora {

    public static double operacao(int a, int b, String opreacao) throws Exception{
        double result;

        switch (opreacao) {
            case "adicionar":
                result = adicionar(a, b);
                
                break;
        
            case "subtrair":
                result = subtrair(a, b);

                break;

            case "multiplicar":
                result = multiplicar(a, b);

                break;

            case "dividir":
                result = dividir(a, b);

                break;
            default:
                throw new Exception("Operação não suportada"); 

        }

        return result;
    }


    /**
     * adiciona 2 números
     * @param a Termo 1
     * @param b Termo 2
     * @return soma dos termos 1 e 2
     */
	private static int adicionar(int a, int b) {
		return a + b;
	}

    /**
     * subtrai 2 números
     * @param a Termo 1
     * @param b Termo 2
     * @return subtração dos termos 1 e 2
     */
	private static int subtrair(int a, int b) {
		return a - b;
	}

    /**
     * multiplica 2 números
     * @param a Termo 1
     * @param b Termo 2
     * @return multiplicação dos termos 1 e 2
     */
	private static int multiplicar(int a, int b) {
		return a * b;
	}

    /**
     * Divide 2 números
     * @param a Termo 1
     * @param b Termo 2
     * @return divisão dos termos 1 e 2
     */
	private static int dividir(int a, int b) {
		if (b == 0) {
			throw new ArithmeticException("Divisão por zero");
		}
		return a / b;
	}

}
