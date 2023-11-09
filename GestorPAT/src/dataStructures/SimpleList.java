package dataStructures;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * Definition of the Simple list class of type Generics
 * 
 * @param <T>
 * 
 **/

public class SimpleList<T> implements Iterable<T> {

	private Node<T> nodeFirst;
	private Node<T> nodeLast;
	private int size;

	public SimpleList() {
		nodeFirst = null;
		nodeLast = null;
		size = 0;
	}

	// Basic methods

	/**
	 * 
	 * Add to top of list
	 *
	 * @param nodeValue
	 */
	public void addTop(T nodeValue) {

		Node<T> newNode = new Node<>(nodeValue);

		if (itsEmpty()) {
			nodeFirst = newNode;
		} else {
			newNode.setSiguienteNodo(nodeFirst);
			nodeFirst = newNode;
		}
		size++;
	}

	/**
	 * 
	 * Counts how many times a value is repeated in the list
	 *
	 * @param value
	 * @return
	 */
	public int countRepetitions(T value) {
		int robin = 0;
		Node<T> aux = nodeFirst;

		while (aux != null) {
			if (aux.getValorNodo().equals(value)) {
				robin++;
			}
			aux = aux.getSiguienteNodo();
		}

		return robin;
	}

	/**
	 * 
	 * Add to the end of the list 
	 *
	 * @param nodeValue
	 */
	public void addEnd(T nodeValue) {
		Node<T> node = new Node<>(nodeValue);

		if (itsEmpty()) {
			nodeFirst = nodeLast = node;
		} else {
			nodeLast.setSiguienteNodo(node);
			nodeLast = node;
		}

		size++;
	}

	/**
	 * 
	 * Get Node the value of a Node 
	 *
	 * @param index
	 * @return
	 */
	public T getNodeValue(int index) {

		Node<T> tempNode = null;
		int robin = 0;

		if (validIndex(index)) {
			tempNode = nodeFirst;

			while (robin < index) {

				tempNode = tempNode.getSiguienteNodo();
				robin++;
			}
		}

		if (tempNode != null)
			return tempNode.getValorNodo();
		else
			return null;
	}

	/**
	 * 
	 * Check if index is valid
	 *
	 * @param index
	 * @return
	 */
	private boolean validIndex(int index) {
		if (index >= 0 && index < size) {
			return true;
		}
		throw new RuntimeException("invalid index");
	}

	/**
	 * 
	 * Check if the list is empty
	 *
	 * @return
	 */
	public boolean itsEmpty() {
		return (nodeFirst == null) ? true : false;
	}

	/**
	 * 
	 * Print the linked list in the console
	 *
	 */
	public void printList() {

		Node<T> aux = nodeFirst;

		while (aux != null) {
			System.out.print(aux.getValorNodo() + "\t");
			aux = aux.getSiguienteNodo();
		}

		System.out.println();
	}

	/**
	 * 
	 * Delete given the value of a node
	 *
	 * @param fact
	 * @return
	 */
	public T delete(T fact) {
		Node<T> node = nodeFirst;
		Node<T> previous = null;
		Node<T> next = null;
		boolean found = false;

		// find the previous node
		while (node != null) {
			if (node.getValorNodo() == fact) {
				found = true;
				break;
			}
			previous = node;
			node = node.getSiguienteNodo();
		}

		if (found) {
			next = node.getSiguienteNodo();
			if (previous == null) {
				nodeFirst = next;
			} else {
				previous.setSiguienteNodo(next);
			}

			if (next == null) {
				nodeLast = previous;
			} else {
				node.setSiguienteNodo(null);
			}

			node = null;
			size--;
			return fact;
		}
		throw new RuntimeException("The element does not exist");
	}

	/**
	 * 
	 * Remove the first node from the list 
	 *
	 * @return
	 */
	public T deleteFirst() {

		if (!itsEmpty()) {
			Node<T> n = nodeFirst;
			T value = n.getValorNodo();
			nodeFirst = n.getSiguienteNodo();

			if (nodeFirst == null) {
				nodeLast = null;
			}

			size--;
			return value;
		}

		throw new RuntimeException("Empty list");
	}

	/**
	 * 
	 * This method obtains a node according to an index
	 *
	 * @param index
	 * @return
	 */
	public Node<T> getNode(int index) {

		if (index >= 0 && index < size) {

			Node<T> node = nodeFirst;

			for (int i = 0; i < index; i++) {
				node = node.getSiguienteNodo();
			}

			return node;
		}

		return null;
	}

	/**
	 * Changes the value of a node given its position in the list
	 * 
	 * @param index position where the data is going to be changed
	 * @param newN  new value by which the node will be updated
	 */
	public void modifyNode(int index, T newN) {

		if (validIndex(index)) {
			Node<T> nodo = getNode(index);
			nodo.setValorNodo(newN);
		}

	}

	/**
	 * Returns the first position where the data is saved
	 * 
	 * @param fact value to search within the list
	 * @return first position of the data
	 */
	public int getNodePosition(T fact) {

		int i = 0;

		for (Node<T> aux = nodeFirst; aux != null; aux = aux.getSiguienteNodo()) {
			if (aux.getValorNodo().equals(fact)) {
				return i;
			}
			i++;
		}

		return -1;
	}

	@Override
	public Iterator<T> iterator() {

		return new SimpleListIterator(nodeFirst);
	}

	public class SimpleListIterator implements Iterator<T> {

		private Node<T> node;
		private int position;

		/**
		 * Constructor de la clase Iterador
		 * 
		 * @param aux Primer Nodo de la lista
		 */
		public SimpleListIterator(Node<T> node) {
			this.node = node;
			this.position = 0;
		}

		@Override
		public boolean hasNext() {
			return node != null;
		}

		@Override
		public T next() {
			T value = node.getValorNodo();
			node = node.getSiguienteNodo();
			position++;
			return value;
		}

		/**
		 * Current position of the list
		 * 
		 * @return position
		 */
		public int getPosition() {
			return position;
		}

	}

	/**
	 * 
	 * Get method of the ListaSimple class
	 *
	 * @return
	 */
	public Node getNodeFirst() {
		return nodeFirst;
	}

	/**
	 * 
	 * Set method of the ListaSimple class
	 *
	 * @param nodeFirst
	 */
	public void setNodeFirst(Node nodeFirst) {
		this.nodeFirst = nodeFirst;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * 
	 * Method that converts a SimpleList into an ArrayList
	 *
	 * @param <T>
	 * @param simpleList
	 * @return
	 */
	public <T> ArrayList<T> convertArraylist(SimpleList<T> simpleList) {
	    ArrayList<T> arrayList = new ArrayList<T>();

	    for (T item : simpleList) {
	        arrayList.add(item);
	    }

	    return arrayList;
	}
	
	/**
	 * 
	 * Method that eliminates a node using an index 
	 *
	 * @param index
	 */
	public void deleteNode(int index) {
	    if (itsEmpty()) {
	        return; // The list is empty, there is nothing to delete.
	    }

	    if (index == 0) {
	        // If the index is 0, delete the first node
	        deleteFirst();
	        return;
	    }

	    if (index >= size || index < 0) {
	        throw new IllegalArgumentException("Invalid index");
	    }

	    Node<T> currentNode = nodeFirst;
	    Node<T> nodePrevious = null;
	    int robin = 0;

	    while (robin < index) {
	        nodePrevious = currentNode;
	        currentNode = currentNode.getSiguienteNodo();
	        robin++;
	    }

	    // At this point, currentnode points to the node we want to delete
	    // and nodePrevious points to the node before it

	    nodePrevious.setSiguienteNodo(currentNode.getSiguienteNodo());
	    size--;
	}
}
