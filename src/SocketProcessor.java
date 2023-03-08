import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class SocketProcessor extends JPanel implements Runnable
{
    Socket s;

    public SocketProcessor(Socket s) {
        this.s =s ;
    }
    @Override
    public void run() {
        if(GameSnake.g.multiPlayer){
            System.out.println("Client connected");

            //khi sever connected se tao mot doi tuong con ran
            Snake player = new  Snake();
            GameScreen.numberOfPlayer++;

            GameSnake.g.CreateSnake(player);
            String data = "";
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                while (data.compareTo("stop") != 0) {
                    data = input.readLine();
                    System.out.println(data);
                    if(data.equals("up")){
                        System.out.println("con ran dang di len");
                        player.setVector(Snake.GO_UP);

                    }
                    if(data.equals("down")){
                        System.out.println("con ran dang di xuong");
                        player.setVector(Snake.GO_DOWN);

                    }
                    if(data.equals("left")){
                        System.out.println("con ran dang di trai");
                        player.setVector(Snake.GO_LEFT);

                    }
                    if(data.equals("right")){
                        System.out.println("con ran dang di phai");
                        player.setVector(Snake.GO_RIGHT);

                    }
                    if(data.equals("P")){
                        GameScreen.isPlaying = !GameScreen.isPlaying;
                        if(GameScreen.isGameOver){
                            GameScreen.isGameOver=false;
                            GameSnake.g.ResetGame();
                        }
                        System.out.println(GameScreen.isPlaying);
                    }

                }
                input.close();
                s.close();
            }
            catch (IOException e) {
                System.out.println("error");
            }
        }
        else {
            System.out.println("dang o che do choi 1 nguoi");
        }

    }

}

