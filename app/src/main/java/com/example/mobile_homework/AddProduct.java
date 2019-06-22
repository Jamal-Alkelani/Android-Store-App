package com.example.mobile_homework;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {

    ImageView previewImage;
    private boolean canEdit=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);



        findViewById(R.id.iv_expDatePicker_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canEdit){
                    pickDate((TextView) findViewById(R.id.dp_expDate_details));
                }else{
                    Toast.makeText(AddProduct.this, "Make Sure The Edit Button Is Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.iv_prodDatePicker_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canEdit){
                    pickDate((TextView) findViewById(R.id.dp_prodDate_details));

                }else{
                    Toast.makeText(AddProduct.this, "Make Sure The Edit Button Is Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        previewImage=findViewById(R.id.preview);
        findViewById(R.id.iv_selectImageFromStorage_addProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data==null) {
            return;
        }
        if (requestCode == 1) {

            final Uri extras = data.getData();

            Log.e("xx",data+"");

            if (extras != null) {
                //Get image
                previewImage.setImageURI(extras);

            }
        }
    }

    void pickDate(final TextView textView){
        final String[] picked_Date = new String[1];

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog datePicker= new DatePickerDialog(this);
            datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    //TODO ->Change the date
                    picked_Date[0] = i+"-"+i1+"-"+i2;
                    textView.setText(picked_Date[0]);
                }
            });
            datePicker.show();

        }

    }
}
