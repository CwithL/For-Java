import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {


    public static void main(String[] args) {
        boolean started = false;
        Socket s = null;
        DataInputStream dis = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8088);
        }catch (BindException e){
            System.out.println("端口使用中。。。");
            System.out.println("请关掉相关端口，并重新运行服务器！");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        try{
            started = true;
            while(started) {
                boolean bConnected = false;
                s = serverSocket.accept();
System.out.println("a Client connected");
                bConnected = true;
                dis = new DataInputStream(s.getInputStream());
                while(bConnected){
                    String str = dis.readUTF();
                    System.out.println(str);
                }
               // dis.close();
            }
        }catch (EOFException e){
            System.out.println("Client closed!");
        }catch (IOException e) {
            //close放在这里也不是特别严谨
            //try {
               // dis.close();
              //  s.close();
           // } catch (IOException e1) {
             //   e1.printStackTrace();
           // }
           e.printStackTrace();
        }finally {
            try {
                //加上判断语句会更加严谨
                if(dis != null) dis.close();
                if(s != null) s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
