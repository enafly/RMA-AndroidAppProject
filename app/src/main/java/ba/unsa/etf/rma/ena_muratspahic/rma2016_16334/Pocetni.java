package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//public class Pocetni extends AppCompatActivity {
public class Pocetni extends AppCompatActivity implements FragmentLista.OnItemClick {

    final ArrayList<Muzicar> muzicari = new ArrayList<Muzicar>();
    boolean siriL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pocetni);


        //////////////// Zadaca 3
        //////////////// Zadatak 1, dio sa onim sto se treba prevesti
        //////////////// Ostali prevodi su u layoutima, a stringovi u stringovima
        /*muzicari.add(new Muzicar("Michael Jackson","pop","www.michaeljackson.com/us/",getString(R.string.mjbio),"Thriller","Man in the mirror","Black or white","Billie Jean","Beat it","Janet Jackson","Whitney Houston","George Michael","Madonna","Prince"));
        muzicari.add(new Muzicar("Hayley Williams","punk","www.paramore.net/",getString(R.string.hwbio),"Emergency","Misery Business","Brick By Boring Brick","The only exception","Future","VersaEmerge","Taylor Michel Momsen","We Are the In Crowd","Automatic Loveletter","Fake Number"));
        muzicari.add(new Muzicar("Ed Sheeran","folk","www.edsheeran.com/",getString(R.string.esbio),"Give Me Love","The A Team","Thinking Out Loud","Lego House","Kiss Me","Justin Townes Earle","Bon Iver","Mumford & Sons","Ben Howard","The Script"));
        muzicari.add(new Muzicar("David Guetta","house","www.davidguetta.com/",getString(R.string.dgbio),"Titanium","Club Can't Handle Me","Without You","She Wolf","Memories","Bingo Players","Gaudino","Faithless","Armin van Buuren","Fedde Le Grand"));
        muzicari.add(new Muzicar("James Alan Hetfield", "metal", "metallica.com/", getString(R.string.jhbio), "Enter Sandman", "Seek and Destroy", "Nothing Else Matters", "Creeping Death", "The Four Horsemen","Number One Common","Lemmy","The Last Act","Verisimo","The 7-String King"));
        */

        siriL = false;
        FragmentManager fm = getFragmentManager();
        FrameLayout ldetalji = (FrameLayout) findViewById(R.id.fragmentDetalji);
        if (ldetalji != null) {
            siriL = true;
            FragmentDetalji fd;
            fd = (FragmentDetalji) fm.findFragmentById(R.id.fragmentDetalji);

            if (fd == null) {
                fd = new FragmentDetalji();

                fm.beginTransaction().replace(R.id.fragmentDetalji, fd).commit();
            }
        }

        FragmentLista fl = (FragmentLista) fm.findFragmentByTag("Lista");

        if (fl == null) {
            fl = new FragmentLista();
            Bundle argumenti = new Bundle();

            argumenti.putParcelableArrayList("Muzicar", muzicari);
            fl.setArguments(argumenti);
            fm.beginTransaction().replace(R.id.fragmentLista, fl, "Lista").commit();
        } else {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }

    @Override
    public void onItemClicked(Muzicar m) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("Muzicar", m);
        FragmentDetalji fd = new FragmentDetalji();
        fd.setArguments(arguments);
        if(siriL){
            getFragmentManager().beginTransaction().replace(R.id.fragmentDetalji, fd).commit();
        }
        else{
            getFragmentManager().beginTransaction().replace(R.id.fragmentLista, fd).addToBackStack(null).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

}