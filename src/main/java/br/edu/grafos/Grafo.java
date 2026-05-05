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
        this.vertices = new ArrayList<>(vertices);
        this.listaAdjacencia = new LinkedHashMap<>();

        for (String vertice : vertices) {
            listaAdjacencia.put(vertice, new LinkedHashSet<>());
        }
    }

    public void adicionarAresta(String origem, String destino) {
        listaAdjacencia.get(origem).add(destino);
    }

    public List<String> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public Set<String> getAdjacentes(String vertice) {
        return Collections.unmodifiableSet(listaAdjacencia.get(vertice));
    }

    public int getGrau(String vertice) {
        return listaAdjacencia.get(vertice).size();
    }

    public int getQuantidadeVertices() {
        return vertices.size();
    }

    public int getIndice(String vertice) {
        return vertices.indexOf(vertice);
    }

    public List<String> getAdjacentesOrdenadosAlfabeticamente(String vertice) {
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
}
