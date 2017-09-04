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
import android.widget.GridView;

public class ReportProductActivity extends AppCompatActivity implements View.OnClickListener{

    private GridView gvShowProducts;
    private Button btnSearchProduct;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_procduct);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }//onCreate

    private void init(){
        this.gvShowProducts = (GridView) findViewById(R.id.gvProductsReport);
        this.gvShowProducts.setNumColumns(4);
        this.btnSearchProduct = (Button) findViewById(R.id.btnSearchProductReport);
        this.btnSearchProduct.setOnClickListener(this);
    }//init

    private void send(){//Este método lo que realiza es llena un gridview con los datos que vienen desde el WS
        String[] chain= {"Income", "Expenses", "Start date", "End date",
                "1500", "2000", "02-08-2017", "02-09-2017",
                "20000", "10000", "02-07-2017", "02-08-2017",
                "5000", "3000", "02-06-2017", "02-07-2017",
                "9000","2000", "02-05-2017", "02-06-2017",
                "1000", "0", "02-04-2017", "02-05-2017"};
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,chain);
        this.gvShowProducts.setAdapter(this.adapter);
    }//send

    @Override
    public void onClick(View v) {
        if(v.getId() == this.btnSearchProduct.getId()){
            send();
        }//this.btnSearchProduct
    }//onClick
}//class
