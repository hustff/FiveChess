package FiveChess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
public class PVCMainBoard extends MainChessBoard {//继承自MainChessBoard
    PVC pvc=new PVC();
    private  class MyMouselistener implements MouseListener//重写点击事件
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x=e.getX();
            int y=e.getY();
            int X=(x-ChessBoard.PLUS_X)/ChessBoard.CHESSBOARD_X;
            int Y=(y-ChessBoard.PLUS_Y)/ChessBoard.CHESSBOARD_Y;
            if(chessBoard.chess[X][Y].getColor()==0)
            {
                if(ChessBoard.is_white) {
                    int win=1;
                    chessBoard.setLimit_time(30);
                    chessBoard.chess[X][Y].setColor(1);
                    if (isGameOver.whowin(X, Y, chessBoard.chess, 1) == 1) {
                        chessBoard.setLimit_time(Integer.MAX_VALUE);
                        win=0;
                        jFrame.setVisible(false);
                        clip.stop();
                        new gameOver("白棋").init();
                    }
                    ChessBoard.is_white = !ChessBoard.is_white;
                    chessBoard.repaint();
                    if(win==1) {
                        Coor coor = pvc.getCoor(chessBoard.chess);
                        chessBoard.setLimit_time(30);
                        chessBoard.chess[coor.getX()][coor.getY()].setColor(2);
                        if (isGameOver.whowin(coor.getX(), coor.getY(), chessBoard.chess, 2) == 2) {
                            chessBoard.setLimit_time(Integer.MAX_VALUE);
                            jFrame.setVisible(false);
                            clip.stop();
                            new gameOver("黑棋").init();
                        }
                        ChessBoard.is_white = !ChessBoard.is_white;
                        chessBoard.repaint();
                    }
                }


            }
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
    public void init()
    {
        RetimeLimit();//开始倒计时
        super.init_Chess(chessBoard.chess);//初始化棋盘
        chessBoard.repaint();
        chessBoard.addMouseListener(new MyMouselistener());
        chessBoard.addMouseMotionListener(new MyouseMoveListener());
        jFrame.setPreferredSize(new Dimension(ChessBoard.BOARD_SIZE_X,ChessBoard.BOARD_SIZE_Y+30));
        clip.start();//开始播放音乐
        jFrame.add(chessBoard);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
