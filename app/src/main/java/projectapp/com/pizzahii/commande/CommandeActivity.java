package projectapp.com.pizzahii.commande;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import projectapp.com.pizzahii.Controller.NetworkConnection;
import projectapp.com.pizzahii.R;

public class CommandeActivity extends AppCompatActivity {

    private PrintWriter writer = new PrintWriter(System.out, true);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public NetworkConnection network;


    static FragmentManager manager;
    String fragToLoad;
    NewCommandeFragment newCommandeFragment;
    private String plats = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        network = new NetworkConnection();
        network.execute();
        manager = getFragmentManager();
        fragToLoad = getIntent().getExtras().getString("FragToLoad");
        loadFrag(fragToLoad);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commande, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFrag(String s) {
        switch (s) {
            case "new":
                newCommandeFragment = new NewCommandeFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.commandeActivityFrame, newCommandeFragment);
                transaction.commit();
                break;
        }
    }

    public void actionActivityCommande(View v) {

        switch (v.getId()) {
            case R.id.validateButton:
                if (newCommandeFragment.isAdded()) {

                }
                break;
            case R.id.cancelButton:
                CommandeActivity.this.finish();
                break;
        }
    }

    /*
    public void afficherPlat(String... listePlats){

        listePlatsView = (ListView) findViewById(R.id.listePlatsDispoView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listePlats);
        listePlatsView.setAdapter(adapter);
        listePlatsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int platPosition = position;
                String platValue = (String) listePlatsView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Position :" + platPosition + " plat séléctionné :" + platValue, Toast.LENGTH_LONG).show();
                writer.println("COMMANDE " + platValue);
            }
        });
    }
    */

    public void getListePlatsDispos() {
        String finListe = "FINLISTE";
        ArrayList<String> tempList = new ArrayList<>();
        writer = network.getWriter();
        writer.println("LISTE");
        System.out.println("Entree dans la methode getList");

        try {
            String ligneServeur = reader.readLine();

            System.out.println("Entree dans la try avant la boucle");
            //while (ligneServeur != finListe) {
                System.out.println("Dans la boucle ");
                tempList.add(ligneServeur);
                ligneServeur = reader.readLine();
            //}
            System.out.println(tempList);

        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        listeToReturn = new String[(tempList.size() - 1)];

        for (int i = 0; i < tempList.size(); i++) {
            listeToReturn[i] = tempList.get(i);
        } */
    }
}