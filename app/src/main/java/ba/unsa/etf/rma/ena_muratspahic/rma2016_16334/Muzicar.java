package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**     * Created by ETF-LAB-1-11 on 3/19/2016.   */
public class Muzicar implements Parcelable {
    String ime;
    String zanr;
    String webStranica;
    String biografija;
    ArrayList<String> pjesme;
    ArrayList<String> albumi;
    String id;

    public Muzicar(){}

    public Muzicar(String ime, String zanr) {

        this.ime = ime;
        this.zanr = zanr;
        pjesme=new ArrayList<String>();
        albumi=new ArrayList<String>();

    }

    public Muzicar(String id, String ime, String zanr) {
        this.id=id;
        this.ime = ime;
        this.zanr = zanr;

    }
    public Muzicar(String ime)
    {
        this.ime=ime;
        this.zanr="download";
    }

    protected Muzicar(Parcel source){
        ime=source.readString();
        zanr=source.readString();
        webStranica=source.readString();
        biografija=source.readString();
    }

    public static final Creator<Muzicar> CREATOR =new Creator<Muzicar>() {
        @Override
        public Muzicar createFromParcel(Parcel source) {
            return new Muzicar(source);
        }

        @Override
        public Muzicar[] newArray(int size) {
            return new Muzicar[size];
        }
    };

    public Muzicar(String ime, String zanr, ArrayList<String> top5, ArrayList<String> albumi) {
        this.ime=ime;
        this.zanr=zanr;
        this.pjesme=top5;
        this.albumi=albumi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getPjesme() {
        return pjesme;
    }

    public void setPjesme(ArrayList<String> pjesme) {
        this.pjesme = pjesme;
    }

    public ArrayList<String> getAlbumi() {
        return albumi;
    }

    public void setAlbumi(ArrayList<String> albumi) {
        this.albumi = albumi;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public String getWebStranica() {
        return webStranica;
    }

    public void setWebStranica(String webStranica) {
        this.webStranica = webStranica;
    }

    public String getBiografija() {
        return biografija;
    }

    public void setBiografija(String biografija) {
        this.biografija = biografija;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(ime);
        dest.writeString(zanr);
        dest.writeString(webStranica);
        dest.writeString(biografija);

    }

}
