package mx.itson.clancanino.interfaces;

import mx.itson.clancanino.Entidades.Sesion;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ClanCaninoAPI {

    @Multipart
    @POST("userLog.php")
    Call<Sesion> ingresar(@Part("email") RequestBody email, @Part("password") RequestBody  password);

    @Multipart
    @POST("user-register.php")
    Call<Sesion> registrar(@Part("name") RequestBody name, @Part("email") RequestBody email, @Part("password") RequestBody  password);
}
