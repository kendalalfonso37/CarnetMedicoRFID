#include <SPI.h>
#include <MFRC522.h>

#define RST_PIN  9
#define SS_PIN  10

MFRC522 mfrc522(SS_PIN, RST_PIN);

void setup(){
  Serial.begin(9600);
  SPI.begin();
  mfrc522.PCD_Init();
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
      Serial.print(" 0");
    }
    else{
      Serial.print(" ");
    }
    Serial.print(mfrc522.uid.uidByte[i], HEX);
  }
  Serial.println();
  mfrc522.PICC_HaltA();
}
