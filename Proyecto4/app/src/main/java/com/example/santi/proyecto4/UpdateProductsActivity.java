package com.example.santi.proyecto4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import DATA.Retrofit.Util;
import DOMAIN.Product;
import UTIL.RESTService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProductsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnUpdateProduct;
    private EditText etName;
    private EditText etCategory;
    private EditText etPrice;
    private EditText etQuantity;
    private EditText etStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }//OnCreate

    private void init(){
        this.btnUpdateProduct = (Button) findViewById(R.id.btnUIpdateProduct);
        this.btnUpdateProduct.setOnClickListener(this);
        this.etName = (EditText) findViewById(R.id.etUpdateNameProduct);
        this.etCategory = (EditText) findViewById(R.id.etCategory);
        this.etPrice = (EditText) findViewById(R.id.etUpdatePriceProduct);
        this.etQuantity = (EditText) findViewById(R.id.etUpdateAmauntProduct);
        this.etStatus = (EditText) findViewById(R.id.etStatus);
    }//init

    @Override
    public void onClick(View v) {
        if(v.getId() == this.btnUpdateProduct.getId()){
            RESTService restService = Util.getRestService();

            Product product = new Product();
            product.setId("2");
            product.setNameProduct(etName.getText().toString());
            product.setCategoryProduct(etCategory.getText().toString());
            product.setPriceProduct(Integer.parseInt(etPrice.getText().toString()));
            product.setAmountProduct(Integer.parseInt(etQuantity.getText().toString()));
            product.setStateProduct(etStatus.getText().toString());

            try{
                Call<Integer> listCall = restService.deleteProduct(product);
                listCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.isSuccessful()) {
                            if(response.body() == 1){
                                Toast.makeText(UpdateProductsActivity.this,"Product successfully updated",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(UpdateProductsActivity.this,"Wrong data!",Toast.LENGTH_SHORT).show();
                            }//else-if
                        }//if
                    }//onResponse

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(UpdateProductsActivity.this,"Error 01: "+t.toString(),Toast.LENGTH_SHORT).show();
                    }//onFailure
                });
                etName.setText("");
                etCategory.setText("");
                etPrice.setText("");
                etQuantity.setText("");
                etStatus.setText("");
            }catch(Exception e){
                Toast.makeText(this,"Error 02: "+e.toString(),Toast.LENGTH_SHORT).show();
            }//try-catch
       }//if
    }//onClick
}//class
