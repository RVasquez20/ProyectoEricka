/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.moduloevaluacion;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ericka_gonzalez
 */
public class Inicial {
    /**
     * Este proceso se encarga de inicializar el semaforo disponible, asi mismo
     * crea e inicia el hilo llamado reporte, y luego muestra el menuInicio.
     */
    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(1);
        
        Reporte reporte=new Reporte(semaforo);
        reporte.start();
        Menus menu=new Menus(semaforo);
       
        menu.menuInicio();
        
        
        try {
            reporte.join();
        } catch (Exception ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
