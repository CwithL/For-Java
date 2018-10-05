import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ChatClient extends Frame {

    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;

    TextField tfTxt = new TextField();
    TextArea taContent = new TextArea();

    Thread tRecv = new Thread(new RecvThread());

    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }

    public void launchFrame() {
        setLocation(400, 300);
        this.setSize(300,300);
        add(tfTxt, BorderLayout.SOUTH);
        add(taContent, BorderLayout.NORTH);
        pack();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnect();
                System.exit(0);
            }
        });
        tfTxt.addActionListener(new TFlistener());
        setVisible(true);
        connect();
//连接上就启动
        tRecv.start();
    }

    public void connect() {
        try {
            s = new Socket("127.0.0.1", 8088);
            dos = new DataOutputStream(s.getOutputStream());
//连接上就开始获取服务端的发给客户端的信息,bConnected = true;
            dis = new DataInputStream(s.getInputStream());
System.out.println("connected!");
            bConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect() {
        try {
            dos.close();
//在这里关闭也不能解决122行报错的问题,因为关掉线程,readUTF还是可能在等
            dis.close();
            s.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        /*
        try {
            //增加dis.close();
            // bConnected = false;这句false没有用，因为readUTF一直在等待你改成了false还是在while循环之中
            // tRecv.join();利用合并还是不行,因为还在等你合并不了,但你可以重启一个监听的线程来监视时间,如果时间长就关闭线程
            bConnected = false;
            tRecv.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                dos.close();
                dis.close();
                s.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        */
    }

    private class TFlistener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfTxt.getText().trim();
           // taContent.setText(str);不用再次获得自己输入的字符串,服务端发过来的一个已经够了
            tfTxt.setText("");
//发一字符串到服务器端DataOutputStream、writeUTF（）写出去
            try {
               // DataOutputStream dos = new DataOutputStream(s.getOutputStream());全局变量直接用dos
                dos.writeUTF(str);
                dos.flush();
                //dos.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

    private class RecvThread implements Runnable{

        @Override
        public void run() {
            try {
                while(bConnected) {
                    String str = dis.readUTF();
                    //System.out.println(str);
                    taContent.setText(taContent.getText() + str + "\n");
                 }//加上下面的语句并不是很好，因为不算解决问题，只是强制停止并输出提示
            }catch (SocketException e) {
//报错因为还在等,你却关闭了这个窗口,报错就关闭这个线程是一种敷衍的解决办法
                System.out.println("force Bye");
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
    }
}
