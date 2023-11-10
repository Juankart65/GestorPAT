/**
 * 
 */
package dataStructures;

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
}

