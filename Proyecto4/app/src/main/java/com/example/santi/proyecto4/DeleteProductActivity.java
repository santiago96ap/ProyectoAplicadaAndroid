package com.example.santi.proyecto4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import DATA.Retrofit.Util;
import DOMAIN.Client;
import DOMAIN.Product;
import UTIL.RESTService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteProductActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView lvProducts;
    private Button btnDeleteProduct;
    private ArrayAdapter<String> adapter;
    private List<Product> respuesta;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = "";
        RESTService restService = Util.getRestService();


        Call<List<Product>> lista = restService.getProduct();

        lista.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if(response.isSuccessful()){
                    respuesta = response.body();
                    initAdapter();
                }//if

            }//onResponse

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(DeleteProductActivity.this,"Error 01: "+t.toString(),Toast.LENGTH_SHORT).show();
            }//onFailure
        });
        init();

    }//onCreate

    private void initAdapter(){
        String [] options = new String[respuesta.size()];
        for (int i = 0 ; i < respuesta.size(); i++){
            options[i]= respuesta.get(i).getNameProduct();
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,options);
        lvProducts.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void init(){
        this.btnDeleteProduct = (Button) findViewById(R.id.btnDeleteProduct);
        this.btnDeleteProduct.setOnClickListener(this);
        this.lvProducts = (ListView) findViewById(R.id.lvProducts);
        //this.lvProducts.setOnClickListener(this);
        this.lvProducts.setOnItemClickListener(this);


    }//init

    @Override
    public void onClick(View v) {
        if(v.getId() == this.btnDeleteProduct.getId()){
            RESTService restService = Util.getRestService();

                Product product = new Product();
                product.setId("1");
                product.setNameProduct(this.name);

                try{
                    Call<Integer> listCall = restService.deleteProduct(product);
                    listCall.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if(response.isSuccessful()) {
                                if(response.body() == 1){
                                    Toast.makeText(DeleteProductActivity.this,"Product successfully removed",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(DeleteProductActivity.this,"Wrong data!",Toast.LENGTH_SHORT).show();
                                }//else-if
                            }//if
                        }//onResponse

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Toast.makeText(DeleteProductActivity.this,"Error 01: "+t.toString(),Toast.LENGTH_SHORT).show();
                        }//onFailure
                    });
                    //   loadJSON();
                }catch(Exception e){
                    Toast.makeText(this,"Error 02: "+e.toString(),Toast.LENGTH_SHORT).show();
                }

            }//if

    }//onClick

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        name = respuesta.get(position).getNameProduct();
        Toast.makeText(getApplicationContext(),respuesta.get(position).getNameProduct(),Toast.LENGTH_SHORT).show();

    }
}//class
