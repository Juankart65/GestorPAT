package dataStructures;

import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * Definici�n de la clase lista Simple de tipo Generics
 * @param <T>
 *
 * **/

public class DoubleList<T> implements Iterable<T>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DoubleNode<T> nodoPrimero;
	private DoubleNode<T> nodoUltimo;
	private int tamanio;


	public DoubleList() {
		nodoPrimero = null;
		nodoPrimero = null;
		tamanio = 0;
	}


	//Metodos basicos


	//Agregar al inicio de la lista
	public void agregarInicio(T valorNodo) {

		DoubleNode<T> nuevoNodo = new DoubleNode<>(valorNodo);

		if(estaVacia())
		{
			nodoPrimero = nodoUltimo = nuevoNodo;
		}
		else
		{
			nuevoNodo.setSiguienteNodo(nodoPrimero);
			nodoPrimero = nuevoNodo;
		}
		tamanio++;
	}


	//Agregar al final de la lista
	public void agregarfinal(T valorNodo) {

		DoubleNode<T> nuevoNodo = new DoubleNode<>(valorNodo);

		if(estaVacia())
		{
			nodoPrimero = nodoUltimo = nuevoNodo;
		}
		else
		{
			nuevoNodo.setSiguienteNodo(nodoPrimero);
			nodoPrimero.setAnteriorNodo(nuevoNodo);
			nodoPrimero = nuevoNodo;
		}
		tamanio++;
	}

	/**
	 * Agrega un valor en la lista en una posici�n espec�fica
	 * @param indice �ndice donde se va a guardar el dato
	 * @param nuevo El valor a guardar
	 */
	public void agregar(T dato, int indice) {

		if(indiceValido(indice)) {

			if(indice==0) {
				agregarInicio(dato);
			}
			else {
				DoubleNode<T> nuevo = new DoubleNode<>(dato);
				DoubleNode<T> actual = obtenerNodo(indice);

				nuevo.setSiguienteNodo(actual);
				nuevo.setAnteriorNodo(actual.getAnteriorNodo());
				actual.getAnteriorNodo().setSiguienteNodo(nuevo);
				actual.setAnteriorNodo(nuevo);

				tamanio++;
			}
		}
	}


	/**
	 * Borra completamente la Lista
	 */
	public void borrarLista() {
		nodoPrimero = nodoUltimo = null;
		tamanio = 0;
	}


	//Obtener Nodo el valor de un Nodo
	public T obtenerValorNodo(int indice) {

		DoubleNode<T> nodoTemporal = null;
		int contador = 0;

		if(indiceValido(indice))
		{
			nodoTemporal = nodoPrimero;

			while (contador < indice) {

				nodoTemporal = nodoTemporal.getSiguienteNodo();
				contador++;
			}
		}

		if(nodoTemporal != null)
			return nodoTemporal.getValorNodo();
		else
			return null;
	}


	//Verificar si indice es valido
	private boolean indiceValido(int indice) {
		if( indice>=0 && indice<tamanio ) {
			return true;
		}
		throw new RuntimeException("�ndice no v�lido");
	}


	//Verificar si la lista esta vacia
	public boolean estaVacia() {
		//		return(nodoPrimero == null)?true:false;
		return nodoPrimero == null && nodoUltimo == null;
	}


	/**
	 * Imprime en consola la lista enlazada
	 */
	public void imprimirLista() {

		DoubleNode<T> aux = nodoPrimero;

		while(aux!=null) {
			System.out.print( aux.getValorNodo()+"\t" );
			aux = aux.getSiguienteNodo();
		}

		System.out.println();
	}

	public void imprimirAtras() {

		for(DoubleNode<T> aux = nodoUltimo; aux!=null; aux = aux.getAnteriorNodo()) {
			System.out.print( aux.getValorNodo()+"\t" );
		}
		System.out.println();

	}


	//Eliminar dado el valor de un nodo
	/**
	 * Elimina un elemento de la lista
	 * @param dato dato a eliminar
	 * @return El dato que se elimina
	 */
	public T eliminar(T dato) {

		DoubleNode<T> nodo = buscarNodo(dato);

		if(nodo!=null) {

			DoubleNode<T> previo = nodo.getAnteriorNodo();
			DoubleNode<T> siguiente = nodo.getSiguienteNodo();

			if(previo==null) {
				nodoPrimero = siguiente;
			}else {
				previo.setSiguienteNodo(siguiente);
			}

			if(siguiente==null) {
				nodoUltimo = previo;
			}else {
				siguiente.setAnteriorNodo(previo);
			}

			nodo=null;
			tamanio--;

			return dato;
		}

		throw new RuntimeException("El valor no existe");
	}


	//Elimina el primer nodo de la lista
	public T eliminarPrimero() {

		if( !estaVacia() ) {
			DoubleNode<T> nodoAux = nodoPrimero;
			T valor = nodoAux.getValorNodo();
			nodoPrimero = nodoAux.getSiguienteNodo();

			if(nodoPrimero==null) {
				nodoUltimo = null;
			}
			else
			{
				nodoPrimero.setAnteriorNodo(null);
			}

			tamanio--;
			return valor;
		}

		throw new RuntimeException("Lista vac�a");
	}


	public T eliminarUltimo() {

		if( !estaVacia() ) {
			T valor = nodoUltimo.getValorNodo();
			DoubleNode<T> prev = obtenerNodo(tamanio-2);
			nodoUltimo = prev;

			if(nodoUltimo==null) {
				nodoPrimero=null;
			}else {
				prev.setSiguienteNodo(null);
			}

			tamanio--;
			return valor;
		}

		throw new RuntimeException("Lista vac�a");
	}


	/**
	 * Devuelve el Nodo de una lista dada su posici�n
	 * @param indice �ndice para obtener el Nodo
	 * @return Nodo objeto
	 */
	private DoubleNode<T> obtenerNodo(int indice) {

		if(indice>=0 && indice<tamanio) {

			DoubleNode<T> nodo = nodoPrimero;

			for (int i = 0; i < indice; i++) {
				nodo = nodo.getSiguienteNodo();
			}

			return nodo;
		}

		return null;
	}

	/**
	 * Devuelve un nodo que contenga un dato espec�fico
	 * @param dato Dato a buscar
	 * @return Nodo
	 */
	private DoubleNode<T> buscarNodo(T dato){

		DoubleNode<T> aux = nodoPrimero;

		while(aux!=null) {
			if(aux.getValorNodo().equals(dato)) {
				return aux;
			}
			aux = aux.getSiguienteNodo();
		}

		return null;
	}



	/**
	 * Cambia el valor de un nodo dada su posici�n en la lista
	 * @param indice posici�n donde se va a cambiar el dato
	 * @param nuevo nuevo valor por el que se actualizar� el nodo
	 */
	public void modificarNodo(int indice, T nuevo) {

		if( indiceValido(indice) ) {
			DoubleNode<T> nodo = obtenerNodo(indice);
			nodo.setValorNodo(nuevo);
		}

	}


	/**
	 * Retorna la primera posici�n donde est� guardado el dato
	 * @param dato valor a buscar dentro de la lista
	 * @return primera posici�n del dato
	 */
	public int obtenerPosicionNodo(T dato) {

		int i = 0;

		for( DoubleNode<T> aux = nodoPrimero ; aux!=null ; aux = aux.getSiguienteNodo() ) {
			if( aux.getValorNodo().equals(dato) ) {
				return i;
			}
			i++;
		}

		return -1;
	}


	/**
	 * Devuelve un elemento de la lista dado su �ndice
	 * @param indice �ndice de la lista
	 * @return dato guardado en el �ndice especificado
	 */
	public T obtener(int indice) {

		if( indiceValido(indice) ) {
			DoubleNode<T> n = obtenerNodo(indice);

			if(n!=null) {
				return n.getValorNodo();
			}
		}

		return null;
	}






	@Override
	public Iterator<T> iterator() {

		return new IteradorListaDoble (nodoPrimero);
	}

	protected class IteradorListaDoble implements Iterator<T>{

		private DoubleNode<T> nodo;
		private int posicion;

		/**
		 * Constructor de la clase Iterador
		 * @param aux Primer Nodo de la lista
		 */
		public IteradorListaDoble(DoubleNode<T> nodo) {
			this.nodo = nodo;
			this.posicion = 0;
		}

		@Override
		public boolean hasNext() {
			return nodo!=null;
		}

		@Override
		public T next() {
			T valor = nodo.getValorNodo();
			nodo = nodo.getSiguienteNodo();
			posicion++;
			return valor;
		}

		public boolean hasPrevious() {
			return nodo!=null;
		}


		public T previous() {
			T aux = nodo.getValorNodo();
			nodo = nodo.getAnteriorNodo();
			posicion--;
			return aux;
		}

		public int nextIndex() {
			return posicion;
		}


		public int previousIndex() {
			return posicion-1;
		}

		public void remove() {
			if(nodo!=null) {
				eliminar(nodo.getValorNodo());
			}
		}

		public void set(T e) {
			if(nodo!=null) {
				nodo.setValorNodo(e);
			}
		}

		public void add(T e) {
			agregarfinal(e);
		}


		/**
		 * Posici�n actual de la lista
		 * @return posici�n
		 */
		public int getPosicion() {
			return posicion;
		}

	}


	public void imprimirHaciaAtras() {

		for(DoubleNode<T> aux = nodoUltimo; aux!=null; aux = aux.getAnteriorNodo()) {
			System.out.print(aux.getValorNodo()+"\t" );
		}
		System.out.println();

	}
	
	//Metodos get y set de la clase ListaSimple


	public DoubleNode<T> getNodoPrimero() {
		return nodoPrimero;
	}


	public void setNodoPrimero(DoubleNode<T> nodoPrimero) {
		this.nodoPrimero = nodoPrimero;
	}


	public int getTamanio() {
		return tamanio;
	}


	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}


	public DoubleNode<T> getNodoUltimo() {
		return nodoUltimo;
	}


	public void setNodoUltimo(DoubleNode<T> nodoUltimo) {
		this.nodoUltimo = nodoUltimo;
	}






}
