package projectapp.com.pizzahii.cuisine;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import projectapp.com.pizzahii.R;


public class NewPlatFrag extends Fragment {

    // Elements de connexion
    public StartNetwork network;
    private Socket socket;

    private PrintWriter writer = new PrintWriter(System.out, true);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public NewPlatFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        network = new StartNetwork();
        network.execute();

        return inflater.inflate(R.layout.fragment_new_plat, container, false);
    }


    //-------------------------------------------------------
    //               Connexion                     ----------
    //-------------------------------------------------------
    private class StartNetwork extends AsyncTask<Void, Void, Boolean> {

        private String ip = "10.0.2.2";
        private int port = 7777;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... v) {
            try {
                socket = new Socket(ip, port);
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Connecté");
                return true;
            } catch (IOException e) {
                System.out.println("Erreur connection");
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean b) {
            if (b) {
                System.out.println("Connected");
            } else {
                System.out.println("NOT Connected");
            }
        }
    }

    public PrintWriter getWriter() { return writer; }
}