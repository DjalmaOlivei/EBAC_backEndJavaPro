
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
       Scanner scanner = new Scanner(System.in);
       System.out.print("Escreva um número para fatorar: ");
       int numero = scanner.nextInt();

       //fatoração metodo TopDown com benchmark de desempenho em tempo 
        long inicioTopDown = System.nanoTime();
        long resultTopDown = fatoraTopDown.of(numero);
        long fimTopDown    = System.nanoTime();
        double tempoTopDown = (fimTopDown - inicioTopDown) / 1_000_000.0;

       System.out.println("O fatorial de " + numero + " é " + resultTopDown + "\n calculado em: "+ tempoTopDown + " verção TopDown!!!");

       //fatoração metodo bottonUp com benchmark de desempenho em tempo 
       long inicioBottonUp = System.nanoTime();
        long resultBottonUp = fatoraBottonUp.of(numero);
        long fimBottonUp    = System.nanoTime();
        double tempoBottonUp = (fimBottonUp - inicioBottonUp) / 1_000_000.0;

       System.out.println("O fatorial de " + numero + " é " + resultBottonUp + "\n calculado em: "+ tempoBottonUp + " verção BottonUp!!!");

       ////fatoração metodo recurcivo simple com benchmark de desempenho em tempo 
       long inicioRecursivo = System.nanoTime();
        long resultRecursivo = fatora.of(numero);
        long fimRecursivo    = System.nanoTime();
        double tempoRecursivo = (fimRecursivo - inicioRecursivo) / 1_000_000.0;

       System.out.println("O fatorial de " + numero + " é " + resultRecursivo + "\n calculado em: "+ tempoRecursivo + " verção Recursivo!!!");

    }
}
