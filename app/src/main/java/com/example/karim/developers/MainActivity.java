package com.example.karim.developers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.x;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener{
    private RewardedVideoAd mad;
    ArrayList<objectForsql> downloaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getsql();
        isPackageInstalled();
        if(downloaded==null){
            return;
        }

        ListView listview= (ListView) findViewById(R.id.listview);
        DownloadAdapter adapter=new DownloadAdapter(getApplicationContext(),downloaded);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(downloaded.get(i).getPackage());
                if(downloaded.get(i).getDownloaded().equals("0")){
                    SQLiteController sqlite=new SQLiteController();
                    sqlite.open(getApplicationContext());
                    sqlite.update(downloaded.get(i));
                    sqlite.close();
                    updatePointsAndDownloads(i);
                }
                if (launchIntent != null) {
                    incPointsOnOpen(i);
                    startActivity(launchIntent);
                }
            }
        });
        mad= MobileAds.getRewardedVideoAdInstance(this);
        mad.setRewardedVideoAdListener(this);
        loadAd();
    }
    private void loadAd(){

            mad.loadAd("ca-app-pub-2346303530561672/6655421503",new AdRequest.Builder().build());

    }
    private void isPackageInstalled() {
        PackageManager pm = getApplicationContext().getPackageManager();
        int i=0;
        if(downloaded==null){
            return;
        }
        while (downloaded.size()!=i) {
            try {
                pm.getPackageInfo(downloaded.get(i).getPackage(), 0);
            } catch (Exception e) {
              downloaded.remove(i);
                i--;
            }
            i++;
        }
    }
    private void getsql(){
        downloaded= new ArrayList<objectForsql>();
        SQLiteController sqLiteController=new SQLiteController();
        sqLiteController.open(getApplicationContext());
        downloaded=sqLiteController.getAllData();
        sqLiteController.close();
    }
    public void addapp(View v){
        startActivity(new Intent(getApplicationContext(),addapp.class));
    }
    public void Download(View v){
        Query db=FirebaseDatabase.getInstance().getReference("users").orderByChild("points").limitToFirst(20);;
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                  if(snap.getKey().equals(SignIn.user.getUid())) {
                      continue;
                  }
                    for (DataSnapshot snap2 : snap.child("aplication").getChildren()) {
                        aplication app = snap2.getValue(aplication.class);
                        try{
                            PackageManager pm = getApplicationContext().getPackageManager();
                            pm.getPackageInfo(app.getPackageName(), 0);
                            continue;
                        }
                        catch (Exception e ) {
                            SQLiteController sqLiteController = new SQLiteController();
                            sqLiteController.open(getApplicationContext());
                            sqLiteController.insert(app);
                            sqLiteController.close();
                            downloaded.add(new objectForsql(app.getPackageName(),app.getUid(),"0"));
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(app.getUrl()));
                            startActivity(i);
                            break;
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
    public void incPointsOnOpen(int i){
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users")
                .child(SignIn.user.getUid()).child("points");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    int f = dataSnapshot.getValue(int.class);
                    databaseReference.setValue(++f);
                }
                catch (Exception e){
                    databaseReference.setValue(1);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        getsql();
        isPackageInstalled();
        loadAd();

    }
    public void showAd(View v){

        mad.show();
    }
    private void updatePointsAndDownloads(int i){
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users")
                .child(SignIn.user.getUid()).child("points");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    int f = dataSnapshot.getValue(int.class);
                    databaseReference.setValue(f+10);
                }
                catch (Exception e){
                    databaseReference.setValue(10);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("users")
                .child(downloaded.get(i).getUid()).child("aplication")
                .child(downloaded.get(i).getPackage().replace(".","")).child("downloads");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int f =dataSnapshot.getValue(int.class);
                db.setValue(++f);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference db2=FirebaseDatabase.getInstance().getReference().child("users")
                .child(downloaded.get(i).getUid()).child("points");
        db2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int f=dataSnapshot.getValue(int.class);
                db2.setValue(f-10);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        mad.show();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
    loadAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }
}
