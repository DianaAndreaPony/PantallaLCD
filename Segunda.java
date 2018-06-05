
package lcd;

import com.panamahitek.ArduinoException;    //Extencion de la Libreria para conectar a arduino
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

public class Segunda extends JFrame {
    //Varaiables que se utilizaran en el programa
    private JButton mensajes, temperatura, hora, volver;
    Container contenedor;
    JPanel panel1, panel2, panel3, panel4;
    JLabel lbl1, lbl2, lbl3,lbl4;
    private final String PORT_NAME = "COM16";
    private static final int DATA_RATE = 9600;
    
    //Creamos una instancia de la clase PanamaHitek_Arduino para iniciar la conexion
    PanamaHitek_Arduino ino = new PanamaHitek_Arduino();

    //Constructor de la clase 
    public Segunda() {
        super("Menu Principal ");
        
        //Contenedor que tendra todos los paneles
        contenedor = getContentPane();
        
        //Inicializamos los paneles que se utilizaran 
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

        panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
        
        //Le asignamos un laytout al frame
        setLayout(new FlowLayout());
        
        //Iniciamos los botones y etiquetas y los agreagamos a su respectivo panel
        mensajes = new JButton("  MENSAJE  ");
        panel1.add(mensajes);

        temperatura = new JButton("TEMPERATURA");
        panel2.add(temperatura);

        hora = new JButton("    HORA   ");
        panel3.add(hora);

        volver = new JButton("REGRESAR");
        panel4.add(volver);

        lbl1 = new JLabel(" ENVIAR MENSAJES");
        panel1.add(lbl1);

        lbl2 = new JLabel(" VER LA TEMPERATURA");
        panel2.add(lbl2);

        lbl3 = new JLabel(" CONOCER LA FECHA Y HORA");
        panel3.add(lbl3);
        
        lbl4 = new JLabel(" VOLVER PAGINA PRINCIPAL");
        panel4.add(lbl4);
        
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
     
        //Agregamos un evento al boton de mensajes 
        mensajes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //Al presionar el boton la conexion se termina 
                try {
                    ino.killArduinoConnection();
                } catch (ArduinoException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Se abre la ventana donde permite escribir los mensajes que se motraran en el LCD
                LCD_Arduino a = new LCD_Arduino();
                a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                a.setSize(450, 350);
                a.setVisible(true);
                a.setResizable(false);
                //Cierra esta ventana sin detener la aplicacion
                dispose();

            }

        });
        
        //Agregamos un evento al boton de temperetura
        temperatura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Al presionar el boton la conexion se termina 
                try {
                    ino.killArduinoConnection();
                } catch (ArduinoException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Se abre la ventana para mostrar la temperatura en el LCD
                Temperatura a = new Temperatura();
                a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                a.setSize(300, 200);
                a.setVisible(true);
                a.setResizable(false);
                //Cierra esta ventana sin detener la aplicacion
                dispose();
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
                //Se abre la ventana para mostrar la hora y fecha en el LCD
                Hora a = new Hora();
                a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                a.setSize(300, 200);
                a.setVisible(true);
                a.setResizable(false);
                //Cierra esta ventana sin detener la aplicacion
                dispose();
            }

        });
        
        //Agregamos un evento al boton de hora
        volver.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Al presionar el boton la conexion se termina 
                try {
                    ino.killArduinoConnection();
                } catch (ArduinoException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Vuelve a la ventana principal
                Principal a = new Principal();
                a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                a.setSize(450, 350);
                a.setVisible(true);
                a.setResizable(false);
                //Cierra esta ventana sin detener la aplicacion
                dispose();
            }

        });
    }
}
    

