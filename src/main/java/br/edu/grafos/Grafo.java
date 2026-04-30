package br.edu.grafos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Grafo {

    private final List<String> vertices;
    private final Map<String, LinkedHashSet<String>> listaAdjacencia;

    public Grafo(List<String> vertices) {
        if (vertices == null || vertices.isEmpty()) {
            throw new IllegalArgumentException("O grafo deve possuir pelo menos um vértice.");
        }

        this.vertices = new ArrayList<>(vertices);
        this.listaAdjacencia = new LinkedHashMap<>();

        for (String vertice : vertices) {
            listaAdjacencia.put(vertice, new LinkedHashSet<>());
        }
    }

    public void adicionarAresta(String origem, String destino) {
        validarVertice(origem);
        validarVertice(destino);

        if (!origem.equals(destino)) {
            listaAdjacencia.get(origem).add(destino);
            listaAdjacencia.get(destino).add(origem);
        }
    }

    public List<String> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public Set<String> getAdjacentes(String vertice) {
        validarVertice(vertice);
        return Collections.unmodifiableSet(listaAdjacencia.get(vertice));
    }

    public int getGrau(String vertice) {
        validarVertice(vertice);
        return listaAdjacencia.get(vertice).size();
    }

    public int getQuantidadeVertices() {
        return vertices.size();
    }

    public int getIndice(String vertice) {
        int indice = vertices.indexOf(vertice);

        if (indice == -1) {
            throw new IllegalArgumentException("Vértice não encontrado: " + vertice);
        }

        return indice;
    }

    public boolean contemVertice(String vertice) {
        return listaAdjacencia.containsKey(vertice);
    }

    public List<String> getAdjacentesOrdenadosAlfabeticamente(String vertice) {
        validarVertice(vertice);
        List<String> adjacentes = new ArrayList<>(listaAdjacencia.get(vertice));
        adjacentes.sort(String.CASE_INSENSITIVE_ORDER);
        return adjacentes;
    }

    public void imprimirListaAdjacencia() {
        System.out.println("LISTA DE ADJACÊNCIA DO GRAFO");

        for (String vertice : vertices) {
            List<String> adjacentes = new ArrayList<>(listaAdjacencia.get(vertice));

            if (adjacentes.isEmpty()) {
                System.out.println(vertice);
            } else {
                System.out.println(vertice + " -> " + String.join(" -> ", adjacentes));
            }
        }

        System.out.println();
    }

    private void validarVertice(String vertice) {
        if (!listaAdjacencia.containsKey(vertice)) {
            throw new IllegalArgumentException("Vértice não encontrado na primeira linha da entrada: " + vertice);
        }
    }
}
