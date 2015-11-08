package projectapp.com.pizzahii;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.ArrayList;

import projectapp.com.pizzahii.Controller.NetworkConnection;
import projectapp.com.pizzahii.commande.CommandeActivity;
import projectapp.com.pizzahii.commande.MenuCommandFrag;
import projectapp.com.pizzahii.cuisine.CuisineActivity;
import projectapp.com.pizzahii.cuisine.MenuCuisineFrag;
import projectapp.com.pizzahii.cuisine.NewPlatFrag;

public class MainActivity extends AppCompatActivity {

    private Button buttonCommande;
    private Button buttonCuisine;
    static FragmentManager manager;
    MenuCommandFrag fragCommande;
    MenuCuisineFrag fragCuisine;
    AccueilFrag accueilFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonCommande = (Button) findViewById(R.id.buttonCommande);
        buttonCuisine = (Button) findViewById(R.id.buttonCuisine);
        manager = getFragmentManager();

        // Verifier si un fragment est deja chargé dans la vue sinon ajouter le fragment d accueil
        Fragment tempFrag = (Fragment) getFragmentManager().findFragmentById(R.id.managerFrameLayout);
        if (tempFrag == null) {
            accueilFrag = new AccueilFrag();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.managerFrameLayout, accueilFrag);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    // Choisir d afficher le fragment de gestion des commandes ou celui pour la gestion des plats
    public void alternerFragment(View v) {

        switch (v.getId()) {
            case R.id.buttonCommande:
                fragCommande = new MenuCommandFrag();
                loadFragment(R.id.managerFrameLayout, fragCommande);
                buttonCommande.setBackgroundResource(R.drawable.selected_background);
                buttonCuisine.setBackgroundResource(R.drawable.unselected_background);
                break;

            case R.id.buttonCuisine:
                fragCuisine = new MenuCuisineFrag();
                loadFragment(R.id.managerFrameLayout, fragCuisine);
                buttonCommande.setBackgroundResource(R.drawable.unselected_background);
                buttonCuisine.setBackgroundResource(R.drawable.selected_background);
                break;
        }
    }

    // Gestion des fragments à ajouter et à enlever
    private void loadFragment(int layout, Fragment frag) {
        Fragment fragToDel = getFragmentManager().findFragmentById(R.id.managerFrameLayout);
        FragmentTransaction transaction1 = manager.beginTransaction();
        FragmentTransaction transaction2 = manager.beginTransaction();
        transaction1.remove(fragToDel);
        transaction1.commit();
        transaction2.add(layout, frag);
        transaction2.commit();
    }

    // Gestion des bouton de l activité commande
    public void actionMenuCommande(View v) {
        switch (v.getId()){
            case R.id.newOrderButtonFrag:
                followLink("new", CommandeActivity.class);
                break;
            case R.id.updateOrderButtonFrag:
                followLink("update", CommandeActivity.class);
                break;
            case R.id.deleteOrderButtonFrag:
                followLink("delete", CommandeActivity.class);
                break;

        }
    }

    // Gestion de l'action des boutons de l activité cuisine
    public void actionMenuCuisine(View v) {
        switch (v.getId()) {
            case R.id.newPlatButtonFrag:
                followLink("new", CuisineActivity.class);
                break;
            case R.id.updateStockButtonFrag:
                followLink("update", CuisineActivity.class);
                break;
        }
    }

    // Aller vers une nouvelle activité
    private void followLink(String frag, Class myClass) {
        Intent intent = new Intent(this, myClass);
        intent.putExtra("FragToLoad", frag);
        startActivity(intent);
    }
}
