package com.example.android.internship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderStatusActivity extends AppCompatActivity {

    private TextView quantity;
    private TextView timeSlot;
    private TextView Service;
    private TextView date;
    private TextView adrress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        quantity = (TextView)findViewById(R.id.quantity_detail);
        timeSlot = (TextView)findViewById(R.id.time_slot_detail);
        Service = (TextView)findViewById(R.id.service_detail);
        date = (TextView)findViewById(R.id.date_detail);
        adrress = (TextView)findViewById(R.id.address_detail);

        order this_order = new order();
        this_order = getIntent().getParcelableExtra("Detail_Order");
        quantity.setText(this_order.getQuantity());
        timeSlot.setText(this_order.getSlot());
        Service.setText(this_order.getService());
        date.setText(this_order.getDate());
        adrress.setText(this_order.getAddress());
    }
}
