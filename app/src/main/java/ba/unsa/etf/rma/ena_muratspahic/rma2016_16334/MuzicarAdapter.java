package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**   *   Created by Ena on 30.03.2016..    */

public class MuzicarAdapter extends ArrayAdapter<Muzicar> {

    int resource;
    Context ctxt;

    public MuzicarAdapter(Context context, int _resource, List<Muzicar> items) {
        super(context, _resource, items);
        ctxt = context;
        resource = _resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout newView;

        if (convertView == null) {

            newView = new LinearLayout(getContext());
            LayoutInflater li = LayoutInflater.from(getContext());

            li.inflate(resource, newView, true);
        }
        else {
            newView = (LinearLayout) convertView;
        }

        Muzicar muzicar = getItem(position);

        TextView ime = (TextView)newView.findViewById(R.id.ime);
        ime.setText(muzicar.getIme());

        TextView zanr =(TextView)newView.findViewById(R.id.zanr);
        zanr.setText(muzicar.getZanr());

        ImageView imageView = (ImageView) newView.findViewById(R.id.icon);
        String genreMatch = muzicar.getZanr();
        if (genreMatch.equals("folk")) {
            imageView.setImageResource(R.drawable.folk);
        }
        else if (genreMatch.equals("house")) {
            imageView.setImageResource(R.drawable.house);
        }
        else if (genreMatch.equals("metal")) {
            imageView.setImageResource(R.drawable.metal);
        }
        else if (genreMatch.equals("pop")) {
            imageView.setImageResource(R.drawable.pop);
        }
        else if (genreMatch.equals("punk")) {
            imageView.setImageResource(R.drawable.punk);
        }
        else if (genreMatch.equals("classical")) {
            imageView.setImageResource(R.drawable.classical);
        }
        else if (genreMatch.equals("electro")) {
            imageView.setImageResource(R.drawable.electro);
        }
        else if (genreMatch.equals("jazz")) {
            imageView.setImageResource(R.drawable.jazz);
        }
        else if (genreMatch.equals("rock")) {
            imageView.setImageResource(R.drawable.rock);
        }
        else if (genreMatch.equals("techno")) {
            imageView.setImageResource(R.drawable.techno);
        }
        else {
            imageView.setImageResource(R.drawable.download);
        }
        return newView;
    }

    public Muzicar getItemClicked(int position) {
        return getItem(position);
    }
}
