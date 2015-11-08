package projectapp.com.pizzahii.Controller;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkConnection extends AsyncTask<Void, Void, Boolean> {

    private String ip = "10.0.2.2";
    private int port = 7777;
    public Socket socket;

    public PrintWriter writer = new PrintWriter(System.out, true);
    public BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            socket = new Socket(ip, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void onPostExecute(Boolean b) {
        if (b) {
            System.out.println("Connected to server");
        }
        else {
            System.out.println("NOT Connected");
        }
    }

    public PrintWriter getWriter() { return this.writer; }

    public BufferedReader getReader() { return this.reader; }

}
