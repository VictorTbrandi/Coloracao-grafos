package br.edu.grafos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EntradaConsole {

    private final Scanner scanner;
    private final LeitorGrafo leitorGrafo;

    public EntradaConsole(Scanner scanner) {
        this.scanner = scanner;
        this.leitorGrafo = new LeitorGrafo();
    }

    public Grafo lerGrafoManual() {
        List<String> linhas = new ArrayList<>();

        System.out.println("Primeira linha: todos os vértices separados por espaço.");
        System.out.println("Demais linhas: vértice seguido de seus adjacentes.");
        System.out.println("Digite FIM para encerrar a entrada manual.");
        System.out.println();

        System.out.print("> ");
        String linha = scanner.nextLine().trim();

        while (!linha.equalsIgnoreCase("FIM")) {
            linhas.add(linha);

            System.out.print("> ");
            linha = scanner.nextLine().trim();
        }

        System.out.println();
        return leitorGrafo.lerLinhas(linhas);
    }
}