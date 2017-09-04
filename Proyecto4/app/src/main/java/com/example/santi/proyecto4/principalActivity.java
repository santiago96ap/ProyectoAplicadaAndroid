package com.example.santi.proyecto4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.File;
import java.util.List;

import DATA.Retrofit.Util;
import DOMAIN.Product;
import UTIL.RESTService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class principalActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgViewNEXT;
    private ImageView imgViewPrevius;
    private TextSwitcher textSwitcher;
    private String [] texts;
    private int chainLength;
    private int index=0;
    private List<Product> productos;
    private String userS;
    private String type;
    private int consumeS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        obtenerProductos();
        userS = getIntent().getStringExtra("userS");
        type = getIntent().getStringExtra("type");
        init();
    }//onCreate

    public void init(){
        this.imgViewPrevius = (ImageView) findViewById(R.id.imgPREVIOUS);
        this.imgViewPrevius.setOnClickListener(this);
        this.imgViewNEXT = (ImageView) findViewById(R.id.imgNEXT);
        this.imgViewNEXT.setOnClickListener(this);
        this.textSwitcher = (TextSwitcher) findViewById(R.id.twProducts);
    }//init

    public void obtenerProductos(){
        RESTService restService = Util.getRestService();
        try{

            Call<List<Product>> lista = restService.getProduct();

            lista.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                    if(response.isSuccessful()){
                        productos = response.body();
                        initAdapter();
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Toast.makeText(principalActivity.this,"Error 01: "+t.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e){
            Toast.makeText(this,"Error 02: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    private void initAdapter(){
        int dollar;
        texts = new String[productos.size()];
        for (int i = 0 ; i < productos.size(); i++){
            dollar = callConsume(productos.get(i).getPriceProduct());
            texts[i]= productos.get(i).getNameProduct() + "\nCategory: " + productos.get(i).getCategoryProduct() + "\nPrice: ¢" + productos.get(i).getPriceProduct() + "\nPrice: $" + dollar+ "\n\nQuantity Available: " + productos.get(i).getAmountProduct();
        }
        chainLength = texts.length;
        loadAnimation();
        setFactory();
        this.textSwitcher.setText(texts[0]);
    }

    public int callConsume(int precie){
        RESTService restService = Util.getRestService();
        try{

            Call<Integer> lista = restService.getCosume();

            lista.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.isSuccessful()){
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                }
            });
        }catch(Exception e){
            return precie / 570;
        }
        return precie / 570;
    }//callConsume

    private void loadAnimation(){
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        this.textSwitcher.setInAnimation(in);
        this.textSwitcher.setOutAnimation(out);
    }//loadAnimation

    private void setFactory(){
        this.textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(principalActivity.this);
                textView.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(25);
                textView.setTextColor(Color.BLACK);
                return  textView;
            }//MakeView
        });
    }//setFactory


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(type.equals("M")){
            getMenuInflater().inflate(R.menu.menu_usuario,menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_client,menu);
        }
        return true;
    }// onCreateOptionsMenu

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.itActualizarContacto:
                intent = new Intent(getApplicationContext(),UpdateUsersActivity.class);
                intent.putExtra("userS", this.userS);
                startActivity(intent);return true;
            case R.id.itRegistrarProducto:
                intent = new Intent(getApplicationContext(),RegisterProductActivity.class);
                intent.putExtra("userS", this.userS);
                startActivity(intent);return true;
            case R.id.itActualizarProducto:
                intent = new Intent(getApplicationContext(),UpdateProductsActivity.class);
                intent.putExtra("userS", this.userS);
                startActivity(intent);return true;
            case R.id.itBuscarProducto:
                intent = new Intent(getApplicationContext(),SearchProductsActivity.class);
                intent.putExtra("userS", this.userS);
                startActivity(intent);return true;
            case R.id.itEliminarProducto:
                intent = new Intent(getApplicationContext(),DeleteProductActivity.class);
                intent.putExtra("userS", this.userS);
                startActivity(intent);return true;
            case R.id.itReporteClientes:
                intent = new Intent(getApplicationContext(),ReportUserActivity.class);
                intent.putExtra("userS", this.userS);
                startActivity(intent);return true;
            case R.id.itReporteProductos:
                intent = new Intent(getApplicationContext(),ReportProductActivity.class);
                intent.putExtra("userS", this.userS);
                startActivity(intent);return true;
            case R.id.itCerrarSesion:
                cerrarSesion();
            default:
                return super.onOptionsItemSelected(item);
        }//switch
    }//onOptionsItemSelected

    private void nextSlide(){
        this.index ++;
        if (this.index == this.chainLength){
            this.index = 0;
        }//if
        this.textSwitcher.setText(texts[index]);
    }//nextSliede

    private void cerrarSesion(){
        File dir = getFilesDir();
        File file = new File(dir, "usuarios.txt");
        boolean deleted = file.delete();
        if(deleted)
            finish();
    }//Fin del método cerrarSesion.

    private void previusSlide(){
        this.index --;
        if (this.index < 0){
            this.index = chainLength - 1;
        }//if
        this.textSwitcher.setText(texts[index]);
    }//nextSliede

    @Override
    public void onClick(View v) {

        if(v.getId() == this.imgViewNEXT.getId()){
            nextSlide();
        }else if(v.getId() == this.imgViewPrevius.getId()){
            previusSlide();
        }//else-if

    }//onClick
}
