package mx.itson.clancanino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        toolbar.setTitle("Información de mascota");

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        setSupportActionBar(toolbar);



        Intent intent = getIntent();
        if(intent.getStringExtra("id") != null) {
            idMascota = Integer.parseInt(intent.getStringExtra("id"));
            obtenerInfo(idMascota);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), IndexActivity.class));
                        overridePendingTransition(2, 2);
                        return true;
                    case R.id.nav_tramite:
                        startActivity(new Intent(getApplicationContext(), ListTramites.class));
                        overridePendingTransition(2, 2);
                        return true;
                    case R.id.nav_log_out:

                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Cerrar sesión");
                        alert.setMessage("¿Seguro desea Cerrar la sesión actual?");

                        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                overridePendingTransition(2, 2);
                                SharedPreferences.Editor editor = getSharedPreferences("Sesion", MODE_PRIVATE).edit();
                                editor.clear();
                                editor.apply();

                                Toast.makeText(context, "Se cerró la sesión correctamente: " , Toast.LENGTH_SHORT).show();

                            }
                        });
                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
                                bottomNavigationView.setSelectedItemId(R.id.nav_home);
                                dialog.dismiss();

                            }
                        });

                        alert.show();
                        return true;


                }

                return false;
            }
        });

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
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}