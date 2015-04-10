package com.mall.intel.rnh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mall.intel.rnh.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;



    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_activity, null);
        }
        else
        {

        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.prodName);
        //StringTokenizer stringTokenizer = new StringTokenizer(list.get(position), " ");
        //listItemText.setText(stringTokenizer.nextToken());
        TextView prdPrice=(TextView)view.findViewById(R.id.price);
        //prdPrice.setText(stringTokenizer.nextToken());*/
        //Handle buttons and add onClickListeners
        String [] array = list.get(position).split(" ");
        listItemText.setText(array[0]);
       //listItemText.setText(array[1]);
        prdPrice.setText(array[1]);
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);


        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return view;
    }
}