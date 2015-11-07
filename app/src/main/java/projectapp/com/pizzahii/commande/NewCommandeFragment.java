package projectapp.com.pizzahii.commande;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.PrintWriter;

import projectapp.com.pizzahii.Controller.ReadServerText;
import projectapp.com.pizzahii.MainActivity;
import projectapp.com.pizzahii.R;

public class NewCommandeFragment extends Fragment {

    ListView listPlatsDispos;

    public NewCommandeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_commande, container, false);

        listPlatsDispos = (ListView) v.findViewById(R.id.listePlatsDispoView);

        String test[] = {"toto", "titi"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, test);
        listPlatsDispos.setAdapter(adapter);

        ReadServerText readServerText = new ReadServerText();
        readServerText.execute();

        CommandeActivity a = (CommandeActivity) getActivity();
        PrintWriter writer = a.network.getWriter();
        writer.println("LISTE");

        return v;
    }
}