package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ena on 28.03.2016..
 */
public class FragmentLista extends Fragment implements MyResultReceiver.Receiver  {
//implements SearchArtist.OnMuzicarSearchDone

    public static DBHandler help;
    public static DBHandlerPjesme helpP;
    public static DBHandlerAlbumi helpA;
    Muzicar m;
    public static ArrayList<Muzicar> muzicariBaza= new ArrayList<Muzicar>();
    ArrayList<String> pjesme;

    public FragmentLista() {}
    public  static ArrayList<String> pj=new ArrayList<String>();

    public  static ArrayList<String> alb=new ArrayList<String>();
    public interface OnItemClick {
        public void onItemClicked(Muzicar m);
    }

    ArrayList<Muzicar> muzicari = new ArrayList<Muzicar>();
    ArrayList<Muzicar> nadjeni = new ArrayList<Muzicar>();
    private Button pretrazi;
    private EditText tekst;
    private MyResultReceiver rezRec;
    private ListView list;
    private OnItemClick oic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_lista, container, false);

        help=new DBHandler(getActivity(),null,null,1);
        helpP=new DBHandlerPjesme(getActivity(),null,null,1);

        helpA=new DBHandlerAlbumi(getActivity(),null,null,1);

        pretrazi =(Button) view.findViewById(R.id.button2);
        tekst =(EditText) view.findViewById(R.id.kadaUnese);

       // this.onSaveInstanceState(savedInstanceState);
        //this.onActivityCreated(savedInstanceState);

        list = (ListView) view.findViewById(R.id.fragmentLista);

        Ispisi();

       /* if(getArguments()!=null && getArguments().containsKey("Muzicar")) {
            Bundle args=getArguments();
            muzicari=args.getParcelableArrayList("Muzicar");*/

            ArrayAdapter<Muzicar> adapter;
            adapter = new MuzicarAdapter(getActivity(),R.layout.element_liste,muzicariBaza);
            list.setAdapter(adapter);

            //DODANIIII TRY CATCH
            try {
                oic = (OnItemClick) getActivity();
            } catch (ClassCastException e) {
                throw new ClassCastException(getActivity().toString() + "Treba implementirati OnItemClick");
            }
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    oic.onItemClicked(nadjeni.get(position));
                }
            });
       // }

        pretrazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String upit;
                upit = tekst.getText().toString();
                if (upit.trim().length() == 0) return;

                Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), MyIntentService.class);
                rezRec = new MyResultReceiver(new Handler());
                rezRec.setReceiver(FragmentLista.this);

                intent.putExtra("tekst", upit);
                intent.putExtra("receiver", rezRec);

                getActivity().startService(intent);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Muzicar muzicar = null;
                String searchTT = tekst.getText().toString();
                if(searchTT.matches("") ){
                    muzicar= muzicariBaza.get(position);
                }

                else muzicar = nadjeni.get(position);

                oic.onItemClicked(muzicar);
            }
        });
        /*tekst = (EditText) view.findViewById(R.id.kadaUnese);
        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchArtist((SearchArtist.OnMuzicarSearchDone)FragmentLista.this).execute(tekst.getText().toString());
            }
        });*/
        Intent intent = getActivity().getIntent();
        String dolazeci = intent.getStringExtra(Intent.EXTRA_TEXT);
        tekst.setText(dolazeci);

        return view;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case MyIntentService.STATUS_RUNNING:
                Toast.makeText(getActivity(), getActivity().getString(R.string.zahtjev), Toast.LENGTH_LONG).show();
                break;
            case MyIntentService.STATUS_FINISHED:
                nadjeni = (ArrayList)resultData.getParcelableArrayList("result");
                MuzicarAdapter a=new MuzicarAdapter(getActivity(), R.layout.element_liste,nadjeni);
                list.setAdapter(a);

                break;
            case MyIntentService.STATUS_ERROR:
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    void Ispisi(){
        String[] koloneRezultat = new String[]{ DBHandler.MUZICAR_ID, DBHandler.MUZICAR_IME, DBHandler.MUZICAR_ZANR};

        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = help.getReadableDatabase();

        Cursor cursor = db.query(DBHandler.DATABASE_TABLE, koloneRezultat, where, whereArgs, groupBy, having, order);

        if(cursor!=null && cursor.getCount()!=0) {

            cursor.moveToLast();
            //pj= UzmiPjesme(cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_ID)));
            //alb= UzmiAlbume(cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_ID)));
            muzicariBaza.add(new Muzicar(cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_IME)), cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_ZANR))));

            while(cursor.moveToPrevious())
            {
                //pj= UzmiPjesme(cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_ID)));
                //alb= UzmiAlbume(cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_ID)));
                muzicariBaza.add(new Muzicar(cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_IME)), cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_ZANR))));
            }
            cursor.moveToFirst();
            //pj= UzmiPjesme(cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_ID)));
            //alb= UzmiAlbume(cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_ID)));
            muzicariBaza.add(new Muzicar(cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_IME)), cursor.getString(cursor.getColumnIndexOrThrow(help.MUZICAR_ZANR))));
        }

        MuzicarCursorAdapter adapterb = new MuzicarCursorAdapter(getActivity(), R.layout.element_liste, cursor, 0);

        list.setAdapter(adapterb);
    }

    ArrayList<String> UzmiPjesme(String id) {

        String [] pjesmeRezultat = new String[]{DBHandlerPjesme.PJESMA_ID, DBHandlerPjesme.PJESMA_IME,DBHandlerPjesme.MUZICAR_ID};
        ArrayList<String> p=new ArrayList<String>();
        SQLiteDatabase dbP = helpP.getReadableDatabase();
        Cursor cursor = dbP.query(DBHandlerPjesme.DATABASE_TABLE_PJESME, pjesmeRezultat, null, null,null,null,null);

        if(cursor!=null && cursor.getCount()!=0) {
            cursor.moveToLast();
            if (id.equals(cursor.getString(cursor.getColumnIndexOrThrow(helpP.MUZICAR_ID)))) {
                p.add(cursor.getString(cursor.getColumnIndexOrThrow(helpP.PJESMA_IME)));
            }
            while (cursor.moveToPrevious()) {
                if (id.equals(cursor.getString(cursor.getColumnIndexOrThrow(helpP.MUZICAR_ID)))) {
                    p.add(cursor.getString(cursor.getColumnIndexOrThrow(helpP.PJESMA_IME)));
                }
            }
            cursor.moveToFirst();
            if (id.equals(cursor.getString(cursor.getColumnIndexOrThrow(helpP.MUZICAR_ID)))) {
                p.add(cursor.getString(cursor.getColumnIndexOrThrow(helpP.PJESMA_IME)));
            }
        }
        return p;
    }

    ArrayList<String> UzmiAlbume(String id) {

        String [] pjesmeRezultat = new String[]{DBHandlerAlbumi.ALBUM_ID, DBHandlerAlbumi.ALBUM_IME,DBHandlerAlbumi.MUZICAR_ID};
        ArrayList<String> al=new ArrayList<String>();
        SQLiteDatabase dbP = helpA.getReadableDatabase();
        Cursor cursor = dbP.query(DBHandlerAlbumi.DATABASE_TABLE_ALBUM, pjesmeRezultat, null, null,null,null,null);

        if(cursor!=null && cursor.getCount()!=0) {
            cursor.moveToLast();
            if (id.equals(cursor.getString(cursor.getColumnIndexOrThrow(helpA.ALBUM_ID)))) {
                al.add(cursor.getString(cursor.getColumnIndexOrThrow(helpA.ALBUM_IME)));
            }
            while (cursor.moveToPrevious()) {
                if (id.equals(cursor.getString(cursor.getColumnIndexOrThrow(helpA.MUZICAR_ID)))) {
                    al.add(cursor.getString(cursor.getColumnIndexOrThrow(helpA.ALBUM_IME)));
                }
            }
            cursor.moveToFirst();
            if (id.equals(cursor.getString(cursor.getColumnIndexOrThrow(helpA.MUZICAR_ID)))) {
                al.add(cursor.getString(cursor.getColumnIndexOrThrow(helpA.ALBUM_IME)));
            }
        }
        return al;
    }
/*
    //public void onDone(Muzicar mu){
    @Override
    public void onDone(ArrayList<Muzicar> mu){
        muzicari.clear();
        for(int i=0; i<mu.size(); i++)  muzicari.add(mu.get(i));
        ma.notifyDataSetInvalidated();
        //tekst.setText(mu.getIme());
        list.setAdapter(ma);
    }*/
}
