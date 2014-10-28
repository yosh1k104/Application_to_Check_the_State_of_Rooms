package org.sunspotworld;
//package com.mysql.jdbc.Driver;

//import com.sun.spot.sensorboard.*;
import com.sun.spot.io.j2me.radiostream.*;
import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.util.*;
import com.sun.spot.peripheral.*;
import java.io.*;
import javax.microedition.io.*;
import javax.microedition.midlet.*;
import java.util.*;


import com.sun.spot.peripheral.Spot;
import com.sun.spot.peripheral.radio.IRadioPolicyManager;
import com.sun.spot.io.j2me.radiostream.*;
import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.util.IEEEAddress;

import java.io.*;
import javax.microedition.io.*;

import java.sql.*;



/**
 * SunSPOT通信テスト用アプリケーション(受信側)
 * ひたすら受け取ったパケットをコンソロールに表示し続ける
 */

 public class Main {
     

    private final String PROTOCOL = "radiogram://:230"; ///< 通信プロトコル文字列
    private DatagramConnection conn; //!< コネクション
    private Datagram datagram;//< データグラム

    double value;
    double light;
    double x;
    double y;
    double z;


    /**
     * Print out our radio address.
     */
    public void run() {
        try {
            Spot.getInstance().getRadioPolicyManager().setOutputPower(31);
            conn = (DatagramConnection) Connector.open(PROTOCOL);
            datagram = conn.newDatagram(conn.getMaximumLength());

            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost/wip";
            String user = "root";
            String pass = "password";
//            java.sql.Connection connection = DriverManager.getConnection(url, user, pass);

//            Statement statement;
            String sql = "";
            int result;

            int count = 0;
            int checkCount = 0;
            boolean check = false;

            boolean[] accCheck = new boolean[3];
            for(int i = 0; i < 3; i++){
                accCheck[i] = false;
            }

            boolean litCheck = false;


            while (true) {
                java.sql.Connection connection = DriverManager.getConnection(url, user, pass);
                Statement statement = connection.createStatement();



                datagram.reset();
                conn.receive(datagram);
                String message = datagram.readUTF();
                System.out.println("message:" + message);

//                if(message.equals("0014.4F01.0000.7DE2")  && (litCheck == false)){
//                    accCheck = true;
////                    litCheck = false;
//                }else if(accCheck && !(message.equals("0014.4F01.0000.7D53"))){
//                    value = Double.parseDouble(message);
//
//                    if(count % 3 == 0){
//                        x = value;
//                        String stringX = Double.toString(x);
////                        update(connection, "acceleration", "x_value", stringX);
////                        sql = "UPDATE acceleration SET x_value = 10 WHERE id = 1";
//                        sql = "UPDATE acceleration SET x_value = " + stringX + " WHERE id = 1";
//                        result = statement.executeUpdate(sql);
////                        System.out.println("rows:" + result);
//                        System.out.println("x = " + x);
//
//                        accCheck = false;
//                    }else if(count % 3 == 1){
//                        y = value;
//                        String stringY = Double.toString(y);
////                        update(connection, "acceleration", "y_value", stringY);
////                        sql = "UPDATE acceleration SET y_value = 10 WHERE id = 1";
//                        sql = "UPDATE acceleration SET y_value = " + stringY + " WHERE id = 1";
//                        result = statement.executeUpdate(sql);
////                        System.out.println("rows:" + result);
//                        System.out.println("y = " + y);
//
//                        accCheck = false;
//                    }else if(count % 3 == 2){
//                        z = value;
//                        String stringZ = Double.toString(z);
////                        update(connection, "acceleration", "z_value", stringZ);
////                        sql = "UPDATE acceleration SET z_value = 10 WHERE id = 1";
//                        sql = "UPDATE acceleration SET z_value = " + stringZ + " WHERE id = 1";
//                        result = statement.executeUpdate(sql);
////                        System.out.println("rows:" + result);
//                        System.out.println("z = " + z + "\n");
//
//                        accCheck = false;
//                    }
//                    count = count + 1;
//                }


                if(message.equals("0014.4F01.0000.7DE21")){
                    accCheck[0] = true;
//                    litCheck = false;
                }else if(accCheck[0] && !(message.equals("0014.4F01.0000.7D53")) && !(message.equals("0014.4F01.0000.7DE22")) && !(message.equals("0014.4F01.0000.7DE23"))){
                    if(litCheck == false){
                    value = Double.parseDouble(message);

                    x = value;
                    String stringX = Double.toString(x);
//                        update(connection, "acceleration", "x_value", stringX);
//                        sql = "UPDATE acceleration SET x_value = 10 WHERE id = 1";
                    sql = "UPDATE acceleration SET x_value = " + stringX + " WHERE id = 1";
                    result = statement.executeUpdate(sql);
//                        System.out.println("rows:" + result);
                    System.out.println("x = " + x);

                    accCheck[0] = false;
                    }else{
                        accCheck[0] = false;
                        message = "";
                    }
                }

                if(message.equals("0014.4F01.0000.7DE22")){
                    accCheck[1] = true;
//                    litCheck = false;
                }else if(accCheck[1] && !(message.equals("0014.4F01.0000.7D53")) && !(message.equals("0014.4F01.0000.7DE21")) && !(message.equals("0014.4F01.0000.7DE23"))){
                    if(litCheck == false){
                    value = Double.parseDouble(message);

                    y = value;
                    String stringY = Double.toString(y);
//                        update(connection, "acceleration", "y_value", stringY);
//                        sql = "UPDATE acceleration SET y_value = 10 WHERE id = 1";
                    sql = "UPDATE acceleration SET y_value = " + stringY + " WHERE id = 1";
                    result = statement.executeUpdate(sql);
//                        System.out.println("rows:" + result);
                    System.out.println("y = " + y);

                    accCheck[1] = false;
                    }else{
                        accCheck[1] = false;
                        message = "";
                    }
                }

                if(message.equals("0014.4F01.0000.7DE23")){
                    accCheck[2] = true;
//                    litCheck = false;
                }else if(accCheck[2] && !(message.equals("0014.4F01.0000.7D53")) && !(message.equals("0014.4F01.0000.7DE22")) && !(message.equals("0014.4F01.0000.7DE23"))){
                    if(litCheck ==false){
                    value = Double.parseDouble(message);

                    z = value;
                    String stringZ = Double.toString(z);
//                        update(connection, "acceleration", "z_value", stringZ);
//                        sql = "UPDATE acceleration SET z_value = 10 WHERE id = 1";
                    sql = "UPDATE acceleration SET z_value = " + stringZ + " WHERE id = 1";
                    result = statement.executeUpdate(sql);
//                        System.out.println("rows:" + result);
                    System.out.println("z = " + z + "\n");

                    accCheck[2] = false;
                    }else{
                        accCheck[2] = false;
                        message = "";
                    }
                }

                if(message.equals("0014.4F01.0000.7D53")){
                    litCheck = true;
//                    accCheck = false;
                }else if(litCheck && !(message.equals("0014.4F01.0000.7DE21")) && !(message.equals("0014.4F01.0000.7DE22")) && !(message.equals("0014.4F01.0000.7DE23"))){
                    if(accCheck[0] == false && accCheck[1] == false && accCheck[2] == false && !(message.equals(""))){
                    value = Double.parseDouble(message);
                    light = value;
                    String stringLight = Double.toString(light);
//                        update(connection, "illumination", "value", stringLight);
//                        sql = "UPDATE illumination SET value = 10 WHERE id = 1";
//                        System.out.print(sql);
                    sql = "UPDATE illumination SET value = " + stringLight + " WHERE id = 1";
                    result = statement.executeUpdate(sql);
                        //System.out.println("rows:" + result);
                    System.out.println("Light = " + light);

                    litCheck = false;
                    }else{
                        litCheck = false;
                    }
                }



//                //System.out.println("message:" + message);
//
//                if(message.equals("0014.4F01.0000.7D53")){
//                    System.out.println(message);
//                    check = false;
//                    checkCount = 3;
//                }else if(message.equals("0014.4F01.0000.7DE2")){
//                    System.out.println(message);
//                    check = false;
//                    checkCount = 3;
//                }else{
//                    //System.out.println("else in");
//                    if(checkCount < 4){
//                        check = false;
//                    }else{
//                        check = true;
//                    }
//                }
//                //System.out.println("check:" + check);
//                //System.out.println("checkCount:" + checkCount + "\n");
//                checkCount++;
//
//
//                if(check == true){
//                    value = Double.parseDouble(message);
//
//
//                    //System.out.println("count:" + count);
//                    //System.out.print(count + ":");
//
//                    if(count % 4 == 0){
//                        light = value;
//                        String stringLight = Double.toString(light);
////                        update(connection, "illumination", "value", stringLight);
////                        sql = "UPDATE illumination SET value = 10 WHERE id = 1";
////                        System.out.print(sql);
//                        sql = "UPDATE illumination SET value = " + stringLight + " WHERE id = 1";
//                        result = statement.executeUpdate(sql);
//                        //System.out.println("rows:" + result);
//                        System.out.println("Light = " + light);
//                    }else if(count % 4 == 1){
//                        x = value;
//                        String stringX = Double.toString(x);
////                        update(connection, "acceleration", "x_value", stringX);
////                        sql = "UPDATE acceleration SET x_value = 10 WHERE id = 1";
//                        sql = "UPDATE acceleration SET x_value = " + stringX + " WHERE id = 1";
//                        result = statement.executeUpdate(sql);
//                        System.out.println("rows:" + result);
//                        System.out.println("x = " + x);
//                    }else if(count % 4 == 2){
//                        y = value;
//                        String stringY = Double.toString(y);
////                        update(connection, "acceleration", "y_value", stringY);
////                        sql = "UPDATE acceleration SET y_value = 10 WHERE id = 1";
//                        sql = "UPDATE acceleration SET y_value = " + stringY + " WHERE id = 1";
//                        result = statement.executeUpdate(sql);
//                        System.out.println("rows:" + result);
//                        System.out.println("y = " + y);
//                    }else if(count % 4 == 3){
//                        z = value;
//                        String stringZ = Double.toString(z);
////                        update(connection, "acceleration", "z_value", stringZ);
////                        sql = "UPDATE acceleration SET z_value = 10 WHERE id = 1";
//                        sql = "UPDATE acceleration SET z_value = " + stringZ + " WHERE id = 1";
//                        result = statement.executeUpdate(sql);
//                        System.out.println("rows:" + result);
//                        System.out.println("z = " + z + "\n");
//                    }
//                    count++;
//
//                }



                //System.out.println(value);

                /*
                String stringX = datagram.readUTF();
                double x = Double.parseDouble(stringX);
                System.out.println("x = " + x);
                String stringY = datagram.readUTF();
                double y = Double.parseDouble(stringY);
                System.out.println("y = " + y);
                String stringZ = datagram.readUTF();
                double z = Double.parseDouble(stringZ);
                System.out.println("z = " + z);
*/
                //System.out.println(message);

                statement.close();
                connection.close();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//    void update(java.sql.Connection connection, String table, String name, String value){
//        try{
//            System.out.println("pass");
//            Statement statement = connection.createStatement();
//            System.out.println("statement in");
//
//            String sql = "UPDATE " + table + " SET " + name + " = "  + value + " WHERE id = 1";
//            System.out.println(sql);
//
//
//            int result = statement.executeUpdate(sql);
//
//            System.out.println("rows:" + result);
//
//            statement.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//     }


    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }
}

