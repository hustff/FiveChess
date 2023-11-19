package FiveChess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoadPage {//双人五子棋的加载界面
    JFrame jFrame=new JFrame("双人五子棋登录");//绘制页面信息
    JButton add=new JButton("加入房间");
    JButton create=new JButton("创建房间");
    JTextField jTextField=new JTextField(20);
    JLabel jLabel=new JLabel("输入房主的ip地址");//输入对方的ip信息
    Box box=Box.createVerticalBox();
    public void init()
    {
        create.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//设置创建房间的点击事件
                add.setVisible(false);
                create.setVisible(false);
                jLabel.setText("创建房间成功,请等待玩家加入.....");
                jLabel.setVisible(true);
                System.out.println("创建房间");
                new PVPMainReceiveBoard().init();
                jFrame.setVisible(false);
            }
        });
        add.addMouseListener(new MouseAdapter() {//设置进入房间的按钮信息
            @Override
            public void mouseClicked(MouseEvent e) {
                if(add.getText().equals("确认输入")) {
                    System.out.println("已经确认输入:" + jTextField.getText());
                        new PVPMainChessBoard(jTextField.getText()).init();
                        jFrame.setVisible(false);
                }
                create.setVisible(false);
                jLabel.setVisible(true);
                jTextField.setVisible(true);
                add.setText("确认输入");
            }
        });
        jLabel.setVisible(false);//输入成功则关闭页面
        jTextField.setVisible(false);
        box.add(create);
        box.add(add);
        box.add(jLabel);
        box.add(jTextField);
        jFrame.setResizable(false);
        jFrame.add(box);
        jFrame.setPreferredSize(new Dimension(150,120));
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
