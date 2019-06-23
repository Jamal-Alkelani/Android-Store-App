package com.example.mobile_homework.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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

    public void setData(List<Product> data){
        this.data=data;
    }

    public Product getData(int i){
        return data.get(i);
    }

    @Override
    public int getCount() {
        if (data!=null)
        return data.size();
        return 0;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view=  LayoutInflater.from(mContext).inflate(R.layout.product_item_list,null);
        ImageView imageView=view.findViewById(R.id.product_image);
        TextView name=view.findViewById(R.id.name);
        TextView description=view.findViewById(R.id.description);
        Glide.with(mContext)
                .load(data.get(i).getPhoto()[1])
                .error(R.drawable.error404)
                .centerCrop()
                .into(imageView);


        name.setText(data.get(i).getName());
        description.setText(data.get(i).getDescription());


        return view;
    }
}
