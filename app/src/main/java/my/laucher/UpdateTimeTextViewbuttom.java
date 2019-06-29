package my.laucher;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import java.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.widget.TextView;

public class UpdateTimeTextViewbuttom  extends TextView{

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            UpdateTimeTextViewbuttom.this.setText((String)msg.obj);
        }
    };
    private String DEFAULT_TIME_FORMAT = "yyyy年MM月dd日\nss";
  //  private String DEFAULT_TIME_FORMAT = "yyyyMMddss";
    
    public UpdateTimeTextViewbuttom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
//                    SimpleDateFormat sdf=new SimpleDateFormat(DEFAULT_TIME_FORMAT);
//                    String str=sdf.format(new Date());
                        SimpleDateFormat dateFormatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
                        String time = dateFormatter.format(Calendar.getInstance().getTime());
                        handler.sendMessage(handler.obtainMessage(100,time));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
    }


}

