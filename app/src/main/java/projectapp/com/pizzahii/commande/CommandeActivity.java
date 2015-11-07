package projectapp.com.pizzahii.commande;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import projectapp.com.pizzahii.Controller.NetworkConnection;
import projectapp.com.pizzahii.Controller.ReadMessages;
import projectapp.com.pizzahii.R;
import projectapp.com.pizzahii.cuisine.NewPlatFrag;
import projectapp.com.pizzahii.cuisine.UpdatePlateFrag;

import static projectapp.com.pizzahii.Controller.NetworkConnection.*;

public class CommandeActivity extends AppCompatActivity {

    private PrintWriter writer = new PrintWriter(System.out, true);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    NetworkConnection network;


    static FragmentManager manager;
    String fragToLoad;
    NewOrderFrag newOrder;
    UpdateOrderFrag updateOrder;
    DeleteOrderFrag deleteOrder;
    private String plats = "";
    ListView listePlatsView;


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
                newOrder = new NewOrderFrag();
                writer.println("QUANTITE");
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.commandeActivityFrame, newOrder);
                transaction.commit();
                break;
            case "update":
                updateOrder = new UpdateOrderFrag();
                FragmentTransaction transaction1 = manager.beginTransaction();
                transaction1.add(R.id.cuisineActivityFrame, updateOrder);
                transaction1.commit();
                break;
            case "delete":
                updateOrder = new UpdateOrderFrag();
                FragmentTransaction transaction2 = manager.beginTransaction();
                transaction2.add(R.id.commandeActivityFrame, deleteOrder);
                transaction2.commit();
                break;
        }
    }

    public void actionActivityCommande(View v) {


        switch (v.getId()) {
            case R.id.validateButton:
                if (newOrder.isAdded()) {

                }
                break;
            case R.id.cancelButton:
                CommandeActivity.this.finish();
                break;
        }
    }

    public void recupererListePlats() {
        String[] listePlats = new String[0];
        List<String>arrayListePlats = new ArrayList<String>();

        try {
            int i = 0;
            writer = network.getWriter();
            reader = network.getReader();
            writer.println("QUANTITE");

            String finListe = "FINLISTE";

            String ligneServeur = reader.readLine();
            while (ligneServeur != finListe) {
                arrayListePlats.add(ligneServeur);
                ligneServeur = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<arrayListePlats.size();i++){
            listePlats[i]=arrayListePlats.get(i);
        }
        listePlatsView = (ListView) findViewById(R.id.listePlats);
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




}
