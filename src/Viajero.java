
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Viajero {
	
	public static void generaArchivo() throws IOException{
		Random rd = new Random();
		File archivo = new File("Matriz.txt");
		FileWriter escribir = new FileWriter(archivo, true);
		int [] numeros = new int [21];
		for(int i = 0; i < 21; i++){
			numeros[i] = (int)(rd.nextDouble()*1000 + 0);
		}
		for(int k=0; k<21;k++){
			System.out.println(numeros[k]);
		}
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 7; j++){	
					if(i == j)
						escribir.write(0 + " ");
					else{
						if(i < j){
							if(i == 0)
								escribir.write(numeros[j - 1] + " ");
							else
								escribir.write(numeros[i + j] + " ");
						}
						else{
							if(j == 0)
								escribir.write(numeros[i - 1] + " ");
							else
								escribir.write(numeros[i + j]+ " ");
						}
					}
				}
			escribir.write("\r\n");
		}
		escribir.close();
	}

	public static void main(String [] args) throws IOException{
		generaArchivo();
		
	}
}
