int s0,s1,s2,s3,co, c1, c2;
void setup() {
  Serial.begin(9600);

}

void loop() {
  s0=analogRead(A0);
  s1=analogRead(A1);
  s2=analogRead(A2);
  s3=analogRead(A3);

 if(s0>100 && s1>100 && s2>100 && s3>100){
  co++;
  c1++;
  //c2++;

  /*single click*/
  
  if(co ==75){
    Serial.print("z");
    co=0;
  }

/*double click*/

  if(c1 ==150){
    Serial.print("a");
    c1=0;
  }
  
 }

 /*Taking input from the IR sensors*/
 
 if(s1<100)  {Serial.print("d"); co=0; c1=0; c2=0;} //down
  if(s3<100)  {Serial.print("r"); co=0; c1=0; c2=0;} //right
 if(s0<100)  {Serial.print("l"); co=0; c1=0; c2=0;} // left
 if(s2<100)  {Serial.print("u"); co=0; c1=0; c2=0;} //up
 
  delay(10);  
}

