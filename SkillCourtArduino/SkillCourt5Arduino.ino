#include <SPI.h>
#include <Adafruit_NeoPixel.h>

// pins for the LEDs:
/*These represent the cables on the UNO and are labeled 8, 9, and 10.*/
const int redPin = 8;
const int greenPin = 9;
const int bluePin = 10;
#define PIN 6

int recv;

boolean conn = false;
boolean routine = false;

Adafruit_NeoPixel strip = Adafruit_NeoPixel(76, PIN, NEO_GRB + NEO_KHZ800);

void setup()
{
  /*pinMode(pin, mode): Configures the specified pin to behave either as an input or an output.*/
  pinMode(redPin, OUTPUT);// make the pins outputs:
  pinMode(greenPin, OUTPUT);
  pinMode(bluePin, OUTPUT);

  //Initialize NeoPixel Strip
  strip.begin();
  strip.setBrightness(254);

  // set initial values  0,0,0
  setLed(0,0,254);//LED
  setLed2(0,0,254);//Strip
  
  /*begin(speed): Sets the speed(baud rate) for serial communication.*/
  Serial.begin(9600);//9600
  //sendVoltage();
}

void loop()
{ 
  /**/
  if(!conn) 
  {
    //254,254,254
    setLed(254, 254, 254);
    setLed2(254, 254, 254);
    conn = true;
  }
  else
  {
    if(!routine)//it is already connected to the Android device but routine hasn't been received
    {
      //100,159,30
      setLed(100, 159, 30);
      setLed2(100, 159, 30); //NeoPixel
      routine = true;
    }
    else//routine playing wating to know which light to turn on or if the end to send back stats and reset variables
    {
       /*
        available(): Get the number of bytes (characters) available for reading from a software serial port. 
        This is data that's already arrived and stored in the serial receive buffer.
      */  
      if(Serial.available())
      {       
        recv = Serial.read();
          
        if(recv == 1)
        {
          setLed(254, 0, 0);
          setLed2(254, 0, 0); //NeoPixel
          
          delay(100);
          sendVoltage2();
        }
        else if(recv == 0)
        {
          setLed(0, 254, 0);
          setLed2(0, 254, 0); //NeoPixel
          
          delay(100);
          sendVoltage();  
        }
        else if(recv == 2)
        {
          setLed(0, 0, 254);
          setLed2(0, 0, 254);
        }
        else if(recv == 3)
        {
          setLed(0, 0, 0);
          setLed2(0, 0, 0);
          
        }
        else if(recv == 4)//routine is over
        {
          setLed(0, 0, 254);
          setLed2(0, 0, 254);
          delay(500); 
          setLed(0, 0, 0);
          delay(500);  
          setLed(0, 0, 254);
          setLed2(0, 0, 254);
          delay(500);  
          setLed(0, 0, 0);
          delay(500);  
          setLed(0, 0, 254);
          setLed2(0, 0, 254);
          delay(500);  
          setLed(0, 0, 0);
          setLed2(0, 0, 254);
          delay(500); 
        }  
      }
    }
  }
}

void sendVoltage() 
{
  while (Serial.available() <= 0) 
  {
    // Read the input on analog pin 0:
    int sensorValue = analogRead(A0);

    // Convert the analog reading (which goes from 0 - 1023) to a voltage (0 - 5V):
    //int voltage = sensorValue * (5.0 / 1023.0); //was float

    //if(voltage >= 1)
    if(sensorValue >= 8)
    {
      Serial.write(0);
      break;
    }   
  }
}

void sendVoltage2() 
{
  while (Serial.available() <= 0) 
  {
    // Read the input on analog pin 0:
    int sensorValue = analogRead(A0);

    // Convert the analog reading (which goes from 0 - 1023) to a voltage (0 - 5V):
    //int voltage = sensorValue * (5.0 / 1023.0); //was float

    if(sensorValue >= 8)
    {
      Serial.write(1);
      break;
    }   
  }
}

/*This is for the board LED*/
void setLed(int redVal, int greenVal, int blueVal)
{

 /*analogWrite: Writes an analog value (PWM wave) to a pin. 
   Can be used to light a LED at varying brightnesses*/

  analogWrite(redPin, redVal); //analogWrite
  analogWrite(greenPin, greenVal);
  analogWrite(bluePin, blueVal);
}

/*This is for the LED Strip*/
void setLed2(int redVal, int greenVal, int blueVal)
{
  int i = 0;
  
  for(i = 0; i < 76; i++)
  {
    strip.setPixelColor(i, redVal, greenVal, blueVal);
  }
 
  strip.show();
}
