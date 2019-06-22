package com.example.mobile_homework.utilities;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.example.mobile_homework.Product;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NetworkHandler {


    private final String URL = "10.0.2.3/HW/phpserver.php";

    private JSONObject buildAction(String action, Product productٍ) {
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
                    jsonAction.put("PID", productٍ.getId());
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

    public String loadData() {
        JSONObject action = buildAction("ReadProducts",null);
        new SendDeviceDetails().execute(URL, action.toString());

        return null;
    }

    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {
                Log.e("xx","inside Async Task... URL = "+params[0]);

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setReadTimeout(15000);
                Log.e("xx","connection opened");

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                Log.e("xx","writing data");

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
//                if (httpURLConnection != null) {
//                    httpURLConnection.disconnect();
//                }
            }

            Log.e("xx",data);
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }
}
