/**
 * 
 */
package dataStructures;

import java.util.ArrayList;
import java.util.LinkedList;

public class Queue<T> {

    private LinkedList<T> elements = new LinkedList<>();

    // Añade un elemento al final de la cola
    public void enqueue(T element) {
        elements.addLast(element);
    }

    // Elimina y devuelve el elemento al frente de la cola
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return elements.removeFirst();
    }

    // Obtiene el elemento al frente de la cola sin eliminarlo
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return elements.getFirst();
    }

    // Verifica si la cola está vacía
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    // Obtiene el tamaño de la cola
    public int size() {
        return elements.size();
    }

    // Limpia la cola
    public void clear() {
        elements.clear();
    }
    
    public static <T> ArrayList<T> colaAArrayList(Queue<T> cola) {
        // Crea un nuevo ArrayList y añade todos los elementos de la cola
        ArrayList<T> lista = new ArrayList<>(cola.size());
        while (!cola.isEmpty()) {
            lista.add(cola.dequeue());
        }
        return lista;
    }
    
    // Obtener el elemento en un índice específico sin eliminarlo de la cola
    public T getElementAt(int index) {
        if (index < 0 || index >= elements.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de límites");
        }
        return elements.get(index);
    }
    
    // Obtener el índice de un elemento en la cola
    public int getIndexByValue(T value) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals(value)) {
                return i;
            }
        }
        // Si no se encuentra el valor, puedes devolver -1 o lanzar una excepción según tus necesidades
        throw new IllegalArgumentException("El valor no está en la cola");
    }
    
    // Elimina el elemento en el índice específico
    public void removeAtIndex(int index) {
        if (index < 0 || index >= elements.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de límites");
        }
        elements.remove(index);
    }
}

