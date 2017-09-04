package com.example.santi.proyecto4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import DATA.Retrofit.Util;
import DOMAIN.Product;
import UTIL.RESTService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterProductActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etCategory;
    private EditText etPrice;
    private EditText etAmount;
    private EditText etStatus;
    private Button btnRegisterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }//onCreate
    private void init(){
        this.etName = (EditText) findViewById(R.id.etNameRegisterProduct);
        this.etCategory = (EditText) findViewById(R.id.etCategory);
        this.etPrice = (EditText) findViewById(R.id.etPriceRegisterProduct);
        this.etAmount = (EditText) findViewById(R.id.etAmountRegisterProduct);
        this.etStatus = (EditText) findViewById(R.id.etStatus);
        this.btnRegisterProduct = (Button) findViewById(R.id.btnRegisterProduct);
        this.btnRegisterProduct.setOnClickListener(this);
    }//init

    @Override
    public void onClick(View v) {
        if(v.getId() == this.btnRegisterProduct.getId()){
            RESTService restService = Util.getRestService();

            Product product = new Product();
            product.setId("3");
            product.setNameProduct(etName.getText().toString());
            product.setCategoryProduct(etCategory.getText().toString());
            product.setPriceProduct(Integer.parseInt(etPrice.getText().toString()));
            product.setAmountProduct(Integer.parseInt(etAmount.getText().toString()));
            product.setStateProduct(etStatus.getText().toString());

            try{
                Call<Integer> listCall = restService.deleteProduct(product);
                listCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.isSuccessful()) {
                            if(response.body() == 1){
                                Toast.makeText(RegisterProductActivity.this,"Product successfully registered",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegisterProductActivity.this,"Wrong data!",Toast.LENGTH_SHORT).show();
                            }//else-if
                        }//if
                    }//onResponse

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(RegisterProductActivity.this,"Error 01: "+t.toString(),Toast.LENGTH_SHORT).show();
                    }//onFailure
                });
                etName.setText("");
                etCategory.setText("");
                etPrice.setText("");
                etAmount.setText("");
                etStatus.setText("");
            }catch(Exception e){
                Toast.makeText(this,"Error 02: "+e.toString(),Toast.LENGTH_SHORT).show();
            }//try-catch
        }//if

    }//onClick10
}//class
