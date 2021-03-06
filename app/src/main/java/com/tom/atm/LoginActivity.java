package com.tom.atm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CAMERA = 5;
    private static final String TAG =LoginActivity.class.getSimpleName() ;
    private EditText edUserid;
    private EditText edPasswd;
    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //camera();
        findViews();
        new TestTask().execute("http://tw.yahoo.com");
    }

    public class TestTask extends AsyncTask<String,Void,Integer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            //Toast
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d(TAG, "onPostExecute: ");
            //Toast
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int data = 0;
            try {
                URL url = new URL(strings[0]);
                data = url.openStream().read();
                Log.d(TAG, "TestTask: " + data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }
    }

    private void findViews() {
        edUserid = findViewById(R.id.userid);
        edPasswd = findViewById(R.id.passwd);
        cbRemember = findViewById(R.id.cb_rem_userid);
        cbRemember.setChecked(getSharedPreferences("atm",MODE_PRIVATE)
                .getBoolean("REMEMBER_USERID",false));
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedPreferences("atm",MODE_PRIVATE)
                        .edit()
                        .putBoolean("REMEMBER_USERID",isChecked)
                        .apply();
            }
        });
        String userid=getSharedPreferences("atm",MODE_PRIVATE)
                .getString("USERID","");
        edUserid.setText(userid);
    }

    private void camera() {
        int permission= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permission== PackageManager.PERMISSION_GRANTED){
            //takePhoto();
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_CAMERA){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takePhoto();
            }
        }
    }

    private void takePhoto() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    public void login(View view){
        final String userid=edUserid.getText().toString();
        final String passwd=edPasswd.getText().toString();
        FirebaseDatabase.getInstance().getReference("users").child(userid).child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String pw= (String) dataSnapshot.getValue();
                        if(pw.equals(passwd)){
                            boolean remember=getSharedPreferences("atm",MODE_PRIVATE)
                                    .getBoolean("REMEMBER_USERID",false);
                            if(remember==true) {
                                getSharedPreferences("atm", MODE_PRIVATE)
                                        .edit()
                                        .putString("USERID", userid)
                                        .apply();
                            }else{
                                getSharedPreferences("atm", MODE_PRIVATE)
                                        .edit()
                                        .putString("USERID", "")
                                        .apply();
                            }
                            setResult(RESULT_OK);
                            finish();
                        }else{
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("登入結果")
                                    .setMessage("登入失敗")
                                    .setPositiveButton("OK",null)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//        if ("jack".equals(userid)&&"1234".equals(passwd)){
//            setResult(RESULT_OK);
//            finish();
//        }

    }

    public void quit(View view){
        finish();
    }
}
