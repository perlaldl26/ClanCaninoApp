package mx.itson.clancanino.utilerias;

import mx.itson.clancanino.interfaces.ClanCaninoAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    public static ClanCaninoAPI obtenerAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://e6f7730f1793.ngrok.io/").addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ClanCaninoAPI.class);
    }
}
