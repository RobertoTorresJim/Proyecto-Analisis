
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Viajero {
	static long ob = 0;
	
	public static void generaArchivo(int n) throws IOException{
		Random rd = new Random();
		File archivo = new File("Matriz.txt");
		if(archivo.exists() && archivo.delete()){
			//System.out.println("Archivo borrado");
			if(archivo.createNewFile()){
				//System.out.println("Nuevo archivo creado");
			}
		}
		
		FileWriter escribir = new FileWriter(archivo, true);
		int [] numeros = new int [((n*n)-n)/2];
		for(int i = 0; i < (((n*n)-n)/2); i++){
			numeros[i] = (int)(rd.nextDouble()*1000 + 0);
		}
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){	
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
	public static int[][] leerArchivo(int n) throws IOException{
		String s1;
		BufferedReader br = new BufferedReader(new FileReader("Matriz.txt")); //lee el texto
		String temp="";
		String bfRead;
		
		while((bfRead = br.readLine())!=null)
			//haz el ciclo, mientras bfRead tiene datos
			temp = temp + bfRead;
		
		s1 = temp;// asigna todos los datos a s1
		int [][] matriz = new int [n][n]; 
		StringTokenizer st = new StringTokenizer(s1);
		while(st.hasMoreTokens()){
			//llena matriz de datos mientras aun haya en st
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					matriz[i][j] = Integer.parseInt(st.nextToken());
				}
			}
		}
		br.close();
		return matriz;
	}

	public static Resultado_FB viajero_fb(int [] ruta, int l, int [][] matrizDistancias, int distancia){
		Resultado_FB resultado = new Resultado_FB();
		
		int n = ruta.length;
		
		if(l == n-1){
			resultado.distancia = distancia + matrizDistancias[ruta[n-1]][ruta[0]];
			
		}
		else{
			resultado.distancia = (int)(Double.POSITIVE_INFINITY);
			for(int i = l; i < n; i++){
				int temp = ruta[l];
				ruta[l] = ruta[i];
				ruta[i] = temp;
				int nuevaDistancia = distancia + matrizDistancias[ruta[l]][ruta[l+1]];//OB
				ob ++;
				resultado.distancia = Math.min(resultado.distancia, viajero_fb(ruta, l+1, matrizDistancias, nuevaDistancia).distancia);
				temp = ruta[l];
				ruta[l] = ruta[i];
				ruta[i] = temp;
			}
		}
		resultado.ruta = ruta;
		resultado.ob = ob;
		return  resultado;
	}
	public ArrayList<Integer> rutaParcial(ArrayList<Integer> ruta, ArrayList<Integer> rutaParcial){
		for(int i = 0; i < ruta.size(); i++){
			for(int j = 0; j < rutaParcial.size(); j++){
				if(ruta.get(i) == rutaParcial.get(j)){
					ruta.remove(i);
				}
			}
		}return ruta;
	}
	
	Nodo arbol = new Nodo();
	
	LinkedList<Nodo> cabecera = new LinkedList<Nodo>();
	int acumulado = 0;
	public Nodo viajero_BB(ArrayList<Integer>  ruta, ArrayList<Integer> rutaParcial, int [][] matrizDeDistancias, int index, int ac ){
		ruta = rutaParcial(ruta, rutaParcial);
		for(int i = 0; i < index; i++){
			rutaParcial.add(ruta.get(i));
			Nodo hijo = new Nodo();
			hijo.acumulado = ac+matrizDeDistancias[rutaParcial.get(rutaParcial.size()-2)][rutaParcial.get(rutaParcial.size()-1)];
			hijo.rutaParcial = rutaParcial;
			rutaParcial.remove(rutaParcial.size()-1);
		}
	}
	
	public Nodo comparaNodos(){
		
	}
	
	
	public int cotaInf (int [][] matrizDeDistancias){
		double cotainf = 0;
		int min1, min2; 
		int x = -1; 
		int y = -1;
		min1 = matrizDeDistancias[0][1];
		min2 = matrizDeDistancias[0][2];
		for(int i = 0; i < matrizDeDistancias.length; i++ ){
			for(int j = 0; j < matrizDeDistancias.length; j++){
				if(matrizDeDistancias[i][j] == 0){
					min1 = matrizDeDistancias[i][j+1];
					x = j;
				}
				else{
					min1 = matrizDeDistancias[i][j];
					x = j;
				}
				if(i != j){
					if(min1 > matrizDeDistancias[i][j]){
						min1 = matrizDeDistancias[i][j];
						y = j;
					}
				}
			}cotainf = cotainf + min1;
			for(int j = 0; j < matrizDeDistancias.length-1; j++){
				if(matrizDeDistancias[i][j] == 0){
					if(x == j+1){
						min2 = matrizDeDistancias[i][j+2];	
					}
				}
				else{
					if(x == j){
						if(matrizDeDistancias[i][j+1] == 0){
							min2 = matrizDeDistancias[i][j+2];
						}
						else{
							min2 = matrizDeDistancias[i][j+1];
						}
				}
					
				if(i != j){
					if(min2 > matrizDeDistancias[i][j] && y != j){
						min2 = matrizDeDistancias[i][j];
					}
				}
			}cotainf = cotainf + min2;
			
			}
		}
		return (int)Math.ceil(cotainf/2);
	}
	
	
	public static void main(String [] args) throws IOException{
		
		/*
		double promedio = 0;
		for(int i = 4; i < 14; i++){
			for(int k = 0; k < 50; k++){
				generaArchivo(i);
				int [][] result = leerArchivo(i);
				int [] inicio = new int [i];
				for(int j = 0; j < i; j++){
					inicio[j] = j;
				}
				Resultado_FB resultado = viajero_fb(inicio, 0, result, 0);
				promedio = promedio + resultado.ob;
			}
			System.out.println(promedio/50);
			promedio = 0;
		}
		*/
		Nodo ejemplo = new Nodo();
		System.out.println("Termina");
	}
}