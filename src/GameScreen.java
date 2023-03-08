import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GameScreen extends JPanel implements Runnable {

     static int numberOfPlayer = 0 ;
    Thread thread;

    static  int [][] bg = new int[20][20];

    static int padding = 10;
    static  int WIDTH = 400;
    static  int HIGHT = 400;

    static boolean isPlaying = false;

    boolean multiPlayer = false;


    static SQLAccess sqlAccess;

    //player mac dinh
    Snake player;

    //list chua nhung player dang duoc choi
    static  List<Snake> playersArePlaying = new ArrayList<>();

    static boolean isGameOver =false;
    public GameScreen(){

        sqlAccess = new SQLAccess();
        //tao doi tuong player tren screen
        player = new Snake();
        numberOfPlayer=1;

        //Score list

        //truyen vao this de chay cac phuong thuc
        thread = new Thread(this);
        thread.start();
        bg[5][6] =2 ;
    }

    public void ResetGame(){
        player.resetGame();
        for (int i= 0 ; i < playersArePlaying.size();i++){
            playersArePlaying.get(i).resetGame();
        }
    }

    public void CreateSnake(Snake player){
        playersArePlaying.add(player);
    }

    public void paintBg(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0 ,0,WIDTH + padding*2,HIGHT + padding*2);
        for(int i = 0 ; i < 20 ; i++){
            for(int j = 0 ; j <20;j++){
                if(bg[i][j]==0)g.setColor(Color.gray);
                if(bg[i][j]==1)g.setColor(Color.red);
                if(bg[i][j]==2)g.setColor(Color.yellow);
                g.fillRect(i*20 + 1,j*20 + 1,18,18);
            }
        }
    }
    public  void drawBoder(Graphics g){
        g.setColor(Color.blue);
        g.drawRect(0,0,WIDTH,HIGHT);
        g.drawRect(1,1,WIDTH-2,HIGHT-2);

    }
    public void DrawScore(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(18f));
        g.drawString(player.nameOfPlayer,450,100);
        g.drawString(Integer.toString(player.score) ,550,100);

        for(int i = 0 ; i < playersArePlaying.size();i++){
            // g.setColor(playersArePlaying.get(i).color);
            g.setColor(Color.BLACK);
            g.setFont(g.getFont().deriveFont(18f));
            g.drawString(playersArePlaying.get(i).nameOfPlayer,450,120+i*20);
            g.drawString(Integer.toString(playersArePlaying.get(i).score) ,550,120+i*20);
        }
    }
    public void paint(Graphics g){

        long t2= 0 ;
        //ve background
        paintBg(g);
        //ve con ran
        //g.setColor(player.color);
        player.drawSnake(g);

        //draw snake connect client
        for(int i = 0 ; i < playersArePlaying.size();i++){
           // g.setColor(playersArePlaying.get(i).color);
            playersArePlaying.get(i).drawSnake(g);
        }
        //draw score
        DrawScore(g);

        //draw boder
        drawBoder(g);

        if(!isPlaying&&!isGameOver){

                g.setColor(Color.black);
                g.setFont(g.getFont().deriveFont(18f));
                g.drawString("PRESS P TO CONTINUE",80,200);


        }
        if(isGameOver){
            g.setColor(Color.black);
            g.setFont(g.getFont().deriveFont(28f));
            g.drawString("GAME OVER",80,200);
        }

    }
    //ham update ~ nhu unity update lai frame sau moi 20s sleep
    public void run(){
        while (true){
            if(isPlaying){

                //chay ham update cua player mac dinh
                player.update();

                //chay ham update cua player dang nhap them
                for(int i = 0 ; i < playersArePlaying.size();i++){
                    playersArePlaying.get(i).update();
                }
            }
            repaint();
            //ki thuat de han che khung hinh chay trong vong 20ms
            try {
                Thread.sleep(20);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

}
