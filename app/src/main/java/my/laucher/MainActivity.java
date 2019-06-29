package my.laucher;
import android.kz.BaseBean.Base;
import android.support.v7.widget.RecyclerView;
import android.content.pm.ResolveInfo;
import java.util.List;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Build;
import android.provider.Settings;
import android.content.Intent;
import android.widget.Toast;
import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;



public class MainActivity extends Base
{
    private RecyclerView re;
    private Recycleradapter ad;
    private List<ResolveInfo> apps;
    private SearchEditText searchEditText;
    private  LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        inlayout(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (!Settings.canDrawOverlays(this))
            {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
                Toast.makeText(this, "请先允许FloatBall出现在顶部", Toast.LENGTH_SHORT).show();
            }
        }

        WallpaperManager manager =WallpaperManager.getInstance(MainActivity.this);
        Drawable drawable=manager.getDrawable();
        linearLayout = findViewById(R.id.desktop_linear);
        linearLayout.setBackground(drawable);

        searchEditText = (SearchEditText) findViewById(R.id.searchEditText);

        //搜索事件
        searchEditText.setOnSearchClickListener(new SearchEditText.OnSearchClickListener(){

                @Override
                public void onSearchClick(View view, String keyword)
                {
                    if (keyword.equals("show"))
                    {
                        Intent intent = new Intent(MainActivity.this, FloatBallService.class);
                        Bundle data = new Bundle();
                        data.putInt("type", FloatBallService.TYPE_ADD);
                        intent.putExtras(data);
                        startService(intent);
                    }
                    if (keyword.equals("hide"))
                    {
                        Intent intent = new Intent(MainActivity.this, FloatBallService.class);
                        Bundle data = new Bundle();
                        data.putInt("type", FloatBallService.TYPE_DEL);
                        intent.putExtras(data);
                        startService(intent);
                    }
                    if (keyword.equals("fuzhu"))
                    {
                        checkAccessibility();
                    }
                }
            });    

		loadApps();		
       re =  findViewById(R.id.re);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //设置方向,默认方向(垂直)
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
       re.setLayoutManager(linearLayoutManager);

       ad = new Recycleradapter(MainActivity.this)
        .inList(apps)
            .inlayout(R.layout.item)
            .inhead(R.layout.head)
            .infoot(R.layout.foot);
        re.setAdapter(ad);
    }


    private void checkAccessibility()
    {
        // 判断辅助功能是否开启
        if (!AccessibilityUtil.isAccessibilitySettingsOn(this))
        {
            // 引导至辅助功能设置页面
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            Toast.makeText(this, "请先开启FloatBall辅助功能", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed()
    {
        System.gc();
	}
    


    private void loadApps()
    {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        apps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }


    
    
}
