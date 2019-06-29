package my.laucher;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by wangxiandeng on 2016/11/25.
 */

public class FloatBallService extends AccessibilityService {
    public static final int TYPE_ADD = 0;
    public static final int TYPE_DEL = 1;
    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, FloatBallService.class);
        startService(intent);
        super.onDestroy();
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle data = intent.getExtras();
        if (data != null) {
            int type = data.getInt("type");
            if (type == TYPE_ADD) {
                FloatWindowManager.addBallView(this);
            } else {
                FloatWindowManager.removeBallView(this);
            }
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
