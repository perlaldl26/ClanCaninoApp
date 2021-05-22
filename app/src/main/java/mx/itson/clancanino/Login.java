package mx.itson.clancanino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import mx.itson.clancanino.Entidades.Mensaje;
import mx.itson.clancanino.Entidades.Sesion;
import mx.itson.clancanino.utilerias.RetrofitUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        context=this;

        SharedPreferences prefs = getSharedPreferences("Sesion", MODE_PRIVATE);
        if (prefs.getAll() != null) {
            String name = prefs.getString("name", "No name defined");
            if(!name.equals("No name defined")){
                startActivity(new Intent(getApplicationContext(), IndexActivity.class));
                overridePendingTransition(2, 2);
            }

        }


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
                    }
                }
            }

            @Override
            public void onFailure(Call<Sesion> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void iniciarSesion(View view) {


        TextView txtEmail = (TextView)findViewById(R.id.editTextUser);
        String email = txtEmail.getText().toString().trim();
        TextView txtPassword = (TextView)findViewById(R.id.editTextPassword);
        String password = txtPassword.getText().toString().trim();

        if(email.equals("") || password.equals("")){

        }else{

            obtenerSesion(email, password);
        }


    }

    public void registro(View view) {
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }


    public void facebook(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/clanclaninoguaymas/"));
        startActivity(browserIntent);

    }

    public void whatsapp(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/526221236966"));
        startActivity(browserIntent);

    }
}