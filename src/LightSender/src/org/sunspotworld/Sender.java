package org.sunspotworld;

import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.peripheral.Spot;
import java.io.IOException;
import javax.microedition.io.*;

/**
 * �f�[�^�O�������M�p�N���X
 */
public class Sender {
    private DatagramConnection conn;//!< �R�l�N�V����
    private Datagram datagram;//!< �f�[�^�O����
    /**
    * �R���X�g���N�^
    */
    public Sender(String protocol,int power,int maxHops)throws IOException{
        Spot.getInstance().getRadioPolicyManager().setOutputPower(power);
        conn = (DatagramConnection)Connector.open(protocol);
        ((RadiogramConnection)conn).setMaxBroadcastHops(maxHops);
        datagram = conn.newDatagram(conn.getMaximumLength());
    }
    /**
    * ���b�Z�[�W���M���\�b�h
    */
    public synchronized void send(String message) throws IOException{
        datagram.reset();
        datagram.writeUTF(message);
        conn.send(datagram);
    }

}