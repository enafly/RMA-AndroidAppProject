package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**      * Created by Ena on 27.04.2016..       */
public class FragmentTopPet extends Fragment{

    public String ime;
    ArrayList<String> lista;
    ListView top5;
    public FragmentTopPet() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View iv = inflater.inflate(R.layout.fragment_top5, container, false);

        if (getArguments()!=null && getArguments().containsKey("Pjesme"))
        {
            //&& getArguments().containsKey("Prezime")

            top5= (ListView) iv.findViewById(R.id.top5);

            Bundle args = getArguments();
            lista = args.getStringArrayList("Pjesme");

            ArrayAdapter<String> adapter;
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);
            top5.setAdapter(adapter);
        }

            top5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String tekstPjesme = lista.get(position);
                    Intent intent = new Intent(Intent.ACTION_SEARCH);
                    intent.setPackage("com.google.android.youtube");
                    intent.putExtra("query", tekstPjesme);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if(intent.resolveActivity(getActivity().getPackageManager()) != null)
                        startActivity(intent);
                    else
                        Toast.makeText(getActivity().getApplicationContext(), getActivity().getString(R.string.jutub), Toast.LENGTH_SHORT).show();
                }
            });

        return iv;
    }
}
