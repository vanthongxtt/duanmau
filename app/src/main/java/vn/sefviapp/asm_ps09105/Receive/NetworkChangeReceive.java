package vn.sefviapp.asm_ps09105.Receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.Toast;

import java.net.URL;
import java.net.URLConnection;
import static vn.sefviapp.asm_ps09105.View.Activity.LoginActivity.checkIsConnects;
public class NetworkChangeReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)) {
                checkIsConnects(true);
                Toast.makeText(context, "Online", Toast.LENGTH_SHORT).show();
            } else {
                checkIsConnects(false);
                Toast.makeText(context, "Offline", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


}
