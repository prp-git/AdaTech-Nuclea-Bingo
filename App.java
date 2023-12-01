import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Benvindo();

        escolhaAutoManual();

        String tipoGeracao = defineApostaManualAuto();

        imprimeTipo(tipoGeracao);

        String[] jogadores = entrarNomesJogadores();

        mostraJogadores(jogadores);


        final int COMPRIMENTO_CARTELA = 5;

        int[] jaSorteados = {};

        int[][] listaDeJogos = montarCartelas(tipoGeracao, jogadores.length);

        int[][] controle = getMatrizControle(jogadores.length, COMPRIMENTO_CARTELA);

        int[] playerScore = new int[jogadores.length];

        boolean primeiraRodada = true;
        boolean continuaJogo = true;
        boolean jogoAcabou = false;
        int rodada = 0;

        while (continuaJogo) {
            mostrarTabelaPontos(jogadores, listaDeJogos, controle);
            mostrarJaSorteados(jaSorteados);

            int[] pontosAcumulados = atualizaTabelaPontos(playerScore, controle);
            int[] pontosRank = rankingPontos(pontosAcumulados);
            System.out.println(Arrays.toString(pontosRank));

            if (!primeiraRodada) {
                boolean parar = pararOuSortear();
                System.out.println("Parar: " + parar);

                if (parar == true) {
                    System.out.println("JOGO ENCERRADO!\nRESULTADO PARCIAL: ");
                    mostrarTabelaPontos(jogadores, listaDeJogos, controle);
                    break;
                } else {
                    continuaJogo = true;
                }
            }
            int[] sorteadosRodada = sortear5Numeros(jaSorteados);
            rodada++;
            System.out.println();
            System.out.println();
            sorteadosNaRodada(rodada, sorteadosRodada);

            int[] jaSorteadosAumentado = expandirListaSorteados(jaSorteados, sorteadosRodada);

            processarMatrizControle(listaDeJogos, sorteadosRodada, controle, jaSorteadosAumentado);

            for (int i = 0; i < jogadores.length; i++) {
                if (controle[i][COMPRIMENTO_CARTELA] == 5) {
                    jogoAcabou = true;
                    System.out.println("TEMOS VENCEDORES!!!");
                    mostrarTabelaPontos(jogadores, listaDeJogos, controle);
                    System.out.println("FIM DE JOGO!");
                }
            }

            jaSorteados = jaSorteadosAumentado;

            System.out.println();
            primeiraRodada = false;
        }
    }

    public static int[] rankingPontos(int[] pontos){
        int comprimento = pontos.length;
        int[] auxiliar = new int[comprimento];

        for (int i = 0; i < comprimento; i++) {
            int numMaiores = 0;
            for (int j = 0; j < comprimento; j++) {
                if (pontos[i] < pontos[j])
                    numMaiores++;
            }
            auxiliar[numMaiores] = pontos[i];
        }
        return auxiliar;
    }

    public static String[] rankingNomes(String[] nomes, int[] pontos){
        int comprimento = pontos.length;

        String[] auxiliar = new String [comprimento];

        for (int i = 0; i < comprimento; i++) {
            int numMaiores = 0;
            for (int j = 0; j < comprimento; j++) {
                if (pontos[i] < pontos[j])
                    numMaiores++;
            }
            auxiliar[numMaiores] = nomes[i];
        }
        return auxiliar;
    }


    private static int[] atualizaTabelaPontos(int [] contadorPontos, int[][] controle) {
        for (int i = 0; i < contadorPontos.length; i++) {
            contadorPontos[i] = controle[i][controle[0].length-1];
        }
        return contadorPontos;
    }

    private static void mostrarJaSorteados(int[] jaSorteados) {
        System.out.print("|      Já sorteados: " + Arrays.toString(jaSorteados) + "|\n");
        System.out.print( "+------------------------------------------------------------------------------+\n");
    }

    private static void sorteadosNaRodada(int round, int[] sorteadosRodada) {
        System.out.print("Sorteados na rodada #" + round + ": " + Arrays.toString(sorteadosRodada) + "\n");
        System.out.print( "+------------------------------------------------------------------------------+\n");
    }

    private static void mostraJogadores(String[] jogadores) {
        System.out.print(
                          "+------------------------------------------------------------------------------+\n");
        System.out.printf("|      Número de jogadores: %-51s|%n", jogadores.length);
        for (int i = 0; i < jogadores.length; i++) {
            System.out.printf("|%7s: %-15s%55s%n", i + 1, jogadores[i], "|");
            }
        System.out.print( "+------------------------------------------------------------------------------+\n");
    }

    private static void imprimeTipo(String tipoGeracao) {
        System.out.print(
                         "+------------------------------------------------------------------------------+\n"
                       + "|                                                                              |\n");
        System.out.printf("|  %30s%-46s|%n", "Tipo de Geracao:    ", tipoGeracao);
        System.out.print("|                                                                              |\n"
                       + "+------------------------------------------------------------------------------+\n");
    }

    private static void escolhaAutoManual() {
        System.out.printf(
//                    "+------------------------------------------------------------------------------+\n"
                    "|                                                                              |\n"
                  + "|        Informe como deseja gerar as cartelas e números a cada rodada:        |\n"
                  + "|                         Manual [M] ou Automático [A]                         |\n"
                  + "|                                                                              |\n"
                  + "+------------------------------------------------------------------------------+\n");
    }

    private static void Benvindo() {
        System.out.printf(
                  "+------------------------------------------------------------------------------+\n"
                + "|                       Ben-vindo ao Bingo Java 50+                            |\n"
                + "+------------------------------------------------------------------------------+\n"
                + "|                                                                              |\n"
                + "|             *  Você pode escolher jogar com uma cartela                      |\n"
                + "|          aleatória ou jogar com 5 números escolhidos por você mesmo.         |\n"
                + "|                                                                              |\n"
                + "|             *  Os números nas cartelas vão de 1 a 60.                        |\n"
                + "+------------------------------------------------------------------------------+\n");
    }
    private static void mostrarTabelaPontos(String[]jogadores, int[][] listaDeJogos, int[][] controle){
        System.out.printf("|%11s%-24s%-24s%19s|%n","Player    ", "Cartela", "Controle", "Soma ");
        for (int i = 0; i < jogadores.length; i++) {
            String line = String.format("|%-11s",jogadores[i]);
            for (int j = 0; j < listaDeJogos[0].length; j++) {
                line += String.format("%02d ", listaDeJogos[i][j]);
            }
            line += "         ";
            for (int j = 0; j < listaDeJogos[0].length + 1; j++) {
                if(j != 5) {
                    line += String.format("%2d ", controle[i][j]);
                } else {
                    line += String.format("%26d  |", controle[i][j]);
                }
            }
            System.out.println(line);
        }
        System.out.println("+------------------------------------------------------------------------------+");
    }


    private static int[][] processarMatrizControle ( int[][] apostas, int[] sorteadosRodada, int[][] controle, int[] jaSorteadosNovos){
        int[][] controleAtualizado = controle;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < apostas.length; j++) {
                for (int k = 0; k < 5; k++) {
                    if (sorteadosRodada[i] == apostas[j][k]) {
                        controleAtualizado[j][k] = 1;
                        controleAtualizado[j][5]++;
                    }
                }
            }
        }
        return controleAtualizado;
    }

    public static boolean pararOuSortear () {
        Scanner sc = new Scanner(System.in);
        boolean parar = false;
        System.out.print("Pretende parar: Pressione (P). Caso contrário pressione outra tecla e ENTER");

        boolean invalido = true;

        while (invalido) {
            String resposta = sc.next().toUpperCase();
            if (resposta.equals("P")) {
                parar = true;
                break;
            } else {
                parar = false;
                break;
            }
        }
        return parar;
    }

    public static boolean pararOuSortear2 () {
        Scanner sc = new Scanner(System.in);
        boolean parar = false;
        System.out.print("Para (P) ou continuar (C): ");
        String resposta = sc.next().toUpperCase();

        if (resposta.equals("P")) {
            parar = true;
        } else {
            parar = false;
        }
        return parar;
    }

    public static String defineApostaManualAuto() {
        Scanner sc = new Scanner(System.in);
        String resposta = "aaa";
        String tipo ="bbb";

        boolean respostaErrada = true;

        while (respostaErrada) {
            System.out.print("Entre com sua opção: ");
            resposta = sc.next().toUpperCase();
            if (!resposta.equals("M") && !resposta.equals("A")) {
                System.out.println("Digite 'M' ou 'A'!");
                respostaErrada = true;
            }
            if (resposta.equals("M")) {
                tipo = "MANUAL";
                respostaErrada = false;
                break;
            } else {
                tipo = "AUTOMATICO";
                respostaErrada = false;
                break;
            }
        }
        return tipo;
    }

    public static int[][] montarCartelas (String tipo,int numeroJogadores){
        int[][] jogos = new int[numeroJogadores][5];
        for (int i = 0; i < numeroJogadores; i++) {
            if (tipo.equals("AUTOMATICO")) {
                jogos[i] = criarCartelaAutomatica();
            } else if (tipo.equals("MANUAL")) {
                jogos[i] = criarCartelaManual();
            }
        }
        return jogos;
    }

    public static int[] criarCartelaManual () {
        Scanner sc = new Scanner(System.in);
        int[] auxiliar = new int[5];
        String[] vetorTexto = new String[5];
        System.out.println("Informe os números separados por vírgula");
        vetorTexto = sc.nextLine().split(",");

        for (int i = 0; i < vetorTexto.length; i++) {
            auxiliar[i] = Integer.parseInt(vetorTexto[i]);
        }
        return ordenaVetor(auxiliar);
    }

    public static int[] criarCartelaAutomatica () {
        int[] sequencia = new int[5];

        for (int i = 0; i < 5; i++) {
            boolean repetido = true;
            while (repetido) {
                int sorteado = (1 + (int) Math.round(Math.random() * 59));
                repetido = false;
                for (int j = 0; j < i; j++) {
                    if (sorteado == sequencia[j]) {
                        repetido = true;
                    }
                }
                if (!repetido) {
                    sequencia[i] = sorteado;
                    repetido = false;
                }
            }
        }
        return ordenaVetor(sequencia);
    }

    public static int[] ordenaVetor ( int[] original){
        int comprimento = original.length;
        int[] auxiliar = new int[comprimento];

        for (int i = 0; i < comprimento; i++) {
            int numMaiores = 0;
            for (int j = 0; j < comprimento; j++) {
                if (original[i] > original[j])
                    numMaiores++;
            }
            auxiliar[numMaiores] = original[i];
        }
        return auxiliar;
    }

    public static String[] entrarNomesJogadores () {
        Scanner sc = new Scanner(System.in);
        System.out.print(
                          "|                                                                              |\n"
                        + "|        Digite o nomes dos jogadores separados por hífen                      |\n"
                        + "|                                                                              |\n"
                        + "+------------------------------------------------------------------------------+\n"
                        + ">>>");

        String[] nomes = sc.nextLine().split("-");
        System.out.println();
        return nomes;
    }

    private static int[][] getMatrizControle ( int num_jogadores, int COMPRIMENTO){
        int[][] matrizControle = new int[num_jogadores][COMPRIMENTO + 1];
        return matrizControle;
    }

    private static int[] sortear5Numeros ( int[] jaSorteados){
        int[] sequencia = new int[5];

        for (int i = 0; i < 5; i++) {
            boolean repetido = true;
            while (repetido) {
                int sorteado = (1 + (int) Math.round(Math.random() * 59));
                repetido = false;
                for (int j = 0; j < i; j++) {
                    if (sorteado == sequencia[j]) {
                        repetido = true;
                        break;
                    }
                }
                if (!repetido) {
                    for (int j = 0; j < jaSorteados.length; j++) {
                        if (sorteado == jaSorteados[j]) {
                            repetido = true;
                            break;
                        }
                    }
                }
                if (!repetido) {
                    sequencia[i] = sorteado;
                }
            }
        }
        expandirListaSorteados(jaSorteados, sequencia);
        return ordenaVetor(sequencia);
    }

    private static int[] expandirListaSorteados(int[] jaSorteados, int[] sorteadosRodada) {
        int size = jaSorteados.length;
        int[] auxiliar=Arrays.copyOf(jaSorteados, size + sorteadosRodada.length);

        for (int i = 0; i < sorteadosRodada.length; i++) {
            auxiliar[size + i] = sorteadosRodada[i];
        }
        return ordenaVetor(auxiliar);
    }
}
