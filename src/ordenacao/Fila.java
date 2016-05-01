package ordenacao;

import java.util.ArrayList;
import java.util.List;

public class Fila<T> {
	
	private List<T> fila;
	private int inicio;
	private int fim;
	private int size;
	
	public Fila(){
		fila = new ArrayList<>();
		inicio = -1;
		fim = -1;
		size = 0;
	}
	
	public void queueIn(T value){
		fim++;
		size++;
		fila.add(value);
	}
	
	public T queueOut(){
		inicio++;
		T aux = fila.get(inicio);
		size--;
		return aux;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public int getInicio(){
		return this.inicio;
	}
}
