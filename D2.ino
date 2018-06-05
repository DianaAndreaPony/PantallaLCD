#include <DS1302.h>
#include <LiquidCrystal.h>

//Inicicalizamos la libreria LCD indicando los pines a los que esta conetado al arduino
LiquidCrystal lcd(12,11,10,9,8,7);

// Inicializacion del modulo RTC
// El primer pin es para el RST, el segundo para DATA(I/O) y el tercero para CLK
DS1302 rtc(2, 3, 4);

// variable tiempo, desde la que le pediremos los datos al modulo
Time tiempo;

//Declaracion de las variables que se van a utilizar
char caracter;        //variable que ira guardando cada caracter en el serial para concatenarlos en el mensaje
char car;             //guardara el primer caracter que ingrese desde el serial para que se ejecute la opcion de mostrar mensajes
String cadena;        //cadena que almacenara el mensaje que se envie desde el serial
int t = 0;            //variable para almacenar el tamaño de la cadena del mensaje
float Temp = 0;       //variable para guardar el valor de la temperatura que se reciba desde el sensor
int An1 = A0;         //pin donde esta concectado el sensor de temperatura
String inicial = "BIENVENIDO!!";  //Mensaje de bienvenida que se muestra en el LCD
char cr;              //guardara el primer caracter que ingrese desde el serial para determinar las otras opciones para mostrar en el LCD

// Se ejecuta cada que el arduino se inicia
void setup() {
  //Iniciamos la comunicacion serial
  Serial.begin(9600);
  //Iniciamos la pantalla LCD
  lcd.begin(16, 2);
}

//Esta funcion se mantiene ejeutandose mientras se este energizando el arduino
void loop() {
  //Conversion de la temperatura a grados Centigrados que arroja el sensor
  Temp = ( 5.0 * analogRead(An1) * 100.0) / 1024.0;
  // Obtencion de datos del modulo RTC
  tiempo = rtc.getTime();

  //Si el serial esta disponible y hay datos
  if (Serial.available() > 0) {
    //esperamos un tiempo
    delay(1000);
    //limpiamos la pantalla
    lcd.clear();
    //leemos el primer dato que es enviado por el serial
    cr = Serial.read();     
     //si el primer caracter es igual a 1
  if (cr == '1') {
    //mientras haya datos en el puerto serie
    while (Serial.available() > 0) {
      //lee el segundo dato y este se almacena en la variable caracter
      caracter = Serial.read();
      //cada caracter que va leyendo lo concatena en la varaible cadena
      cadena += caracter;
    }// fin del ciclo while   
    //obtiene el tamaño de la cadena
    t = cadena.length();
    // si el tamaño de la cadena es mayor a 16
    if (t > 16) {
      //limpiamos la pantalla
      lcd.clear();
      //Se imprimen los primeros 16 caracteres en el renglon 1
      lcd.print(cadena.substring(0, 16));
      //Se salta al renglon 2
      lcd.setCursor(0, 1);
      //Se imprimen los caracteres que hayan faltado
      lcd.print(cadena.substring(16, t));
    }
    //si no es mayor a 16
    else {
      //limpiamos la pantalla
      lcd.clear();
      //Imprimimos la cadena
      lcd.print(cadena);
    }
  }
    //caracter nulo
    caracter = '\0';
    //en la cadena ya no habra nada 
    cadena = "\0";
  }
    //guardmos el primer valor que es enviado por el serial en la varaible car
    car=cr;
    //llamamos el metodo temperatura 
    temperatura();
    //llamamos al metodo hora
    hora();
    //llamamos al metodo bienvenida
    bienvenida();
}//fin del ciclo loop

//Metodo temperatura donde se muestra la temperatura en el lcd
void temperatura(void) {
  //Si el primer caracter es igual a 2
  if (car == '2') {
    //limpiamos la pantalla lcd
    lcd.clear();
    //imprimimos la palabra Temperatura
    lcd.print("Temperatura");
    //situamos el cursor en la poscion 6,1
    lcd.setCursor(6, 1);
    //imprimimos el valor Temp
    lcd.print(Temp);
    //imprimios la letra C para indicar grados centigrados
    lcd.print(" C");
    //esperamos un tiempo
    delay(1000);
  }
}//Fin del metodo temperatura

//Metodo hora donde se muestra los datos del rtc en el lcd
void hora(void) {
  //Si el primere dato en el puerto serie es igual a 3
  if (car == '3') {
    //limpiamos la pantalla
    lcd.clear();
    //Imprimimos la palabra hoy para hacer referencia al dia en el que estamos
    lcd.print("HOY:");
    // La variable tiempo.dow (dia de la semana) tedra valor de 1 para dia lunes y 7 para domingo.
    if (tiempo.dow == 1) lcd.print("lun");
    if (tiempo.dow == 2) lcd.print("mar");
    if (tiempo.dow == 3) lcd.print("mie");
    if (tiempo.dow == 4) lcd.print("jue");
    if (tiempo.dow == 5) lcd.print("vie");
    if (tiempo.dow == 6) lcd.print("sab");
    if (tiempo.dow == 7) lcd.print("dom");

    // Imprime el dia
    lcd.print(tiempo.date);
    // Barra de separación
    lcd.print("/");
    // Imprime el mes
    lcd.print(tiempo.mon);
    // Barra de separación
    lcd.print("/");
    // Imprime el año
    lcd.print(tiempo.year);
    // Escribe en ese lugar del LCD
    lcd.setCursor(0, 1);
    //Imprimimos la palabra hora
    lcd.print("HORA:");
    // Imprime la hora
    lcd.print(tiempo.hour, DEC);
    // Separación de dos puntos
    lcd.print(":");
    // Imprime los minutos
    lcd.print(tiempo.min, DEC);
    // Espera para no sobrecargar las comunicaciones con el modulo.
    delay(3000);
  }
}//Fin del metodo hora

//Metodo bienvenida que meuestra el mensaje de inicio en el lcd
void bienvenida(void){
   //Si el primere dato en el puerto serie es igual a 4
  if (car == '4') {
    //imprimimos la variable inicial
      lcd.print(inicial);
      // desplaza 13 posiciones hacia la izquierda para moverlo fuera de pantalla hacia la izquierda
      for (int posicion = 0; posicion < 13; posicion++) {
       // desplazarse una posición hacia la izquierda
        lcd.scrollDisplayLeft();
       //espera un momento
        delay(150);
      }

      // desplaza 29 posiciones hacia la derecha para moverlo fuera de pantalla hacia la derecha:
      for (int posicion = 0;posicion < 29; posicion++) {
        // desplazarse una posición hacia la derecha
        lcd.scrollDisplayRight();
        // espera un momento
        delay(150);
      }

      // desplaza 16 posiciones hacia la izquierda para moverlo de nuevo al centro
      for (int posicion = 0; posicion < 16; posicion++) {
        // desplazarse una posición hacia la izquierda
        lcd.scrollDisplayLeft();
        // espera un momento
        delay(150);
      }
      //espera un tiempo
      delay(1000);
      //limpia la pantalla
      lcd.clear();
    }
}//Fin del metodo bienvenida
//Fin del programa
