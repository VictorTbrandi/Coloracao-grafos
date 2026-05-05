package br.edu.grafos;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Grafo grafo = new EntradaConsole(scanner).lerGrafoManual();

            grafo.imprimirListaAdjacencia();

            ColoracaoWelshPowell coloracao = new ColoracaoWelshPowell(grafo);
            coloracao.executar();
            coloracao.imprimirMatrizFinal();
        }
    }
}
