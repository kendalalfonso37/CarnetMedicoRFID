/*
 * --------------------------------------------------------------------------------------------------------------------
 * ARC Sistema de busqueda de pacientes por tarjetas RFID
 * 
 * Objetivo: buscar identificadores de paciente a traves de una tarjeta RFID con Arduino Uno
 * al detectar una tarjeta RFID debera recorrer los bytes recuperados del identificador y 
 * hacer un pitido con un buzzer
 * --------------------------------------------------------------------------------------------------------------------
 * Typical pin layout used:
 * -----------------------------------------------------------------------------------------
 *             MFRC522      Arduino       Arduino   Arduino    Arduino          Arduino
 *             Reader/PCD   Uno/101       Mega      Nano v3    Leonardo/Micro   Pro Micro
 * Signal      Pin          Pin           Pin       Pin        Pin              Pin
 * -----------------------------------------------------------------------------------------
 * RST/Reset   RST          9             5         D9         RESET/ICSP-5     RST
 * SPI SS      SDA(SS)      10            53        D10        10               10
 * SPI MOSI    MOSI         11 / ICSP-4   51        D11        ICSP-4           16
 * SPI MISO    MISO         12 / ICSP-1   50        D12        ICSP-1           14
 * SPI SCK     SCK          13 / ICSP-3   52        D13        ICSP-3           15
 */

// Librerias para Modulo RFID
#include <SPI.h>
#include <MFRC522.h>
// Configuracion de pines para RFID
#define RST_PIN  9
#define SS_PIN  10
// Pin para Buzzer
int buzzPin = 3;
// Inicializacion para Modulo RFID
MFRC522 mfrc522(SS_PIN, RST_PIN);

void setup(){
  // Seteando Modulo RFID
  Serial.begin(9600);
  SPI.begin();
  mfrc522.PCD_Init();
  // Seteando Buzzer
  pinMode(buzzPin, OUTPUT);
}

void loop(){
  if( ! mfrc522.PICC_IsNewCardPresent() ){
    return;
  }


  if( ! mfrc522.PICC_ReadCardSerial() ){
    
    return;
  }

  // Serial.print("UID: ");
  for(byte i = 0; i<mfrc522.uid.size; i++){
    if(mfrc522.uid.uidByte[i] < 0x10){
      Serial.print("0");
    }
    else{
      Serial.print("");
    }
    Serial.print(mfrc522.uid.uidByte[i], HEX);
  }
  Serial.println();
  // Pitido del Buzzer
  digitalWrite(buzzPin, HIGH);
  delay(500);
  digitalWrite(buzzPin, LOW);
  mfrc522.PICC_HaltA();
}
