package com.example.mobile_homework.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.example.mobile_homework.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONHandler {
    private Context mContext;

    public JSONHandler(Context context) {
        mContext=context;
    }

    final String DATA="data";
    final String STATUS_MSG="1";
    public List<Product> convertJSONToProductObject(String jsonString){
        List<Product> list=new ArrayList<>();
        boolean status=false;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray data=jsonObject.getJSONArray(DATA);
            for(int i = 0; i < data.length(); i++){
                JSONObject productRecord = data.getJSONObject(i);

                if (productRecord.has("status")){
                    if(productRecord.get("status").equals(STATUS_MSG)){
                        status=true;
                        break;
                    }
                }

                String PID=productRecord.get("PID").toString();
                String name=productRecord.get("name").toString();
                String desc=productRecord.get("description").toString();
                String production_date=productRecord.get("production_date").toString();
                String expiration_date=productRecord.get("expiration_date").toString();
                String image_name=productRecord.get("image_name").toString();
                String location="https://sbda.000webhostapp.com/Mobile_HW_ServerSide/images/"+productRecord.get("location").toString()+".png";
                String photo[]=new String[2];
                photo[0]=image_name;
                photo[1]=location;
                Product product=new Product(PID,name,desc,production_date,expiration_date,photo);
                list.add(product);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(!status){
            Toast.makeText(mContext, "Oops Something wrong Happened", Toast.LENGTH_SHORT).show();
        }
        return list;
    }
}
