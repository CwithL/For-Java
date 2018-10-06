package homework;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class keyEvent_example extends JFrame{

    public keyEvent_example() {
        // TODO Auto-generated constructor stub
        super();
        setBounds(100,100,500,375);
        setTitle("键盘事件实例");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final JLabel label = new JLabel();
        label.setText("备注");
        getContentPane().add(label,BorderLayout.WEST);

        final JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        JTextArea textArea = new JTextArea();
        textArea.addKeyListener(new KeyListener() {


            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                String KeyText = KeyEvent.getKeyText(e.getKeyCode());//获得编码然后再按照编码格式还原文本
                if(e.isActionKey()){
                    System.out.println("你按下的是动作键“" + KeyText+"”");
                }
                else{

                    System.out.println("你按下的是非动作键" + KeyText + "“");
                    int keyCode = e.getKeyCode();
                    switch(keyCode){

                        case KeyEvent.VK_CONTROL : System.out.print("，Ctrl键被按下");break;
                        case KeyEvent.VK_ALT : System.out.print("，Alt键被按下");break;
                        case KeyEvent.VK_SHIFT : System.out.print("，shift被按下");break;
                    }
                    System.out.println();
                }

            }

            public void keyTyped(KeyEvent e){
                System.out.println("此次输入的是，"+e.getKeyChar()+"”");
            }

            public void keyReleased(KeyEvent e){

                String keyText = KeyEvent.getKeyText( e.getKeyCode() );
                System.out.println("你释放的是“" + keyText + "”键");
                System.out.println();

            }
        });

        //textArea.setLineWrap(true);
//	   textArea.setRows(3);
//	   textArea.setColumns(15);
        scrollPane.setViewportView(textArea);    //创建一个窗口（如果有必要）并设置其视图。不直接为
        //JScrollPane构造方法提供视图的应用程序应使用此方法指定将显示在窗口中的滚动组件子级  form API
    }

    public static void main(String[] args){
        keyEvent_example frame =  new keyEvent_example();
        frame.setVisible(true);
    }
}