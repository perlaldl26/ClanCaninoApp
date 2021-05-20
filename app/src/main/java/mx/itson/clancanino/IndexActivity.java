package mx.itson.clancanino;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import mx.itson.clancanino.Entidades.Mascotas;
import mx.itson.clancanino.adapters.MascotaAdapter;
import mx.itson.clancanino.utilerias.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class IndexActivity extends AppCompatActivity {
    Context context;
    ListView listaMascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }


    }

    @Override
    protected void onResume() {
        cargarLista();
        super.onResume();
    }

    public void cargarLista(){
        try{
        Call<List<Mascotas>> llamada = RetrofitUtil.obtenerAPI().obtenerMascotas();

        llamada.enqueue(new Callback<List<Mascotas>>() {
            @Override
            public void onResponse(Call<List<Mascotas>> call, Response<List<Mascotas>> response) {
                if (response.isSuccessful()) {
                    List<Mascotas> mascotas = response.body();

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