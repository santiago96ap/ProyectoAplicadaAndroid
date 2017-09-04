package com.example.santi.proyecto4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DATA.Retrofit.Util;
import DOMAIN.Client;
import DOMAIN.Product;
import UTIL.RESTService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductsActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etSearchProduct;
    private Button btnSearchProduct;
    private EditText etShowProduct;
    private ArrayList<String> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }//OnCreate

    private void init(){
        this.etSearchProduct = (EditText) findViewById(R.id.etSearchProducts);
        this.etShowProduct = (EditText) findViewById(R.id.etShowTextProduct);
        this.btnSearchProduct = (Button) findViewById(R.id.btnSearchProducts);
        this.btnSearchProduct.setOnClickListener(this);

    }//init

    @Override
    public void onClick(View v) {
        if(v.getId() == this.btnSearchProduct.getId()){
            final String nameP = etSearchProduct.getText().toString();
            RESTService restService = Util.getRestService();
            try{
                Call<List<Product>> listCallC = restService.getProduct();
                listCallC.enqueue(new Callback<List<Product>>() {

                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if(response.isSuccessful()) {
                            for (int i = 0 ; i < response.body().size(); i++){
                                if(response.body().get(i).getNameProduct().toString().contains(nameP)){
                                    products.add(response.body().get(i).getNameProduct() + "\nCategory: " +
                                            response.body().get(i).getCategoryProduct() + "\nPrice: ¢" +
                                            response.body().get(i).getPriceProduct());
                                }//if
                            }//for
                            showProducts();
                        }//if
                    }//onResponse

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(SearchProductsActivity.this,"Error 01: "+t.toString(),Toast.LENGTH_SHORT).show();
                    }//onFailure
                });
                etSearchProduct.setText("");
            }catch(Exception e){
                Toast.makeText(this,"Error 02: "+e.toString(),Toast.LENGTH_SHORT).show();
            }//try-catch
        }//if
    }//onClick

    public void showProducts(){
        for (int i = 0 ; i < products.size(); i++){
            etShowProduct.setText(etShowProduct.getText() + products.get(i) + "\n");
        }//for
    }//showProducts

}//class
