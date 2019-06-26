package com.example.mobile_homework;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import com.example.mobile_homework.Adapters.ProductListAdapter;
import com.example.mobile_homework.utilities.NetworkHandler;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static ListView lv_products;
    static LinearLayout spinKit;
    static LinearLayout empty_data;
    public static ProductListAdapter adapter;
    Context mContext;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        lv_products = findViewById(R.id.lv_products);
        spinKit = findViewById(R.id.spin_kit);
        empty_data=findViewById(R.id.empty_data_main);
        adapter = new ProductListAdapter(this, null);
        lv_products.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Pal Shop");
        setSupportActionBar(toolbar);

        lv_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(), Details.class);
                Product product=adapter.getData(i);
                intent.putExtra(Details.NAME,product.getName());
                intent.putExtra(Details.DESCRIPTION,product.getName());
                intent.putExtra(Details.PDATE,product.getProduction_date());
                intent.putExtra(Details.EDATE,product.getExpiration_date());
                intent.putExtra(Details.PID,product.getId());
                intent.putExtra(Details.IMAGE,product.getPhoto()[1]);

                //sending image from Main to Detilas
                ImageView toSendImage=view.findViewById(R.id.product_image);
                toSendImage.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) toSendImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                intent.putExtra(Details.IMAGE,bitmap);

                startActivity(intent);
            }
        });


//        NetworkHandler networkHandler = new NetworkHandler(mContext);
//        networkHandler.loadData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        lv_products.setVisibility(View.GONE);
        spinKit.setVisibility(View.VISIBLE);
        NetworkHandler networkHandler = new NetworkHandler(this);
        networkHandler.loadData();
    }

    public static void setAdapter(List<Product> data) {
        if (data.size()==0){
            empty_data.setVisibility(View.VISIBLE);
            lv_products.setVisibility(View.GONE);
            spinKit.setVisibility(View.GONE);
        }else{
            empty_data.setVisibility(View.GONE);
            adapter.setData(data);
//            adapter.notifyDataSetChanged();
            lv_products.setVisibility(View.VISIBLE);
            spinKit.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_items, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_addProduct) {
            startActivity(new Intent(this, AddProduct.class));
        }
        return true;


    }



}
