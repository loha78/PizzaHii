package projectapp.com.pizzahii.Controller;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by m406368 on 28/10/2015.
 */




    public class NetworkConnection extends AsyncTask<Void, Void, Boolean> {

        private String ip = "10.0.2.2";
        private int port = 7777;
        public Socket socket;

        public PrintWriter writer = new PrintWriter(System.out, true);
        public BufferedReader reader;

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
                System.out.println("Connected");
            } else {
                System.out.println("NOT Connected");
            }
        }

        public BufferedReader getReader() {
            return this.reader;
        }

        public PrintWriter getWriter() {
            return this.writer;
        }

    }



