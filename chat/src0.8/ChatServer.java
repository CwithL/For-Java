import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {


    public static void main(String[] args) {
        boolean started = false;
        try {
            ServerSocket serverSocket = new ServerSocket(8088);
            started = true;
            while(started) {
                boolean bConnected = false;
                Socket s = serverSocket.accept();
System.out.println("a Client connected");
                bConnected = true;
                DataInputStream dis = new DataInputStream(s.getInputStream());
                while(bConnected){
                    String str = dis.readUTF();
                    System.out.println(str);
                }
                dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
