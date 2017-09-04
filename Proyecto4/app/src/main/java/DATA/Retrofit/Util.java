package DATA.Retrofit;

import UTIL.RESTService;

/**
 * Created by ajoha on 1/9/2017.
 */

public class Util {

    private Util() {}

    public static final String BASE_URL = "http://192.168.43.235:8094/";

    public static RESTService getRestService() {

        return ClientRetrofit.getClient(BASE_URL).create(RESTService.class);

    }//getRestService

}//Util
