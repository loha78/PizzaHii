package projectapp.com.pizzahii.Controller;

import android.os.AsyncTask;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by m406368 on 28/10/2015.
 */
public class NetworkConnection extends AsyncTask<Void, Void, Boolean> {

    private String ip = "172.18.1.153";
    private int port = 7777;
    public Socket socket;

    public PrintWriter writer = new PrintWriter(System.out, true);

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            socket = new Socket(ip, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void onPostExecute(Boolean b) {
        if (b) {
            System.out.println("Connected");
        }
        else {
            System.out.println("NOT Connected");
        }
    }

    public PrintWriter getWriter() {
        return this.writer;
    }
}
