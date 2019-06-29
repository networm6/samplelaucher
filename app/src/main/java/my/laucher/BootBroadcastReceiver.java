package my.laucher;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class BootBroadcastReceiver extends BroadcastReceiver {  

      private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";    
    @Override  
    public void onReceive(Context context, Intent intent) {    
        if (intent.getAction().equals(ACTION_BOOT)) { //开机启动完成后，要做的事情 
            Intent intents= new Intent(context, FloatBallService.class);
            Bundle data = new Bundle();
            data.putInt("type", FloatBallService.TYPE_ADD);
            intents.putExtras(data);
           context. startService(intents);
        }  
    }  
} 
