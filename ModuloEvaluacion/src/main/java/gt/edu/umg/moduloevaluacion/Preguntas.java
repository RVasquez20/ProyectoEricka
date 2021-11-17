/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.moduloevaluacion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
/**
 *
 * @author ericka_gonzalez
 */
public class Preguntas {

    private ArrayList<Integer> numeroLineas = new ArrayList<>();
    private HashSet<Integer> numeroPreguntas = new HashSet<>();
    private Scanner datos = new Scanner(System.in);
    private final String rutaPreguntas = "/usr/src/ProyectoFinal/Datos/preguntas.txt";
    private final String rutaRespuestas = "/usr/src/ProyectoFinal/Datos/respuestas.txt";

    public Semaphore semaforo;

    public Preguntas(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    /**
     * Este proceso es el encargado de almacenar el numero de linea en el cual
     * se encuentre alguna pregunta con la categoria que fue seleccionada por el
     * estudiante y fue enviada como parametro.
     * @param categoria 
     */
    public void procesoEvaluacion(String categoria) {
        try {
            numeroLineas.clear();
            String lineaLeido = "";
            int noPregunta = 0;
            FileReader archivoPreguntas = new FileReader(rutaPreguntas);
            BufferedReader bffPreguntas = new BufferedReader(archivoPreguntas);
            while ((lineaLeido = bffPreguntas.readLine()) != null) {
                String[] tokens = lineaLeido.split("-");
                noPregunta++;
                if (tokens[0].equals(categoria)) {
                    
                    numeroLineas.add(noPregunta);
                }
            }
            bffPreguntas.close();
            /*se llama a la funcion que servira para elegir las preguntas y que llamara a su vez al proceso
            de respuesta*/
            seleccionAleatoriaPreguntas();
        } catch (FileNotFoundException ex) {
            System.out.println("Error :" + ex.getMessage() + " Hable con un Catedratico para\n que Agrege"
                    + "nuevas Preguntas");
        } catch (IOException ex) {
            System.out.println("Error :" + ex.getMessage());
        }
    }

    /**
     * Este proceso es el encargado de seleccionar las 3 preguntas aleatorias,
     * selecciona 3 numeros de preguntas aleatorio y los almacena para luego
     * iterar sobre cada uno de ellos con un Iterator y llamar al proceso busquedaPregunta.
     */
    public void seleccionAleatoriaPreguntas() {
        numeroPreguntas.clear();
        while (numeroPreguntas.size() != 3) {
            
            int aleatorio = (int)(Math.random() * numeroLineas.size());
            numeroPreguntas.add(numeroLineas.get(aleatorio));
        }
        Iterator<Integer> iterador = numeroPreguntas.iterator();
        while (iterador.hasNext()) {
            int numero=iterador.next();
            //se llama a esta funcion para buscar la pregunta correspondiente al numero
            busquedaPregunta(numero);
        }
    }

    /**
     *Este proceso se encarga de buscar la pregunta en el archivo preguntas
     * segun el numero recibido como parametro para poder enviar los datos tanto 
     * la categoria, como la pregunta y el numero de pregunta al proceso evaluacionEstudiante.
     * @param numeroDeLinea 
     */
    private void busquedaPregunta(int numeroDeLinea) {
        try {
            String lineaLeido = "";
            int noPregunta = 0;
            FileReader archivoPreguntas = new FileReader(rutaPreguntas);
            BufferedReader bffPreguntas = new BufferedReader(archivoPreguntas);
            while ((lineaLeido = bffPreguntas.readLine()) != null) {
                String[] tokens = lineaLeido.split("-");
                noPregunta++;
                if (noPregunta == numeroDeLinea) {
                    evaluacionEstudiante(tokens[0], tokens[1],noPregunta);
                }
            }
            archivoPreguntas.close();
            bffPreguntas.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Error :" + ex.getMessage() + " Hable con un Catedratico para\n que Agrege"
                    + "nuevas Preguntas");
        } catch (IOException ex) {
            System.out.println("Error :" + ex.getMessage());
        }
    }

    /**
     * Este proceso es el encargado de mostrar al estudiante la categoria, pregunta 
     * y quedar a la espera de la respuesta, la categoria, la pregunta, y el numero de pregunta
     * son recibidos como parametros.
     * Luego que el estudiante ingrese la respuesta, verifica el semaforo, si esta disponible
     * lo cambia y escribe en el archivo de respuestas con el formato correcto los datos nuevos
     * (categoria,numero de pregunta,Respuesta del estudiante). Al finalizar la escritura
     * de los datos correspondientes en el archivo, libera el semaforo.
     * @param categoriaSelecta
     * @param pregunta
     * @param numero 
     */
    public void evaluacionEstudiante(String categoriaSelecta, String pregunta,int numero) {
             String Respuesta = "";
                    System.out.println("Categoria:"+categoriaSelecta);
                    System.out.println("Pregunta:" + pregunta);
                    System.out.println("Respuesta:");
                    Respuesta = datos.nextLine();

                    try {
                        
                        semaforo.acquire();
                        FileWriter escribir = new FileWriter(rutaRespuestas, true);
                        escribir.append(categoriaSelecta + "-" + numero + "-" + Respuesta + "\n");
                        escribir.flush();
                        escribir.close();
                        semaforo.release();
                    } catch (Exception ex) {
                        System.out.println("Error :" + ex.getMessage());
                    }   
    }

}
