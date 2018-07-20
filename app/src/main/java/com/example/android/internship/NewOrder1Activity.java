package com.example.android.internship;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class NewOrder1Activity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mDate;
    private EditText mAddress;
    private EditText mSlot;
    private EditText mService;

    private Button placeOrder;

    private order newOrder;

    private Spinner mTimeSlotSpinner;
    private String timeSlot;

    private Spinner mQuantitySpinner;
    private String quantity;

    private TextView dateCalendar;
    private ImageView calendar;
    Calendar mCurrentDate;
    int day, month, year;
    private String date;

    private ImageView machine;
    private ImageView iron;
    private String service;

    private int deliveryDate, deliveryMonth, deliveryYear;

    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mMessagesDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order1);

        ///mUsername = (EditText)findViewById(R.id.username);
        //mDate = (EditText)findViewById(R.id.date);
        mAddress = (EditText)findViewById(R.id.address);
        //mSlot = (EditText)findViewById(R.id.time_slot);
        //mService = (EditText)findViewById(R.id.service);

        placeOrder = (Button)findViewById(R.id.place_order);
        newOrder = new order();

        mTimeSlotSpinner = (Spinner)findViewById(R.id.spinner_time_slot);

        mQuantitySpinner = (Spinner)findViewById(R.id.spinner_quantity);

        machine = (ImageView) findViewById(R.id.machine);
        iron = (ImageView)findViewById(R.id.iron);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("order");

        calendar = (ImageView)findViewById(R.id.calendar);
        dateCalendar = (TextView) findViewById(R.id.date_calendar);
        mCurrentDate = Calendar.getInstance();

        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        month = month+1;
        dateCalendar.setText(day+"/"+month+"/"+year);


        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewOrder1Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int months, int dayOfMonth) {
                        months = months+1;
                        dateCalendar.setText(dayOfMonth+"/"+months+"/"+year);

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        date = calculateDeliveryDate();

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place_order();
                //finish();
                Intent i = new Intent(NewOrder1Activity.this,OrderSummaryActivity.class);
                i.putExtra("DeliveryDate",date);
                startActivity(i);
            }
        });

        machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                machine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                service = "machine";
            }
        });

        iron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iron.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                service = "iron";
            }
        });

        setupTimeSlotSpinner();
        setupQuantitySpinner();
        date = calculateDeliveryDate();
    }

    public void place_order(){
        newOrder.setQuantity(quantity);
        newOrder.setAddress(mAddress.getText().toString());
        newOrder.setDate(dateCalendar.getText().toString());
        newOrder.setService(service);
        newOrder.setSlot(timeSlot);
        mMessagesDatabaseReference.push().setValue(newOrder);

    }

    private void setupTimeSlotSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter timeSlotSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.time_slots_options,
                 android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        timeSlotSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mTimeSlotSpinner.setAdapter(timeSlotSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mTimeSlotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                    if (selection.equals("10-12")) {
                        timeSlot = "10-12";
                    } else if (selection.equals("1-3")) {
                        timeSlot = "1-3";
                    } else {
                        timeSlot ="5-7" ;
                    }

            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupQuantitySpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter quantitySpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.quantity_options,
                android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        quantitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mQuantitySpinner.setAdapter(quantitySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (selection.equals("1-20")) {
                    quantity = "1-20";
                } else if (selection.equals("20-40")) {
                    quantity = "20-40";
                } else {
                    quantity ="40+" ;
                }

            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String calculateDeliveryDate(){
        String delivery;
        if(quantity != null){
            if (quantity.equals("1-20") ){
                //2 days
                if (day==31){
                    deliveryDate = 2;
                }
                else if (day == 29 ){
                    deliveryDate = 1;
                }
                else{
                    deliveryDate = day +2;
                }
            }
        }


        deliveryMonth = month;
        deliveryYear = year;

        delivery = String.valueOf(deliveryDate) + "/" +String.valueOf(deliveryMonth) + "/" + String.valueOf(deliveryYear);
        return delivery;
    }

}
