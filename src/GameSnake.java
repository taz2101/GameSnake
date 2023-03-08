import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameSnake extends JFrame {

    static ArrayList<Socket> clientList = new ArrayList<Socket>();
    static GameScreen g;

    static List<Player> player = new ArrayList<>();



    public GameSnake(){
        setSize(700,500);

        JPanel panel = new JPanel();
        //panel.setBounds(0,0,500,500);
        //SQLAccess.getConnection();
        //tat se khong chay ngam
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new handler());
        setVisible(true);
        JButton StartSingePlayer = new JButton("Single Player ");
        JButton StartMultiPlayer = new JButton("Multiplayer");
        JButton Trophy = new JButton("Rankings");


        StartSingePlayer.setBounds(100,200,80,50);
        StartMultiPlayer.setBounds(200,200,80,50);

        Trophy.setBounds(300,200,80,50);
        panel.add(StartSingePlayer);
        panel.add(StartMultiPlayer);
        panel.add(Trophy);
        add(panel);
        StartSingePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartSingePlayer.setVisible(false);
                StartMultiPlayer.setVisible(false);
                Trophy.setVisible(false);
                g = new GameScreen();
                g.multiPlayer=false;
                add(g);
            }
        });
        StartMultiPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartSingePlayer.setVisible(false);
                StartMultiPlayer.setVisible(false);
                Trophy.setVisible(false);
                g = new GameScreen();
                g.multiPlayer=true;
                add(g);
            }
        });
        Trophy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SQLAccess s = new SQLAccess();
                s.ReadDB();

                panel.setVisible(false);

                JPanel ranking = new JPanel();

                JButton backBtn = new JButton("Back To Menu");

                backBtn.setBounds(200,100,80,50);
                JTable table = new JTable();
                ranking.setBounds(200,200,500,500);

                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Name");
                model.addColumn("Score");

                for(int i = 0 ; i < player.size();i++){
                    String data = player.get(i).name +"\t" +player.get(i).Score;
                    model.addRow(data.split("\t")); //split cat data theo /t
                    System.out.println(player.get(i).name +"\t" +player.get(i).Score);
                }
                backBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ranking.setVisible(false);
                        panel.setVisible(true);
                    }
                });
                table.setModel(model);
                ranking.add(table);
                ranking.add(backBtn);
                add(ranking);


            }
        });




    }

    public static void main(String[] args) throws IOException {
        GameSnake f = new GameSnake();

        //khoi tao sever
        ServerSocket sever = new ServerSocket(12345);
        System.out.println("Sever started");
        while (true) {
            Socket s = sever.accept();
            clientList.add(s);
            SocketProcessor sp = new SocketProcessor(s);
            Thread th = new Thread(sp);
            th.start();

        }
    }
    private class handler implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            if(e.getKeyCode()==KeyEvent.VK_P){
               GameScreen.isPlaying = !GameScreen.isPlaying;
               if(GameScreen.isGameOver){
                   GameScreen.isGameOver=false;
                   g.player.resetGame();
               }

               System.out.println(GameScreen.isPlaying);
            }
           if(e.getKeyCode()==KeyEvent.VK_UP){
               g.player.setVector(Snake.GO_UP);
           }
            if(e.getKeyCode()==KeyEvent.VK_DOWN){
                g.player.setVector(Snake.GO_DOWN);
            }
            if(e.getKeyCode()==KeyEvent.VK_LEFT){
                g.player.setVector(Snake.GO_LEFT);
            }
            if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                g.player.setVector(Snake.GO_RIGHT);
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
