package com.liujian.myqq.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liujian.myqq.R;
import com.liujian.myqq.data.Contact;

import java.util.ArrayList;

/**
 * Created by sh.liujian on 2015-10-20.
 */
public class ContactAdapter extends BaseAdapter {

    private ArrayList<Contact> mContacts;
    private LayoutInflater inflater;
    private Context mContext;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        mContacts = contacts;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Contact contact = mContacts.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_item, null);
            holder = new ViewHolder();
            holder.ivwHead = (ImageView) convertView.findViewById(R.id.contact_item_ivw_head);
            holder.tvwName = (TextView) convertView.findViewById(R.id.contact_item_tvw_name);
            holder.tvwLastTalk = (TextView) convertView.findViewById(R.id.contact_item_tvw_last);
            holder.tvwLastTime = (TextView) convertView.findViewById(R.id.contact_item_tvw_time);
            holder.tvwMessageCount = (TextView) convertView.findViewById(R.id.contact_item_tvw_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvwName.setText(contact.name);
        holder.tvwLastTalk.setText(contact.lastTalk);
        holder.tvwLastTime.setText(contact.lastTime);
        if (contact.messageCount > 0) {
            holder.tvwMessageCount.setText(contact.messageCount +"");
        } else {
            holder.tvwMessageCount.setVisibility(View.INVISIBLE);
        }
        holder.ivwHead.setImageResource(Integer.valueOf(contact.imageUrl));
        return convertView;
    }

    class ViewHolder {
        TextView tvwName;
        TextView tvwLastTalk;
        TextView tvwLastTime;
        TextView tvwMessageCount;
        ImageView ivwHead;
    }
}
