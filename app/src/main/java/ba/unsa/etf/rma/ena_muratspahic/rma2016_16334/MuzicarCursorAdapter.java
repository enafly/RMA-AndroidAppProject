package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Ena on 11.06.2016..
 */
public class MuzicarCursorAdapter extends ResourceCursorAdapter {

    private LayoutInflater cursorInflater;

    //private static final String MUZICAR_IME =DBHandler.MUZICAR_IME ;

    public MuzicarCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView ime = (TextView) view.findViewById(R.id.ime);
        ime.setText(cursor.getString(cursor.getColumnIndex(DBHandler.MUZICAR_IME)));
        TextView zanr = (TextView) view.findViewById(R.id.zanr);
        zanr.setText(cursor.getString(cursor.getColumnIndex(DBHandler.MUZICAR_ZANR)));
    }
    /*public View newView (Context context, Cursor cursor, ViewGroup parent){
        return cursorInflater.inflate(R.layout.element_liste, parent, false);
    }*/

}

