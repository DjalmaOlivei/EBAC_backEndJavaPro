public class App {
    public static void main(String[] args) {

        // Valores pagos e valores a serem pagos
        int pagoReais = 20;
        int pagoCentavos = 50;

        // Valores do produto
        int ValorReais = 12;
        int ValorCentavos = 87;

        // Calculando o troco
        int trocoReais = pagoReais - ValorReais;
        int trocoCentavos;

        if (pagoCentavos < ValorCentavos) {
            trocoReais -= 1;
            trocoCentavos = (pagoCentavos + 100) - ValorCentavos;
        } 
        else trocoCentavos = pagoCentavos - ValorCentavos;
        
        // Notas e moedas disponíveis
        int[] Reais = { 100, 50, 20, 10, 5, 2, 1};
        int[] centavos = { 50, 25, 10, 5, 1};

        calcularTrocoDinheiro(trocoReais, trocoCentavos, Reais, centavos);
    }

    /**
     * Calcula o valor em dinheiro, considerando as notas e moedas disponíveis.
     * @param ValorReais Valor referente aos Reais
     * @param ValorCentavos Valor referente aos Centavos
     * @param Reais Notas disponíneis ex: 100, 50, 20, 10, 5, 2, 1
     * @param centavos Moedas disponíveis ex: 50, 25, 10, 5, 1
     */
    public static void calcularTrocoDinheiro(int ValorReais, int ValorCentavos,int[] Reais, int[] centavos) {
        System.out.println("Valor em Reais: " + ValorReais);
        System.out.println("Valor em Centavos: " + ValorCentavos);

        System.out.println("Notas de Reais:");
        for (int i = 0; i < Reais.length; i++) {
            int quantidadeNotas = ValorReais / Reais[i];
            if (quantidadeNotas > 0) {
                System.out.println(quantidadeNotas + " nota(s) de R$" + Reais[i]);
                ValorReais -= quantidadeNotas * Reais[i];
            }
        }

        System.out.println("Moedas de Centavos:");
        for (int i = 0; i < centavos.length; i++) {
            int quantidadeMoedas = ValorCentavos / centavos[i];
            if (quantidadeMoedas > 0) {
                System.out.println(quantidadeMoedas + " moeda(s) de " + centavos[i] + " centavo(s)");
                ValorCentavos -= quantidadeMoedas * centavos[i];
            }
        }
    }

}
