package com.example.mobile_homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import com.example.mobile_homework.Adapters.ProductListAdapter;
import com.example.mobile_homework.utilities.NetworkHandler;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

class MainActivity extends AppCompatActivity {

    private ListView lv_products;
    private SpinKitView spinKit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_products=findViewById(R.id.lv_products);
        spinKit=findViewById(R.id.spin_kit);

        ProductListAdapter adapter=new ProductListAdapter(this,generateFakeData());
        lv_products.setAdapter(adapter);

        Toolbar toolbar=findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        spinKit.setVisibility(View.GONE);
        lv_products.setVisibility(View.VISIBLE);

        lv_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(),Details.class));
            }
        });

        NetworkHandler networkHandler=new NetworkHandler();
        networkHandler.loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_items, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mi_addProduct){
            startActivity(new Intent(this,AddProduct.class));
        }
        return true;


    }

    List<Product> generateFakeData(){
        ArrayList<Product> fakeData=new ArrayList<Product>();
        String x[]=new String[2];
        x[0]="asd";
        x[1]="asd";
        for (int i=0;i<15;i++){
            Product product=new Product("123","Laptop","The Ultimate new brand of Lenovo Gaming Laptop" +
                    "With the latest features","5-5-2017","none",new String[2]);
            fakeData.add(product);
        }

        return fakeData;
    }

    boolean loadData(){
        return false;
    }
}
