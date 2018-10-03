package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Ena on 28.05.2016..
 */
public class MyIntentService extends IntentService{
    public static final int STATUS_RUNNING =0 ;
    public static final int STATUS_FINISHED=1;
    public static final int STATUS_ERROR=2;

    public ArrayList<Muzicar> listaSearch=new ArrayList<Muzicar>();

    public MyIntentService() {
        super(null);
    }
    public MyIntentService(String name) {
        super(name);

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<Muzicar> results;
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String upit=intent.getStringExtra("tekst");
        String url1 = "https://api.spotify.com/v1/search?q=" + URLEncoder.encode(upit) + "&type=artist";
        Bundle bundle = new Bundle();
        try {

            receiver.send(STATUS_RUNNING, Bundle.EMPTY);

            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONObject artists = jo.getJSONObject("artists");
            JSONArray items = artists.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                ArrayList<String> topPjesme=new ArrayList<String>();
                ArrayList<String> albumi=new ArrayList<String>();
                JSONObject artist = items.getJSONObject(i);
                String name = artist.getString("name");
                String artist_ID = artist.getString("id");

                String url2="https://api.spotify.com/v1/artists/"+artist_ID+"/top-tracks?country=GB";
                URL urlNovi=new URL(url2);
                HttpURLConnection urlConnection2 = (HttpURLConnection) urlNovi.openConnection();
                InputStream in2 = new BufferedInputStream(urlConnection2.getInputStream());
                String rezultat2 = convertStreamToString(in2);
                JSONObject jo2 = new JSONObject(rezultat2);
                JSONArray tracks = jo2.getJSONArray("tracks");
                for(int j=0;j<tracks.length();j++) {
                    JSONObject track = tracks.getJSONObject(j);
                    String nameTr = track.getString("name");
                    topPjesme.add(j,nameTr);
                }

                String url3="https://api.spotify.com/v1/artists/"+artist_ID+"/albums?market=ES&album_type=single&limit=2";
                URL urlNovi2=new URL(url3);
                HttpURLConnection urlConnection3 = (HttpURLConnection) urlNovi2.openConnection();
                InputStream in3 = new BufferedInputStream(urlConnection3.getInputStream());
                String rezultat3 = convertStreamToString(in3);
                JSONObject jo3 = new JSONObject(rezultat3);
                JSONArray albums = jo3.getJSONArray("items");
                for(int j=0;j<albums.length();j++) {
                    JSONObject album = albums.getJSONObject(j);
                    String nameAl = album.getString("name");
                    albumi.add(j,nameAl);
                }

                JSONArray genres=artist.getJSONArray("genres");
                String genre;
                if (genres.isNull(0)) genre=" ";
                else genre=genres.getString(0);


                Muzicar novi=new Muzicar(name,genre,topPjesme,albumi);
                listaSearch.add(i, novi);

            }

            bundle.putParcelableArrayList("result", listaSearch);
            receiver.send(STATUS_FINISHED, bundle);
        }
        catch (Exception e) {
            bundle.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, bundle);
        }

    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }
}
