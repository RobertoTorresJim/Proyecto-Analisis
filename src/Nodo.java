import java.util.ArrayList;
import java.util.LinkedList;

public class Nodo {
	int acumulado;
	int peso;
	int cotainf;
	Object [] rutaPA;

	ArrayList<Integer> rutaP = new ArrayList<Integer>();
	LinkedList<Nodo> hijos = new LinkedList<Nodo>();
	
	public void getRutaP(ArrayList<Integer> rutaP){
		for(int i = 0; i < rutaP.size(); i++){
			this.rutaP.add(rutaP.get(i));
		}
	}
}
