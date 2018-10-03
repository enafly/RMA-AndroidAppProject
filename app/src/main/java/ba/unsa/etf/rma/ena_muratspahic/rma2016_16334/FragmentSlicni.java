package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ena on 27.04.2016..
 */
public class FragmentSlicni extends Fragment {
//Ovo je sada fragment za albume!!!
    public ArrayList<String> albumi;
    boolean nadji;
    public FragmentSlicni() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View iv = inflater.inflate(R.layout.fragment_slicni,  container, false);

        ListView lista= (ListView)iv.findViewById(R.id.albumi);
        if(getArguments()!=null && getArguments().containsKey("Albumi"))
        {
            Bundle args=getArguments();
            nadji=args.getBoolean("search");
            albumi=args.getStringArrayList("Albumi");
            TextView labela=(TextView) iv.findViewById(R.id.slicni);
            if (nadji == true) labela.setText(getResources().getString(R.string.slicni));
            ArrayAdapter<String> adapter;
            adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,albumi);
            lista.setAdapter(adapter);
        }
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*String adresa= "https://api.spotify.com/v1/albums/"+id;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(adresa));

                if(intent.resolveActivity(getActivity().getPackageManager()) != null)
                    getActivity().startActivity(intent);
                else
                    Toast.makeText(getActivity().getApplicationContext(), "Nema", Toast.LENGTH_SHORT).show();


            */
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getString(R.string.pokusaj),Toast.LENGTH_LONG).show();
            }
        });

            return iv;
        }
    }
