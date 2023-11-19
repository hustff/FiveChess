package FiveChess;

public class PVC {

   int [] scores;
   public PVC(){
       scores=new int[11];//初始化五元组
       scores[0]=7;//五元组为空
       scores[1]=35;//有一个子
       scores[2]=800;//有两个子
       scores[3]=15000;//有三个
       scores[4]=800000;//有四个
       scores[5]=0;//不存在
       scores[6]=0;//混杂
       scores[7]=15;//有一个其他子
       scores[8]=400;//有两个其他子
       scores[9]=1800;//三个
       scores[10]=100000;//四个
   }


    public int addScores(Chess[][]  chess,int x,int y)//计算该点的分数之和
    {
        int sum=0;
        for(int i=x-4;i<=x;i++){//计算横向的五元组
            int num=0;//棋子数量
            //保存初始位置
            for(int j=i;j<=i+4;j++){//防止越界
                if(j<0||j+4>14){
                    num=5;
                    break;
                }
                if(chess[j][y].getColor()==2)num++;
                if(chess[j][y].getColor()==1){
                    num=6;
                    break;
                }

            }
            sum+=scores[num];
        }
        for(int i=y-4;i<=y;i++){//计算纵向的五元组
            int num=0;//棋子数量
            //保存初始位置
            for(int j=i;j<=i+4;j++){//防止越界
                if(j<0||j+4>14){
                    num=5;
                    break;
                }
                if(chess[x][j].getColor()==2)num++;
                if(chess[x][j].getColor()==1){
                    num=6;
                    break;
                }

            }
            sum+=scores[num];
        }
        for(int i=-4;i<=0;i++){//计算-x方向的分数;
         int num=0;
         for(int j=i;j<=i+4;j++){
             if(x+j<0||x+j>14||y+j<0||y+j>14){
                 num=5;
                 break;
             }
             if(chess[x+j][y+j].getColor()==2)num++;
             if(chess[x+j][y+j].getColor()==1){
                 num=6;
                 break;
             }

         }
            sum+=scores[num];
        }
        for(int i=-4;i<=0;i++){//计算x方向的分数;
            int num=0;
            for(int j=i;j<=i+4;j++){
                if(x-j<0||x-j>14||y+j<0||y+j>14){
                    num=5;
                    break;
                }
                if(chess[x-j][y+j].getColor()==2)num++;
                if(chess[x-j][y+j].getColor()==1){
                    num=6;
                    break;
                }
            }
            sum+=scores[num];
        }
        for(int i=x-4;i<=x;i++){//计算白子横向的五元组
            int num=0;//棋子数量
            //保存初始位置
            for(int j=i;j<=i+4;j++){//防止越界
                if(j<0||j+4>14){
                    num=0;
                    break;
                }
                if(chess[j][y].getColor()==1)num++;
                if(chess[j][y].getColor()==2){
                    num=0;
                    break;
                }

            }
            sum+=scores[num+6];
        }
        for(int i=y-4;i<=y;i++){//计算纵向的五元组
            int num=0;//棋子数量
            //保存初始位置
            for(int j=i;j<=i+4;j++){//防止越界
                if(j<0||j+4>14){
                    num=0;
                    break;
                }
                if(chess[x][j].getColor()==1)num++;
                if(chess[x][j].getColor()==2){
                    num=0;
                    break;
                }

            }
            sum+=scores[num+6];
        }
        for(int i=-4;i<=0;i++){//计算-x方向的分数;
            int num=0;
            for(int j=i;j<=i+4;j++){
                if(x+j<0||x+j>14||y+j<0||y+j>14){
                    num=0;
                    break;
                }
                if(chess[x+j][y+j].getColor()==1)num++;
                if(chess[x+j][y+j].getColor()==2){
                    num=0;
                    break;
                }

            }
            sum+=scores[num+6];
        }
        for(int i=-4;i<=0;i++){//计算x方向的分数;
            int num=0;
            for(int j=i;j<=i+4;j++){
                if(x-j<0||x-j>14||y+j<0||y+j>14){
                    num=0;
                    break;
                }
                if(chess[x-j][y+j].getColor()==1)num++;
                if(chess[x-j][y+j].getColor()==2){
                    num=0;
                    break;
                }
            }
            sum+=scores[num+6];
        }
        return sum;
    }
    public Coor getCoor(Chess[][]chess){//获取下一个点的坐标
        int x=0,y=0;
        Coor coor=new Coor(0,0);
        int max=0;
        for(x=0;x<15;x++){
            for(y=0;y<15;y++){
                if(chess[x][y].getColor()==0){
                    if(addScores(chess,x,y)>max) {//选出分数最大的地方
                        coor.setX(x);
                        coor.setY(y);
                        max = addScores(chess, x, y);
                    }
                }
            }
        }
        return coor;
    }

}
