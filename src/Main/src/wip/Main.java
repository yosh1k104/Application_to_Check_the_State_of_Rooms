/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wip;

import java.sql.*;
import java.util.Date;

/**
 *
 * @author yoshiki
 */
public class Main {

    static JavaMailSend javaMailSend = new JavaMailSend();
    static JavaMailReceive javaMailReceive = new JavaMailReceive();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        javaMailReceive.process();
        int length = javaMailReceive.getLength();

        String stringX = "";
        String stringY = "";
        String stringZ = "";
        String stringLight = "";
        String stringUS = "";

        boolean checkX = false;
        boolean checkY = false;
        boolean checkZ = false;
        boolean checkLit = false;
        boolean checkStand = false;
        boolean checkSit = false;

        String text = "";

        System.out.println("length" + length);
        System.out.println("Date:" + new Date());

        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost/wip";
            String user = "root";
            String pass = "password";

            while (true) {
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement stmtAcc = connection.createStatement();
                Statement stmtLit = connection.createStatement();
                Statement stmtUS = connection.createStatement();
                String sqlAcc = "SELECT * FROM acceleration";
                String sqlLit = "SELECT * FROM illumination";
                String sqlUS = "SELECT * FROM ultrasonic";

                ResultSet rsAcc = stmtAcc.executeQuery(sqlAcc);
                ResultSet rsLit = stmtLit.executeQuery(sqlLit);
                ResultSet rsUS = stmtUS.executeQuery(sqlUS);

                while (rsAcc.next()) {
                    stringX = rsAcc.getString("x_value");
                    double x = Double.parseDouble(stringX);
                    stringY = rsAcc.getString("y_value");
                    double y = Double.parseDouble(stringY);
                    stringZ = rsAcc.getString("z_value");
                    double z = Double.parseDouble(stringZ);


                    if (x >= 0.34 && x < 0.38) {
//                        text += "x = " + stringX + "\n";
                        checkX = true;
                        System.out.println("x_value = " + stringX);
                    }
                    if (y >= -1.0 && y < -0.96) {
//                        text += "y = " + stringY + "\n";
                        checkY = true;
                        System.out.println("y_value = " + stringY);
                    }
                    if (z >= 0.14 && z < 0.16) {
//                        text += "z = " + stringZ + "\n";
                        checkZ = true;
                        System.out.println("z_value = " + stringZ + "\n");
                    }
                }

                while (rsLit.next()) {
                    stringLight = rsLit.getString("value");
                    double light = Double.parseDouble(stringLight);

                    if (light > 35) {
//                        text += "Light = " + stringLight + "\n";
                        checkLit = true;
                        System.out.println("Light = " + stringLight + "\n");
                    }
                }

                while (rsUS.next()) {
                    stringUS = rsUS.getString("value");
                    int sonic = Integer.parseInt(stringUS);

                    if (sonic >= 890 && sonic > 930) {
                        //if (sonic >= 85 && sonic > 89) {

//                        text += "UltraSonic = " + stringUS;
                            checkSit = true;
                            System.out.println("UltraSonic = " + stringUS + "\n");
                        }

//                        if (sonic >= 42 && sonic < 46) {
                        if (sonic >= 490 && sonic < 530) {
                            checkStand = true;
                            System.out.println("UltraSonic = " + stringUS + "\n");
                        }
                    }


                    if (checkX || checkY || checkZ) {
//                    text += "使用目的： 会議\n";
                        System.out.println("使用目的： 会議\n");
//                    text += "Light = " + stringLight + "\n";
                    } else {
                        System.out.println("使用目的： 私用\n");
//                    text += "使用目的: 私用\n";
                    }

                    if (length < javaMailReceive.getLength()) {
                        System.out.println("Address:" + javaMailReceive.getAddress());
                        text += javaMailReceive.getDate() + " 時点での使用状況をおしらせします。\n";
                        text += "\n----------------------------------------------------------\n\n";
                        System.out.println("getDate:" + javaMailReceive.getDate());

                        String address = javaMailReceive.getAddress().toString();

                        if (checkX && checkY && checkZ) {
                            text += "使用目的： 会議\n";
                           // text += "x = " + stringX + "\n";
                            //text += "y = " + stringY + "\n";
                            //text += "z = " + stringZ + "\n";
                        } //                    if (checkY) {
                        //                        text += "y = " + stringY + "\n";
                        //                    }
                        //                    if (checkZ) {
                        //                        text += "z = " + stringZ + "\n";
                        //                    }
                        else if (checkLit) {
                            text += "使用目的： 会議\n";
                            System.out.println("使用目的： 会議\n");
                            //text += "Light = " + stringLight + "\n";
                        } else {
                            System.out.println("使用目的： 私用\n");
                            text += "使用目的: 私用\n";
                        }

                        if (checkSit) {
                            text += "室内状況: 使用中\n";
                            //text += "UltraSonic = " + stringUS + "\n";
                        } else {
                            text += "室内状況: 空室\n";
                        }

                        if (checkStand) {
                            System.out.println("もうすぐ空きます\n");
                        } else {
                            System.out.println("まだ使用中です\n");
                        }

                        text += "\n----------------------------------------------------------\n\n";

                        javaMailSend.send(address, text);

                        text = "";

                        length = javaMailReceive.getLength();
                        //break;
                    }

                    javaMailReceive.process();
                    checkX = false;
                    checkY = false;
                    checkZ = false;
                    checkLit = false;
                    checkStand = false;
                    checkSit = false;

                    stmtAcc.close();

                    stmtLit.close();

                    stmtUS.close();

                    connection.close();
                }

            } catch  (Exception e) {
            e.printStackTrace();

        }

    }
}
