package br.edu.grafos;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LeitorGrafo {

    public Grafo lerLinhas(List<String> linhas) {
        if (linhas == null || linhas.isEmpty()) {
            throw new IllegalArgumentException("A entrada está vazia.");
        }

        int indicePrimeiraLinhaValida = encontrarPrimeiraLinhaValida(linhas);
        String primeiraLinha = limparComentario(linhas.get(indicePrimeiraLinhaValida)).trim();

        if (primeiraLinha.isEmpty()) {
            throw new IllegalArgumentException("A primeira linha deve conter os vértices do grafo.");
        }

        List<String> vertices = extrairVertices(primeiraLinha);
        Grafo grafo = new Grafo(vertices);

        for (int i = indicePrimeiraLinhaValida + 1; i < linhas.size(); i++) {
            String linha = limparComentario(linhas.get(i)).trim();

            if (!linha.isEmpty()) {
                String[] partes = linha.split("\\s+");
                String vertice = partes[0];
                int numeroLinha = i + 1;

                validarVerticeDeclarado(grafo, vertice, numeroLinha);
                adicionarAdjacentes(grafo, partes, numeroLinha);
            }
        }

        return grafo;
    }

    private int encontrarPrimeiraLinhaValida(List<String> linhas) {
        int indice = 0;
        int resultado = -1;

        while (indice < linhas.size() && resultado == -1) {
            String linha = limparComentario(linhas.get(indice)).trim();

            if (!linha.isEmpty()) {
                resultado = indice;
            }

            indice++;
        }

        if (resultado == -1) {
            throw new IllegalArgumentException("A entrada não possui linhas válidas.");
        }

        return resultado;
    }

    private List<String> extrairVertices(String primeiraLinha) {
        String[] tokensVertices = limparComentario(primeiraLinha).trim().split("\\s+");
        Set<String> verticesSemRepeticao = new LinkedHashSet<>();

        for (String token : tokensVertices) {
            if (!token.isBlank()) {
                verticesSemRepeticao.add(token);
            }
        }

        if (verticesSemRepeticao.isEmpty()) {
            throw new IllegalArgumentException("A primeira linha deve conter pelo menos um vértice.");
        }

        return new ArrayList<>(verticesSemRepeticao);
    }

    private void adicionarAdjacentes(Grafo grafo, String[] partes, int numeroLinha) {
        String vertice = partes[0];

        for (int j = 1; j < partes.length; j++) {
            String adjacente = partes[j];

            validarAdjacenteDeclarado(grafo, adjacente, numeroLinha);
            grafo.adicionarAresta(vertice, adjacente);
        }
    }

    private void validarVerticeDeclarado(Grafo grafo, String vertice, int numeroLinha) {
        if (!grafo.contemVertice(vertice)) {
            throw new IllegalArgumentException(
                    "Linha " + numeroLinha + ": o vértice '" + vertice + "' não foi declarado na primeira linha."
            );
        }
    }

    private void validarAdjacenteDeclarado(Grafo grafo, String adjacente, int numeroLinha) {
        if (!grafo.contemVertice(adjacente)) {
            throw new IllegalArgumentException(
                    "Linha " + numeroLinha + ": o adjacente '" + adjacente + "' não foi declarado na primeira linha."
            );
        }
    }

    private String limparComentario(String linha) {
        int indiceComentario = linha.indexOf('#');
        String resultado = linha;

        if (indiceComentario != -1) {
            resultado = linha.substring(0, indiceComentario);
        }

        return resultado;
    }
}
