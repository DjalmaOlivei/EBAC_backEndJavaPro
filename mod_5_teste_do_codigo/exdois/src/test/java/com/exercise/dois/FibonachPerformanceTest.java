package com.exercise.dois;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FibonachPerformanceTest {

    @Test
    void performanceDifferentNs() {
        int[] ns = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertEquals(1, Fibonach.calcularFibonach(ns[0]));
        assertEquals(1, Fibonach.calcularFibonach(ns[1]));
        assertEquals(2, Fibonach.calcularFibonach(ns[2]));
        assertEquals(3, Fibonach.calcularFibonach(ns[3]));
        assertEquals(5, Fibonach.calcularFibonach(ns[4]));
        assertEquals(8, Fibonach.calcularFibonach(ns[5]));
        assertEquals(13, Fibonach.calcularFibonach(ns[6]));
        assertEquals(21, Fibonach.calcularFibonach(ns[7]));
        assertEquals(34, Fibonach.calcularFibonach(ns[8]));
    }
}
