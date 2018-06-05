
package lcd;

import com.panamahitek.ArduinoException;    //Execpcion de arduino
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


public class Temperatura extends JFrame {

    
    //Varaiables que se utilizaran en el programa
    
    private JLabel lblmensaje;
    private JButton temperatura, hora;
    Container contenedor;
    JPanel panel1, panel2, panel3;
    int caracteres = 32;;
    private final String PORT_NAME = "COM16";
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;
    
    //Creamos una instancia de la clase PanamaHitek_Arduino para iniciar la conexion
    PanamaHitek_Arduino ino = new PanamaHitek_Arduino();

    //Constructor de la clase
    public Temperatura() {
        super("Enviar Mensajes ");

        //Contenedor que tendra todos los paneles
        contenedor = getContentPane();

        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

        //Le asignamos un laytout al frame
        setLayout(new FlowLayout());
        
        //Iniciamos los elementos y los agreagamos a su respectivo panel
        lblmensaje = new JLabel("Temperatura");
        panel1.add(lblmensaje);

        temperatura = new JButton("TEMPERATURA");
        panel3.add(temperatura);

        hora = new JButton("OPCIONES");
        panel3.add(hora);

        //cada panel lo agregamos al contenedor 
        contenedor.add(panel1, BorderLayout.CENTER);
        contenedor.add(panel2, BorderLayout.CENTER);
        contenedor.add(panel3, BorderLayout.CENTER);

        //Iniciamos la conexion a arduino por medio del puerto COM17
        try {
            ino.arduinoTX(PORT_NAME, DATA_RATE);
        } catch (ArduinoException ex) {
            Logger.getLogger(LCD_Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Agregamos un evento al boton de temperatura
        temperatura.addActionListener(new ActionListener() {
            @Override
            //Metodo para enviar datos a al arduino
            //Enviamos un 2 que servira en el programa  de arduino
            //para saber que se desplegara en la pantalla
            public void actionPerformed(ActionEvent ae) {
                EnviarDatos("2");
            }

        });

        //Agregamos un evento al boton de hora
        hora.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Al presionar el boton la conexion se termina 
                try {
                    ino.killArduinoConnection();
                } catch (ArduinoException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Se abre la segunda ventanda con las opciones 
                Segunda a = new Segunda();
                a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                a.setSize(450, 350);
                a.setVisible(true);
                a.setResizable(false);
                dispose();
            }

        });

    }

    //Metodo enviar datos para enviar los datos al arduino 
    private void EnviarDatos(String data) {
        //por medio de la instancia ino llamamos al metodo sendData 
        //y enviamos el dato que recibe el metodo hacia al arduino 
        try {
            ino.sendData(data);
        } catch (ArduinoException ex) {
            Logger.getLogger(LCD_Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(LCD_Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
