package com.example.santi.proyecto4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import DATA.Retrofit.ClientRetrofit;
import DATA.Retrofit.Util;
import DOMAIN.Client;
import UTIL.RESTService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRegisterUser;
    private EditText etNameUser;
    private EditText etMailUser;
    private EditText etPass;
    private EditText etCardNumberUser;

    private ClientRetrofit clientRetrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }//constructor

    private void init(){
        this.etNameUser = (EditText) findViewById(R.id.etNombre);
        this.btnRegisterUser = (Button) findViewById(R.id.btnRegistrarUsuario);
        this.etMailUser = (EditText) findViewById(R.id.etEmail);
        this.etCardNumberUser = (EditText) findViewById(R.id.etTarjetaRegistrar);
        this.etPass = (EditText) findViewById(R.id.etPass);
        this.btnRegisterUser.setOnClickListener(this);

    }//init

    @Override
    public void onClick(View v) {
        if(v.getId() == this.btnRegisterUser.getId()){
            RESTService restService = Util.getRestService();
            if(!etNameUser.getText().toString().equals("") && !etMailUser.getText().toString().equals("") &&
                  !etPass.getText().toString().equals("") && !etCardNumberUser.getText().toString().equals("")){
                Client client = new Client();
                client.setId("1");
                client.setName(etNameUser.getText().toString());
                client.setMail(etMailUser.getText().toString());
                client.setPass(etPass.getText().toString());
                client.setCard(etCardNumberUser.getText().toString());

             try{
                 Call<Integer> listCall = restService.sendClient(client);
                 listCall.enqueue(new Callback<Integer>() {
                     @Override
                     public void onResponse(Call<Integer> call, Response<Integer> response) {
                         if(response.isSuccessful()) {
                             if(response.body() == 1){
                                 Toast.makeText(RegisterUserActivity.this,"Successful registration!",Toast.LENGTH_SHORT).show();
                             }else{
                                 Toast.makeText(RegisterUserActivity.this,"Error! Mail already entered.",Toast.LENGTH_SHORT).show();
                             }//else-if
                         }//if
                     }//onResponse

                     @Override
                     public void onFailure(Call<Integer> call, Throwable t) {
                         Toast.makeText(RegisterUserActivity.this,"Error 01: "+t.toString(),Toast.LENGTH_SHORT).show();
                     }//onFailure
                 });
                 etNameUser.setText("");
                 etPass.setText("");
                 etMailUser.setText("");
                 etCardNumberUser.setText("");
             }catch(Exception e){
                 Toast.makeText(this,"Error 02: "+e.toString(),Toast.LENGTH_SHORT).show();
             }

            }//if
        }//btnRegisterUser

    }//onClick

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
    }//showResponse

}//class

