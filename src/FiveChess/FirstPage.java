package FiveChess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class FirstPage  {
    JFrame jFrame=new JFrame("五子棋");//设计主页面
    BufferedImage bufferedImage;
    {
        try {
            bufferedImage = ImageIO.read(new File("Resourse\\first.png"));//读取主页面的背景图
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class Canvas extends JPanel//自定义画布
    {
        @Override
        public void paint(Graphics g) {
            g.drawImage(bufferedImage,0,0,600,410,null);
        }
    }

    Canvas canvas=new Canvas();

    public void init()
    {
        jFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//获取鼠标点击事件
                int x=e.getX();
                int y=e.getY();
                if(x>=220&&x<=472&&y>=75&&y<=150)//判断点击的位置加载函数
                {
                    jFrame.setVisible(false);
                     new PVCMainBoard().init();
                }
                else if(x>=220&&x<=472&&y>150&&y<225){
                    jFrame.setVisible(false);
                    new MainChessBoard().init();
                }
                else if(y>=225&&y<=300&&x>=220&&x<=472)
                {
                    jFrame.setVisible(false);
                     new LoadPage().init();
                }
                else if(y>=325&&y<=400&&x>=220&&x<=472)
                {
                    System.exit(0);
                }
            }
        });
        jFrame.add(canvas);
        canvas.repaint();
        jFrame.setPreferredSize(new Dimension(600,410));//设置大小
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
    public static void main(String[] args) {
        new FirstPage().init();
    }



}
