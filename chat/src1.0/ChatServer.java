import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    boolean started = false;
    ServerSocket serverSocket = null;

    public static void main(String[] args) {
        new ChatServer().start();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(8088);
            started = true;
        }catch (BindException e){
            System.out.println("端口使用中。。。");
            System.out.println("请关掉相关端口并重新运行服务器！");
            System.exit(0);
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        try{

            while(started) {
                //应当在这里等待，可以用异步的解决办法，也可以使用多线程
                Socket s = serverSocket.accept();
                Client c = new Client(s);
System.out.println("a Client connected");
                new Thread(c).start();
                // dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //把最后的关了
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client implements Runnable{
        private Socket s;
        private DataInputStream dis = null;
        private boolean bConnected = false;

        public Client(Socket s){
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

         @Override
         public void run() {
             try {
                    while (bConnected) {
                     //阻塞性方法，会导致死循环，无法连接第二个Client端，傻傻等
                     String str = dis.readUTF();
                     System.out.println(str);
                    }
                 } catch(EOFException e){
                     System.out.println("Client closed!");
                 } catch(IOException e){
                     //close放在这里也不是特别严谨
                     //try {
                     // dis.close();
                     //  s.close();
                     // } catch (IOException e1) {
                     //   e1.printStackTrace();
                     // }
                     e.printStackTrace();
                 } finally{
                     try {
                         //加上判断语句会更加严谨
                         if (dis != null) dis.close();
                         if (s != null) s.close();
                     } catch (IOException e1) {
                         e1.printStackTrace();
                     }
                 }
             }
     }
}
