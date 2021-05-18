package mx.itson.clancanino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import mx.itson.clancanino.Entidades.Sesion;
import mx.itson.clancanino.utilerias.RetrofitUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);




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
        Intent i = new Intent(this, IndexActivity.class);
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