package projectapp.com.pizzahii.cuisine;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.PrintWriter;

import projectapp.com.pizzahii.Controller.NetworkConnection;
import projectapp.com.pizzahii.R;

public class CuisineActivity extends AppCompatActivity {

    private PrintWriter writer = new PrintWriter(System.out, true);
    NetworkConnection network;

    static FragmentManager manager;
    String fragToLoad;
    NewPlatFrag newPlatFrag;
    UpdatePlateFrag updatePlateFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine);
        network = new NetworkConnection();
        network.execute();
        writer = network.getWriter();
        manager = getFragmentManager();
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

    public void actionActivityCuisine(View v) {

        Fragment fragment = (Fragment) getFragmentManager().findFragmentById(R.id.cuisineActivityFrame);

        switch (v.getId()) {
            case R.id.validateButton:
                EditText name = (EditText) findViewById(R.id.platName);
                EditText quantite = (EditText) findViewById(R.id.platQte);
                System.out.println("Click test");
                writer = network.getWriter();
                writer.println("AJOUT " + quantite.getText().toString() + " " + name.getText().toString());
                CuisineActivity.this.finish();
                break;

            case R.id.cancelButton:
                CuisineActivity.this.finish();
                break;
        }

    }
}
