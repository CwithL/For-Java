import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class ChatClient extends Frame {

    Socket socket;

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
                System.exit(0);
            }
        });
        tfTxt.addActionListener(new TFlistener());
        setVisible(true);
        connect();
    }

    public void connect() {
        try {
            socket = new Socket("127.0.0.1", 8088);
System.out.println("connected!");
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

        }
    }
}
