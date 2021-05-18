package mx.itson.clancanino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import mx.itson.clancanino.Entidades.Mascotas;
import mx.itson.clancanino.Entidades.Sesion;
import mx.itson.clancanino.utilerias.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoMascota extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_mascota);

        context = this;
        Intent intent = getIntent();
        if(intent.getStringExtra("id") != null) {
            int id = Integer.parseInt(intent.getStringExtra("id"));
            obtenerInfo(id);
        }
    }

    public void obtenerInfo(int id){



        Call<Mascotas> llamada = RetrofitUtil.obtenerAPI().obtenerInfoMascota(id);

        llamada.enqueue(new Callback<Mascotas>() {
            @Override
            public void onResponse(Call<Mascotas> call, Response<Mascotas> response) {
                if(response.isSuccessful()){
                    Mascotas mascota = response.body();


                    TextView idMasc = (TextView) findViewById(R.id.idMascota);
                    idMasc.setText(mascota.getNombre());




                }
            }

            @Override
            public void onFailure(Call<Mascotas> call, Throwable t) {

            }
        });
    }
}