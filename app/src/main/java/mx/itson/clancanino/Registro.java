package mx.itson.clancanino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mx.itson.clancanino.Entidades.Mensaje;
import mx.itson.clancanino.utilerias.RetrofitUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
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

    public void registrar(View view) {

        TextView txtName = (TextView) findViewById(R.id.editTextUserName);
        String name = txtName.getText().toString().trim();

        TextView txtEmail = (TextView) findViewById(R.id.editTextUser);
        String email = txtEmail.getText().toString().trim();
        TextView txtPassword = (TextView) findViewById(R.id.editTextPassword);
        String password = txtPassword.getText().toString().trim();
        TextView txtPassword2 = (TextView) findViewById(R.id.editTextPassword2);
        String password2 = txtPassword.getText().toString().trim();

        if (email.equals("") || password.equals("") || password2.equals("") || name.equals("")) {
            Toast.makeText(getApplicationContext(), "Ingresa la contraseña y correo", Toast.LENGTH_LONG).show();
        } else {
            if (password2.equals(password)) {
                Toast.makeText(getApplicationContext(), "Registro correcto", Toast.LENGTH_LONG).show();
                mandarRegistro(email, password, name);
            } else {
                Toast.makeText(getApplicationContext(), "Las contraseñas son diferentes", Toast.LENGTH_LONG).show();
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