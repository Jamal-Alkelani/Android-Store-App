package com.example.mobile_homework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mobile_homework.Product;
import com.example.mobile_homework.R;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ProductListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Product> data;

    public ProductListAdapter(Context context, List<Product> data) {
        this.mContext=context;
        this.data=data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=  LayoutInflater.from(mContext).inflate(R.layout.product_item_list,null);
        //TODO -> Download Image from server and view it
        ImageView imageView=view.findViewById(R.id.product_image);
        TextView name=view.findViewById(R.id.name);
        TextView description=view.findViewById(R.id.description);

        name.setText(data.get(i).getName());
        description.setText(data.get(i).getDescription());


        return view;
    }
}
