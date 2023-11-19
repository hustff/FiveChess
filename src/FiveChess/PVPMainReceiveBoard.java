package FiveChess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.net.ServerSocket;
public class PVPMainReceiveBoard extends PVPMainChessBoard{
    ServerSocket serverSocket;
    {
        try {
            serverSocket = new ServerSocket(10086);
            socket=serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public PVPMainReceiveBoard()
    {
        qQchatWindow=new QQchatWindow();
    }
    public void ReceiveChess()
    {
       new Thread(()->
       {
           while(true)
           {
               try {
                   BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String[] ans=bufferedReader.readLine().split(",");
                   System.out.println("收到信息"+ans[0]+ans[1]);
                    chessBoard.chess[Integer.parseInt(ans[0])][Integer.parseInt(ans[1])].setColor(1);
                    chessBoard.limit_time=30;
                    ChessBoard.is_white=false;
                   if (IsGameOver.whowin(Integer.parseInt(ans[0]), Integer.parseInt(ans[1]), chessBoard.chess, 1) == 1) {
                       jFrame.setVisible(false);
                       clip.stop();
                       new gameOver("白棋").init();
                   }
                    chessBoard.repaint();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

       }).start();
    }
    public void init()
    {
        RetimeLimit();
        init_Chess(chessBoard.chess);
        chessBoard.addMouseListener(new PVPMainReceiveBoard.MyMouselistener());
        chessBoard.addMouseMotionListener(new PVPMainReceiveBoard.Mymousemovelistener());
        chessBoard.setPreferredSize(new Dimension(ChessBoard.BOARD_SIZE_X,ChessBoard.BOARD_SIZE_Y));
        jFrame.add(chessBoard);
        clip.start();
        qQchatWindow.setVisible(true);
        qQchatWindow.setPreferredSize(new Dimension(300,615));
        jFrame.add(qQchatWindow,BorderLayout.EAST);
        qQchatWindow.init();
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ReceiveChess();
        chessBoard.repaint();
    }
       private class Mymousemovelistener  implements MouseMotionListener
    {
        @Override
        public void mouseDragged(MouseEvent e) {
        }
        @Override
        public void mouseMoved(MouseEvent e) {
            if(!ChessBoard.is_white) {
                int x = e.getX();
                int y = e.getY();
                int X = (x - ChessBoard.PLUS_X) / ChessBoard.CHESSBOARD_X;
                int Y = (y - ChessBoard.PLUS_Y) / ChessBoard.CHESSBOARD_Y;
                chessBoard.position_X = X;
                chessBoard.position_Y = Y;
                chessBoard.repaint();
            }
        }
    }

    private  class MyMouselistener implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(!ChessBoard.is_white) {
                int x = e.getX();
                int y = e.getY();
                int X = (x - ChessBoard.PLUS_X) / ChessBoard.CHESSBOARD_X;
                int Y = (y - ChessBoard.PLUS_Y) / ChessBoard.CHESSBOARD_Y;
                if (chessBoard.chess[X][Y].getColor() == 0) {
                        chessBoard.chess[X][Y].setColor(2);try {
                        SendChess(X,Y);chessBoard.limit_time=30;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ChessBoard.is_white = !ChessBoard.is_white;
                }
                if (isGameOver.whowin(X, Y, chessBoard.chess, 2) == 2) {

                    jFrame.setVisible(false);
                    clip.stop();
                    new gameOver("黑棋").init();
                }
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
}
