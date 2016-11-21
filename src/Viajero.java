
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
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
	/*
	 * @return int[][] 
	 * 
	 * Funcion que regresa una matriz entera, leida de un archivo de texto
	 */
	public static int[][] leerArchivo() throws IOException{
		String s1;
		BufferedReader br = new BufferedReader(new FileReader("Matriz.txt")); //lee el texto
		String temp="";
		String bfRead;
		
		while((bfRead = br.readLine())!=null)
			//haz el ciclo, mientras bfRead tiene datos
			temp = temp + bfRead;
		
		s1 = temp;// asigna todos los datos a s1
		int [][] matriz = new int [7][7]; 
		StringTokenizer st = new StringTokenizer(s1);
		System.out.println(s1);
		
		while(st.hasMoreTokens()){
			//llena matriz de datos mientras aun haya en st
			for(int i = 0; i < 7; i++){
				for(int j = 0; j < 7; j++){
					matriz[i][j] = Integer.parseInt(st.nextToken());
				}
			}
		}
		br.close();
		return matriz;
	}

	
	
	public static void main(String [] args) throws IOException{
		//generaArchivo();
		leerArchivo();
	}
}











