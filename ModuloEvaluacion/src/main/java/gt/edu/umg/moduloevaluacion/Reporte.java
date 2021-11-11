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
public class Reporte extends Thread{
     public Semaphore semaforo;
     private final String rutaPreguntas = "/home/ghostman/volume4/Preguntas.csv";
     private final String rutaRespuestas = "/home/ghostman/volume4/Respuestas.csv";
     private final String rutaReporte = "/home/ghostman/volume4/Reporte.html";
    public Reporte(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
    while (true) {
            String texto = "",pregunta="";
            try {
                semaforo.acquire();
                //html,preguntas
                FileReader lecturaRespuestas = new FileReader(rutaRespuestas);

                FileWriter escribir = new FileWriter(rutaReporte);
                escribir.write("<!DOCTYPE html>\n"
                        + "<html lang=\"en\">\n"
                        + "<head>\n"
                        + "  <title>Reporte</title>\n"
                        + "  <meta charset=\"utf-8\">\n"
                        + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                        + "  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n"
                        + "  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n"
                        + "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "<div class=\"container\">\n"
                        + "<table class=\"table table-bordered\">\n"
                        + "    <thead>\n"
                        + "\n"
                        + "      <tr class=\"info\">\n"
                        + "        <td>Categoria</td>\n"
                        + "        <td>Pregunta</td>\n"
                        + "        <td>Respuesta</td>\n"
                        + "      </tr>\n"
                        + "      </thead>\n"
                        + "       <tbody>\n");

                BufferedReader bufferLectura = new BufferedReader(lecturaRespuestas);
                while ((texto = bufferLectura.readLine()) != null) {
                    String[] result = texto.split("-");
                    ///System.out.println(result[0]);
                    escribir.write("<tr><td>"+result[0]+"</td><td>"+pregunta(Integer.parseInt(result[1]))+
                            "</td><td>"+result[2]+"</td></tr>");
                    


                }
                escribir.write("</tbody>\n" +
"  </table>\n" +
"  </div>\n" +
"  </body>\n" +
"  </html>");
                escribir.flush();
                escribir.close();

               
                lecturaRespuestas.close();
                semaforo.release();
                sleep(3000);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String pregunta(int numeroLinea) {
        String pregunta=""; 
        try {
             //busca en preguntas y retorna la respuesta
             
             int numero=0;
             FileReader lecturaPreguntas = new FileReader(rutaPreguntas);
             BufferedReader bufferPreguntas = new BufferedReader(lecturaPreguntas);
             while ((pregunta = bufferPreguntas.readLine()) != null) {
                 numero++;
                 if(numero==numeroLinea){
                     String[] resultPregunta = pregunta.split("-");
                     pregunta=resultPregunta[1];
                     break;
                 }
                 
             }
            
             lecturaPreguntas.close();
             
         } catch (Exception ex) {   
            System.out.println("Errorp -->" + ex.getMessage());
         } 

        return pregunta;
    }
    
}
