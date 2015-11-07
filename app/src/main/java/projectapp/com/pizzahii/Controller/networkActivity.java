package projectapp.com.pizzahii.Controller;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by NAJI on 06/11/2015.
 */
public class networkActivity extends AppCompatActivity{

    public PrintWriter writer = new PrintWriter(System.out, true);
    public BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public ReadMessages readMessages;

    public class NetworkConnection extends AsyncTask<Void, Void, Boolean> {

        private String ip = "10.0.2.2";
        private int port = 7777;
        public Socket socket;

        @Override
        protected void onPreExecute() {
            System.out.println("StartNetwork.onPreExecute");
        }



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
                readMessages = new ReadMessages();
                readMessages.execute();
            } else {
                System.out.println("NOT Connected");
            }
        }

    }

    public class ReadMessages extends AsyncTask<Void, String, Void> {
        public BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        @Override
        protected Void doInBackground(Void... v) {
            while (!isCancelled()) {
                try {
                    String message = reader.readLine();
                    publishProgress(message);
                } catch (IOException e) {
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... messages) {
            publishProgress(messages[0] + "\n");
        }

        private void publishProgress(String string){
            System.out.println(string);
        }

    }


}
