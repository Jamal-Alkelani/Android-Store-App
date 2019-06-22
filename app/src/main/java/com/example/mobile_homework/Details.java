package com.example.mobile_homework;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.*;
import org.w3c.dom.Text;

public class Details extends AppCompatActivity {

    private boolean canEdit=false;
    private EditText name;
    private EditText desc;
    private Button update,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name=findViewById(R.id.et_name_details);
        desc=findViewById(R.id.et_description_details);
        name.setEnabled(false);
        desc.setEnabled(false);

        update=findViewById(R.id.btn_update);
        delete=findViewById(R.id.btn_delete);
        update.setEnabled(false);

        findViewById(R.id.fbtn_editProduct_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canEdit=!canEdit;
                name.setEnabled(!name.isEnabled());
                desc.setEnabled(!desc.isEnabled());
                update.setEnabled(!update.isEnabled());
            }
        });


        findViewById(R.id.iv_expDatePicker_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canEdit){
                    pickDate((TextView) findViewById(R.id.dp_expDate_details));
                }else{
                    Toast.makeText(Details.this, "Make Sure The Edit Button Is Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.iv_prodDatePicker_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canEdit){
                    pickDate((TextView) findViewById(R.id.dp_prodDate_details));

                }else{
                    Toast.makeText(Details.this, "Make Sure The Edit Button Is Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
