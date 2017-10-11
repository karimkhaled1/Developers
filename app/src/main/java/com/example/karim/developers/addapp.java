package com.example.karim.developers;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class addapp extends AppCompatActivity {
EditText Package;
    EditText url;
    ListView listView;
ArrayList<aplication> apps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addapp);
        Package= (EditText) findViewById(R.id.Package);
        url= (EditText) findViewById(R.id.URl);
        getmyapps();
         listView=(ListView)findViewById(R.id.listmyapps);
        myappsAdapter adapter=new myappsAdapter(getApplicationContext(),apps);
        listView.setAdapter(adapter);
    }

    public void add(View v) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
        aplication app=new aplication(Package.getText().toString().trim(),url.getText().toString().trim(),SignIn.user.getUid());
        database.child(SignIn.user.getUid()).child("aplication").child(app.getPackageName().replace(".","")).setValue(app);
        apps.add(app);
    }
public void getmyapps(){
    apps=new ArrayList<>();
    DatabaseReference db=FirebaseDatabase.getInstance().getReference("users");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                if(snap.getKey().equals(SignIn.user.getUid())) {
                    for (DataSnapshot snap2 : snap.child("aplication").getChildren()) {
                        aplication app = snap2.getValue(aplication.class);
                        apps.add(app);

                    }
                }
            }

        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
        }
    });
}
}