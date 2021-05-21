package mx.itson.clancanino.interfaces;

import java.util.List;

import mx.itson.clancanino.Entidades.Mascotas;
import mx.itson.clancanino.Entidades.Mensaje;
import mx.itson.clancanino.Entidades.Sesion;
import mx.itson.clancanino.Entidades.UsuarioInfo;
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
    Call<Mensaje> registrar(@Part("name") RequestBody name, @Part("email") RequestBody email, @Part("password") RequestBody  password);

    @GET("pets.php")
    Call<List<Mascotas>> obtenerMascotas();

    @GET("pet.php")
    Call<Mascotas> obtenerInfoMascota(@Query("id") int id);

    @GET("getUser.php")
    Call<UsuarioInfo> obtenerInfoUsuario(@Query("idUsuario") int id);

    @Multipart
    @POST("user-info.php")
    Call<Mensaje> ingresarInfoPersonal(@Part("idUsuario") RequestBody idUsuario, @Part("idMascota") RequestBody idMascota, @Part("edad") RequestBody edad, @Part("direccion") RequestBody direccion, @Part("numeroMascotas") RequestBody numeroMascotas, @Part("telefono") RequestBody  telefono, @Part("cedula") RequestBody  cedula, @Part("celular") RequestBody  celular);


}
