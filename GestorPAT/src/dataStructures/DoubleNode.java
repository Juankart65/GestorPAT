package dataStructures;

import java.io.Serializable;

/**
 * Clase nodo aplicando Generics
 * 
 * 
 * 
 **/

public class DoubleNode<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DoubleNode<T> siguienteNodo;
	private DoubleNode<T> anteriorNodo;
	private T valorNodo;

	/**
	 * Constructor de la clase Nodo
	 * 
	 * @param dato Elemento que se guarda en el Nodo
	 */
	public DoubleNode(T valorNodo) {
		this.valorNodo = valorNodo;
	}

	public DoubleNode() {
		super();
	}

	/**
	 * Constructor de la clase Nodo
	 * 
	 * @param dato      Elemento que se guarda en el Nodo
	 * @param siguiente Enlace al siguiente Nodo
	 */
	public DoubleNode(T dato, DoubleNode<T> siguiente, DoubleNode<T> anterior) {
		super();
		this.valorNodo = dato;
		this.siguienteNodo = siguiente;
		this.anteriorNodo = anterior;
	}

	// Metodos get y set de la clase Nodo

	public DoubleNode<T> getSiguienteNodo() {
		return siguienteNodo;
	}

	public void setSiguienteNodo(DoubleNode<T> siguienteNodo) {
		this.siguienteNodo = siguienteNodo;
	}

	public T getValorNodo() {
		return valorNodo;
	}

	public void setValorNodo(T valorNodo) {
		this.valorNodo = valorNodo;
	}

	public DoubleNode<T> getAnteriorNodo() {
		return anteriorNodo;
	}

	public void setAnteriorNodo(DoubleNode<T> anteriorNodo) {
		this.anteriorNodo = anteriorNodo;
	}

}
