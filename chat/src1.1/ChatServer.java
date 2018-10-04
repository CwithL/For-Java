import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatServer {

    boolean started = false;
    ServerSocket serverSocket = null;
//利用集合类来解决存储多个Client对象
    List<Client> clients = new ArrayList<Client>();

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
                clients.add(c);
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
        private DataOutputStream dos = null;

        public Client(Socket s){
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String str) {
            try {
                dos.writeUTF(str);
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
                     for(int i = 0; i<clients.size();i++) {
                         Client c = clients.get(i);
                         c.send(str);
                     }
                     /*发消息到各客户端不需要锁定，而且这样的效率非常低
                      for(Iterator<Client> it = clients.iterator();it.hasNext();) {
                          Client c = it.next();
                          c.send(str);
                      }
                      */
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
                         if (dos != null) dos.close();
                         if (s != null) s.close();
                     } catch (IOException e1) {
                         e1.printStackTrace();
                     }
                 }
             }
     }
}
