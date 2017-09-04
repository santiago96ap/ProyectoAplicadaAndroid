package com.example.santi.proyecto4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import DATA.Retrofit.Util;
import DOMAIN.Client;
import DOMAIN.Manager;
import UTIL.RESTService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private Button btnRegistrarse;
    private EditText etPass;
    private EditText etUsuario;
    private Call<List<Client>> listCallC;
    private RESTService restService;
    private String mail;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }//constructor

    private void init(){
        obtenerDatos();
        this.btnLogin = (Button) findViewById(R.id.btnLogin);
        this.btnLogin.setOnClickListener(this);
        this.btnRegistrarse = (Button) findViewById(R.id.btnRegistrar);
        this.btnRegistrarse.setOnClickListener(this);
        this.etUsuario = (EditText) findViewById(R.id.etUsuario);
        this.etPass = (EditText) findViewById(R.id.etPass);
    }//init

    private String setDataPost(String email,String password){
        HttpURLConnection connection;
        String retorna = null;
        String datos=email+","+password;
        System.out.println(datos);
        try {
            URL url = new URL("http://rsaplenguajesucr.hol.es/Proyecto2Lenguajes/?controlador=Android&accion=login");
            String dato = "correo="+ URLEncoder.encode(datos,"UTF-8");
            String linea = "";
            int respuesta = 0;
            StringBuilder resultado = null;

            connection = (HttpURLConnection)url.openConnection();


            connection.setDoOutput(true);
            connection.setFixedLengthStreamingMode(dato.getBytes().length);

            connection.setRequestProperty("content-type","application/x-www-form-urlencoded");

            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            out.write(dato.getBytes());
            out.flush();
            out.close();

            //
            respuesta = connection.getResponseCode();
            resultado = new StringBuilder();
            if(respuesta == HttpURLConnection.HTTP_OK){
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while((linea=reader.readLine())!= null){
                    resultado.append(linea);
                }//End while((linea=reader.readLine())!= null)
                return resultado.toString();
            }//End if(respuesta == HttpURLConnection.HTTP_OK)

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }//End try-catch

        return retorna;
    }//End setDataPost

    private int getJson(String response){
        try{
            JSONArray jsonArray = new JSONArray(response);
            if(jsonArray.length() > 0){
                return 1;
            }//End If(jsonArray.length() > 0)
        }catch (Exception e){}
        return 0;
    }//End getJson


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == this.btnRegistrarse.getId()){
            Intent intent = new Intent(getApplicationContext(), RegisterUserActivity.class);
            startActivity(intent);
        }else if(v.getId() == this.btnLogin.getId()){
            if(!etUsuario.getText().toString().equals("") && !etPass.getText().toString().equals("")){
                mail = etUsuario.getText().toString();
                pass = etPass.getText().toString();
                etPass.setText("");
                etUsuario.setText("");
                validateS();
            }//if
        }//boton
    }//onClick

    public void validateS(){
        restService = Util.getRestService();
        Manager manager = new Manager();
        manager.setName(mail);
        manager.setPass(pass);

        try{
            Call<Boolean> listCall = restService.sendManager(manager);
            listCallC = restService.getClient();
            listCall.enqueue(new Callback<Boolean>() {

                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()) {
                        if(response.body() == true){
                            registrarUsuarioArchivo(mail, pass);
                            Intent i = new Intent(getApplicationContext(),principalActivity.class);
                            i.putExtra("userS",etUsuario.getText().toString());
                            i.putExtra("type","M");
                            startActivity(i);
                            Toast.makeText(MainActivity.this,"Welcome!",Toast.LENGTH_SHORT).show();
                        }else{
                            validateC();
                        }//else-if
                    }//if
                }//onResponse

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(MainActivity.this,"Error 01: "+t.toString(),Toast.LENGTH_SHORT).show();
                }//onFailure
            });
        }catch(Exception e){
            Toast.makeText(this,"Error 02: "+e.toString(),Toast.LENGTH_SHORT).show();
        }//try-catch
    }//validateS

    public void validateC (){
        try{
            listCallC = restService.getClient();
            listCallC.enqueue(new Callback<List<Client>>() {

                @Override
                public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                    if(response.isSuccessful()) {
                        boolean flag = false;
                        for (int i = 0 ; i < response.body().size(); i++){
                            if(mail.equals(response.body().get(i).getMail().toString()) &&
                                pass.equals(response.body().get(i).getPass().toString())){
                                flag = true;
                            }//if
                        }//for
                        if(flag){
                            registrarUsuarioArchivo(mail, pass);
                            Intent i = new Intent(getApplicationContext(),principalActivity.class);
                            i.putExtra("userS",etUsuario.getText().toString());
                            i.putExtra("type","C");
                            startActivity(i);
                            Toast.makeText(MainActivity.this,"Welcome!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Wrong data!",Toast.LENGTH_SHORT).show();
                        }//else-if
                    }//if
                }//onResponse

                @Override
                public void onFailure(Call<List<Client>> call, Throwable t) {
                    Toast.makeText(MainActivity.this,"Error 01: "+t.toString(),Toast.LENGTH_SHORT).show();
                }//onFailure
            });
            etPass.setText("");
            etUsuario.setText("");
        }catch(Exception e){
            Toast.makeText(this,"Error 02: "+e.toString(),Toast.LENGTH_SHORT).show();
        }//try-catch
    }//validateC

    private void obtenerDatos(){
        String[] archivos = fileList();

        if (existe(archivos, "usuarios.txt"))
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("usuarios.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String texto = "";
                while (linea != null) {
                    texto = texto + linea;
                    linea = br.readLine();
                }//Fin del while.

                br.close();
                archivo.close();

                String[] datosUsuario = texto.split(";");
                this.mail = datosUsuario[0];
                this.pass = datosUsuario[1];

                validateS();

            } catch (IOException e) {
            }//Fin del try-catch.
    }//Fin del método obtenerDatos.

    private boolean existe(String[] archivos, String archivoBuscar) {
        for (int f = 0; f < archivos.length; f++)
            if (archivoBuscar.equals(archivos[f]))
                return true;
        return false;
    }//Fin del método existe.

    public void registrarUsuarioArchivo(String usuario, String contrasena) {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("usuarios.txt", MainActivity.MODE_PRIVATE));
            String dato = usuario + ";" + contrasena;
            archivo.write(dato);
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
        }//Fin del try-catch.
    }//Fin del método registrarUsuarioArchivo.

}//class

