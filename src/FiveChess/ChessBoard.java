package FiveChess;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChessBoard  extends JPanel  {
    public static boolean is_white=true;
    public static final int CHESSBOARD_X=40;//棋子的大小
    public static final int CHESSBOARD_Y=40;
    public static final int PLUS_X=12;//棋盘的偏移量
    public static final int PLUS_Y=12;
    public static final int CHESS_R=36;
    Chess[][]chess=new Chess[15][15];//保存棋子的数组
    BufferedImage background;
    BufferedImage black;
    BufferedImage white;
    BufferedImage position;
    public int position_X=-100;//设置边框初始位置
    public int position_Y=-100;
    private int limit_time=30;//设置下棋的时间
    public int getLimit_time() {
        return limit_time;
    }

    public void setLimit_time(int limit_time ) {
        this.limit_time = limit_time;
    }

    public static final int BOARD_SIZE_X=615;
    public static final int BOARD_SIZE_Y=615;
    {
        try {
            position=ImageIO.read(new File("Resourse\\position.gif"));//加载图片
            white=ImageIO.read(new File("Resourse\\storn_white_20190825_083840.png"));
            black=ImageIO.read(new File("Resourse\\storn_black.png"));
            background = ImageIO.read(new File("Resourse\\bg_20190825_083840.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void paint(Graphics g) {
        g.drawImage(background,0,0,BOARD_SIZE_X,BOARD_SIZE_Y,null);//绘制背景
        g.setFont(new Font("Songst",Font.BOLD,30));

        if(limit_time<10)g.setColor(Color.RED);//倒数10秒字体颜色改变
        else
            g.setColor(Color.BLACK);
        if(is_white){
            g.drawString("现在是白棋的下棋时间,倒计时:"+limit_time,PLUS_X,PLUS_Y*2);
        }
        else g.drawString("现在是黑棋的下棋时间,倒计时:"+limit_time,PLUS_X,PLUS_Y*2);
        for(int i=0;i<15;i++)
        {
            for(int j=0;j<15;j++)
            {
                if(chess[i][j].getColor()==0)continue;//绘制棋子
                else if(chess[i][j].getColor()==1)
                {
                    int Pic_X=PLUS_X+i*CHESSBOARD_X;
                    int Pic_Y=PLUS_Y+j*CHESSBOARD_Y;
                    g.drawImage(white,Pic_X,Pic_Y,CHESS_R,CHESS_R,null);
                }
                else {
                    g.drawImage(black,PLUS_X+i*CHESSBOARD_X,PLUS_Y+j*CHESSBOARD_Y,CHESS_R,CHESS_R,null);
                }
            }
        }

            g.drawImage(position,PLUS_X+position_X*CHESSBOARD_X,PLUS_Y+position_Y*CHESSBOARD_Y,CHESS_R,CHESS_R,null);


    }
}
