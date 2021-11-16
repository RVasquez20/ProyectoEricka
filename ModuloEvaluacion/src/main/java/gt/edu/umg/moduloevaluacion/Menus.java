/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.moduloevaluacion;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 *
 * @author ericka_gonzalez
 */
public class Menus {

    private Scanner datos = new Scanner(System.in);

    public Semaphore semaforo;

    public Menus(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    /**
     *Este proceso muestra el menuInicio en el cual el estudiante podra elegir
     * una categoria para empezar el proceso de evaluacion.
     */
    public void menuInicio() {
        Preguntas pregunta = new Preguntas(semaforo);
        String opcionElejida = "";
        System.out.println("********************");
        System.out.println("*    Menu Inicio   *");
        System.out.println("********************");
        System.out.println("*1.BASIC_COMMANDS  *");
        System.out.println("*2.SHELL_SCRIPTS   *");
        System.out.println("*3.SECURE_SHELL    *");
        System.out.println("*4.POSIX_SEMAPHORES*");
        System.out.println("*5.MAVEN           *");
        System.out.println("*6.JAVA_THREADS    *");
        System.out.println("*7.DOCKERS         *");
        System.out.println("*8.Salir           *");
        System.out.println("********************");
        opcionElejida = datos.nextLine();
        switch (opcionElejida.charAt(0)) {
            case '1': {
                pregunta.procesoEvaluacion("BASIC_COMMANDS");
                menuInicio();
                break;
            }
            case '2': {
                pregunta.procesoEvaluacion("SHELL_SCRIPTS");
                menuInicio();
                break;
            }
            case '3': {
                pregunta.procesoEvaluacion("SECURE_SHELL");
                menuInicio();
                break;
            }
            case '4': {
                pregunta.procesoEvaluacion("POSIX_SEMAPHORES");
                menuInicio();
                break;
            }
            case '5': {
                pregunta.procesoEvaluacion("MAVEN");
                menuInicio();
                break;
            }
            case '6': {
                pregunta.procesoEvaluacion("JAVA_THREADS");
                menuInicio();
                break;
            }
            case '7': {
                pregunta.procesoEvaluacion("DOCKERS");
                menuInicio();
                break;
            }
            case '8': {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("Opcion No Disponible Elija Nuevamente");
                menuInicio();
                break;
            }
        }
    }

}
