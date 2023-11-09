package dataStructures;

public class DoubleCircularList<T> {

    private DoubleNode<T> nodoPrimero;
    private DoubleNode<T> nodoUltimo;
    private int tamanio;

    public DoubleCircularList() {
        nodoPrimero = null;
        nodoUltimo = null;
        tamanio = 0;
    }

    public void insertar(T valor, int posicion) {
        if (posicion < 0 || posicion > tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        DoubleNode<T> nuevoNodo = new DoubleNode<>(valor);

        if (estaVacia()) {
            nodoPrimero = nodoUltimo = nuevoNodo;
            nodoPrimero.setSiguienteNodo(nuevoNodo);
            nodoPrimero.setAnteriorNodo(nuevoNodo);
        } else if (posicion == 0) {
            nuevoNodo.setSiguienteNodo(nodoPrimero);
            nuevoNodo.setAnteriorNodo(nodoUltimo);
            nodoPrimero.setAnteriorNodo(nuevoNodo);
            nodoUltimo.setSiguienteNodo(nuevoNodo);
            nodoPrimero = nuevoNodo;
        } else {
            int cont = 0;
            DoubleNode<T> aux = nodoPrimero;
            while (cont < posicion - 1) {
                aux = aux.getSiguienteNodo();
                cont++;
            }
            nuevoNodo.setSiguienteNodo(aux.getSiguienteNodo());
            nuevoNodo.setAnteriorNodo(aux);
            aux.getSiguienteNodo().setAnteriorNodo(nuevoNodo);
            aux.setSiguienteNodo(nuevoNodo);
        }

        tamanio++;
    }

    public int buscar(T valor) {
        @SuppressWarnings("unused")
		int cont = 0;
        int pos = -1;

        if (!estaVacia()) {
            DoubleNode<T> aux = nodoPrimero;
            for (int i = 0; i < tamanio; i++) {
                if (aux.getValorNodo().equals(valor)) {
                    pos = i;
                    break;
                }
                aux = aux.getSiguienteNodo();
            }
        }

        return pos;
    }

    public void agregarFinal(T valorNodo) {
        DoubleNode<T> nuevoNodo = new DoubleNode<>(valorNodo);

        if (estaVacia()) {
            nodoPrimero = nodoUltimo = nuevoNodo;
            nodoPrimero.setSiguienteNodo(nuevoNodo);
            nodoPrimero.setAnteriorNodo(nuevoNodo);
        } else {
            nuevoNodo.setSiguienteNodo(nodoPrimero);
            nuevoNodo.setAnteriorNodo(nodoUltimo);
            nodoUltimo.setSiguienteNodo(nuevoNodo);
            nodoPrimero.setAnteriorNodo(nuevoNodo);
            nodoUltimo = nuevoNodo;
        }

        tamanio++;
    }

    public boolean estaVacia() {
        return tamanio == 0;
    }

    public void imprimirLista() {
        if (estaVacia()) {
            System.out.println("La lista está vacía.");
        } else {
            DoubleNode<T> aux = nodoPrimero;
            int cont = 0;
            do {
                System.out.print(aux.getValorNodo() + "\t");
                aux = aux.getSiguienteNodo();
                cont++;
            } while (aux != nodoPrimero && cont < tamanio);
            System.out.println();
        }
    }
    
    public void eliminarPorIndice(int indice) {
        if (estaVacia()) {
            return; // No hay nodos que eliminar en una lista vacía.
        }

        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        if (tamanio == 1) {
            // Caso especial: solo hay un nodo en la lista.
            nodoPrimero = nodoUltimo = null;
        } else if (indice == 0) {
            // Eliminar el primer nodo.
            nodoPrimero.getSiguienteNodo().setAnteriorNodo(nodoUltimo);
            nodoUltimo.setSiguienteNodo(nodoPrimero.getSiguienteNodo());
            nodoPrimero = nodoPrimero.getSiguienteNodo();
        } else if (indice == tamanio - 1) {
            // Eliminar el último nodo.
            nodoUltimo.getAnteriorNodo().setSiguienteNodo(nodoPrimero);
            nodoPrimero.setAnteriorNodo(nodoUltimo.getAnteriorNodo());
            nodoUltimo = nodoUltimo.getAnteriorNodo();
        } else {
            // Eliminar un nodo en el medio de la lista.
            int contador = 0;
            DoubleNode<T> aux = nodoPrimero;
            while (contador < indice) {
                aux = aux.getSiguienteNodo();
                contador++;
            }
            aux.getAnteriorNodo().setSiguienteNodo(aux.getSiguienteNodo());
            aux.getSiguienteNodo().setAnteriorNodo(aux.getAnteriorNodo());
        }

        tamanio--;
    }
}
