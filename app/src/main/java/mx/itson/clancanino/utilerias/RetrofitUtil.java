package mx.itson.clancanino.utilerias;

import mx.itson.clancanino.Entidades.Mascotas;
import mx.itson.clancanino.interfaces.ClanCaninoAPI;
import okhttp3.MediaType;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    public static ClanCaninoAPI obtenerAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://clancanino.000webhostapp.com/apis/").addConverterFactory(GsonConverterFactory.create())

                .build();
        return retrofit.create(ClanCaninoAPI.class);
    }


}
