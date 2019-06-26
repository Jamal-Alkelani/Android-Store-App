package com.example.mobile_homework;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.example.mobile_homework.utilities.NetworkHandler;

public class Details extends AppCompatActivity {

    public static final String PID = "PID";
    public static final String NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PDATE = "PDATE";
    public static final String EDATE = "EDATE";
    public static final String IMAGE = "IMAGE";
    private boolean canEdit = false;
    private EditText name;
    private EditText desc;
    private Button update, delete;
    private TextView PDate, EDate;
    ImageView image;
    ImageView selectImage,preview;
    public static RelativeLayout process_action;
    public static boolean from_gallrey=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = findViewById(R.id.et_name_details);
        desc = findViewById(R.id.et_description_details);
        name.setEnabled(false);
        desc.setEnabled(false);

        EDate = findViewById(R.id.dp_expDate_details);
        PDate = findViewById(R.id.dp_prodDate_details);
        image=findViewById(R.id.iv_image_details);
        process_action=findViewById(R.id.process_action);
        selectImage=findViewById(R.id.iv_selectImageFromStorage_addProduct);

        final Intent intent = getIntent();
        if (intent != null) {
            name.setText(intent.getStringExtra(NAME));
            desc.setText(intent.getStringExtra(DESCRIPTION));
            EDate.setText(intent.getStringExtra(EDATE));
            PDate.setText(intent.getStringExtra(PDATE));

            if (!from_gallrey) {
//                Glide.with(this)
//                        .load(intent.getStringExtra(IMAGE))
//                        .error(R.drawable.error404)
//                        .centerCrop()
//                        .into(image);
                Bitmap bitmap=intent.getParcelableExtra(IMAGE);
                image.setImageBitmap(bitmap);
            }
//            byte[] byteArray = getIntent().getByteArrayExtra("image");
//            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//            ImageView imageView=findViewById(R.id.iv_image_details);
//            imageView.setImageBitmap(bmp);

            selectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickImage();
                }
            });
        }

        update = findViewById(R.id.btn_update);
        delete = findViewById(R.id.btn_delete);
        update.setEnabled(false);

        name=findViewById(R.id.et_name_details);
        desc=findViewById(R.id.et_description_details);
        PDate=findViewById(R.id.dp_prodDate_details);
        EDate=findViewById(R.id.dp_expDate_details);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkHandler networkHandler=new NetworkHandler(getApplicationContext());
                networkHandler.deleteProduct(new Product(intent.getStringExtra(PID)));
                process_action.setVisibility(View.VISIBLE);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkHandler networkHandler=new NetworkHandler(getApplicationContext());
                String []photo=new String[2];
                photo[0]="some NAmes";
                Product product=new Product(intent.getStringExtra(PID),name.getText().toString(),desc.getText().toString(),
                        PDate.getText().toString(),EDate.getText().toString(),photo);

                image.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                product.setImage(bitmap);
                networkHandler.updateProduct(product);
                process_action.setVisibility(View.VISIBLE);

            }
        });

        findViewById(R.id.fbtn_editProduct_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canEdit = !canEdit;
                name.setEnabled(!name.isEnabled());
                desc.setEnabled(!desc.isEnabled());
                update.setEnabled(!update.isEnabled());
            }
        });


        findViewById(R.id.iv_expDatePicker_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canEdit) {
                    pickDate((TextView) findViewById(R.id.dp_expDate_details));
                } else {
                    Toast.makeText(Details.this, "Make Sure The Edit Button Is Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.iv_prodDatePicker_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canEdit) {
                    pickDate((TextView) findViewById(R.id.dp_prodDate_details));

                } else {
                    Toast.makeText(Details.this, "Make Sure The Edit Button Is Clicked", Toast.LENGTH_SHORT).show();
                }
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


            if (extras != null) {
                //Get image
                from_gallrey=true;
                Glide.with(this)
                        .load(extras)
                        .error(R.drawable.error404)
                        .centerCrop()
                        .into(image);

            }
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
