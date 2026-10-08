

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

        // Lista de elementos
        List<Object> s = List.of('a', 'b', 'c', 'd');
        //tamanho dos subconjuntos
        int n = 2;

        List<List<Object>> resultado = new ArrayList<>();
        
        gerar(s, n, 0, new ArrayList<>(), resultado);
        
        System.out.println(resultado);

    }

    /**
     * Gera todas as combinações possíveis de tamanho "n" a partir da lista s. (independente da ordem)
     * @param s Elementos
     * @param n Tamanho do subconjunto
     * @param inicio Índice de início na lista s
     * @param atual Lista atual de elementos
     * @param resultado Lista de resultados
     */
    public static void gerar(List<Object> s, int n, int inicio, List<Object> atual, List<List<Object>> resultado) {
        if (atual.size() == n) {
            resultado.add(new ArrayList<>(atual));
            return;
        }
        
        for (int i = inicio; i < s.size(); i++) {
            atual.add(s.get(i));
            gerar(s, n, i + 1, atual, resultado);
            atual.remove(atual.size() - 1);
        }
    }

}
