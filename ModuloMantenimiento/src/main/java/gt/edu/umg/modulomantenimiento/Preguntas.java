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
    public final String rutaPreguntas = "/usr/src/ProyectoFinal/Datos/preguntas.txt";
    private String categoriaPreguntas, preguntaNueva;

    /**
     * Este Proceso se encarga de verificar si el archivo existe , sino existe
     * pedira ingresar una pregunta para poder generarlo, luego se encargara de
     * leer todo el archivo de preguntas y mostrar un listado con cada una de
     * ellas incluyendo el numero de pregunta, la categoria y la misma pregunta.
     */
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
                System.out.println("Error :"+ex);
            } catch (IOException ex) {
                   System.out.println("Error :"+ex);
            }

        }
    }

    /**
     * Este proceso se encargara de verificar si el archivo existe, si en dado
     * caso el archivo no existe lo genera, luego se muestra el menu con las
     * categorias disponibles, luego de seleccionar la deseada el profesor
     * escribira la pregunta y luego se enviaran los datos a la funcion
     * agregarPreguntaNueva.
     */
    public void nuevaPregunta() {
        File preguntasArchivo = new File(rutaPreguntas);
        if (!preguntasArchivo.exists()) {
            try {
                preguntasArchivo.createNewFile();
            } catch (IOException ex) {
                   System.out.println("Error :"+ex);
            }
        } else {

            Menus categoria = new Menus();
            categoriaPreguntas = categoria.menuCategorias();
            System.out.println("Ingrese su nueva Pregunta:");
            preguntaNueva = datos.nextLine();
            agregarPreguntaNueva(preguntasArchivo, categoriaPreguntas, preguntaNueva);
        }
    }

    /**
     * Este Proceso recibe 3 parametros los cuales son el archivo de preguntas,
     * la categoria previamente seleccionada, y la pregunta ingresada.
     *
     * @param archivo
     * @param categoria
     * @param pregunta Luego se encarga de escribirlo con el formato
     * correspondiente en el archivo de preguntas, posteriormente mostrara un
     * mensaje informando el agregado realizado.
     */
    public void agregarPreguntaNueva(File archivo, String categoria, String pregunta) {
        try {
            FileWriter agregarPregunta = new FileWriter(archivo, true);
            agregarPregunta.append(categoria + "-" + pregunta + "\n");
            System.out.println("Pregunta Agregada Correctamente");
            agregarPregunta.flush();
            agregarPregunta.close();
        } catch (IOException ex) {
              System.out.println("Error :"+ex);
        }
    }

}
