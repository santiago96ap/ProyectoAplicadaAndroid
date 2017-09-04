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

public class ReportUserActivity extends AppCompatActivity implements View.OnClickListener {

    private GridView gvShowClient;
    private Button btnSearchClientReport;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }//OnCreate

    private void init(){
        this.gvShowClient = (GridView) findViewById(R.id.gvReportUser);
        this.gvShowClient.setNumColumns(4);
        this.btnSearchClientReport = (Button) findViewById(R.id.btnSearchClientReport);
        this.btnSearchClientReport.setOnClickListener(this);
    }//init

    private void send(){//Este método lo que realiza es llena un gridview con los datos que vienen desde el WFC
        String [] chain = {"Client", "Product", "Quantity", "Total",
                "mcalvo09@", "camisa", "1", "1500",
                "francarcr22@","Sueta","3", "9000",
                "ajohan16@", "Camiseta", "2", "4000",
                "kevinvarp18@", "Vestimenta", "1", "3000"
        };
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chain);
        this.gvShowClient.setAdapter(this.adapter);
    }//send

    @Override
    public void onClick(View v) {
        if(v.getId() == this.btnSearchClientReport.getId()){
            send();
        }//btnSearchClientReport

    }//onClicck
}//class
