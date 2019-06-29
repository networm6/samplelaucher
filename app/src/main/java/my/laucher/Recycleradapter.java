package my.laucher;
import android.support.v7.widget.RecyclerView;
import android.content.pm.ResolveInfo;
import java.util.List;
import android.app.Activity;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.ImageView;

import android.widget.Button;
import android.widget.TextView;
import com.bumptech.glide.Glide;


public  class Recycleradapter extends RecyclerView.Adapter
{

    private final static int HEAD_COUNT = 1;
    private final static int FOOT_COUNT = 1;

    private final static int TYPE_HEAD = 0;
    private final static int TYPE_CONTENT = 1;
    private final static int TYPE_FOOTER = 2;
    private  List<ResolveInfo> list;
    private Activity in;
    private int head,foot,layout;
    public  Recycleradapter(Activity in)
    {
        this.in = in;
    }
    public Recycleradapter inList(List<ResolveInfo> list)
    {
        this.list = list;
        return this;
    }
    public Recycleradapter inhead(int head)
    {
        this.head = head;
        return this;
    }
    public Recycleradapter inlayout(int layout)
    {
        this.layout = layout;
        return this;
    }
    public Recycleradapter infoot(int foot)
    {
        this.foot = foot;
        return this;
    }
    public int getContentSize()
    {
        return list.size();
    }

    public boolean isHead(int position)
    {
        return HEAD_COUNT != 0 && position == 0;
    }

    public boolean isFoot(int position)
    {
        return FOOT_COUNT != 0 && position == getContentSize() + HEAD_COUNT;
    }

    @Override
    public int getItemViewType(int position)
    {
        int contentSize = getContentSize();
        if (HEAD_COUNT != 0 && position == 0)
        { // 头部
            return TYPE_HEAD;
        }
        else if (FOOT_COUNT != 0 && position == HEAD_COUNT + contentSize)
        { // 尾部
            return TYPE_FOOTER;
        }
        else
        {
            return TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_HEAD)
        {
            View itemView = LayoutInflater.from(this.in).inflate(this.head, parent, false);
            return new Recycleradapter.HeadHolder(itemView);
        }
        else if (viewType == TYPE_CONTENT)
        {
            View itemView = LayoutInflater.from(this.in).inflate(this.layout, parent, false);
            return new Recycleradapter.ContentHolder(itemView);
        }
        else
        {
            View itemView = LayoutInflater.from(this.in).inflate(this.foot, parent, false);
            return new Recycleradapter.FootHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof Recycleradapter.HeadHolder)
        { // 头部

        }
        else if (holder instanceof Recycleradapter.ContentHolder)
        { // 内容
            Recycleradapter.ContentHolder myHolder = (Recycleradapter.ContentHolder) holder;
            ResolveInfo   info=    this.list.get(position - 1);
            android.graphics.drawable.Drawable dr=info.activityInfo.loadIcon(in.getPackageManager());
            myHolder.putout.setImageDrawable(dr);
            myHolder.putout.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            myHolder.te.setText(info.loadLabel(in.getPackageManager()).toString());
           


            myHolder.bu.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View p1)
                    {
                        ResolveInfo info = list.get(position-1);
                        //该应用的包名
                        String pkg = info.activityInfo.packageName;
                        //应用的主activity类
                        String cls = info.activityInfo.name;
                        ComponentName componet = new ComponentName(pkg, cls);

                        Intent intent = new Intent();
                        intent.setComponent(componet);
                        intent. addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        in.  startActivity(intent);
                    }
                });

        }
        else
        { // 尾部

        }
    }

    @Override
    public int getItemCount()
    {
        return list.size() + HEAD_COUNT + FOOT_COUNT;
    }

    // 头部
    private class HeadHolder extends RecyclerView.ViewHolder
    {
        public HeadHolder(View itemView)
        {
            super(itemView);
        }
    }

    // 内容

    class ContentHolder extends RecyclerView.ViewHolder
    {

        private ImageView putout;
        private TextView te;
        private Button bu;
        public ContentHolder(View itemView)
        {
            super(itemView);

            putout = itemView.findViewById(R.id.itemImageView1);
            te = itemView.findViewById(R.id.itemTextView1);
            bu = itemView.findViewById(R.id.itemButton1);
        }
    }
    // 尾部
    private class FootHolder extends RecyclerView.ViewHolder
    {
        public FootHolder(View itemView)
        {
            super(itemView);
        }
    }

}
