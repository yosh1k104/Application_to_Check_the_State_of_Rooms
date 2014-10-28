import de.bezier.data.sql.*;
import processing.serial.*;


MySQL mysql;
Serial myPort;

//int value;
//char lf = 10;
//float num;
//int Size;
//float radius;

void setup(){
//    size(500, 500);

    println(Serial.list()); //シリアルポートの表示
    String portName = Serial.list()[2];//第1番を選択
    myPort = new Serial(this, portName, 9600);
    myPort.clear();//受信済み文字をクリア
    
}

void draw(){
}

void serialEvent(Serial serial){
    String str;
    char lf = 10;

  
    str = myPort.readStringUntil(lf);
  
    if(str != null){
        str = trim(str);
        
        int value = int(str);
        connectMySQL(value);
        println("value: " + value);
    }
}

void connectMySQL(int value){
    String user = "root";
    String pass = "password";
    String dbHost = "localhost";
    String database = "wip";
    mysql = new MySQL(this, dbHost, database, user, pass);
    
    if(mysql.connect()){
//       msql.query("SELECT Size FROM circles");
//       radius=msql.getInt(Size);
//       
//       println(radius);
//       
//       for(int i=0; i<2; i++){
//       num=random(1,10);
//       
//       msql.execute( "INSERT INTO circles (Size) VALUES ("+num+");" );
//       }
        mysql.execute("UPDATE ultrasonic SET value = " + value +" where id = 1;");
    }
    else{
        println( "FAIL" );
    }    
}

  
