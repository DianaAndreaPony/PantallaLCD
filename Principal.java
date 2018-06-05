
package lcd;

import com.panamahitek.ArduinoException;    //Excepción de la Libreria para conectar a arduino
import com.panamahitek.PanamaHitek_Arduino; //Libreria para conectar a arduino
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;

public class Principal extends JFrame {

    
    //Varaiables que se utilizaran en el programa
    private JButton btn2, btn; 
    Container contenedor;
    JPanel panel1, panel2, panel3, panel4;
    JLabel lbl1,lbl2;
    private final String PORT_NAME = "COM16";
    private static final int DATA_RATE = 9600;

    //Creamos una instancia de la clase PanamaHitek_Arduino para iniciar la conexion
    PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    
    //Constructor de la clase 
    public Principal() {
        
        super("Menu Principal");
        //Contenedor que tendra todos los paneles
        contenedor = getContentPane();
        
        //Inicializamos los paneles
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 30));

        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 30));

        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 30));
        
        panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 30));
        
        //Le asignamos un laytout al frame
        setLayout(new FlowLayout());
        
        //Iniciamos los botones y los agreagamos a su respectivo panel
        btn2 = new JButton("Bienvenida");
        

        btn = new JButton("Opciones");
        

        lbl1 = new JLabel("Presiona para iniciar la pantalla!");
        panel1.add(lbl1);
        panel1.add(btn2);
        
        lbl2 = new JLabel("Presiona para ir a las opciones");
        panel3.add(lbl2);
        panel3.add(btn);
        
        //cada panel lo agregamos al contenedor 
        contenedor.add(panel1, BorderLayout.CENTER);
        contenedor.add(panel2, BorderLayout.CENTER);
        contenedor.add(panel3, BorderLayout.CENTER);
        contenedor.add(panel4, BorderLayout.CENTER);
        
        //Iniciamos la conexion a arduino por medio del puerto COM17
        try {
            ino.arduinoTX(PORT_NAME, DATA_RATE);
        } catch (ArduinoException ex) {
            Logger.getLogger(LCD_Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Agregamos un evento al boton de opciones 
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //Al presionar el boton la conexion se termina 
                try {
                    ino.killArduinoConnection();
                } catch (ArduinoException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Segunda a = new Segunda();
                a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                a.setSize(450, 450);
                a.setVisible(true);
                a.setResizable(false);
                //Cierra esta ventana sin detener la aplicacion
                dispose();
            }
        });
        
        //Agregamos un evento al boton de bienvenida
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //llamamos al metodo enviar datos, mandamos un numero 4 al presionar el boton
                EnviarDatos("4");
            }
        });
    }
    
    //Metodo enviar datos 
    private void EnviarDatos(String data) {

        try {
            //por medio de la instancia ino llamamos al metodo sendData 
            //y enviamos el dato que recibe el metodo hacia al arduino 
            ino.sendData(data);
        } catch (ArduinoException ex) {
            Logger.getLogger(LCD_Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(LCD_Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        // Metodo principal en donde se manda llamar el Frame
        Principal a = new Principal();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setSize(450, 350);
        a.setVisible(true);
        a.setResizable(false);
    }
}
