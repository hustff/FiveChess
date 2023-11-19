package FiveChess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class gameOver {
    BufferedImage b;

    {
        try {
            b = ImageIO.read(new File("Resourse\\gameover.png"));//加载结束游戏的图片
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class MyCanvas extends JPanel
    {
        @Override
        public void paint(Graphics g) {
        g.drawImage(b,0,0,615,615,null);
        g.setFont(new Font("Songst",Font.BOLD,30));
        g.drawString("点击屏幕重新开始.....",200,550);//绘制胜利信息
        g.drawString("恭喜"+name+"获得胜利",200,100);
        }
    }
    MyCanvas myCanvas=new MyCanvas();//重新开始游戏
    String name;
    public gameOver(String name) {
        this.name = name;
    }
    JFrame jFrame=new JFrame("五子棋");

    public void init(){
        myCanvas.setPreferredSize(new Dimension(615,615));
        myCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//设置点击事件
                jFrame.setVisible(false);
                new FirstPage().init();
            }
        });
        jFrame.add(myCanvas);

        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
