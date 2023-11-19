package FiveChess;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import static javax.sound.sampled.AudioSystem.*;
public class MainChessBoard {
    Clip clip;

    {
        try {
            clip = getClip();//添加背景音乐
            clip.open(AudioSystem.getAudioInputStream(new File("Resourse\\123_02.wav")));
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    IsGameOver isGameOver=new IsGameOver();
    JFrame jFrame=new JFrame("五子棋");//绘制游戏的主页面
    ChessBoard chessBoard=new ChessBoard();
    JMenuBar jMenuBar=new JMenuBar();
    JMenuItem jMenuItem=new JMenuItem("悔棋");
    Coor[] coors=new Coor[235];
    int coorstack=-1;
    public MainChessBoard()  {
    }
    public void init()
    {
        RetimeLimit();//开始倒计时
        init_Chess(chessBoard.chess);//初始化棋盘
        jMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("hello");
                Withdraw();
            }
        });
        jMenuBar.add(jMenuItem);
        chessBoard.repaint();
        chessBoard.addMouseListener(new MyMouselistener());
        chessBoard.addMouseMotionListener(new MyouseMoveListener());
        jFrame.setPreferredSize(new Dimension(ChessBoard.BOARD_SIZE_X,ChessBoard.BOARD_SIZE_Y+30));
        clip.start();//开始播放音乐
        jFrame.add(chessBoard);
        jFrame.setJMenuBar(jMenuBar);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    void init_Chess(Chess[][] chess)
    {
        int i,j;
        for(i=0;i<15;i++)
        {
            for(j=0;j<15;j++)
            {
                chess[i][j]=new Chess();
                chess[i][j].setColor(0);//初始将所有棋子的颜色值为0
                coors[i*15+j]=new Coor(0,0);//初始悔棋栈
            }

        }

    }
    void Withdraw()
    {
        if(coorstack>0){
            int x=coors[coorstack].getX();
            int y=coors[coorstack].getY();
            chessBoard.chess[x][y].setColor(0);
            x=coors[coorstack-1].getX();
            y=coors[coorstack-1].getY();
            chessBoard.chess[x][y].setColor(0);
            coorstack=coorstack-2;
            chessBoard.repaint();
        }
    }

    public void RetimeLimit()//设置结束时间的倒计时
    {
        new Thread(()->{//多线程处理倒计时
            while (true)
            {
                try {
                    Thread.sleep(1000);//休息一秒
                    chessBoard.setLimit_time(chessBoard.getLimit_time()-1);
                    chessBoard.repaint();//重新绘制页面
                    if(chessBoard.getLimit_time()<0)
                    {
                        jFrame.setVisible(false);
                        if(chessBoard.is_white)//判断是否胜利
                            new gameOver("黑棋").init();
                        else
                            new gameOver("白棋").init();
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    private Coor SearchGrid(MouseEvent e){
        int x=e.getX();
        int y=e.getY();
        int X=(x-ChessBoard.PLUS_X)/ChessBoard.CHESSBOARD_X;
        int Y=(y-ChessBoard.PLUS_Y)/ChessBoard.CHESSBOARD_Y;
        Coor coor=new Coor(X,Y);
        return coor;
    }
     class MyouseMoveListener implements MouseMotionListener
    {
        @Override
        public void mouseDragged(MouseEvent e) {
        }
        @Override
        public void mouseMoved(MouseEvent e) {//获取移动鼠标的位置画蓝色框框
            Coor coor=SearchGrid(e);
            chessBoard.position_X=coor.getX();
            chessBoard.position_Y=coor.getY();
            chessBoard.repaint();
        }
    }
    class Color{
        private int ChessColor;
        public int getChessColor() {
            return ChessColor;
        }
        public void setChessColor(int chessColor) {
            ChessColor = chessColor;
        }
    }
    private  void SetChess(Coor coor,Color color){
        chessBoard.chess[coor.getX()][coor.getY()].setColor(color.getChessColor());//更改颜色信息
        if (isGameOver.whowin(coor.getX(), coor.getY(), chessBoard.chess, color.getChessColor()) == color.getChessColor()) {//判断游戏是否结束
            jFrame.setVisible(false);
            clip.stop();
            if(color.getChessColor()==Chess.CHESS_WHITE) {
                new gameOver("白棋").init();
            }
            else {
                new gameOver(("黑棋")).init();
            }
            }

        ChessBoard.is_white = !ChessBoard.is_white;

    }
    private void AddChess(Coor coor){
        coorstack=coorstack+1;
        coors[coorstack]=coor;
    }
      class MyMouselistener implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {//获取鼠标点击位置
            Coor coor=SearchGrid(e);
            chessBoard.setLimit_time(30);
            if(chessBoard.chess[coor.getX()][coor.getY()].getColor()==0)
            {
                if(ChessBoard.is_white) {
                    SetChess(coor,1);
                }
                else {
                    SetChess(coor,2);
                }
               AddChess(coor);
            }
            chessBoard.repaint();

        }
        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public static void main(String[] args) {
        new MainChessBoard().init();
    }

}
