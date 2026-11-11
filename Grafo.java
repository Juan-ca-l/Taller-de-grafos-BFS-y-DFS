import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class Grafo {
    private final boolean[][] matrizAdyacencia;
    private final String[] nombresVertices;

    public Grafo(int cantidadVertices) {
        if (cantidadVertices < 0) {
            throw new IllegalArgumentException("La cantidad de vertices no puede ser negativa.");
        }
        matrizAdyacencia = new boolean[cantidadVertices][cantidadVertices];
        nombresVertices = new String[cantidadVertices];
        for (int vertice = 0; vertice < cantidadVertices; vertice++) {
            nombresVertices[vertice] = "Vertice " + vertice;
        }
    }

    public Grafo(String... nombresVertices) {
        this(nombresVertices.length);
        for (int vertice = 0; vertice < nombresVertices.length; vertice++) {
            asignarNombreVertice(vertice, nombresVertices[vertice]);
        }
    }

    public void asignarNombreVertice(int vertice, String nombre) {
        validarVertice(vertice);
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del vertice no puede estar vacio.");
        }
        nombresVertices[vertice] = nombre.trim();
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
        System.out.printf("%-16s", "");
        for (String nombre : nombresVertices) {
            System.out.printf("%-16s", nombre);
        }
        System.out.println();

        for (int fila = 0; fila < matrizAdyacencia.length; fila++) {
            System.out.printf("%-16s", nombresVertices[fila]);
            for (int columna = 0; columna < matrizAdyacencia.length; columna++) {
                System.out.printf("%-16d", matrizAdyacencia[fila][columna] ? 1 : 0);
            }
            System.out.println();
        }
    }

    public void imprimirArista(int origen, int destino) {
        if (existeArista(origen, destino)) {
            System.out.println("Existe una vía entre " + nombresVertices[origen] + " y " + nombresVertices[destino] + ".");
        } else {
            System.out.println("No existe una vía entre " + nombresVertices[origen] + " y " + nombresVertices[destino] + ".");
        }
    }

    private void imprimirRecorrido(String tipo, List<Integer> recorrido) {
        System.out.print(tipo + ": ");
        for (int posicion = 0; posicion < recorrido.size(); posicion++) {
            if (posicion > 0) {
                System.out.print(" -> ");
            }
            System.out.print(nombresVertices[recorrido.get(posicion)]);
        }
        System.out.println();
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
        Grafo grafo = new Grafo(
                "Bogotá D.C.",
                "Chipaque",
                "Cáqueza",
                "Quetame",
                "Guayabetal",
                "Villavicencio",
                "Restrepo",
                "Cumaral",
                "Paratebueno"
        );

        for (int vertice = 0; vertice < 8; vertice++) {
            grafo.agregarArista(vertice, vertice + 1);
        }

        grafo.grafo();
        grafo.imprimirRecorrido("DFS recursivo", grafo.dfsRecursivo(0));
        grafo.imprimirRecorrido("DFS iterativo", grafo.dfsIterativo(0));
        grafo.imprimirRecorrido("BFS", grafo.bfs(0));
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