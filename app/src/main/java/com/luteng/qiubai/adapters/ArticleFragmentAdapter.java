package com.luteng.qiubai.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.luteng.qiubai.R;
import com.luteng.qiubai.Utils.CircleTransform;
import com.luteng.qiubai.Utils.Enjoiner;
import com.luteng.qiubai.entity.CommentEntity;
import com.luteng.qiubai.entity.TotalEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by John on 2015/12/30.
 */
public class ArticleFragmentAdapter extends BaseAdapter{
    private Context context;
    private List<CommentEntity.ItemsEntity> items;

    public ArticleFragmentAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_article,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        CommentEntity.ItemsEntity item = items.get(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if(item.getUser()==null){
            holder.icon.setImageResource(R.mipmap.ic_launcher);
            holder.name.setText("匿名用户");
        }else{
            holder.name.setText(item.getUser().getLogin());
            Picasso.with(context)
                    .load(Enjoiner.getIconURL(item.getUser().getId(),item.getUser().getIcon()))
                    .transform(new CircleTransform())
                    .into(holder.icon);
            String s =  Enjoiner.getIconURL(item.getUser().getId(), item.getUser().getIcon());
            Log.d("FragmentExclusiveAdapter", "iconUrl = " + s);

        }
        Log.d("ArticleFragmentAdapter", "data 3333 = "+item.getContent());

        holder.content.setText(item.getContent()+"");
        holder.time.setText(item.getCreated_at()+"");
        return convertView;
    }
    //直接调用就可以
    public void addAll(Collection<?extends CommentEntity.ItemsEntity> collection){
        items.addAll(collection);
        Log.d("ArticleFragmentAdapter", "all all = "+collection.size());
        notifyDataSetChanged();
    }
    private static class ViewHolder{
        private ImageView icon;
        private TextView name;
        private TextView content;
        private TextView time;
        public ViewHolder(View view){
            icon = (ImageView) view.findViewById(R.id.article_fragment_user_icon);
            name = (TextView) view.findViewById(R.id.article_fragment_user_name);
            content = (TextView) view.findViewById(R.id.article_fragment_user_comment);
            time = (TextView) view.findViewById(R.id.article_fragment_user_comment_time);
        }
    }
}
