package com.example.mobile_homework;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.mobile_homework.utilities.NetworkHandler;

public class AddProduct extends AppCompatActivity {

    ImageView previewImage;
    private boolean canEdit = true;
    EditText name, desc;
    TextView PDate, EDate;
    RelativeLayout process_action;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);



        name = findViewById(R.id.et_name_details);
        desc = findViewById(R.id.et_description_details);
        PDate = findViewById(R.id.dp_prodDate_details);
        EDate = findViewById(R.id.dp_expDate_details);
        process_action = findViewById(R.id.process_action);

        findViewById(R.id.iv_selectImageFromCamera_addProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    handleCameraCapture();
                }
            }
        });

        findViewById(R.id.iv_expDatePicker_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canEdit) {
                    pickDate((TextView) findViewById(R.id.dp_expDate_details));
                } else {
                    Toast.makeText(AddProduct.this, "Make Sure The Edit Button Is Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.iv_prodDatePicker_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canEdit) {
                    pickDate((TextView) findViewById(R.id.dp_prodDate_details));

                } else {
                    Toast.makeText(AddProduct.this, "Make Sure The Edit Button Is Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        previewImage = findViewById(R.id.preview);
        findViewById(R.id.iv_selectImageFromStorage_addProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        findViewById(R.id.btn_uploadData_addProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] photo = new String[2];
                photo[0] = "some NAmes";
                Product product = new Product("-1", name.getText().toString(), desc.getText().toString(),
                        PDate.getText().toString(), EDate.getText().toString(), photo);
                previewImage.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) previewImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                product.setImage(bitmap);


                NetworkHandler networkHandler = new NetworkHandler(getApplicationContext());
                networkHandler.addProduct(product);
                process_action.setVisibility(View.VISIBLE);
            }
        });

//        findViewById(R.id.btn_uploadImage_addProduct).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                NetworkHandler networkHandler=new NetworkHandler();
////                networkHandler.UploadImage(product);
//            }
//        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void handleCameraCapture() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
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
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == 1) {

            final Uri extras = data.getData();

            Log.e("xx", data + "");

            if (extras != null) {
                //Get image
                previewImage.setImageURI(extras);

            }
        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            previewImage.setImageBitmap(photo);

        }
    }

    void pickDate(final TextView textView) {
        final String[] picked_Date = new String[1];

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog datePicker = new DatePickerDialog(this);
            datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    //TODO ->Change the date
                    picked_Date[0] = i + "-" + i1 + "-" + i2;
                    textView.setText(picked_Date[0]);
                }
            });
            datePicker.show();

        }

    }
}
