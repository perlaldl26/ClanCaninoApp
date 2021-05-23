package mx.itson.clancanino;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import mx.itson.clancanino.Entidades.Mascotas;
import mx.itson.clancanino.Entidades.Mensaje;
import mx.itson.clancanino.Entidades.Tramite;
import mx.itson.clancanino.adapters.MascotaAdapter;
import mx.itson.clancanino.adapters.TramiteAdapter;
import mx.itson.clancanino.utilerias.RetrofitUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTramites extends AppCompatActivity {

    int idUsuario;
    Context context;
    ListView listaTramites;
    ListView listaTramitesSearch;

    List<Tramite> tramites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tramites);


        Toolbar mToolBar = (Toolbar)findViewById(R.id.toolbar);
        mToolBar.setTitle("Mis tramites ");

        mToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);


        setSupportActionBar(mToolBar);


        SharedPreferences prefs = getSharedPreferences("Sesion", MODE_PRIVATE);
        if (prefs.getAll() != null) {

            idUsuario = prefs.getInt("idUser", 0);}

        context = this;
        


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_tramite);
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
        cargarLista();
        super.onResume();
    }

    public void cargarLista(){
        try{
            Call<List<Tramite>> llamada = RetrofitUtil.obtenerAPI().obtenerTramites(idUsuario);

            llamada.enqueue(new Callback<List<Tramite>>() {
                @Override
                public void onResponse(Call<List<Tramite>> call, Response<List<Tramite>> response) {
                    if (response.isSuccessful()) {
                        tramites = response.body();

                        listaTramites = findViewById(R.id.listaTramites);
                        TramiteAdapter adapter = new TramiteAdapter(context, tramites);

                        listaTramites.setAdapter(adapter);
                        registerForContextMenu(listaTramites);

                        listaTramites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int idTramite = Integer.parseInt(((TextView) view.findViewById(R.id.txtId)).getText().toString());

                                Toast.makeText(context, String.valueOf(idTramite), Toast.LENGTH_LONG).show();
                            }
                        });




                    }
                }

                @Override
                public void onFailure(Call<List<Tramite>> call, Throwable t) {
                    Log.e("Failure", t.getLocalizedMessage());

                }
            });
        }catch(Exception ex){

            Log.e("Error al cargar lista", ex.getMessage());
        }

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo){
        super.onCreateContextMenu(menu, view,contextMenuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_tramite, menu);
    }




    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Tramite tramite = (Tramite) listaTramites.getItemAtPosition(info.position);

        if(item.getItemId() == R.id.eliminar){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("¿Deseas eliminar el registro?");
            builder.setTitle("Deseas eliminar");
            builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String idTramite = String.valueOf(tramite.getId());
                    String estado = tramite.getEstado();
                    if(estado != "aceptado"){
                        eliminarTramite(idTramite, estado);

                    }else{
                        Toast.makeText(context, "Un tramite ya aceptado no puede ser eliminado" , Toast.LENGTH_LONG).show();
                    }


                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();


        }

        return true;

    }
    public void eliminarTramite(String id,  String estado) {

        RequestBody idTramite = RequestBody.create(MediaType.parse("text/plain"), id);

        RequestBody estadoTram = RequestBody.create(MediaType.parse("text/plain"), estado);

        Call<Mensaje> llamada = RetrofitUtil.obtenerAPI().eliminarTramite(idTramite, estadoTram);

        llamada.enqueue(new Callback<Mensaje>() {
            @Override
            public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                if (response.isSuccessful()) {
                    Mensaje sesion = response.body();

                    if (sesion.getSuccess() == 1) {

                        Toast.makeText(getApplicationContext(), sesion.getMessage(), Toast.LENGTH_LONG).show();
                        
                        onResume();
                    } else {
                        Toast.makeText(getApplicationContext(), sesion.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Mensaje> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }



}