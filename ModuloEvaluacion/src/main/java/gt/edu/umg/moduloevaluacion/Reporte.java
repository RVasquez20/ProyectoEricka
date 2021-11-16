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
import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ericka_gonzalez
 */
public class Reporte extends Thread {

    public Semaphore semaforo;
    private final String rutaPreguntas = "/usr/src/ProyectoFinal/Datos/preguntas.txt";
    private final String rutaRespuestas = "/usr/src/ProyectoFinal/Datos/respuestas.txt";
    private final String rutaReporte = "/usr/src/ProyectoFinal/Reporte/respuestas.html";

    public Reporte(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    /**
     * Este proceso es el encargado desde que se incia el programa de
     * verficar el semaforo , si esta disponible lo cambia y lee el archivo de 
     * respuestas, almacena la categoria, y la respuesta, el numero de pregunta se lo envia
     * a la funcion pregunta para obtener la pregunta correspondiente.
     * Luego escribe en el archivo Respuestas.html que es el reporte los datos
     * con el formato correspondiente, luego de terminar con cada uno de los registros
     * en el archivo de respuestas,libera el semaforo y espera la cantidad de 
     * 3 segundos para repetir todo el proceso.
     */
    @Override
    public void run() {
        while (true) {
            String texto = "";
            try {
                semaforo.acquire();
                FileReader lecturaRespuestas = new FileReader(rutaRespuestas);

                FileWriter escribirReporte = new FileWriter(rutaReporte);
                BufferedReader bufferLectura = new BufferedReader(lecturaRespuestas);
                escribirReporte.write("<!DOCTYPE html>\n"
                        + "<html lang=\"en\">\n"
                        + "<head>\n"
                        + "<title>Reporte</title>\n"
                        + "<meta charset=\"utf-8\">\n"
                        + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                        + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                        + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">\n"
                        + "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n"
                        + "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js\"></script>\n"
                        + "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js\"></script>"
                        + "</head>\n"
                        + "<body>\n"
                        + "<div class=\"container\">\n"
                        + "  <table class=\"table table-danger\">\n"
                        + "    <thead style='background: #B57EDC ;'>"
                        + "      <tr class=\"info\">\n"
                        + "        <td>Categoria</td>\n"
                        + "        <td>Pregunta</td>\n"
                        + "        <td>Respuesta</td>\n"
                        + "      </tr>\n"
                        + "      </thead>\n"
                        + "       <tbody>\n");
                while ((texto = bufferLectura.readLine()) != null) {
                    String[] result = texto.split("-");
                    escribirReporte.write("<tr><td>" + result[0] + "</td><td>" + pregunta(Integer.parseInt(result[1]))
                            + "</td><td>" + result[2] + "</td></tr>");
                }
                escribirReporte.write("</tbody>\n"
                        + "  </table>\n"
                        + "  </div>\n"
                        + "  </body>\n"
                        + "  </html>");
                escribirReporte.flush();
                escribirReporte.close();
                lecturaRespuestas.close();
                semaforo.release();
                sleep(3000);
            } catch (Exception ex) {
                System.out.println("Error :" + ex.getMessage());
            }
        }
    }

    /**
     * Esta funcion Recibe como parametro el numero de linea en el cual se encuentra
     * la pregunta a buscar, lee el archivo de preguntas y busca la linea indicada
     * para luego retornar la pregunta como tal.
     * @param numeroLinea
     * @return pregunta
     */
    public String pregunta(int numeroLinea) {
        String pregunta = "";
        try {
            int numero = 0;
            FileReader lecturaPreguntas = new FileReader(rutaPreguntas);
            BufferedReader bufferPreguntas = new BufferedReader(lecturaPreguntas);
            while ((pregunta = bufferPreguntas.readLine()) != null) {
                numero++;
                if (numero == numeroLinea) {
                    String[] resultPregunta = pregunta.split("-");
                    pregunta = resultPregunta[1];
                    break;
                }

            }

            lecturaPreguntas.close();

        } catch (Exception ex) {
            System.out.println("Error :" + ex.getMessage());
        }

        return pregunta;
    }

}
