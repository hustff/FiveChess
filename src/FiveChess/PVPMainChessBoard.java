package FiveChess;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import static javax.sound.sampled.AudioSystem.getClip;

public class PVPMainChessBoard extends MainChessBoard{
    QQchatWindow qQchatWindow;//创建聊天页面
    public PVPMainChessBoard() {

    }
    public PVPMainChessBoard(String s) {//实现网络连接
        try {
            this.socket = new Socket(s,10086);
            qQchatWindow=new QQchatWindow(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Socket socket;
    public  void Receive()//接收棋子信息
    {
        new Thread(()-> {
            while (true) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String[] ans = bufferedReader.readLine().split(",");
                    chessBoard.chess[Integer.parseInt(ans[0])][Integer.parseInt(ans[1])].setColor(2);//获取坐标并转化
                    chessBoard.limit_time=30;
                    ChessBoard.is_white=true;
                    if (IsGameOver.whowin(Integer.parseInt(ans[0]), Integer.parseInt(ans[1]), chessBoard.chess, 2) == 2) {//判断是否胜利
                        jFrame.setVisible(false);
                        clip.stop();
                        new gameOver("黑棋").init();
                    }
                    chessBoard.repaint();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    public void SendChess(int x,int y) throws IOException {//发送棋子坐标
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write(x+","+y);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        System.out.println("发送信息"+x+"  "+y);
    }
    public void init()//初始化函数
    {
        RetimeLimit();
        init_Chess(chessBoard.chess);//初始化棋盘
        chessBoard.repaint();
        chessBoard.addMouseListener(new PVPMainChessBoard.MyMouselistener());//添加监听器
        chessBoard.addMouseMotionListener(new MyouseMoveListener());
        chessBoard.setPreferredSize(new Dimension(ChessBoard.BOARD_SIZE_X,ChessBoard.BOARD_SIZE_Y));
        clip.start();//播放音乐
        jFrame.add(chessBoard);
        qQchatWindow.setPreferredSize(new Dimension(300,615));
        qQchatWindow.setVisible(true);
        jFrame.add(qQchatWindow,BorderLayout.EAST);
        qQchatWindow.init();//初始化聊天界面
        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Hello world");
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                {
                    qQchatWindow.jButton.doClick();
                }
            }
        });
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Receive();
        chessBoard.repaint();
    }
    private  class MyMouselistener implements MouseListener//重写事件监听器
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(ChessBoard.is_white) {
                int x = e.getX();
                int y = e.getY();
                int X = (x - ChessBoard.PLUS_X) / ChessBoard.CHESSBOARD_X;
                int Y = (y - ChessBoard.PLUS_Y) / ChessBoard.CHESSBOARD_Y;
                if (chessBoard.chess[X][Y].getColor() == 0) {
                    chessBoard.chess[X][Y].setColor(1);
                    try {
                        SendChess(X,Y);//发送坐标
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ChessBoard.is_white = !ChessBoard.is_white;
                    chessBoard.limit_time=30;
                    if (isGameOver.whowin(X, Y, chessBoard.chess, 1) == 1) {
                        jFrame.setVisible(false);
                        clip.stop();
                        new gameOver("白棋").init();
                    }
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
