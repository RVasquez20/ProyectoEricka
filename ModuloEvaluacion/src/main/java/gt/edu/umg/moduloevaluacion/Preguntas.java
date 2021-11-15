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
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ericka_gonzalez
 */
public class Preguntas {

    private ArrayList<Integer> numeroLineas = new ArrayList<>();
    private HashSet<Integer> numeroPreguntas = new HashSet<>();
    private Scanner datos = new Scanner(System.in);
    private final String rutaPreguntas = "/home/ghostman/volume4/Preguntas.csv";
         private final String rutaRespuestas = "/home/ghostman/volume4/Respuestas.csv";

    public Semaphore semaforo;

    public Preguntas(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    public void procesoEvaluacion(String Categoria) {
        try {
            String lineaLeido = "";
            int noPregunta = 0;
            FileReader archivoPreguntas = new FileReader(rutaPreguntas);
            BufferedReader bffPreguntas = new BufferedReader(archivoPreguntas);
            while ((lineaLeido = bffPreguntas.readLine()) != null) {
                String[] tokens = lineaLeido.split("-");
                noPregunta++;
                if (tokens[0].equals(Categoria)) {
                    
                    numeroLineas.add(noPregunta);
                }
            }
            bffPreguntas.close();
            //se llama a la funcion que servira para elegir las preguntas y que llamara a su vez al proceso
            //de respuesta
            seleccionAleatoriaPreguntas();
        } catch (FileNotFoundException ex) {
            System.out.println("Error -->" + ex.getMessage() + " Hable con un Catedratico para\n que Agrege"
                    + "nuevas Preguntas");
        } catch (IOException ex) {
            System.out.println("Error -->" + ex.getMessage());
        }
    }

    public void seleccionAleatoriaPreguntas() {
        while (numeroPreguntas.size() != 3) {
            
            int aleatorio = (int)(Math.random() * numeroLineas.size());
            numeroPreguntas.add(numeroLineas.get(aleatorio));
        }
        System.out.println(Arrays.asList(numeroLineas));
        System.out.println(Arrays.asList(numeroPreguntas));
        Iterator<Integer> iterador = numeroPreguntas.iterator();
        while (iterador.hasNext()) {
            int numero=iterador.next();
            busquedaPregunta(numero);
        }
    }

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
            System.out.println("Error -->" + ex.getMessage() + " Hable con un Catedratico para\n que Agrege"
                    + "nuevas Preguntas");
        } catch (IOException ex) {
            System.out.println("Error2 -->" + ex.getMessage());
        }
    }

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
                        System.out.println("Error -->" + ex.getMessage());
                    }   
    }

}
