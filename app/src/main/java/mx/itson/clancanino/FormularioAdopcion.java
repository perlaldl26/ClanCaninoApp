package mx.itson.clancanino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import mx.itson.clancanino.Entidades.Mascotas;
import mx.itson.clancanino.Entidades.Mensaje;
import mx.itson.clancanino.Entidades.UsuarioInfo;
import mx.itson.clancanino.utilerias.RetrofitUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class FormularioAdopcion extends AppCompatActivity {

    int idUsuario;
    int idMascota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        if(intent.getStringExtra("idMascota") != null) {
            idMascota = Integer.parseInt(intent.getStringExtra("idMascota"));
            
        }
        setContentView(R.layout.activity_formulario_adopcion);


        Toolbar mToolBar = findViewById(R.id.toolbar);
        mToolBar.setTitle("Registro para adopción ");

        mToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);


        setSupportActionBar(mToolBar);


        SharedPreferences prefs = getSharedPreferences("Sesion", MODE_PRIVATE);
        if (prefs.getAll() != null) {
            String name = prefs.getString("name", "No name defined");
            String email = prefs.getString("email", "No email defined");
            idUsuario = prefs.getInt("idUser", 0);


            Call<UsuarioInfo> llamada = RetrofitUtil.obtenerAPI().obtenerInfoUsuario(idUsuario);

            llamada.enqueue(new Callback<UsuarioInfo>() {
                @Override
                public void onResponse(Call<UsuarioInfo> call, Response<UsuarioInfo> response) {
                    if (response.isSuccessful()) {
                        UsuarioInfo userInfo = response.body();
                        if (userInfo.getDireccion() != null){

                            EditText txtEdad = (EditText) findViewById(R.id.editEdad);
                            txtEdad.setText(String.valueOf(userInfo.getEdad()));


                            EditText txtNumMasc = (EditText) findViewById(R.id.numeroMasc);
                            txtNumMasc.setText(String.valueOf(userInfo.getNumeroMascotas()));

                            EditText txtCedula = (EditText) findViewById(R.id.editCedula);
                            txtCedula.setText(userInfo.getCedula());

                            EditText txtCelular = (EditText) findViewById(R.id.editCelular);
                            txtCelular.setText(userInfo.getCelular());

                            EditText txtTelefono = (EditText) findViewById(R.id.editTelefono);
                            txtTelefono.setText(userInfo.getTelefono());

                            EditText txtDireccion = (EditText) findViewById(R.id.editDirección);
                            txtDireccion.setText(userInfo.getDireccion());

                            Button boton = (Button) findViewById(R.id.buttonIngresar);
                            boton.setVisibility(View.VISIBLE);


                        }

                        Button boton = (Button) findViewById(R.id.buttonIngresar);
                        boton.setVisibility(View.VISIBLE);
                        boton.setEnabled(true);

                    }
                }

                @Override
                public void onFailure(Call<UsuarioInfo> call, Throwable t) {

                }
            });

            EditText txtName = (EditText) findViewById(R.id.editTextUserName);
            txtName.setText(name);
            txtName.setEnabled(false);
            txtName.setInputType(InputType.TYPE_NULL);

            EditText txtEmail = (EditText) findViewById(R.id.editTextEmail);
            txtEmail.setText(email);
            txtEmail.setEnabled(false);
            txtEmail.setInputType(InputType.TYPE_NULL);

        }
        Button boton = (Button) findViewById(R.id.buttonIngresar);
        boton.setVisibility(View.VISIBLE);
        boton.setEnabled(true);

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

    public void registrar(View view) {
        Button boton = (Button) findViewById(R.id.buttonIngresar);
        boton.setEnabled(false);

        EditText txtEdad = (EditText) findViewById(R.id.editEdad);
        String edad = txtEdad.getText().toString().trim();

        EditText txtNumMasc = (EditText) findViewById(R.id.numeroMasc);
        String numMasc = txtNumMasc.getText().toString().trim();

        EditText txtCedula = (EditText) findViewById(R.id.editCedula);
        String cedula = txtCedula.getText().toString().trim();

        EditText txtCelular = (EditText) findViewById(R.id.editCelular);
        String celular = txtCelular.getText().toString().trim();

        EditText txtTelefono = (EditText) findViewById(R.id.editTelefono);
        String telefono = txtTelefono.getText().toString().trim();

        EditText txtDireccion = (EditText) findViewById(R.id.editDirección);
        String direccion = txtDireccion.getText().toString().trim();


        if (numMasc.trim().equals("") || cedula.trim().equals("") || celular.trim().equals("") || telefono.trim().equals("") || direccion.trim().equals("") || edad.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "No se han llenado todos los datos", Toast.LENGTH_LONG).show();
            boton.setEnabled(true);
        } else {

            mandarTramite(idUsuario, idMascota, edad, numMasc, cedula, celular, telefono, direccion);
        }
    }


    public void mandarTramite(int strIdUsuario, int strIdMascota, String strEdad, String strNumMasc, String strCedula, String strCelular, String strTelefono, String strDireccion) {

        RequestBody idUsuario = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(strIdUsuario));

        RequestBody idMascota = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(strIdMascota));

        RequestBody edad = RequestBody.create(MediaType.parse("text/plain"), strEdad);

        RequestBody numeroMascotas = RequestBody.create(MediaType.parse("text/plain"), strNumMasc);

        RequestBody cedula = RequestBody.create(MediaType.parse("text/plain"), strCedula);

        RequestBody celular = RequestBody.create(MediaType.parse("text/plain"), strCelular);

        RequestBody telefono = RequestBody.create(MediaType.parse("text/plain"), strTelefono);

        RequestBody direccion = RequestBody.create(MediaType.parse("text/plain"), strDireccion);


        Call<Mensaje> llamada = RetrofitUtil.obtenerAPI().ingresarInfoPersonal(idUsuario, idMascota, edad, direccion, numeroMascotas, telefono, cedula, celular  );

        llamada.enqueue(new Callback<Mensaje>() {
            @Override
            public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                if (response.isSuccessful()) {
                    Mensaje sesion = response.body();

                    if (sesion.getSuccess() == 1) {

                        Toast.makeText(getApplicationContext(), sesion.getMessage(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), ListTramites.class));
                        overridePendingTransition(2, 2);
                    } else {
                        Toast.makeText(getApplicationContext(), sesion.getMessage(), Toast.LENGTH_LONG).show();
                        Button boton = (Button) findViewById(R.id.buttonIngresar);
                        boton.setEnabled(true);
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