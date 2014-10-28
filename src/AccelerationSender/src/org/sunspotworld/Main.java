/*
 * StartApplication.java
 *
 * Created on 2010/06/17 18:58:13;
 */

package org.sunspotworld;

import com.sun.spot.peripheral.Spot;
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.sensorboard.peripheral.ISwitch;
import com.sun.spot.sensorboard.peripheral.ITriColorLED;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.peripheral.radio.IRadioPolicyManager;
import com.sun.spot.io.j2me.radiostream.*;
import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.sensorboard.IDemoBoard;
import com.sun.spot.sensorboard.peripheral.ILightSensor;
import com.sun.spot.sensorboard.peripheral.ITemperatureInput;
import com.sun.spot.util.*;

import com.sun.spot.sensorboard.peripheral.IAccelerometer3D;

import java.io.*;
import javax.microedition.io.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 *
 * The manifest specifies this class as MIDlet-1, which means it will
 * be selected for execution.
 */
public class Main extends MIDlet {

    private ITriColorLED [] leds = EDemoBoard.getInstance().getLEDs();
    private IAccelerometer3D Accele = EDemoBoard.getInstance().getAccelerometer();//加速度センサーのインスタンス作成
    private Sender sender; //!< 通信を行う
    private final String PROTOCOL ="radiogram://broadcast:230"; //!< 通信プロトコル

    protected void startApp() throws MIDletStateChangeException {
        System.out.println("Hello, world");
        new BootloaderListener().start();   // monitor the USB (if connected) and recognize commands from host

        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        String serialNum = IEEEAddress.toDottedHex(ourAddr);
        System.out.println("Our radio address = " + IEEEAddress.toDottedHex(ourAddr));

        IDemoBoard eDemo = EDemoBoard.getInstance();
//        ITemperatureInput temp = eDemo.getADCTemperature();
        ILightSensor lightSensor = eDemo.getLightSensor();
        ISwitch sw1 = eDemo.getSwitches()[EDemoBoard.SW1];

        leds[0].setRGB(0,100,0);                // set color to moderate red
        leds[1].setRGB(0,100,0);                // set color to moderate red
        leds[2].setRGB(100,0,0);                // set color to moderate red
        leds[3].setRGB(100,0,0);                // set color to moderate red
        leds[4].setRGB(100,0,0);

//        while(sw1.isOpen()){
        while (true) {                  // done when switch is pressed
            leds[0].setOn();                    // Blink LED
            leds[1].setOn();
            leds[2].setOn();                    // Blink LED
            leds[3].setOn();
            leds[4].setOn();                    // Blink LED


            try{
                sender = new Sender(PROTOCOL, 31, 3);

//                String start = "start";
//                sender.send(serialNum);
//                System.out.println(serialNum);

//                double lightIndication = lightSensor.getValue();
//                String light = Double.toString(lightIndication);
////                sender.send("lightINdication = " + lightIndication);
//                sender.send(light);
//                System.out.println("lightINdication = " + lightIndication);
//                double tempC = temp.getCelsius();
 //               sender.send("tempC = " + tempC);
//                System.out.println("tempC = " + tempC);
                double x = Accele.getAccelX();
                double y = Accele.getAccelY();
                double z = Accele.getAccelZ();

                String stringX = Double.toString(x);
                String stringY = Double.toString(y);
                String stringZ = Double.toString(z);

                sender.send(serialNum + "1");
                System.out.println(serialNum + "1");
                sender.send(stringX);
                System.out.println("x = " + x);
                sender.send(serialNum + "2");
                System.out.println(serialNum + "2");
                sender.send(stringY);
                System.out.println("y = " + y);
                sender.send(serialNum + "3");
                System.out.println(serialNum + "3");
                sender.send(stringZ);
                System.out.println("z = " + z);


//                sender.send("(x, y, z) = (" + x + ", " + y + ", " + z + ")\n");
//                System.out.println("(x, y, z) = (" + x + ", " + y + ", " + z + ")\n");
            }catch(IOException ex){
//                System.out.println("IO exception:"+ex);
                ex.printStackTrace();
            }
            Utils.sleep(250);                   // wait 1/4 seconds
            leds[0].setOff();
            leds[1].setOff();
            leds[2].setOff();
            leds[3].setOff();
            leds[4].setOff();

            Utils.sleep(500);                  // wait 1 second
        }
        //notifyDestroyed();                      // cause the MIDlet to exit
    }

    protected void pauseApp() {
        // This is not currently called by the Squawk VM
    }

    /**
     * Called if the MIDlet is terminated by the system.
     * I.e. if startApp throws any exception other than MIDletStateChangeException,
     * if the isolate running the MIDlet is killed with Isolate.exit(), or
     * if VM.stopVM() is called.
     *
     * It is not called if MIDlet.notifyDestroyed() was called.
     *
     * @param unconditional If true when this method is called, the MIDlet must
     *    cleanup and release all resources. If false the MIDlet may throw
     *    MIDletStateChangeException  to indicate it does not want to be destroyed
     *    at this time.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
        for (int i = 0; i < 8; i++) {
            leds[i].setOff();
        }
    }
}