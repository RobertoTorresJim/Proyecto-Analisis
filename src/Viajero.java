
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import javax.annotation.processing.SupportedSourceVersion;

public class Viajero {
	static long ob = 0;
	
	public static void generaArchivo(int n) throws IOException{
		Random rd = new Random();
		File archivo = new File("tsp_535.txt");
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
	public static ArrayList<Integer> rutaParcial(ArrayList<Integer> ruta, ArrayList<Integer> rutaParcial){
		for(int i = 0; i < ruta.size(); i++){
			for(int j = 0; j < rutaParcial.size(); j++){
				if(ruta.get(i) == rutaParcial.get(j)){
					ruta.remove(i);
				}
			}
		}return ruta;
	}
	
	static Nodo arbol = new Nodo();
	
	LinkedList<Nodo> cabecera = new LinkedList<Nodo>();
	int acumulado = 0;
	public static Nodo viajero_BB(ArrayList<Integer>  ruta,
			ArrayList<Integer> rutaParcial,
			int [][] matrizDeDistancias,
			int index,
			int ac ){
		
		if(index == 0){
			return null;
		}
		else{
			ArrayList <Integer> rutaFaltante = new ArrayList<Integer>(); 
			rutaFaltante = rutaParcial(ruta, rutaParcial);
			LinkedList<Nodo> aux = new LinkedList<Nodo>();
			//ArrayList <Integer> rutaAux = new ArrayList<Integer>();
			for(int i = 0; i < rutaFaltante.size(); i++){
				//System.out.println(ruta.get(i));
				rutaParcial.add(rutaFaltante.get(i));
				//rutaAux = rutaParcial;
				//System.out.println(rutaParcial.get(i));
				Nodo hijo = new Nodo();
				hijo.acumulado = ac + matrizDeDistancias[rutaParcial.get(rutaParcial.size()-2)][rutaParcial.get(rutaParcial.size()-1)];
				hijo.getRutaP(rutaParcial);
				//System.out.println(hijo.rutaParcial);
				hijo.cotainf = cotaInfAc(matrizDeDistancias, hijo.rutaP);
				aux.add(hijo);
				rutaParcial.remove(rutaParcial.size()-1);
			}
			for(int i = 0; i < aux.size(); i++){
				System.out.println(aux.get(i).rutaP);
			}
			arbol.hijos.add(comparaNodos(aux));
			//System.out.println(arbol.hijos.getFirst().rutaParcial);
			viajero_BB(ruta, arbol.hijos.getLast().rutaP, matrizDeDistancias, index-1, arbol.hijos.getLast().acumulado);
			}
		return arbol;
		}
	
	public static Nodo comparaNodos(LinkedList<Nodo> aux){
		
		Nodo result = new Nodo();
		LinkedList<Nodo> cotaRep = new LinkedList<Nodo>();
		int cotamin = aux.getFirst().cotainf;
		int acmin;
		for(int i = 1; i < aux.size(); i++){
			if(cotamin > aux.get(i).cotainf){
				cotamin = aux.get(i).cotainf;
			}
		}
		for(int i = 0; i < aux.size(); i++){
			if(cotamin == aux.get(i).cotainf){
				cotaRep.add(aux.get(i));
			}
		}
		if(cotaRep.size() == 1)
			return cotaRep.getFirst();
		else{
			acmin = cotaRep.getFirst().acumulado;
			for(int i = 0; i < cotaRep.size(); i++){
				if(acmin >= cotaRep.get(i).acumulado){
					result = cotaRep.get(i);
				}
			}
			return result;
			
		}
		
	}

	public static int cotaInfAc (int [][] matrizDeDistancias, ArrayList<Integer> rutaParcial){
				
		double cotainf = 0.0;
		int min = (int)(Double.POSITIVE_INFINITY);
		int i;
		
		for(i = 0; i < matrizDeDistancias.length; i++){
			if(i == rutaParcial.get(rutaParcial.size()-1) || i == rutaParcial.get(rutaParcial.size()-2)){
				if(i == rutaParcial.get(rutaParcial.size()-1)){
					if(matrizDeDistancias[rutaParcial.get(rutaParcial.size()-1)][i] != 0){
						if(i != rutaParcial.get(rutaParcial.size()-1)){
							min = matrizDeDistancias[rutaParcial.get(rutaParcial.size()-1)][i];
							for(int j = i; j < matrizDeDistancias.length; j++){
								if(min > matrizDeDistancias[rutaParcial.get(rutaParcial.size()-1)][j]){
									if(j != rutaParcial.get(rutaParcial.size()-2) && matrizDeDistancias[rutaParcial.get(rutaParcial.size()-1)][j] != 0){
										min = matrizDeDistancias[rutaParcial.get(rutaParcial.size()-1)][j];
										cotainf = cotainf + min;
										min = (int)(Double.POSITIVE_INFINITY);
									}
								}
							}
						}
					}
				}
				else{
					if(matrizDeDistancias[rutaParcial.get(rutaParcial.size()-2)][i] != 0){
						if(i != rutaParcial.get(rutaParcial.size()-1)){
							min = matrizDeDistancias[rutaParcial.get(rutaParcial.size()-2)][i];
							for(int j = i; j < matrizDeDistancias.length; j++){
								if(min > matrizDeDistancias[rutaParcial.get(rutaParcial.size()-2)][j]){
									if(j != rutaParcial.get(rutaParcial.size()-1) && matrizDeDistancias[rutaParcial.get(rutaParcial.size()-2)][j] != 0){
										min = matrizDeDistancias[rutaParcial.get(rutaParcial.size()-2)][j];
										cotainf = cotainf + min;
										min = (int)(Double.POSITIVE_INFINITY);
									}
								}
							}	
						}
					}
				}
			}//fin afectado
			else{
				for (int j = 0; j < matrizDeDistancias.length; j++){
					if(matrizDeDistancias[i][j] != 0 && min > matrizDeDistancias[i][j]){
						min = matrizDeDistancias[i][j];
					}
				}
				cotainf = cotainf + min;
				min = (int)(Double.POSITIVE_INFINITY);
			}
		}
		return (int)Math.ceil(cotainf/2);
	}
	
	public static int cotaInf (int [][] matrizDeDistancias){
		double cotainf = 0.0;
		int min1, min2; 
		int y = -1;
		
		for(int i = 0; i < matrizDeDistancias.length; i++ ){
			if(matrizDeDistancias[i][0] == 0){
				min1 = matrizDeDistancias[i][1];
				min2 = matrizDeDistancias[i][2];
			}
			else{
				if(matrizDeDistancias[i][1] == 0){
					min1 = matrizDeDistancias[i][0];
					min2 = matrizDeDistancias[i][3];
				}
				else{
					min1 = matrizDeDistancias[i][0];
					min2 = matrizDeDistancias[i][1];
				}
			}
			for(int j = 0; j < matrizDeDistancias.length; j++){
				if(i != j){
					if(min1 > matrizDeDistancias[i][j]){
						min1 = matrizDeDistancias[i][j];
						y = j;
					}
				}
			}
			cotainf = cotainf + min1;
			for(int j = 0; j < matrizDeDistancias.length; j++){
				if(i != j){
					if(y != j && min2 > matrizDeDistancias[i][j]){
						min2 = matrizDeDistancias[i][j];
					}
				}
			}cotainf = cotainf + min2;
			
		}
		return (int)Math.ceil(cotainf/2);
	}

	public void  ACO_TSP(double alpha, double beta, double ro, int numHormigas, int numIteraciones, String nombreArchvo) throws IOException{
		DatosACO inicializaDatos = inicializaDatos(nombreArchvo, numHormigas);
		ArrayList<Hormiga> hormigas = inicializaDatos.hormigas;
		int [][] MatrizDistancias = inicializaDatos.matrizDistancias;
		double [][] heuristica = inicializaDatos.heuristica;
		double [][] feromonas = inicializaDatos.feromonas;
		double [][] infoSeleccionada = inicializaDatos.infoSeleccionada;
		
		int iteraciones = 0;
		while(iteraciones < numIteraciones){
			hormigas = construyeSelecciones(hormigas, numHormigas, MatrizDistancias, infoSeleccionada);
			
			hormigas = hillClimbing(hormigas, MatrizDistancias);
			
			//FALTA CODIGO
			
		}
		
	}
	public ArrayList<Hormiga> construyeSelecciones(ArrayList<Hormiga> hormigas,
			int numHormigas,
			int [][] matrizDistancias,
			double[][] infoSeleccionada){
		Random random = new Random();
		for(int i = 0; i < numHormigas; i++){
			for(int j = 0; j < matrizDistancias.length; j++)
				hormigas.get(i).no_visitadas.set(j, j);
				hormigas.get(i).ruta
		}
		int paso = 1;
		for(int i = 0; i < numHormigas; i++){
			 int r = (int)(random.nextDouble()*matrizDistancias.length + 0);
			 hormigas.get(i).ruta.set(paso, r);
			 hormigas.get(i).no_visitadas = rutaParcial( hormigas.get(i).no_visitadas,hormigas.get(i).ruta);
		}
		while(paso < matrizDistancias.length){
			paso++;
			for ( int k = 0; k < numHormigas; k++){
				reglaDecision(k, paso);
			}
			for (int k = 0; k < numHormigas; k++){
				hormigas.get(k).longitudRuta = calculaLongitudRuta(k);
			}
		}
		
		return ;
	}
	
	public ArrayList<Hormiga> hillClimbing(ArrayList<Hormiga> hormigas, int [][] matrizDistancias){
		return ;
	}
	public double [][] heuristica(int [][] matrizDistancias){
		double [][] heuristica = new double [matrizDistancias.length][matrizDistancias.length];
		for(int i = 0; i < matrizDistancias.length; i++){
			for(int j = 0; j < matrizDistancias.length; j++){
				if(i != j)
					heuristica[i][j] = 1/matrizDistancias[i][j];
				
				else heuristica[i][j] = 0;
			}
		}return heuristica;
	}
	
	public DatosACO inicializaDatos(String nombreArchivo, int numHormigas) throws IOException{
		DatosACO datos = new DatosACO();
		datos.matrizDistancias = leerArchivo(535);
		datos.heuristica = heuristica(datos.matrizDistancias);
		for(int i = 0; i < datos.matrizDistancias.length; i++){
			for(int j = 0; j < datos.matrizDistancias.length; j++){
				datos.feromonas[i][j] = 0;
				datos.infoSeleccionada[i][j] = 0;
			}
		}
		return datos;
	}
	
	public static void main(String [] args) throws IOException{
		generaArchivo(535);
		int [][] ejemplo = leerArchivo(535);
		Nodo ejemploNodo = new Nodo();
		ejemploNodo.acumulado = 0;
		System.out.println(ejemploNodo.cotainf = cotaInf(ejemplo));
		ArrayList<Integer> rutaP = new ArrayList<Integer>();
		rutaP.add(0);
		ejemploNodo.getRutaP(rutaP);
		ArrayList<Integer> ruta = new ArrayList<Integer>();
		for(int i = 0; i < 535; i++){
			ruta.add(i);
		}
		System.out.println(ruta);
		Nodo prueba = viajero_BB(ruta, ejemploNodo.rutaP, ejemplo, 534, ejemploNodo.acumulado);
		System.out.println(prueba.hijos.getLast().acumulado);
	}
}