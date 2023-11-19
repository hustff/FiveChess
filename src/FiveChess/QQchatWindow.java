package FiveChess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class QQchatWindow extends JPanel{//继承JPanel组件
    String ip;
    JButton jButton=new JButton("发送消息");//发送按钮
    JTextArea jTextArea=new JTextArea(35,30);//设置文本区域大小
    JTextField jTextField=new JTextField(20);//设置最下面区域大小
    Socket socket;
    ServerSocket serverSocket;//设置TCP的发送接收函数
    Box box=Box.createVerticalBox();
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    public QQchatWindow(String ip) {//初始化QQ的窗口
        this.ip = ip;
        try {
            serverSocket=null;
            socket = new Socket(this.ip, 10087);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ReceiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     public QQchatWindow()
     {
         try {
             serverSocket=new ServerSocket(10087);
             socket=serverSocket.accept();
             bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
             ReceiveMessage();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    void ReceiveMessage()//多线程接收消息
    {
        new Thread(()->{
            while (true)
            {
                try {
                    String s=bufferedReader.readLine();
                    jTextArea.append("对手:   "+s+"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    void init()//初始化函数窗口
    {

        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String s=jTextField.getText();
                if(!s.equals("")) {//发送消息
                    jTextField.setText("");
                    jTextArea.append("您:  " + s + "\n");
                    try {
                        bufferedWriter.write(s);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        jTextArea.setEditable(false);//令消息接收窗口不可被编辑
        jTextArea.add(new JScrollBar());
        box.add(jTextArea);
        box.add(jTextField);
        box.add(jButton);
         this.add(box);
        this.setPreferredSize(new Dimension(300,615));
       this.setVisible(true);
    }

}
