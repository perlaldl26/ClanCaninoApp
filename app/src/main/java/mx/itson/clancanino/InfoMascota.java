package mx.itson.clancanino;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import mx.itson.clancanino.Entidades.Mascotas;
import mx.itson.clancanino.utilerias.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoMascota extends AppCompatActivity {

    int idMascota;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_mascota);

        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        Intent intent = getIntent();
        if(intent.getStringExtra("id") != null) {
            idMascota = Integer.parseInt(intent.getStringExtra("id"));
            obtenerInfo(idMascota);
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




                    Button btnAdopt = (Button) findViewById(R.id.btnAdopt);
                    btnAdopt.setVisibility(View.VISIBLE);



                }
            }

            @Override
            public void onFailure(Call<Mascotas> call, Throwable t) {

            }
        });
    }

    public void adoptar(View view) {
        Intent i = new Intent(context, FormularioAdopcion.class);
        i.putExtra("idMascota", String.valueOf(idMascota));
        startActivity(i);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searh_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}