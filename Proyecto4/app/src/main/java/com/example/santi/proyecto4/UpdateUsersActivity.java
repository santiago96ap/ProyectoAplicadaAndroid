package com.example.santi.proyecto4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import DATA.Retrofit.Util;
import DOMAIN.Client;
import DOMAIN.Product;
import UTIL.RESTService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUsersActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnUpdateUser;
    private EditText etName;
    private EditText etEmail;
    private EditText etPass;
    private EditText etNumberCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }//onCreate

    private void init(){
        this.btnUpdateUser = (Button) findViewById(R.id.btnUpdateUser);
        this.btnUpdateUser.setOnClickListener(this);
        this.etName = (EditText) findViewById(R.id.etUpdateNameUser);
        this.etEmail = (EditText) findViewById(R.id.etUpdateMailUser);
        this.etPass = (EditText) findViewById(R.id.etUpdatePassUser);
        this.etNumberCard = (EditText) findViewById(R.id.etUpdateNumberCardUser);
    }//init

    @Override
    public void onClick(View v) {
        if(v.getId() == this.btnUpdateUser.getId()){
            RESTService restService = Util.getRestService();

            Client client= new Client();
            client.setId("2");
            client.setName(etName.getText().toString());
            client.setMail(etEmail.getText().toString());
            client.setPass(etPass.getText().toString());
            client.setCard(etNumberCard.getText().toString());

            try{
                Call<Integer> listCall = restService.sendClient(client);
                listCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.isSuccessful()) {
                            if(response.body() == 1){
                                Toast.makeText(UpdateUsersActivity.this,"Client successfully updated",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(UpdateUsersActivity.this,"Wrong data!",Toast.LENGTH_SHORT).show();
                            }//else-if
                        }//if
                    }//onResponse

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(UpdateUsersActivity.this,"Error 01: "+t.toString(),Toast.LENGTH_SHORT).show();
                    }//onFailure
                });
                etName.setText("");
                etPass.setText("");
                etEmail.setText("");
                etNumberCard.setText("");
            }catch(Exception e){
                Toast.makeText(this,"Error 02: "+e.toString(),Toast.LENGTH_SHORT).show();
            }//try-catch
        }//if
    }//onClick
}//class
