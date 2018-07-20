package com.example.android.internship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderSummaryActivity extends AppCompatActivity {

    private Button done;
    private TextView deliveryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("DeliveryDate");
        Log.e("delivery date",message);

        deliveryDate = (TextView)findViewById(R.id.delivery_date);
        deliveryDate.setText(message);

        done = (Button)findViewById(R.id.done_order);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderSummaryActivity.this,ThankYouActivity.class);
                startActivity(i);
            }
        });
    }
}
