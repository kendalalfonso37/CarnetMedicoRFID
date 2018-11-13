/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.arqui;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author DTO
 */
public class rxSimple {
//Se crea una variable tipo PanamaHitek_Arduino

    static PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    //Se crea un eventListener para el puerto serie
    static SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        //Si se recibe algun dato en el puerto serie, se ejecuta el siguiente metodo
        public void serialEvent(SerialPortEvent serialPortEvent) {
            try {
                /*
                Los datos en el puerto serie se envian caracter por caracter. Si se
                desea esperar a terminar de recibir el mensaje antes de imprimirlo, 
                el metodo isMessageAvailable() devolvera TRUE cuando se haya terminado
                de recibir el mensaje, el cual podra ser impreso a traves del metodo
                printMessage()
                 */
                if (ino.isMessageAvailable()) {
                    //Se le asigna el mensaje recibido a la variable msg
                    String msg = ino.printMessage();
                    //Se imprime la variable msg
                    JOptionPane.showMessageDialog(null, msg);
                    System.out.println("aqiiiiiiii"+msg    );
                }
            } catch (SerialPortException ex) {
                Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArduinoException ex) {
                Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
   
            

    public static void main(String[] args) {
        try {
            //Se inicializa la conexion con el Arduino en el puerto COM5
            ino.arduinoRX("COM6", 9600, listener);
        } catch (ArduinoException ex) {
            Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
