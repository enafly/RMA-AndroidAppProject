package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**      * Created by ETF-LAB-1-11 on 3/14/2016.     */
public class ElementListe extends ArrayAdapter<Muzicar> {

    int resource;
    public ElementListe(Context context,int _resource, List<Muzicar> items) {
        super(context, _resource, items);
        resource = _resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView;
        if (convertView == null) {
            newView = LayoutInflater.from(getContext()).inflate(R.layout.element_liste, null);
        }
        else
        {
            newView = convertView;
        }
        Muzicar cItem = getItem(position);

        TextView tvName = (TextView) newView.findViewById(R.id.ime);
        TextView tvGenre = (TextView) newView.findViewById(R.id.zanr);
        ImageView ivIcon = (ImageView) newView.findViewById(R.id.icon);


        tvName.setText(cItem.getIme());
        tvGenre.setText(cItem.getZanr());

        ivIcon.setImageResource(GenreFactory.GetImageResForGenre(cItem.getZanr()));

        return newView;
    }

}
