
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        // colhe input do usuário
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um número para fatorar: ");
        int numero = scanner.nextInt();

        // chama recurso de fatoração
        System.out.println("Resultado da fatoração: " + fatora.of(numero));

    }
}
