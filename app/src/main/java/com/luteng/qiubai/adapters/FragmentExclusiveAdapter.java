package com.luteng.qiubai.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.luteng.qiubai.ArticlesActivity;
import com.luteng.qiubai.R;
import com.luteng.qiubai.Utils.CircleTransform;
import com.luteng.qiubai.Utils.Enjoiner;
import com.luteng.qiubai.entity.TotalEntity;
import com.squareup.picasso.Picasso;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by John on 2015/12/29.
 */
public class FragmentExclusiveAdapter extends BaseAdapter implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private Context context;
    private List<TotalEntity.ItemsEntity> list;

    public FragmentExclusiveAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        TotalEntity.ItemsEntity item = list.get(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.content.setText(item.getContent());
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
        if(item.getImage()==null){
            holder.image.setVisibility(View.GONE);
        }else{
            Picasso.with(context).load(Enjoiner.getImageURL((String)item.getImage()))
                    .placeholder(R.mipmap.ic_launcher)//下载中图片
                    .error(R.mipmap.ic_launcher)//下载失败的图片
                    .resize(parent.getWidth(),0)//防止是0的情况
                    .into(holder.image);
        }
        if(item.getType()==null){
            holder.type.setVisibility(View.GONE);
            holder.typeName.setVisibility(View.GONE);
        }else{
            holder.type.setImageResource(R.mipmap.ic_rss_hot);
            holder.typeName.setText("热门");
        }
        //评论条的Linear
        //TODO:设置监听
        holder.small.setText("好笑 "+(item.getVotes().getUp()+item.getVotes().getDown()));
        holder.comment.setText("评论 "+item.getComments_count());
        holder.share.setText("分享 "+ item.getShare_count());

        holder.commentLinear.setTag(position);
        holder.commentLinear.setOnClickListener(this);

        holder.content.setTag(position);
        holder.content.setOnClickListener(this);

        holder.commentButton.setTag(position);
        holder.commentButton.setOnClickListener(this);

        //group设置监听
        holder.group.setTag(position);
        holder.group.setOnCheckedChangeListener(this);


        return convertView;
    }
    //直接调用就可以
    public void addAll(Collection<?extends TotalEntity.ItemsEntity> collection){
        list.addAll(collection);
        notifyDataSetChanged();
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int currentPosition = (int) group.getTag();
        switch (checkedId) {
            //TODO:增加减少，需要数据库的支持
            case R.id.fragment_exclusive_comment_group_button_support:
                int down = list.get(currentPosition).getVotes().getDown();
                down++;
                list.get(currentPosition).getVotes().setDown(down);
                break;
            case R.id.fragment_exclusive_comment_group_button_unsupport:
                int up = list.get(currentPosition).getVotes().getUp();
                up--;
                list.get(currentPosition).getVotes().setUp(up);
                break;
        }
        View buttonView = group.findViewById(checkedId);
        if (buttonView != null) {
            ViewCompat.setScaleX(buttonView, 0.8f);
            ViewCompat.setScaleY(buttonView, 0.8f);
            ViewCompat.animate(buttonView).scaleX(1).scaleY(1).setDuration(300).start();
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int position = (int) v.getTag();
        Log.d("FragmentExclusiveAdapter", "position = "+position);
        Bundle bundle = new Bundle();
        Log.d("FragmentExclusiveAdapter", "item222 = "+list.get(position));

        bundle.putSerializable("item",list.get(position));
        Intent intent = new Intent(context, ArticlesActivity.class);
        intent.putExtras(bundle);
        switch (id) {
            case R.id.fragment_exclusive_linear_comment:
                Toast.makeText(context,"linear的点击跳转",Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_exclusive_comment_comment_button:
                Toast.makeText(context,"comment按钮的点击跳转",Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_exclusive_content:
                Toast.makeText(context,"content的点击跳转",Toast.LENGTH_SHORT).show();
                break;
        }
        context.startActivity(intent);
    }



    //减少findViewById
    private static class ViewHolder{
        private ImageView icon;
        private TextView name;
        private ImageView type;
        private TextView typeName;
        private TextView content;
        private ImageView image;

        private LinearLayout commentLinear;
        private TextView small;
        private TextView comment;
        private TextView share;
        private RadioGroup group;
        private ImageButton commentButton;
        //构造找到控件
        public ViewHolder(View view){
            icon = (ImageView) view.findViewById(R.id.fragment_exclusive_user_icon);
            name = (TextView) view.findViewById(R.id.fragment_exclusive_user_name);
            type = (ImageView) view.findViewById(R.id.fragment_exclusive_type_image);
            typeName = (TextView) view.findViewById(R.id.fragment_exclusive_type_name);
            content = (TextView) view.findViewById(R.id.fragment_exclusive_content);
            image = (ImageView) view.findViewById(R.id.fragment_exclusive_image);
            commentLinear = (LinearLayout) view.findViewById(R.id.fragment_exclusive_linear_comment);
            small = (TextView) view.findViewById(R.id.fragment_exclusive_comment_small);
            comment = (TextView) view.findViewById(R.id.fragment_exclusive_comment_comment);
            share = (TextView) view.findViewById(R.id.fragment_exclusive_comment_share);
            group = (RadioGroup) view.findViewById(R.id.fragment_exclusive_comment_radio_group);
            commentButton = (ImageButton) view.findViewById(R.id.fragment_exclusive_comment_comment_button);
        }

    }
}
