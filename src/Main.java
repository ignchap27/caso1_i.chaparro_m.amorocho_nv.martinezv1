

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	int P; //tamanio pagina
	int NF; // numero filas
	int NC; //numero columnas
	int NR; //numero referencias
	int NP; //numero de paginas virtuales

	// Opción 1: Método para generar las referencias
	public void generarReferencias(Scanner scanner) {
		System.out.println("Generar Referencias.");
		System.out.println("Tamanio de pagina: ");
		P = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("Ruta del archivo: ");
		String input = scanner.nextLine();
		Imagen img = new Imagen(input);
		int longitud = img.leerLongitud();
		
		
		System.out.println("P=" + P);
		char[] vector = new char[longitud];
		img.recuperar(vector, longitud);
		NF = img.alto;
		NC = img.ancho;
		System.out.println("NF=" + img.alto);
		System.out.println("NC=" + img.ancho);
		
		NR = (longitud * 17) + 16;
		System.out.println("NR=" + NR);
		
		double div = (double)longitud/P;
		int res = (int) Math.ceil(div);
		NP = ((img.alto * 3 * img.ancho)/P)+ res;
		
		System.out.println("NP=" + NP);
		byte[][][] copyImg = img.imagen.clone();
		
		
		int longitudMensaje = img.leerLongitud(); //en caracteres
		
		
		char[] componentes = {'R','G','B'};
		int desplazamiento, desplazamientoMensaje, pagMensaje, posCaracter;
		
		int bytesTotales =0;
		pagMensaje = NP-res;
		/*/
		if((16 + (longitudMensaje * 8))%P == 0) {
			pagMensaje--;
		}
		/*/
		
		int bitsCaracter = 0;
		
		
		String folderPath = "src/Referencias";
		String filePath = folderPath + "/referencias.txt";
		File folder = new File(folderPath);
		
		 if (!folder.exists()) {
	            folder.mkdirs();
	        }
		 
		 String txtReferencias = "";
		 
		 		
		for(int i=0; i<copyImg.length;i++) { //fila i
			  for(int j =0; j< copyImg[0].length; j++) { // columna j
				  for(int k =0; k< copyImg[0][0].length; k++) { //byte k = R,G,B
					  
					  if(bytesTotales < (longitudMensaje*8)+16) {
						  int pagina = bytesTotales / P;
						  desplazamiento = bytesTotales % P;
						  char comp = componentes[k%3];
						  System.out.printf("Imagen[%d][%d]%c, %d, %d, R \n",i,j,comp,pagina,desplazamiento);
						  txtReferencias += "Imagen["+i+"]["+j+"]."+comp+", "+pagina+", "+desplazamiento+", R \n";


						  if(bytesTotales >= 15) {
							  posCaracter = bitsCaracter/8;
							  						  
							  if(bytesTotales != 15) {
								  desplazamientoMensaje = ((img.alto * 3 * img.ancho) + (bitsCaracter/8))%P;
								 
								  System.out.printf("Mensaje[%d], %d, %d, W \n", posCaracter, pagMensaje, desplazamientoMensaje);
								  txtReferencias += "Mensaje["+posCaracter+"], "+pagMensaje+", "+desplazamientoMensaje+", W \n";
								  bitsCaracter++;
							  }
							  
							  //Si estoy iniciando un caracter primero lo inicializo en 0 en el vector-> el siguiente
							  if((bytesTotales+1)%8 == 0 &&  bytesTotales+1 != (longitudMensaje*8)+16) {
								  posCaracter = bitsCaracter/8;
								  int nextDespl = (((img.alto * 3 * img.ancho) + (bitsCaracter/8))+1)%P;
								  int nextPg = pagMensaje;
								  if(nextDespl == 0 && bitsCaracter%8 ==0) {
									  nextPg++;
									}
								  System.out.printf("--Mensaje[%d], %d, %d, W \n", posCaracter, nextPg, nextDespl-1);
								  txtReferencias += "--Mensaje["+posCaracter+"], "+nextPg+", "+(nextDespl-1)+", W \n";
								  pagMensaje = nextPg;
							  }

						  }

						  bytesTotales ++;

					  }
				  }
			  }
		}
		
		System.out.println("Bytes totales: "+bytesTotales);
		System.out.println("bitsCaracter: " + bitsCaracter);
		
		try {
            // Crear el objeto FileWriter y BufferedWriter
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("P="+P+" y longitud= "+longitud+"\n");
			bufferedWriter.write("NF="+NF+"\n");
			bufferedWriter.write("NC="+NC+"\n");
			bufferedWriter.write("NR="+NR+"\n");
			bufferedWriter.write("NP="+NP+"\n");
            bufferedWriter.write(txtReferencias);
            bufferedWriter.close();
            
            System.out.println("Archivo generado con éxito");
            
            
            }catch (IOException e) {
                e.printStackTrace();
            }
		
	}
	
	
	// Opción 2: Método para calcular número de fallas de página, porcentaje de hits y tiempos
		 public void calcularFallosYPorcentajeHits() {
			 System.out.println("Fallos y porcentajes");
		 }

	public static void main(String[] args) {
		boolean continuar= true;
		boolean referencias = false;
		Main main = new Main();
		
		
		while(continuar) {
			System.out.println("---- Menu Main T ----");

			Scanner scanner = new Scanner(System.in);
			System.out.println("Elija una opción:");
			System.out.println("1. Generación de las referencias.");
			System.out.println("2. Calcular datos buscados: número de fallas de página, porcentaje de hits, tiempos.");
			System.out.println("3. Salir");

			
			int opcion = scanner.nextInt();
			scanner.nextLine();

			if (opcion == 1) {
				// Opción 1: Generar referencias de página
				main.generarReferencias(scanner);
				referencias = true;

			} else if (opcion == 2) {
				// Opción 2: Calcular número de fallas de página, hits, tiempos
				if (!referencias) {
					System.out.println("Primero debe generar las referencias en la opción 1.");
				} else {
					main.calcularFallosYPorcentajeHits();
				}
			} 

			else if(opcion == 3) {
				continuar = false;
				scanner.close();
			}

			else {
				System.out.println("Opción no válida. Por favor, elija 1 o 2.");
			}
			
		}
	}
	
	 
	 
	
	

}

