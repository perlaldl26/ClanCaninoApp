package mx.itson.clancanino.interfaces;

import mx.itson.clancanino.Entidades.Sesion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClanCaninoAPI {

    @POST("apis/userLog.php")
    Call<Sesion> ingresar(@Query("email") String email, @Query("password") String password);
}
