
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Viajero {
	
	public static void generaArchivo(){
		Random rd = new Random();
		for(int i = 0; i < 21; i++){
		int numero = (int)(rd.nextDouble()*1000 + 0);
		try{
			File archivo = new File("Matriz.txt");
			FileWriter escribir = new FileWriter(archivo, true);
			escribir.write(numero + " ");
			escribir.close();
		}
		catch(Exception e){
			System.out.println("Error al escribir");
		}
		}
		
	}
	
	

	public static void main(String [] args){
		generaArchivo();
		
	}
}
