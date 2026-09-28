package com.exercise.um;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

import com.exercise.um.Calculadora;

public class CalculadoraTest {

    @Test
    void testAdicionar() throws Exception {
        assertEquals(5, (Calculadora.operacao(3,2,"adicionar")));
    }

    @Test
    void testSubtrair() throws Exception {
        assertEquals(1, (Calculadora.operacao(3,2,"subtrair")));
    }

    @Test
    void testMultiplicar() throws Exception {
        assertEquals(6, (Calculadora.operacao(3,2,"multiplicar")));
    }

    @Test
    void testDividir() throws Exception {
        assertEquals(2, (Calculadora.operacao(6,3,"dividir")));
    }

    @Test
    void testDividirPorZero() throws Exception {
        assertThrows(ArithmeticException.class, () -> {
            Calculadora.operacao(1, 0, "dividir");
        });
    }
}
