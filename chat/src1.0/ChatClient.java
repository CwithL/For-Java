import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient extends Frame {

    Socket s = null;
    DataOutputStream dos = null;

    TextField tfTxt = new TextField();
    TextArea taContent = new TextArea();

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
    }

    public void connect() {
        try {
            s = new Socket("127.0.0.1", 8088);
            dos = new DataOutputStream(s.getOutputStream());
System.out.println("connected!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect() {
        try {
            dos.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class TFlistener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfTxt.getText().trim();
            taContent.setText(str);
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
}
