package com.example.android.internship;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MyOrdersActivity extends AppCompatActivity {

    private ListView listView;
    ArrayList<order> orderList;

    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mMessagesDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("order");
        mMessagesDatabaseReference.keepSynced(true);

        mMessagesDatabaseReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        orderList = new ArrayList<order>();
                        for (DataSnapshot snap: dataSnapshot.getChildren()) {

                            orderList.add(snap.getValue(order.class));
                            Collections.reverse(orderList);
                            OrderAdapter adapter = new OrderAdapter(MyOrdersActivity.this,orderList);

                            listView.setStackFromBottom(false);
                            listView.setAdapter(adapter);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }
}
