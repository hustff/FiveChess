package FiveChess;
public class Chess {
    public final static int CHESS_WHITE=1;//设置棋子的颜色,黑色为2,白色为1,空白为0
    public final static int CHESS_BLACK=2;
    public final static int CHESS_BLANK=0;
    private int color;
    private Coor coor;
    public Coor getCoor() {
        return coor;
    }
    public void setCoor(Coor coor) {
        this.coor = coor;
    }
    public Chess() {
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
}
