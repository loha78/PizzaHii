package projectapp.com.pizzahii.cuisine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import projectapp.com.pizzahii.R;

public class UpdatePlateFrag extends Fragment {

    private ArrayList<String> nomDesPlats = new ArrayList<>();
    private ArrayList<String> qteDesPlats = new ArrayList<>();

    Spinner spinner;

    // Elements de connexion
    private Socket socket;
    private PrintWriter writer = new PrintWriter(System.out, true);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private ReadMessages readMessages;

    public UpdatePlateFrag() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StartNetwork().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_update_plate, container, false);

        spinner = (Spinner) v.findViewById(R.id.platNameSpinner);


        // Inflate the layout for this fragment
        return v;
    }

    public String getSelectedValue() {
        if (spinner != null && spinner.getSelectedItem() != null) {
            return (String)spinner.getSelectedItem();
        }

        return "";
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
                readMessages = new ReadMessages();
                readMessages.execute();
                writer.println("QUANTITE");
            } else {
                System.out.println("NOT Connected");
            }
        }
    }

    public PrintWriter getWriter() { return this.writer; }

    private class ReadMessages extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... v) {
            while (!isCancelled()) {
                String message = "";
                Log.v("tag", "while");
                try {
                    message = reader.readLine();
                    Log.v("tag", message);
                    if (message.equals("FINLISTE")) {
                        break;
                    }

                    if(!(message.contains(":")) && !(message.equals("FINLISTE"))) {
                        nomDesPlats.add(message);
                        qteDesPlats.add(reader.readLine());
                    } else {
                        // liste récup
                        Log.v("tag", "fin liste");
                    }
                } catch (IOException e) {
                    Log.v("tag", "IOException");
                    e.printStackTrace();
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.v("tag", "noms des plats size " + nomDesPlats.size());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, nomDesPlats);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.notifyDataSetChanged();
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String item = adapterView.getItemAtPosition(i).toString();
                    // Toast.makeText(parent.getContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            super.onPostExecute(aVoid);
        }
    }
}
