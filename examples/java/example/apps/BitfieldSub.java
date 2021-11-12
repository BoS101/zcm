package example.apps;

import java.io.*;
import java.util.Arrays;
import zcm.zcm.*;
import javazcm.types.bitfield_t;

public class BitfieldSub implements ZCMSubscriber
{
    ZCM zcm;
    ZCM.Subscription s;

    public BitfieldSub()
    {
        zcm = ZCM.getSingleton();
        zcm.start();
        sub();
    }

    public void sub()
    {
        s = zcm.subscribe("BITFIELD", this);
    }

    public void unsub()
    {
        zcm.unsubscribe(s);
    }

    public void messageReceived(ZCM zcm, String channel, ZCMDataInputStream ins)
    {
        System.out.println("Received message on channel " + channel);

        try {
            if (channel.equals("BITFIELD")) {
                bitfield_t msg = new bitfield_t(ins);
                assert (msg.field1 == -1);
                assert (msg.field2[1][1] == -1);
                assert (msg.field2[1][2] ==  0);
                assert (msg.field2[1][3] == -1);
                assert (msg.field2[1][4] ==  0);
                assert (msg.field2[2][1] == -1);
                assert (msg.field2[2][2] ==  0);
                assert (msg.field2[2][3] == -1);
                assert (msg.field2[2][4] ==  0);
                assert (msg.field3 == -1);
                assert (msg.field4 == -3);
                assert (msg.field5 == 7);
                assert (msg.field6 == 0);
                assert (msg.field7 == 0);
                assert (msg.field8_dim1 == 0);
                assert (msg.field8_dim2 == 0);
                assert (msg.field8.length == 0);
                assert (msg.field9 == ~(1 << 27) + 1);
                assert (msg.field10 == (((long)1 << 52) | 1));
                assert (msg.field11 == 3);
                for (int i = 0; i < 2; ++i) {
                    for (int j = 0; j < 2; ++j) {
                        for (int k = 0; k < 2; ++k) {
                            for (int l = 0; l < 2; ++l) {
                                assert (msg.field12[i][j][k][l] == (byte)(k + l + 1));
                            }
                        }
                    }
                }
                assert (msg.field15 == -60);
                assert (msg.field16 == 2);
                assert (msg.field19 == 68);
                assert (msg.field20 == 2);
                assert (msg.field22 == -1);
            }
        } catch (IOException ex) {
            System.out.println("Exception: " + ex);
        }
    }

    static void sleep(long ms)
    { try { Thread.sleep(ms); } catch (InterruptedException ex) {} }

    public static void main(String[] args)
    {
        BitfieldSub s = new BitfieldSub();
        while(true) {
            sleep(1000);
        }
    }
}
