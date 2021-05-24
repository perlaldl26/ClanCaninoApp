package mx.itson.clancanino;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import mx.itson.clancanino.Entidades.Mascotas;
import mx.itson.clancanino.adapters.MascotaAdapter;
import mx.itson.clancanino.utilerias.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity {
    Context context;
    ListView listaMascotas;
    ListView listaMascotasSearch;

    List<Mascotas> mascotas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        cargarLista();
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.pullRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarLista();

            }
        });



        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
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

    @Override
    protected void onResume() {

        super.onResume();
    }

    public void cargarLista(){
        try{
        Call<List<Mascotas>> llamada = RetrofitUtil.obtenerAPI().obtenerMascotas();

        llamada.enqueue(new Callback<List<Mascotas>>() {
            @Override
            public void onResponse(Call<List<Mascotas>> call, Response<List<Mascotas>> response) {
                if (response.isSuccessful()) {
                    mascotas = response.body();

                    listaMascotas = findViewById(R.id.listaMascotas);
                    MascotaAdapter adapter = new MascotaAdapter(context, mascotas);

                    listaMascotas.setAdapter(adapter);

                    listaMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int idMasc = Integer.parseInt(((TextView) view.findViewById(R.id.txtId)).getText().toString());

                            Intent i = new Intent(context, InfoMascota.class);
                            i.putExtra("id", String.valueOf(idMasc));
                            startActivity(i);
                        }
                    });

                    SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.pullRefresh);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Mascotas>> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());

            }
        });
        }catch(Exception ex){

            Log.e("Error al cargar lista", ex.getMessage());
        }

    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searh_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query == null){
                    MascotaAdapter adapter = new MascotaAdapter(context, mascotas);
                    listaMascotas.setAdapter(adapter);
                }else{

                List<Mascotas> searchmascotas = new ArrayList<>();
                listaMascotasSearch = findViewById(R.id.listaMascotas);



                mascotas.stream()
                        .filter(m -> ((m.getNombre().toUpperCase().contains(query.toUpperCase()))  || (m.getEspecie().toUpperCase().contains(query.toUpperCase()))))
                        .forEach(searchmascotas::add);

                MascotaAdapter adapter = new MascotaAdapter(context, searchmascotas);
                listaMascotas.setAdapter(adapter);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == null){
                    MascotaAdapter adapter = new MascotaAdapter(context, mascotas);
                    listaMascotas.setAdapter(adapter);
                }else {


                    List<Mascotas> searchmascotas = new ArrayList<>();
                    listaMascotasSearch = findViewById(R.id.listaMascotas);


                    mascotas.stream()
                            .filter(m -> ((m.getNombre().toUpperCase().contains(newText.toUpperCase())) || (m.getEspecie().toUpperCase().contains(newText.toUpperCase()))))
                            .forEach(searchmascotas::add);

                    MascotaAdapter adapter = new MascotaAdapter(context, searchmascotas);
                    listaMascotas.setAdapter(adapter);
                }

                return true;
            }
        });

    return super.onCreateOptionsMenu(menu);
    }





}