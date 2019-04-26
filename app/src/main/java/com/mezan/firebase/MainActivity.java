package com.mezan.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
Button sendData;
Firebase mRootRef;
EditText textName,textUser,textPassword;
//TextView rData;
ArrayList<String> mArray=new ArrayList<>();
ListView listView;
    ArrayAdapter<String> adapter;

static int serial=0;
    int res = 0;
//String data = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Firebase.setAndroidContext(this);
        //mRootRef = new Firebase("https://fir-eadda.firebaseio.com/USERs");
        sendData= findViewById(R.id.btnAdd);
        textName= findViewById(R.id.Ename);
        textUser= findViewById(R.id.Euser);
        textPassword= findViewById(R.id.Epass);
      //  rData=(TextView) findViewById(R.id.Rdata);
        listView= findViewById(R.id.list_item);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mArray);
        listView.setAdapter(adapter);

        //direct key access
        /*mRootRef = new Firebase("https://fir-eadda.firebaseio.com/Name");
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                rData.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(MainActivity.this,"Error "+ firebaseError.toString(),Toast.LENGTH_LONG).show();
            }
        });*/

        /* root to key access
        mRootRef = new Firebase("https://fir-eadda.firebaseio.com/");
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    //data += snapshot.getKey()+"\n";
                    for(DataSnapshot snapshot1:dataSnapshot.getChildren()){
                        data += snapshot1.getKey()+":"+snapshot1.getValue()+"\n\n";
                        rData.setText(data);
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
*/
            RetrieveData();
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  data="";
                //rData.setText(data);
                mRootRef = new Firebase("https://fir-eadda.firebaseio.com/USERs"+serial);
                Firebase mRefChild1=mRootRef.child("Name");
                Firebase mRefChild2=mRootRef.child("Username");
                Firebase mRefChild3=mRootRef.child("Password");

               // mRefChild.setValue("AKASH");
               try {
                   String name= textName.getText().toString();
                   String user= textUser.getText().toString();
                   String pass= textPassword.getText().toString();
                   mRefChild1.setValue(name);
                   mRefChild2.setValue(user);
                   mRefChild3.setValue(pass);
                   serial++;
               }catch (Exception e){
                   e.printStackTrace();
               }

            }
        });

    }

    public void RetrieveData(){
       // Firebase ref = new Firebase("https://fir-eadda.firebaseio.com/"); tag:1
        //Firebase ref = new Firebase("https://fir-eadda.firebaseio.com/USERs0"); tag:2
      //  Firebase ref = new Firebase("https://fir-eadda.firebaseio.com/"); //tag:3



//tag 4 way to retrieve data
        for(int i=0;i<3;i++){
            Firebase ref = new Firebase("https://fir-eadda.firebaseio.com/USERs"+i);
            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //String value=dataSnapshot.getKey();
                    String value=dataSnapshot.getValue(String.class);//a single object full data set
                    mArray.add(value);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }

       /* ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //data+=dataSnapshot.getChildrenCount();
                //tag 1 away
                // long a=dataSnapshot.getChildrenCount();
                // String val=String.valueOf(a);
                //   res =Integer.parseInt(val);
                *//*for(int i=0;i<res;i++){
                    String root = "USERs"+i;
                    data+=dataSnapshot.child(root).child("Name").getValue(String.class)+"\n";
                    data+=dataSnapshot.child(root).child("Username").getValue(String.class)+"\n";
                    data+=dataSnapshot.child(root).child("Password").getValue(String.class)+"\n\n";
                }*//*
                *//*
                tag 2 way
                Map<String,String> map = dataSnapshot.getValue(Map.class);
                String name=map.get("Name");
                String uname=map.get("Username");
                String pass=map.get("Password");
                data = name+"\n"+uname+"\n"+pass+"\n";*//*

                //tag 3 way
                long a=dataSnapshot.getChildrenCount();
                String val=String.valueOf(a);
                int res=Integer.parseInt(val);
                for(int i=0;i<res;i++){
                    String root = "USERs"+i;
                    Map<String,String>map = dataSnapshot.child(root).getValue(Map.class);
                    String name=map.get("Name");
                    String uname=map.get("Username");
                    String pass=map.get("Password");
                    mArray.add(uname);
                    adapter.notifyDataSetChanged();
                    // data += name+"\n"+uname+"\n"+pass+"\n";
                }
                 rData.setText(data);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
*/


    }
}
