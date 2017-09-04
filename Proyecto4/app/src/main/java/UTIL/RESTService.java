package UTIL;

import java.util.List;

import DOMAIN.Client;
import DOMAIN.Manager;
import DOMAIN.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * Created by ajoha on 1/9/2017.
 */

public interface RESTService {

    //Administradores
    @Headers({"Content-Type:application/json"})
    @POST("api/Manager")
    Call<Boolean> sendManager(@Body Manager manager);

    //Clientes
    @GET("api/Client")
    Call<List<Client>>  getClient();

    @Headers({"Content-Type:application/json"})
    @POST("api/Client")
    Call<Integer> sendClient(@Body Client client);

    //Productos
    @GET("api/Product")
    Call<List<Product>>  getProduct();

    @Headers({"Content-Type:application/json"})
    @POST("api/Product")
    Call<Integer> deleteProduct(@Body Product product);

    @GET("api/Cosume")
    Call<Integer> getCosume();

}//RESTService
