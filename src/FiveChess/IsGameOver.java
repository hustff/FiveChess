package FiveChess;

public class IsGameOver {
    public static int whowin(int xIndex, int yIndex,Chess[][] chess,int role){//判断游戏是否结束
        int continueCount = 1    ;//连续棋子个数
        //横向向左
        for(int x=xIndex-1;x>=0;x--){
            if(chess[x][yIndex].getColor()== role){
                continueCount++;
            }
            else{
                break;
            }
        }
        //横向向右
        for(int x=xIndex+1;x<15;x++){
            if(chess[x][yIndex].getColor() == role){
                continueCount++;
            }
            else{
                break;
            }
        }
        //判断是否大于5
        if(continueCount>=5){
            return role;
        }
        else{
            continueCount = 1;
        }
        //纵向向上
        for(int y=yIndex-1;y>=0;y--){
            if(chess[xIndex][y].getColor() == role){
                continueCount++;
            }
            else{
                break;
            }
        }
        //纵向向下
        for(int y=yIndex+1;y<15;y++){
            if(chess[xIndex][y].getColor() == role){
                continueCount++;
            }
            else{
                break;
            }
        }
        //判断是否大于5
        if(continueCount>=5){
            return role;
        }
        else{
            continueCount = 1;
        }
        //左上
        for(int x=xIndex-1,y=yIndex-1;x>=0 && y>=0;x--,y--){
            if(chess[x][y].getColor() == role){
                continueCount++;
            }
            else{
                break;
            }
        }
        //右下
        for(int x=xIndex+1,y=yIndex+1;x<15 && y<15;x++,y++){
            if(chess[x][y].getColor() == role){
                continueCount++;
            }
            else{
                break;
            }
        }
        //判断是否大于5
        if(continueCount>=5){
            return role;
        }
        else{
            continueCount = 1;
        }
        //右上
        for(int x=xIndex+1,y=yIndex-1;x<15 && y>=0;x++,y--){
            if(chess[x][y].getColor() == role){
                continueCount++;
            }
            else{
                break;
            }
        }
        //左下
        for(int x=xIndex-1,y=yIndex+1;x>=0 && y<15;x--,y++){
            if(chess[x][y].getColor() == role){
                continueCount++;
            }
            else{
                break;
            }
        }
        //判断是否大于5
        if(continueCount>=5){
            return role;
        }
        else{
           continueCount=1;
        }
        return 0;
    }


}

