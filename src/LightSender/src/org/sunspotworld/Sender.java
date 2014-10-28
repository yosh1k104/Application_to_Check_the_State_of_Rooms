package org.sunspotworld;

import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.peripheral.Spot;
import java.io.IOException;
import javax.microedition.io.*;

/**
 * データグラム送信用クラス
 */
public class Sender {
    private DatagramConnection conn;//!< コネクション
    private Datagram datagram;//!< データグラム
    /**
    * コンストラクタ
    */
    public Sender(String protocol,int power,int maxHops)throws IOException{
        Spot.getInstance().getRadioPolicyManager().setOutputPower(power);
        conn = (DatagramConnection)Connector.open(protocol);
        ((RadiogramConnection)conn).setMaxBroadcastHops(maxHops);
        datagram = conn.newDatagram(conn.getMaximumLength());
    }
    /**
    * メッセージ送信メソッド
    */
    public synchronized void send(String message) throws IOException{
        datagram.reset();
        datagram.writeUTF(message);
        conn.send(datagram);
    }

}