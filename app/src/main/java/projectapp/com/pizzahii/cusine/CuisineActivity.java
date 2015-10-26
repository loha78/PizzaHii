package projectapp.com.pizzahii.cusine;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import projectapp.com.pizzahii.R;

public class CuisineActivity extends AppCompatActivity {

    static FragmentManager manager;
    String fragToLoad;
    NewPlatFrag newPlatFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine);
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
        }
    }
}
