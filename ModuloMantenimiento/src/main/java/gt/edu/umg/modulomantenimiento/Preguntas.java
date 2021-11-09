/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.modulomantenimiento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ericka_gonzalez
 */
public class Preguntas {

    public Scanner datos = new Scanner(System.in);
    public final String rutaPreguntas = "/home/ghostman/volume4/Preguntas.csv";
    private String categoriaPreguntas, preguntaNueva;

    public void mostrarPreguntas() {
        File preguntasArchivo = new File(rutaPreguntas);
        if (!preguntasArchivo.exists()) {
            System.out.println("Archivo Inexistente,Agrege una Pregunta nueva para Generarlo...");
        } else {
            try {
                String lineaLeido = "";
                int noPregunta = 0;
                FileReader archivoPreguntas = new FileReader(preguntasArchivo);
                BufferedReader bffPreguntas = new BufferedReader(archivoPreguntas);
                while ((lineaLeido = bffPreguntas.readLine()) != null) {
                    noPregunta++;
                    String[] tokenPalabra = lineaLeido.split("-");
                    System.out.println("No." + noPregunta);
                    System.out.println("Categoria:" + tokenPalabra[0]);
                    System.out.println("Pregunta:" + tokenPalabra[1]);
                    System.out.println("------------------------------------------------------");
                }
                bffPreguntas.close();
                archivoPreguntas.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Preguntas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Preguntas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void nuevaPregunta() {
       File preguntasArchivo = new File(rutaPreguntas);
        if (!preguntasArchivo.exists()) {
           try {
               preguntasArchivo.createNewFile();
           } catch (IOException ex) {
               Logger.getLogger(Preguntas.class.getName()).log(Level.SEVERE, null, ex);
           }
        } else {
        
            Menus categoria=new Menus();
            categoriaPreguntas=categoria.menuCategorias();
            System.out.println("Ingrese su nueva Pregunta:");
            preguntaNueva=datos.nextLine();
            agregarPreguntaNueva(preguntasArchivo,categoriaPreguntas,preguntaNueva);
        } 
    }
    
    public void agregarPreguntaNueva(File archivo,String categoria,String pregunta){
        try {
            FileWriter agregarPregunta = new FileWriter(archivo, true);
             agregarPregunta.append(categoria+"-"+ pregunta + "\n");
            agregarPregunta.flush();
            agregarPregunta.close();
            System.out.println("Pregunta Agregada Exitosamente :D");  
        } catch (IOException ex) {
            Logger.getLogger(Preguntas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
