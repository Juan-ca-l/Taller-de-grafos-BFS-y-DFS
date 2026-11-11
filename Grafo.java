import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class Grafo {
    private final boolean[][] matrizAdyacencia;

    public Grafo(int cantidadVertices) {
        if (cantidadVertices < 0) {
            throw new IllegalArgumentException("La cantidad de vertices no puede ser negativa.");
        }
        matrizAdyacencia = new boolean[cantidadVertices][cantidadVertices];
    }

    public void agregarArista(int origen, int destino) {
        validarVertice(origen);
        validarVertice(destino);
        matrizAdyacencia[origen][destino] = true;
        matrizAdyacencia[destino][origen] = true;
    }

    public void eliminarArista(int origen, int destino) {
        validarVertice(origen);
        validarVertice(destino);
        matrizAdyacencia[origen][destino] = false;
        matrizAdyacencia[destino][origen] = false;
    }

    public boolean existeArista(int origen, int destino) {
        validarVertice(origen);
        validarVertice(destino);
        return matrizAdyacencia[origen][destino];
    }

    public boolean exiteArista(int origen, int destino) {
        return existeArista(origen, destino);
    }

    public void grafo() {
        System.out.print("   ");
        for (int vertice = 0; vertice < matrizAdyacencia.length; vertice++) {
            System.out.print(vertice + " ");
        }
        System.out.println();

        for (int fila = 0; fila < matrizAdyacencia.length; fila++) {
            System.out.print(fila + ": ");
            for (int columna = 0; columna < matrizAdyacencia.length; columna++) {
                System.out.print((matrizAdyacencia[fila][columna] ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }

    public void imprimirArista(int origen, int destino) {
        if (existeArista(origen, destino)) {
            System.out.println("Existe una arista entre " + origen + " y " + destino + ".");
        } else {
            System.out.println("No existe una arista entre " + origen + " y " + destino + ".");
        }
    }

    public List<Integer> dfsRecursivo(int inicio) {
        validarVertice(inicio);
        List<Integer> ordenVisita = new ArrayList<>();
        boolean[] visitados = new boolean[matrizAdyacencia.length];
        dfsRecursivo(inicio, visitados, ordenVisita);
        return ordenVisita;
    }

    private void dfsRecursivo(int vertice, boolean[] visitados, List<Integer> ordenVisita) {
        visitados[vertice] = true;
        ordenVisita.add(vertice);

        for (int vecino = 0; vecino < matrizAdyacencia.length; vecino++) {
            if (matrizAdyacencia[vertice][vecino] && !visitados[vecino]) {
                dfsRecursivo(vecino, visitados, ordenVisita);
            }
        }
    }

    public List<Integer> dfsIterativo(int inicio) {
        validarVertice(inicio);
        List<Integer> ordenVisita = new ArrayList<>();
        boolean[] visitados = new boolean[matrizAdyacencia.length];
        Deque<Integer> pila = new ArrayDeque<>();
        pila.push(inicio);

        while (!pila.isEmpty()) {
            int vertice = pila.pop();
            if (visitados[vertice]) {
                continue;
            }
            visitados[vertice] = true;
            ordenVisita.add(vertice);

            for (int vecino = matrizAdyacencia.length - 1; vecino >= 0; vecino--) {
                if (matrizAdyacencia[vertice][vecino] && !visitados[vecino]) {
                    pila.push(vecino);
                }
            }
        }
        return ordenVisita;
    }

    public List<Integer> bfs(int inicio) {
        validarVertice(inicio);
        List<Integer> ordenVisita = new ArrayList<>();
        boolean[] visitados = new boolean[matrizAdyacencia.length];
        Queue<Integer> cola = new ArrayDeque<>();
        visitados[inicio] = true;
        cola.add(inicio);

        while (!cola.isEmpty()) {
            int vertice = cola.remove();
            ordenVisita.add(vertice);

            for (int vecino = 0; vecino < matrizAdyacencia.length; vecino++) {
                if (matrizAdyacencia[vertice][vecino] && !visitados[vecino]) {
                    visitados[vecino] = true;
                    cola.add(vecino);
                }
            }
        }
        return ordenVisita;
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo(5);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(0, 2);
        grafo.agregarArista(1, 3);
        grafo.agregarArista(1, 4);
        grafo.agregarArista(2, 4);

        grafo.grafo();
        System.out.println("DFS recursivo: " + grafo.dfsRecursivo(0));
        System.out.println("DFS iterativo: " + grafo.dfsIterativo(0));
        System.out.println("BFS: " + grafo.bfs(0));
        grafo.imprimirArista(0, 1);
        grafo.eliminarArista(0, 1);
        grafo.imprimirArista(0, 1);
    }

    private void validarVertice(int vertice) {
        if (vertice < 0 || vertice >= matrizAdyacencia.length) {
            throw new IndexOutOfBoundsException("Vertice fuera de rango: " + vertice);
        }
    }
}