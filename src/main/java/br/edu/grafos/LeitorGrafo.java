package br.edu.grafos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeitorGrafo {

    public Grafo lerLinhas(List<String> linhas) {
        List<String> vertices = new ArrayList<>(Arrays.asList(linhas.get(0).split("\\s+")));
        Grafo grafo = new Grafo(vertices);

        for (int i = 1; i < linhas.size(); i++) {
            String[] partes = linhas.get(i).split("\\s+");
            String vertice = partes[0];

            for (int j = 1; j < partes.length; j++) {
                grafo.adicionarAresta(vertice, partes[j]);
            }
        }

        return grafo;
    }
}
