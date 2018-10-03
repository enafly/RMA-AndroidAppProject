package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Ena on 28.03.2016..
 */
public class BroadcastReceiverMoj extends BroadcastReceiver{



    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            Toast toast = Toast.makeText(context,context.getString(R.string.konekcija) , Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
