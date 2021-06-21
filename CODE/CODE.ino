#include <ESP8266WiFi.h>
#include "FirebaseESP8266.h"
#include <ArduinoJson.h>

#define FIREBASE_HOST "vanthosmarthome-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "POKdg7lMHmPJekr89kZcyUSDLkUstHF1dkp6Mcx2"
#define WIFI_SSID "Van Tho 15"
#define WIFI_PASSWORD "vannhucu"

#define SHOWPin D0
#define DenChinhPin D1
#define DenHocPin D2
#define DenNguPin D3
#define Quat1Pin D4
#define Quat2Pin D5
#define CoiPin D6
#define CuaPin D7
#define DHTPin D8
#define KHIGAPin A0

FirebaseData firebaseData;
String path = "/";
FirebaseJson json;
long long last = 0;

String Cua = "OFF", DenChinh = "OFF", DenHoc = "OFF", DenNgu = "OFF", Quat1 = "OFF", Quat2 = "OFF";
String KhiGa = "45", NhietDo = "28", DoAm="30";

void ReadDataToFirebase();
void WriteDataToFirebase(String NhietDo,String DoAm, String KhiGa);
void HandleRelay();

void setup()
{
  pinMode(SHOWPin, OUTPUT);
  pinMode(DenChinhPin, OUTPUT);
  pinMode(DenNguPin, OUTPUT);
  pinMode(DenHocPin, OUTPUT);
  pinMode(Quat1Pin, OUTPUT);
  pinMode(Quat2Pin, OUTPUT);
  pinMode(CoiPin, OUTPUT);
  pinMode(CuaPin, OUTPUT);
  Serial.begin(9600);

  digitalWrite(SHOWPin, 0);
  digitalWrite(DenChinhPin, 0);
  digitalWrite(DenNguPin, 0);
  digitalWrite(DenHocPin, 0);
  digitalWrite(Quat1Pin, 0);
  digitalWrite(Quat2Pin, 0);
  digitalWrite(CoiPin, 0);
  digitalWrite(CuaPin, 0);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ") ;
  Serial.println(WiFi.localIP());

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
  if (!Firebase.beginStream(firebaseData, path))
  {
    Serial.println("REASON:+ " + firebaseData.errorReason());
    Serial.println();
  }
  last = millis();
}

void loop()
{
  digitalWrite(SHOWPin, !digitalRead(SHOWPin));
  WriteDataToFirebase( NhietDo , DoAm,  KhiGa);
  ReadDataToFirebase();
  HandleRelay();
  Serial.println("Cua "+Cua+ "DenChinh "+ DenChinh+ "DenHoc "+ DenHoc+ "DenNgu "+ DenNgu+ "Quat1 "+ Quat1+ "Quat2 "+ Quat2);
  Serial.println("NhietDO " +NhietDo+ " DoAm " + DoAm + " KhiGa " + KhiGa);
  
}
void HandleRelay()
{
  if(Cua=="ON")
  {
     digitalWrite(CuaPin, 1);
  }
  else
  {
    digitalWrite(CuaPin, 0);
  }
  if(DenChinh=="ON")
  {
     digitalWrite(DenChinhPin, 1);
  }
  else
  {
    digitalWrite(DenChinhPin, 0);
  }
  if(DenHoc=="ON")
  {
     digitalWrite(DenHocPin, 1);
  }
  else
  {
    digitalWrite(DenHocPin, 0);
  }
  if(DenNgu=="ON")
  {
     digitalWrite(DenNguPin, 1);
  }
  else
  {
    digitalWrite(DenNguPin, 0);
  }
   if(Quat1=="ON")
  {
     digitalWrite(Quat1Pin, 1);
  }
  else
  {
    digitalWrite(Quat1Pin, 0);
  }
   if(Quat2=="ON")
  {
     digitalWrite(Quat2Pin, 1);
  }
  else
  {
    digitalWrite(Quat2Pin, 0);
  }
}
void ReadDataToFirebase()
{
  if (Firebase.getString(firebaseData, path + "/Cua"))
  {
    Cua = firebaseData.stringData();
  }
  if (Firebase.getString(firebaseData, path + "/DenChinh"))
  {
    DenChinh = firebaseData.stringData();
  }
  if (Firebase.getString(firebaseData, path + "/DenHoc"))
  {
    DenHoc = firebaseData.stringData();
  }
  if (Firebase.getString(firebaseData, path + "/DenNgu"))
  {
    DenNgu = firebaseData.stringData();
  }
  if (Firebase.getString(firebaseData, path + "/Quat1"))
  {
    Quat1 = firebaseData.stringData();
  }
  if (Firebase.getString(firebaseData, path + "/Quat2"))
  {
    Quat2 = firebaseData.stringData();
  }
}
void WriteDataToFirebase(String nhietdo,String doam, String khiga)
{
  if (millis() - last > 500)
  {
    Firebase.setString(firebaseData, path + "/KhiGa", khiga);
    Firebase.setString(firebaseData, path + "/NhietDo", nhietdo);
    Firebase.setString(firebaseData, path + "/DoAm", doam);
    NhietDo = String(random(15, 45));
    KhiGa = String(random(30, 90));
    DoAm = String(random(10, 80));
    last = millis();
  }
}
