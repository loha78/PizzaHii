package projectapp.com.pizzahii.cuisine;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import projectapp.com.pizzahii.R;


/*
    Activité principale de la gestion des plats en cuisine
 */
public class CuisineActivity extends AppCompatActivity {

    private PrintWriter writer = new PrintWriter(System.out, true);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static FragmentManager manager;

    // Determine lors de la creation de la vue quel fragment charger
    String fragToLoad;

    NewPlatFrag newPlatFrag;
    UpdatePlateFrag updatePlateFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine);
        manager = getFragmentManager();

        // Recuperer dans l'intention le mot clé qui determine le fragment souhaité
        fragToLoad = getIntent().getExtras().getString("FragToLoad");
        loadFrag(fragToLoad);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cuisine, menu);
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
                newPlatFrag = new NewPlatFrag();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.cuisineActivityFrame, newPlatFrag);
                transaction.commit();
                break;
            case "update":
                updatePlateFrag = new UpdatePlateFrag();
                FragmentTransaction transaction1 = manager.beginTransaction();
                transaction1.add(R.id.cuisineActivityFrame, updatePlateFrag);
                transaction1.commit();
                break;
        }
    }

    // Methode associée aux boutons annuler ou valider
    public void actionActivityCuisine(View v) {

        Fragment fragment = (Fragment) getFragmentManager().findFragmentById(R.id.cuisineActivityFrame);

        switch (v.getId()) {
            case R.id.validateButton:
                // Action du bouton valider pour une mise a jour du nombre de plat
                if (fragment instanceof UpdatePlateFrag) {
                    String plat = updatePlateFrag.getSelectedValue();
                    writer = updatePlateFrag.getWriter();
                    if (!TextUtils.isEmpty(plat)){
                        EditText quantite = (EditText) findViewById(R.id.platQte);
                        writer.println("AJOUT " + quantite.getText().toString() + " "  + plat);
                    }
                }

                // Action si on cree un nouveau plat
                if (fragment instanceof NewPlatFrag) {
                    writer = newPlatFrag.getWriter();
                    EditText name = (EditText) findViewById(R.id.platName);
                    EditText quantite = (EditText) findViewById(R.id.platQte);
                    writer.println("AJOUT " + quantite.getText().toString() + " " + name.getText().toString());
                }

                CuisineActivity.this.finish();
                break;

            case R.id.cancelButton:
                CuisineActivity.this.finish();
                break;
        }

    }
}
