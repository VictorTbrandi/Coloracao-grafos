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
        boolean recebendoEntrada = true;

        System.out.println("Primeira linha: todos os vértices separados por espaço.");
        System.out.println("Demais linhas: vértice seguido de seus adjacentes.");
        System.out.println("Digite FIM para encerrar a entrada manual.");
        System.out.println();

        while (recebendoEntrada) {
            System.out.print("> ");
            String linha = scanner.nextLine();

            if (linha.trim().equalsIgnoreCase("FIM")) {
                recebendoEntrada = false;
            } else if (!linha.trim().isEmpty()) {
                linhas.add(linha);
            }
        }

        if (linhas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma linha foi informada para montar o grafo.");
        }

        System.out.println();
        return leitorGrafo.lerLinhas(linhas);
    }
}
