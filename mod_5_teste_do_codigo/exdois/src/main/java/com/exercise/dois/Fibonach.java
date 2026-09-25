package com.exercise.dois;

public class Fibonach {

    /**
     * Calcula o n-ésimo termo da escala fibonach
     * @param n
     * @return
     */
	public static long calcularFibonach(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n não pode ser negativo");
		}
		if (n == 0) return 0L;
		if (n == 1) return 1L;

		long prev = 0L;
		long curr = 1L;
		for (int i = 2; i <= n; i++) {
			long next;
			try {
				next = Math.addExact(prev, curr);
			} catch (ArithmeticException e) {
				throw new ArithmeticException("Resultado excede o limite de long");
			}
			prev = curr;
			curr = next;
		}
		return curr;
	}

}
