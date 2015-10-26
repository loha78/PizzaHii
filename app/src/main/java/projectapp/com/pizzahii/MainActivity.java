package projectapp.com.pizzahii;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import projectapp.com.pizzahii.commande.MenuCommandFrag;
import projectapp.com.pizzahii.cusine.MenuCuisineFrag;

public class MainActivity extends AppCompatActivity {

    static FragmentManager manager;
    MenuCommandFrag fragCommande;
    MenuCuisineFrag fragCuisine;
    AccueilFrag accueilFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getFragmentManager();

        accueilFrag = new AccueilFrag();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.managerFrameLayout, accueilFrag);
        transaction.commit();

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

    public void alternerFragment(View v) {

        switch (v.getId()) {
            case R.id.buttonCommande:
                fragCommande = new MenuCommandFrag();
                loadFragment(R.id.managerFrameLayout, fragCommande);
                break;

            case R.id.buttonCuisine:
                fragCuisine = new MenuCuisineFrag();
                loadFragment(R.id.managerFrameLayout, fragCuisine);
                break;
        }
    }

    // Gestion des fragments à ajouter et à enelever
    private void loadFragment(int layout, Fragment frag) {
        Fragment fragToDel = getFragmentManager().findFragmentById(R.id.managerFrameLayout);
        FragmentTransaction transaction1 = manager.beginTransaction();
        FragmentTransaction transaction2 = manager.beginTransaction();
        transaction1.remove(fragToDel);
        transaction1.commit();
        transaction2.add(layout, frag);
        transaction2.commit();
    }
}
