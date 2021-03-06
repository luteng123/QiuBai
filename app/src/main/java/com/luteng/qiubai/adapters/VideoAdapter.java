package com.luteng.qiubai.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.luteng.qiubai.R;
import com.luteng.qiubai.Utils.CircleTransform;
import com.luteng.qiubai.Utils.Enjoiner;
import com.luteng.qiubai.entity.TotalEntity;
import com.luteng.qiubai.entity.VideoEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by John on 2015/12/29.
 */
public class VideoAdapter extends BaseAdapter{
    private Context context;
    private List<VideoEntity.ItemsEntity> list;

    public VideoAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_video,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        VideoEntity.ItemsEntity item = list.get(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        //holder.content.setText(item.getContent());
        if(item.getUser()==null){
            holder.icon.setImageResource(R.mipmap.ic_launcher);
            holder.name.setText("匿名用户");
        }else{
            holder.name.setText(item.getUser().getLogin());
            Picasso.with(context)
                    .load(Enjoiner.getIconURL(item.getUser().getId(), item.getUser().getIcon()))
                    .transform(new CircleTransform())
                    .into(holder.icon);
            String s =  Enjoiner.getIconURL(item.getUser().getId(), item.getUser().getIcon());
            Log.d("FragmentExclusiveAdapter", "iconUrl = " + s);

        }
        if(item.getPic_url()==null){
            holder.image.setVisibility(View.GONE);
        }else{
            Picasso.with(context).load(item.getPic_url())
                    .placeholder(R.mipmap.ic_launcher)//下载中图片
                    .error(R.mipmap.ic_launcher)//下载失败的图片
                    .resize(parent.getWidth(),0)//防止是0的情况
                    .into(holder.image);
            Log.d("VideoAdapter", "Pic_url = "+item.getPic_url());

        }
        return convertView;
    }
    //直接调用就可以
    public void addAll(Collection<?extends VideoEntity.ItemsEntity> collection){
        list.addAll(collection);
        notifyDataSetChanged();
    }
    //减少findViewById
    private static class ViewHolder{
        private ImageView icon;
        private TextView name;
        private ImageView image;

        private LinearLayout commentLinear;
        private TextView small;
        private TextView comment;
        private TextView share;
        private TextView regeneration;
        private RadioGroup group;
        private ImageButton commentButton;
        //构造找到控件
        public ViewHolder(View view){
            icon = (ImageView) view.findViewById(R.id.fragment_video_user_icon);
            name = (TextView) view.findViewById(R.id.fragment_video_user_name);
            image = (ImageView) view.findViewById(R.id.fragment_video_image);
        }

    }
}
