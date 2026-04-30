package br.edu.grafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ColoracaoWelshPowell {

    private final Grafo grafo;
    private final EstadoCor[][] matrizCores;
    private final int[] corAtribuidaPorVertice;
    private final Queue<String> fila;
    private final Set<String> verticesColoridos;

    public ColoracaoWelshPowell(Grafo grafo) {
        this.grafo = grafo;
        this.matrizCores = new EstadoCor[grafo.getQuantidadeVertices()][grafo.getQuantidadeVertices()];
        this.corAtribuidaPorVertice = new int[grafo.getQuantidadeVertices()];
        this.fila = new LinkedList<>();
        this.verticesColoridos = new HashSet<>();

        inicializarMatrizComoLivre();
    }

    public void executar() {
        System.out.println("EXECUÇÃO DO ALGORITMO MAIOR PRIMEIRO (WELSH-POWELL) COM FILA");
        System.out.println();

        int passo = 1;

        while (verticesColoridos.size() < grafo.getQuantidadeVertices()) {
            if (fila.isEmpty()) {
                String proximoVertice = buscarMaiorVerticeNaoColorido();
                inserirNaFilaSePermitido(proximoVertice);
                imprimirEstadoFila("Fila após inserir o maior vértice ainda não colorido");
            }

            if (!fila.isEmpty()) {
                imprimirEstadoFila("Fila antes da remoção");
                String verticeAtual = fila.poll();

                if (verticesColoridos.contains(verticeAtual)) {
                    imprimirEstadoFila("Fila após ignorar vértice já colorido: " + verticeAtual);
                } else {
                    int cor = buscarPrimeiraCorLivre(verticeAtual);
                    ocuparCor(verticeAtual, cor);
                    bloquearCorNosAdjacentesEInserirNaFila(verticeAtual, cor);

                    System.out.println("PASSO " + passo);
                    System.out.println("Vértice removido da fila: " + verticeAtual);
                    System.out.println("Cor atribuída ao vértice " + verticeAtual + ": " + cor);
                    imprimirEstadoFila("Fila após bloquear adjacentes de " + verticeAtual + " na cor " + cor);
                    System.out.println();

                    passo++;
                }
            }
        }
    }

    private void inicializarMatrizComoLivre() {
        for (int linha = 0; linha < matrizCores.length; linha++) {
            for (int coluna = 0; coluna < matrizCores[linha].length; coluna++) {
                matrizCores[linha][coluna] = EstadoCor.LIVRE;
            }
        }
    }

    private String buscarMaiorVerticeNaoColorido() {
        String maiorVertice = null;

        for (String vertice : grafo.getVertices()) {
            if (!verticesColoridos.contains(vertice)) {
                if (maiorVertice == null) {
                    maiorVertice = vertice;
                } else {
                    int grauVertice = grafo.getGrau(vertice);
                    int grauMaiorVertice = grafo.getGrau(maiorVertice);

                    if (grauVertice > grauMaiorVertice) {
                        maiorVertice = vertice;
                    } else if (grauVertice == grauMaiorVertice
                            && vertice.compareToIgnoreCase(maiorVertice) < 0) {
                        maiorVertice = vertice;
                    }
                }
            }
        }

        if (maiorVertice == null) {
            throw new IllegalStateException("Não há vértices pendentes para colorir.");
        }

        return maiorVertice;
    }

    private void inserirNaFilaSePermitido(String vertice) {
        if (!verticesColoridos.contains(vertice) && !fila.contains(vertice)) {
            fila.add(vertice);
        }
    }

    private int buscarPrimeiraCorLivre(String vertice) {
        int indiceVertice = grafo.getIndice(vertice);
        int coluna = 0;
        int corEncontrada = 0;

        while (coluna < matrizCores[indiceVertice].length && corEncontrada == 0) {
            int cor = coluna + 1;

            if (matrizCores[indiceVertice][coluna] == EstadoCor.LIVRE
                    && !existeAdjacenteComCor(vertice, cor)) {
                corEncontrada = cor;
            }

            coluna++;
        }

        if (corEncontrada == 0) {
            throw new IllegalStateException("Não foi encontrada cor livre para o vértice " + vertice + ".");
        }

        return corEncontrada;
    }

    private boolean existeAdjacenteComCor(String vertice, int cor) {
        boolean encontrado = false;

        for (String adjacente : grafo.getAdjacentes(vertice)) {
            int indiceAdjacente = grafo.getIndice(adjacente);

            if (corAtribuidaPorVertice[indiceAdjacente] == cor) {
                encontrado = true;
            }
        }

        return encontrado;
    }

    private void ocuparCor(String vertice, int cor) {
        int indiceVertice = grafo.getIndice(vertice);
        matrizCores[indiceVertice][cor - 1] = EstadoCor.OCUPADO;
        corAtribuidaPorVertice[indiceVertice] = cor;
        verticesColoridos.add(vertice);
    }

    private void bloquearCorNosAdjacentesEInserirNaFila(String vertice, int cor) {
        for (String adjacente : grafo.getAdjacentesOrdenadosAlfabeticamente(vertice)) {
            int indiceAdjacente = grafo.getIndice(adjacente);

            if (matrizCores[indiceAdjacente][cor - 1] == EstadoCor.LIVRE) {
                matrizCores[indiceAdjacente][cor - 1] = EstadoCor.BLOQUEADO;
            }

            inserirNaFilaSePermitido(adjacente);
        }
    }

    private void imprimirEstadoFila(String mensagem) {
        System.out.println(mensagem + ": " + formatarFila());
    }

    private String formatarFila() {
        String resultado = "vazia";

        if (!fila.isEmpty()) {
            resultado = String.join(" - ", fila);
        }

        return resultado;
    }

    public void imprimirMatrizFinal() {
        System.out.println("MATRIZ FINAL DAS CORES");
        imprimirLinhaSeparadora();

        System.out.printf("%-8s", " ");

        for (int cor = 1; cor <= grafo.getQuantidadeVertices(); cor++) {
            System.out.printf("%-6d", cor);
        }

        System.out.println();
        imprimirLinhaSeparadora();

        List<String> vertices = grafo.getVertices();

        for (int linha = 0; linha < vertices.size(); linha++) {
            System.out.printf("%-8s", vertices.get(linha));

            for (int coluna = 0; coluna < grafo.getQuantidadeVertices(); coluna++) {
                System.out.printf("%-6s", simboloCelula(matrizCores[linha][coluna], coluna));
            }

            System.out.println();
        }

        imprimirLinhaSeparadora();
        imprimirResumoCores();
    }

    private String simboloCelula(EstadoCor estado, int indiceColuna) {
        String simbolo = String.valueOf(indiceColuna + 1);

        if (estado == EstadoCor.LIVRE) {
            simbolo = " ";
        } else if (estado == EstadoCor.BLOQUEADO) {
            simbolo = "X";
        }

        return simbolo;
    }

    private void imprimirLinhaSeparadora() {
        int largura = 8 + (grafo.getQuantidadeVertices() * 6);
        System.out.println("-".repeat(largura));
    }

    private void imprimirResumoCores() {
        System.out.println();
        System.out.println("RESUMO DA COLORAÇÃO");

        int maiorCorUsada = 0;
        List<String> vertices = grafo.getVertices();

        for (int i = 0; i < vertices.size(); i++) {
            System.out.println(vertices.get(i) + " -> cor " + corAtribuidaPorVertice[i]);
            maiorCorUsada = Math.max(maiorCorUsada, corAtribuidaPorVertice[i]);
        }

        System.out.println("Total de cores utilizadas: " + maiorCorUsada);
    }

    public List<String> getResultadoColoracao() {
        List<String> resultado = new ArrayList<>();

        for (int i = 0; i < grafo.getVertices().size(); i++) {
            resultado.add(grafo.getVertices().get(i) + " -> cor " + corAtribuidaPorVertice[i]);
        }

        return resultado;
    }
}
