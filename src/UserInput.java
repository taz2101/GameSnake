import java.io.*;
import java.net.Socket;


public class UserInput implements Runnable {

    @Override

    public void run() {
        BufferedReader bf = new BufferedReader( new InputStreamReader(System.in));
        try {
            String data = bf.readLine();
            for(Socket s : GameSnake.clientList){
                if(s.isConnected()){
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter((s.getOutputStream())));
                    bw.write(data + "\n");
                    bw.flush();
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
