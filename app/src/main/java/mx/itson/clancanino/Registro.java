package mx.itson.clancanino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mx.itson.clancanino.Entidades.Mensaje;
import mx.itson.clancanino.Entidades.Sesion;
import mx.itson.clancanino.utilerias.RetrofitUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        context= this;
    }


    public void obtenerSesion(String strEmail, String strPassword){

        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), strEmail);

        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), strPassword);

        Call<Sesion> llamada = RetrofitUtil.obtenerAPI().ingresar(email, password);

        llamada.enqueue(new Callback<Sesion>() {
            @Override
            public void onResponse(Call<Sesion> call, Response<Sesion> response) {
                if(response.isSuccessful()){
                    Sesion sesion = response.body();

                    if(sesion.getSuccess() == 1){
                        SharedPreferences.Editor editor = getSharedPreferences("Sesion", MODE_PRIVATE).edit();
                        editor.putInt("idUser", sesion.getIdUsuario());
                        editor.putString("userRol", sesion.getRol());
                        editor.putString("name", sesion.getNombre());
                        editor.putString("email", sesion.getCorreo());
                        editor.commit();

                        Intent i = new Intent(context, IndexActivity.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), sesion.getMessage(), Toast.LENGTH_LONG).show();
                    }else{

                        Toast.makeText(getApplicationContext(),  sesion.getMessage(), Toast.LENGTH_LONG).show();
                        Button boton = (Button) findViewById(R.id.buttonIngresar);
                        boton.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<Sesion> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }






    public void mandarRegistro(String strEmail, String strPassword, String strName) {

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), strName);

        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), strEmail);

        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), strPassword);

        Call<Mensaje> llamada = RetrofitUtil.obtenerAPI().registrar(name, email, password);

        llamada.enqueue(new Callback<Mensaje>() {
            @Override
            public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                if (response.isSuccessful()) {
                    Mensaje sesion = response.body();

                    if (sesion.getSuccess() == 1) {

                        Toast.makeText(getApplicationContext(), sesion.getMessage(), Toast.LENGTH_LONG).show();

                        Intent i = new Intent(context, Login.class);
                        i.putExtra("correo", strEmail);
                        i.putExtra("contrasena", strPassword);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(), sesion.getMessage(), Toast.LENGTH_SHORT).show();
                        Button boton = (Button) findViewById(R.id.buttonRegistrarse);
                        boton.setEnabled(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<Mensaje> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void registrar(View view) {

        TextView txtName = (TextView) findViewById(R.id.editTextUserName);
        String name = txtName.getText().toString().trim();

        TextView txtEmail = (TextView) findViewById(R.id.editTextUser);
        String email = txtEmail.getText().toString().trim();
        TextView txtPassword = (TextView) findViewById(R.id.editTextPassword);
        String password = txtPassword.getText().toString().trim();
        TextView txtPassword2 = (TextView) findViewById(R.id.editTextPassword2);
        String password2 = txtPassword2.getText().toString().trim();

        if (email.equals("") || password.equals("") || password2.equals("") || name.equals("")) {
            Toast.makeText(getApplicationContext(), "Ingresa todos los campos", Toast.LENGTH_LONG).show();
        } else {
            Button boton = (Button) findViewById(R.id.buttonRegistrarse);
            boton.setEnabled(false);

            if (password2.equals(password)) {
           //     Toast.makeText(getApplicationContext(), "Registro correcto", Toast.LENGTH_LONG).show();
                mandarRegistro(email, password, name);
            } else {
                Toast.makeText(getApplicationContext(), "Las contraseñas son diferentes", Toast.LENGTH_LONG).show();
                boton.setEnabled(true);
            }

        }
    }

    public void inicio(View view) {
        finish();
    }


    public void facebook(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/clanclaninoguaymas/"));
        startActivity(browserIntent);

    }

    public void whatsapp(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/526221236966"));
        startActivity(browserIntent);

    }



}