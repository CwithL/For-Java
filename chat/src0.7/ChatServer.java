import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8088);
            while(true) {
                Socket s = serverSocket.accept();
System.out.println("a Client connected");
                DataInputStream dis = new DataInputStream(s.getInputStream());
                String str = dis.readUTF();
                System.out.println(str);
                dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
