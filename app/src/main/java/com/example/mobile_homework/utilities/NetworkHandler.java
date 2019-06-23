package com.example.mobile_homework.utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import com.example.mobile_homework.Details;
import com.example.mobile_homework.MainActivity;
import com.example.mobile_homework.Product;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;

public class NetworkHandler {

//    https://sbda.000webhostapp.com/Mobile_HW_ServerSide/phpserver.php
    private final String URL = "https://sbda.000webhostapp.com/Mobile_HW_ServerSide/test.php";
    private Context mContext;
    String action;
    public NetworkHandler(Context context) {
        mContext=context;
    }

    private JSONObject buildAction(String action, Product productٍ) {
        action=action;
        JSONObject jsonAction = new JSONObject();
        try {

            jsonAction.put("action", action);
            switch (action) {
                case "AddProduct":
                    jsonAction.put("PID", -1);
                    jsonAction.put("name", productٍ.getName());
                    jsonAction.put("description", productٍ.getDescription());
                    jsonAction.put("EDate", productٍ.getExpiration_date());
                    jsonAction.put("PDate", productٍ.getProduction_date());
                    jsonAction.put("image_name", productٍ.getPhoto()[0]); //photo name
                    jsonAction.put("image_data",convertImageToString(productٍ.getImage()));
                    break;
                case "UpdateProduct":
                    jsonAction.put("PID", productٍ.getId());
                    jsonAction.put("name", productٍ.getName());
                    jsonAction.put("description", productٍ.getDescription());
                    jsonAction.put("EDate", productٍ.getExpiration_date());
                    jsonAction.put("PDate", productٍ.getProduction_date());
                    jsonAction.put("image_name", productٍ.getPhoto()[0]); //photo name

                    break;
                case "DeleteProduct":
                    Log.e("aa",productٍ.getId());
                    jsonAction.put("PID", productٍ.getId());
                    break;

                case "GetImage":
                    jsonAction.put("image_name",productٍ.getPhoto()[0]);
                    break;

                case "SendImage":
                    jsonAction.put("PID",productٍ.getId());
                    jsonAction.put("image",convertImageToString(productٍ.getImage()));
                    break;

                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonAction;
    }

    public boolean addProduct(Product product) {
        JSONObject action = buildAction("AddProduct",product);
        new SendDeviceDetails().execute(URL, action.toString());
        return true;
    }

    public boolean updateProduct(Product product) {
        JSONObject action = buildAction("UpdateProduct",product);
        new SendDeviceDetails().execute(URL, action.toString());
        return true;
    }

    public boolean deleteProduct(Product product) {
        JSONObject action = buildAction("DeleteProduct",product);
        new SendDeviceDetails().execute(URL, action.toString());
        return true;
    }

    public void loadData() {
        JSONObject action = buildAction("ReadProducts",null);
        new SendDeviceDetails().execute(URL, action.toString());
    }

    public Bitmap loadImage(Product product){
        JSONObject action = buildAction("GetImage",product);
        new SendDeviceDetails().execute(URL, action.toString());
        return null;
    }

    public boolean UploadImage(Product product){
        JSONObject action = buildAction("SendImage",product);
        new SendDeviceDetails().execute(URL, action.toString());
        return true;
    }

    private String convertImageToString(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
        byte [] byte_arr = stream.toByteArray();
        String image_str = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            image_str = Base64.getEncoder().encodeToString(byte_arr);
        }
        return image_str;
    }

    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {
                Log.e("xx","inside Async Task... URL = "+params[1]);


                URL url=new URL(URL);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setReadTimeout(15000);
                Log.e("xx","connection opened");

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                Log.e("xx","finished");

                wr.writeBytes("" + params[1]);


                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
            JSONHandler jsonHandler=new JSONHandler();
            List<Product> products=jsonHandler.convertJSONToProductObject(result);
            MainActivity.setAdapter(products);
            if(action!=null){
                if(action.equals("UpdateProduct") || action.equals("DeleteProduct"))
                    Details.process_action.setVisibility(View.GONE);
            }



        }
    }
}
