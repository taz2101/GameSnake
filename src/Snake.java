import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Snake {
    int size = 3;

    long velocity  = 200;

    int maxSize = 7;

    Color color;

    int score;

    String nameOfPlayer;

    //toa do x va y cua 1 khuc trong con ran
    int[]x;
    int[]y;

    //cac bien tinh, tao no da duoc tao roi = > khong the thay doi gia tri trong luc chay chuong trinh
    public static int GO_UP = 1;
    public static int GO_DOWN = -1;
    public static int GO_LEFT = 2;
    public static int GO_RIGHT = -2;

    int vector= Snake.GO_DOWN;
    long beginTime = 0;
    public Snake(){

        Random r = new Random();

        score = 0 ;


        nameOfPlayer = JOptionPane.showInputDialog("Nhap ten cua ban");

        int chosen = r.nextInt(3);
        if(chosen==0){
            color = Color.red;
        }
        else if (chosen==1){
            color = Color.blue;
        }
        else  if(chosen==3){
            color = Color.CYAN;
        }
        else {
            color = Color.MAGENTA;
        }


        x = new int[400];
        y = new int[400];

        x[0] = 5;
        y[0] = 4;

        x[1] = 5;
        y[1] = 3;

        x[2] = 5;
        y[2] = 2;


    }

    public void resetGame(){
        x = new int[400];
        y = new int[400];

        x[0] = 5;
        y[0] = 4;

        x[1] = 5;
        y[1] = 3;

        x[2] = 5;
        y[2] = 2;
        size=3;
        score = 0;

        vector=Snake.GO_DOWN;

        velocity=200;
    }

    //set huuong di cua con ran va check khong cho ran quay dau lai
    public void setVector(int v){

        if(vector != -v){
            vector = v;
        }

    }

    public boolean checkPointInSnake(int x1, int y1) {
        for (int i = 0; i < size; i++)
            if (x[i] == x1 && y[1] == y1) {
                return true;
            }
            return false;
    }

    public Point randomMoi(){
        Random r = new Random();
        int x;
        int y;

        do {
            x = r.nextInt(19);
            y= r.nextInt(19);
        }
        while (checkPointInSnake(x,y));
        return  new Point(x,y);
    }
    public void update(){

        if(size % maxSize == 0){
            size++;
            velocity = (int)( velocity * 0.7);
        }

        for(int i = 1 ; i < size;i++){
            if(x[0]==x[i] && y[0]==y[i]){
              GameScreen.isPlaying=false;
              GameScreen.isGameOver=true;
              GameScreen.sqlAccess.InsertDB(nameOfPlayer,score);
            }
        }

        //tao ra su chuyen dong cua con ran
        if(System.currentTimeMillis() - beginTime > velocity){

            //an moi
            if(GameScreen.bg[x[0]][y[0]]==2){
                size++;
                score = score+2;
                GameScreen.bg[x[0]][y[0]]=0;
                GameScreen.bg[randomMoi().x][randomMoi().y] = 2;
            }

            //moi khi update toa do cua khuc sau se bang toa do cua khuc truoc
            for(int i = size - 1 ; i > 0 ;i--){
                x[i] = x[i-1];
                y[i] = y[i-1];
            }
            if(vector ==Snake.GO_UP){
                y[0]--;
            }
            if(vector ==Snake.GO_DOWN){
                y[0]++;
            }
            if(vector ==Snake.GO_LEFT){
                x[0]--;
            }
            if(vector ==Snake.GO_RIGHT){
                x[0]++;
            }

            if(x[0]<0) x[0] = 19;
            if(x[0]>19) x[0] = 0;
            if(y[0]<0) y[0] = 19;
            if(y[0]>19) y[0] = 0;


            beginTime = System.currentTimeMillis();
        }

    }
    public void drawSnake(Graphics g){
        g.setColor(color);
        for(int i = 0 ; i<size;i++){
            if(i == 0){
                g.setColor(Color.GREEN);
                g.fillRect(x[i]*20 ,y[i]*20 ,18,18);
                g.setColor(color);
            }
            else {
                g.fillRect(x[i]*20 ,y[i]*20 ,18,18);
            }
            }
            //can cong them 1 chi so vao vi tri nhung loai bo de tao hieu ung

    }

}
