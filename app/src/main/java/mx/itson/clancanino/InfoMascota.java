package mx.itson.clancanino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import mx.itson.clancanino.Entidades.Mascotas;
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


                    TextView nombre = (TextView) findViewById(R.id.textNombre);
                    nombre.setText(mascota.getNombre());

                    TextView edad = (TextView) findViewById(R.id.textEdad);
                    edad.setText(String.valueOf(mascota.getEdad())+ " años");

                    TextView tipo = (TextView) findViewById(R.id.textTipo);
                    tipo.setText(mascota.getEspecie());

                    TextView sexo = (TextView) findViewById(R.id.textSexo);
                    sexo.setText(mascota.getSexo());

                    TextView historia = (TextView) findViewById(R.id.txtHistoria);
                    historia.setText(mascota.getHistoria());

                    TextView otro = (TextView) findViewById(R.id.txtOtros);
                    otro.setText(mascota.getObservaciones());


                    ImageView imageMascota = (ImageView) findViewById(R.id.imgMascota);
                    String url = "https://clancanino.000webhostapp.com/" +mascota.getFoto();
                    System.out.println(url);
                    Glide.with(context)
                            .load(url)
                            .apply(new RequestOptions().override(1300,1800))
                            .into(imageMascota);







                }
            }

            @Override
            public void onFailure(Call<Mascotas> call, Throwable t) {

            }
        });
    }

    public void adoptar(View view) {
        Intent i = new Intent(this, FormularioAdopcion.class);
        startActivity(i);
    }
}