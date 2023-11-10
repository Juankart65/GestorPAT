package dataStructures;

import java.io.Serializable;

/**
 * Node class applying Generics
 * 
 * 
 * 
 **/

public class Node<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Node<T> nextNode;
	private T valueNode;

	/**
	 * Node class constructor
	 * 
	 * @param valueNode Element that is saved in the Node
	 */
	public Node(T valueNode) {
		this.valueNode = valueNode;
	}

	public Node() {
		super();
	}

	/**
	 * Node class constructor
	 * 
	 * @param fact Element that is saved in the Node
	 * @param next Link to the next Node
	 */
	public Node(T fact, Node<T> next) {
		super();
		this.valueNode = fact;
		this.nextNode = next;
	}

	// Get and set methods of the Node class

	public Node<T> getSiguienteNodo() {
		return nextNode;
	}

	public void setSiguienteNodo(Node<T> siguienteNodo) {
		this.nextNode = siguienteNodo;
	}

	public T getValorNodo() {
		return valueNode;
	}

	public void setValorNodo(T valorNodo) {
		this.valueNode = valorNodo;
	}
}
