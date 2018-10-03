package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FragmentDetalji extends Fragment{

    Muzicar muzicar;
    boolean y;
   // ArrayList<Muzicar> muzicariBaza=FragmentLista.muzicariBaza;
    public ArrayList<String> pjesme, albumi;
    DBHandler help;
    DBHandlerPjesme helpP;
    DBHandlerAlbumi helpA;

    public FragmentDetalji() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View iv= inflater.inflate(R.layout.fragment_detalji, container, false);

        help=new DBHandler(getActivity(),null,null,1);
        helpP=new DBHandlerPjesme(getActivity(),null,null,1);
        helpA=new DBHandlerAlbumi(getActivity(),null,null,1);

        final ToggleButton toggle = (ToggleButton) iv.findViewById(R.id.toggleButton);
        TextView ime = (TextView) iv.findViewById(R.id.tvName);
        TextView zanr = (TextView) iv.findViewById(R.id.tvGenre);
        TextView bio = (TextView) iv.findViewById(R.id.tvBio);
        final TextView pg = (TextView) iv.findViewById(R.id.tvWebSite);

        if(getArguments()!=null&&getArguments().containsKey("Muzicar")) {
            muzicar = getArguments().getParcelable("Muzicar");

            ime.setText(muzicar.ime);
            zanr.setText(muzicar.zanr);
            pg.setText("Unknown");
            bio.setText("Unknown");
            pjesme=muzicar.pjesme;
            albumi=muzicar.albumi;
            Button posalji = (Button) iv.findViewById(R.id.posalji);

            posalji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent posaljiIntent = new Intent();
                    posaljiIntent.setAction(Intent.ACTION_SEND);
                    posaljiIntent.putExtra(Intent.EXTRA_TEXT, muzicar.getBiografija());
                    posaljiIntent.setType("text/plain");
                    startActivity(posaljiIntent);
                }
            });

            FragmentManager fragmentManager = getChildFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentTopPet fragmentTop5 = new FragmentTopPet();
            Bundle argumenti = new Bundle();
            argumenti.putString("Ime", muzicar.getIme());
            argumenti.putStringArrayList("Pjesme", pjesme);
            fragmentTop5.setArguments(argumenti);
            fragmentTransaction.replace(R.id.dijeteFragment, fragmentTop5).commit();
            //getChildFragmentManager().beginTransaction().replace(R.id.dijeteFragment, fragmentTop5).commit();
        }

            toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toggle.isChecked() == false) {
                        FragmentTopPet ftp = new FragmentTopPet();
                        Bundle topPet = new Bundle();
                        topPet.putString("Ime", muzicar.getIme());
                        topPet.putStringArrayList("Pjesme", pjesme);
                        ftp.setArguments(topPet);
                        getChildFragmentManager().beginTransaction().replace(R.id.dijeteFragment, ftp).commit();
                    } else {
                        FragmentSlicni fs = new FragmentSlicni();
                        Bundle slicni = new Bundle();
                        slicni.putStringArrayList("Albumi", albumi);

                        fs.setArguments(slicni);
                        getChildFragmentManager().beginTransaction().replace(R.id.dijeteFragment, fs).commit();
                    }
                }
            });

        pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = pg.getText().toString();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        DodajMuzicara(muzicar.ime,muzicar.zanr, pjesme, albumi);// muzicar.albumi);

        return iv;
    }
   /* public boolean Exists(String ime) {
        SQLiteDatabase db = help.getReadableDatabase();
        Cursor cursor = db.rawQuery(DBHandler.MUZICAR_IME, new String[] { ime});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return true;
    }*/
    void DodajMuzicara(String ime, String zanr,ArrayList<String> pjesme, ArrayList<String> albumi) {

        if (FragmentLista.muzicariBaza.size()==0 ||  FragmentLista.muzicariBaza.size()==1)
        {
            //Open connection to write data
            SQLiteDatabase db = help.getWritableDatabase();
            ContentValues values = new ContentValues();
           // int id=1;
           // values.put(help.MUZICAR_ID,id);
            values.put(help.MUZICAR_IME, ime);
            values.put(help.MUZICAR_ZANR, zanr);
            //DodajPjesme(pjesme, id);
            // Inserting Row
            db.beginTransaction();
            db.insert(help.DATABASE_TABLE, null, values);
            db.setTransactionSuccessful();
            //id++;
            for(int i=FragmentLista.muzicariBaza.size(); i>0; i--) {
                FragmentLista.muzicariBaza.add(i,FragmentLista.muzicariBaza.get(i-1));
            }
            FragmentLista.muzicariBaza.add(0, new Muzicar(ime, zanr));
            Toast.makeText(getActivity(), "Dodan Muzicar", Toast.LENGTH_LONG).show();
            db.endTransaction();

            //db.close(); // Closing database connection
            //return (String) help.MUZICAR_ID;
        }
        else if(FragmentLista.muzicariBaza.size()>1)
        {
            boolean ima=false;
            for(int i=0; i<FragmentLista.muzicariBaza.size(); i++){
                if(ime.equals(FragmentLista.muzicariBaza.get(i).getIme()))
                {
                    ima=true;
                    Toast.makeText(getActivity(),"Postoji u bazi!", Toast.LENGTH_LONG).show();
                    break;
                }
            }
            if(!ima)
            {
                //Open connection to write data
                SQLiteDatabase db = help.getWritableDatabase();
                ContentValues values = new ContentValues();
                //Dodaje pjesme na muzicara
                //int id=1;
                //values.put(help.MUZICAR_ID, id);

                values.put(help.MUZICAR_IME, ime);
                values.put(help.MUZICAR_ZANR, zanr);

                //DodajPjesme(pjesme, id);

                // Inserting Row
                db.beginTransaction();
                db.insert(help.DATABASE_TABLE, null, values);
                //id++;
                for(int i=FragmentLista.muzicariBaza.size(); i>0; i--) {
                    FragmentLista.muzicariBaza.add(i,FragmentLista.muzicariBaza.get(i-1));
                }
                FragmentLista.muzicariBaza.add(0, new Muzicar(ime, zanr));

                db.setTransactionSuccessful();
                Toast.makeText(getActivity(), "Dodan Muzicar", Toast.LENGTH_LONG).show();
                db.endTransaction();
                //db.close(); // Closing database connection
                //return (String) help.MUZICAR_ID;
            }
        }
    }
    void DodajPjesme(ArrayList<String> pjesme, int id){
        SQLiteDatabase dbP = helpP.getWritableDatabase();

        for (int i=0; i< pjesme.size();i++) {
            ContentValues values = new ContentValues();
            values.put(helpP.MUZICAR_ID, id);
            values.put(helpP.PJESMA_IME, pjesme.get(i));
            // Inserting Row
            dbP.beginTransaction();
            dbP.insert(helpP.DATABASE_TABLE_PJESME, null, values);
            FragmentLista.pj.add(pjesme.get(i));
            dbP.setTransactionSuccessful();
            dbP.endTransaction();
        }
        Toast.makeText(getActivity(), "Dodane pjesme", Toast.LENGTH_LONG).show();
    }
    void DodajAlbume(ArrayList<String> albumi, int id){
        SQLiteDatabase dbP = helpA.getWritableDatabase();

        for (int i=0; i< albumi.size();i++) {
            ContentValues values = new ContentValues();
            values.put(helpA.MUZICAR_ID, id);
            values.put(helpA.ALBUM_IME, pjesme.get(i));
            // Inserting Row
            dbP.beginTransaction();
            dbP.insert(helpA.DATABASE_TABLE_ALBUM, null, values);
            FragmentLista.alb.add(albumi.get(i));
            dbP.setTransactionSuccessful();
            dbP.endTransaction();
        }
        Toast.makeText(getActivity(), "Dodani albumi", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
