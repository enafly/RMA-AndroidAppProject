package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by Ena on 28.05.2016..
 */
public class MyResultReceiver extends ResultReceiver {
    private Receiver mReceiver;

    public MyResultReceiver(android.os.Handler handler) {
        super(handler);
    }
    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
