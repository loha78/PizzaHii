package projectapp.com.pizzahii.commande;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import projectapp.com.pizzahii.MainActivity;
import projectapp.com.pizzahii.R;

public class CommandeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<String> listePlatsDisponibles = new ArrayList<String>();
    List<String> listePlatsCommandes = new ArrayList<String>();
    List<String> listeMessage = new ArrayList<String>();
    String[] tableauMessage;
    List<Commande>listeCommande = new ArrayList<Commande>();

    private final static String LISTE_PLATS_DISPONIBLES = "liste des plats disponibles";
    private final static String LISTE_PLATS_COMMANDES = "liste des plats commandes";
    private final static String LISTE_COMMANDES = "liste des commandes";

    ArrayAdapter adapterPlatsDisponibles;
    ListView listViewPlatsDisponibles;

    ListView listViewPlatsCommandes;
    ArrayAdapter adpteurPlatsCommandes;

    EditText numeroCommandeEditText;


    private PrintWriter writer = new PrintWriter(System.out, true);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private Socket socket;
    private StartNetwork startNetwork;
    private ReadMessages readMessages;

    Button validerCommandeButton;
    Button annulerCommandeButton;
    Button retourMenuButton;

    String plat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        listViewPlatsDisponibles = (ListView) findViewById(R.id.listePlatsDispoView);
        listViewPlatsCommandes = (ListView) findViewById(R.id.listePlatsCommandesView);
        listViewPlatsDisponibles.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        validerCommandeButton = (Button)findViewById(R.id.validateButton);
        annulerCommandeButton = (Button)findViewById(R.id.cancelButton);
        retourMenuButton = (Button)findViewById(R.id.returnButton);

        startNetwork = new StartNetwork();
        startNetwork.execute();

        if(savedInstanceState != null){
            listePlatsCommandes = savedInstanceState.getStringArrayList(LISTE_PLATS_COMMANDES);
            listePlatsDisponibles = savedInstanceState.getStringArrayList(LISTE_PLATS_DISPONIBLES);
            afficherListePlatCommandes();
            afficherListePlatDisponibles();
        }
        else {
            listePlatsDisponibles = new ArrayList<String>();
            afficherListePlatDisponibles();
        }

        numeroCommandeEditText = (EditText)findViewById(R.id.newOrderNumber);
        numeroCommandeEditText.setText("" + listeCommande.size() + 1);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        System.out.println("Dans onSaveInstanceState");

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList(LISTE_PLATS_DISPONIBLES, (ArrayList<String>) listePlatsDisponibles);
        savedInstanceState.putStringArrayList(LISTE_PLATS_COMMANDES, (ArrayList<String>) listePlatsCommandes);
    }

    @Override
    public void onStart(){
        super.onStart();
        startNetwork = new StartNetwork();
        startNetwork.execute();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_commande, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        plat = listePlatsDisponibles.get(position).substring(0, (listePlatsDisponibles.get(position).length() - 3));
        Toast.makeText(getApplicationContext(), "Le plat selectionne :" + plat.trim()+" est en commande, patientez quelques secondes svp", Toast.LENGTH_LONG).show();
        listePlatsCommandes.add(plat.trim());
        afficherListePlatCommandes();
    }

    @Override
    public void finish(){
        Intent intent = new Intent(this, MainActivity.class);
        writer.println("LOGOUT");
        readMessages.cancel(true);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(intent);
        super.finish();
    }

    public void performButtonAction(View v){
        switch(v.getId()){
            case R.id.returnButton:
                System.out.println("bouton retour detecte");
                returnMenu();
                break;
            case R.id.cancelButton:
                System.out.println("bouton annuler detecte");
                annulerCommande();
                Toast.makeText(getApplicationContext(), "Commande en cours d'annulation, patientez quelques secondes svp", Toast.LENGTH_LONG).show();
                break;
            case R.id.validateButton:
                System.out.println("bouton valider detecte");
                Toast.makeText(getApplicationContext(), "Commande en cours de validation, patientez quelques secondes svp", Toast.LENGTH_LONG).show();
                validerCommande();
                break;
        }
    }

    private void afficherListePlatDisponibles(){
        writer.println("QUANTITE");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listePlatsDisponibles);
        listViewPlatsDisponibles.setAdapter(adapter);
    }

    private void afficherListePlatCommandes(){
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listePlatsCommandes);
        listViewPlatsCommandes.setAdapter(adapter);
    }

    private void returnMenu(){
        finish();
    }

    private void validerCommande() {
        listePlatsDisponibles = new ArrayList<String>();
        System.out.println("entree dans validercommande()");
        for (int i = 0;i<listePlatsCommandes.size();i++){
            writer.println("COMMANDE "+listePlatsCommandes.get(i));
        }
        //writer.println("COMMANDE " + plat.trim());
        System.out.println("prise en compte de la commande");

        Commande commande = new Commande(listeCommande.size()+1);
        commande.setListePlatsCommandes(listePlatsCommandes);
        listeCommande.add(commande);

        listePlatsCommandes= new ArrayList<String>();
        afficherListePlatCommandes();
        afficherListePlatDisponibles();

        int numeroCommandeSuivant = listeCommande.size() + 1;
        numeroCommandeEditText.setText("" + numeroCommandeSuivant);

    }

    private void annulerCommande(){
        listePlatsCommandes.clear();
        listePlatsDisponibles.clear();
        afficherListePlatCommandes();
        afficherListePlatDisponibles();
    }


    private class StartNetwork extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            System.out.println("StartNetwork.onPreExecute");
        }

        @Override
        protected Boolean doInBackground(Void... v) {
            System.out.println("StartNetwork.doInBackground");
            try {
                socket = new Socket("10.0.2.2", 7777);
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("connexion");
                return true;
            } catch (IOException e) {
                System.out.println("erreur de connexion");
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean b) {
            System.out.println("StartNetwork.onPostExecute");
            if (b) {
                readMessages = new ReadMessages();
                readMessages.execute();
                writer.println("QUANTITE");
            }
        }
    }



    private class ReadMessages extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... v) {
            while (!isCancelled()) {
                String message = "";
                try {
                    message = reader.readLine();
                    if (!message.startsWith("Client")&&!message.contains("Plat")&&!message.contains("plats")&&!message.equals("FINLISTE")&&!message.equals("Le plat")){
                        String quantite = reader.readLine();
                        if (!quantite.equals("0")){
                            listePlatsDisponibles.add(message+"      "+quantite);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("erreur de récupération des plats");
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... messages) {

        }
    }

}