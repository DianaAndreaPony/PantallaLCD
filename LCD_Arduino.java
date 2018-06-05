package lcd;

import com.panamahitek.ArduinoException;     //Execpcion de arduino
import com.panamahitek.PanamaHitek_Arduino;  //Libreria para conectar a arduino
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
//import gnu.io.SerialPort;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;


public class LCD_Arduino extends JFrame {

    //Varaiables que se utilizaran en el programa
    private JTextField mensaje;
    private JLabel lblmensaje;
    private JButton enviar, temperatura;
    Container contenedor;
    JPanel panel1, panel2, panel3;
    int caracteres = 32;
  //  SerialPort serialPort;
    private final String PORT_NAME = "COM16";
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;

    //Creamos una instancia de la clase PanamaHitek_Arduino para iniciar la conexion
    PanamaHitek_Arduino ino = new PanamaHitek_Arduino();

    //Constructor de la clase 
    public LCD_Arduino() {
        super("Enviar Mensajes ");
        
        //Contenedor que tendra todos los paneles
        contenedor = getContentPane();
        
        //Inicializamos los paneles
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

        //Le asignamos un laytout al frame
        setLayout(new FlowLayout());
        
        //Iniciamos los elementos y los agreagamos a su respectivo panel
        mensaje = new JTextField(40);
        panel1.add(mensaje);

        lblmensaje = new JLabel("Caracteres Disponibles");
        panel2.add(lblmensaje);

        enviar = new JButton("ENVIAR MENSAJE");
        panel3.add(enviar);

        temperatura = new JButton("OPCIONES");
        panel3.add(temperatura);

        
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
        
        //Agregamos un evento al boton de enviar 
        enviar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //Metodo para enviar datos a al arduino
                //Enviamos un 1 que servira en el programa  de arduino
                //para saber que se desplegara en la pantalla
                //enviamos la cadena de texto ingresada en el textifield
                EnviarDatos("1" + mensaje.getText());
                mensaje.setText("");
                letras();
            }
        });
        //Agregamos un evento al boton de temperatura
        temperatura.addActionListener(new ActionListener() {
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
        
        // Evento en el textfield que nos indica cuantos caracteres
       //tenemos disponibles para motrar en el LCD 
        mensaje.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent ke) {
                //Metodo que cuenta los caracteres disponibles
                letras();
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }

        });
        
        //Agregamos un evento el textfield mensaje
        mensaje.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //Metodo para enviar datos a al arduino
                //Enviamos un 1 que servira en el programa  de arduino
                //para saber que se desplegara en la pantalla
                //enviamos laa cadena de texto ingresada en el textifield
                
                EnviarDatos("1" + mensaje.getText());
                mensaje.setText("");
            }
        });
    }
    
    //Metodo que nos indica cuantos caracteres nos quedan 
    //disponibles para desplegarlos en el  LCD
    public void letras() {
        caracteres = 32 - mensaje.getText().length(); //Indica la cantidad de caracteres
        //disponibles. En el LCD solo se permite imprimir 32 caracteres.

        if (caracteres <= 0) { //Si la cantidad de caracteres se ha //agotado...
            lblmensaje.setText("Caracteres disponibles: 0"); //Se imprime que la cantidad de caracteres disponibles es 0
            String cadena = ""; //Se declara la variable que guardará el //mensaje a enviar
            cadena = mensaje.getText(); //Se asigna el //texto del TextField a la variable cadena
            cadena = cadena.substring(0, 32); //Se evita que por //alguna razón la //variable contenga
            //más de 32 caracteres, utilizando el substring que crea un //string a partir de uno mayor.
            mensaje.setText(cadena); //se regresa la //cadena con 32 caracteres al TextField
        } else {
            //Si la cantidad de caracteres disponibles es ayor a 0 solamente //se imprimirá la cantidad de caracteres disponibles
            lblmensaje.setText("Caracteres disponibles: " + (caracteres));
        }
    }
    
    //Metodo enviar datos para enviar los datos al arduino 
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
}
