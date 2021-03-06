package server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

class Receiver{

    static byte[] getReply() {

        ByteBuffer buf = ByteBuffer.allocate(1024); //buffer for coming bytes
        byte[] clear = new byte[1024]; //std buffer for "everything OK" reply
        byte[] done; //std buffer for exchanging done reply
        done = new byte[1024];
        byte[] bad = new byte[1024]; //std buffer for "something went wrong" reply
        clear[0] = 111; // Ok signal
        bad[0] = 22; // Error signal
        done[0] = 33;
        byte[] result = new byte[0];
        byte[] assist = new byte[1024];

        try {
            while (true) {

                ServerController.getChannel().receive(buf);
                    if (Arrays.equals(buf.array(), done)) {
                        return result;
                    }
                    if (PacketUtils.checkHash(buf.array())) {
                        ServerController.getChannel().send(ByteBuffer.wrap(clear), ServerController.getRemoteAddr());
                        if (!Arrays.equals(assist,buf.array())) {
                            result = PacketUtils.merge(result, Arrays.copyOfRange(buf.array(), 0, 1012));
                            assist = Arrays.copyOf(buf.array(),1024);
                        }
                    } else {
                        ServerController.getChannel().send(ByteBuffer.wrap(bad), ServerController.getRemoteAddr());
                    }
                    buf.clear();
            }
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("Error in IO");
        }
        return null;
    }
}
