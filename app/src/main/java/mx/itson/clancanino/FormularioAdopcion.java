package mx.itson.clancanino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class FormularioAdopcion extends AppCompatActivity {

    int idUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        if(intent.getStringExtra("id") != null) {
            idUsuario = Integer.parseInt(intent.getStringExtra("id"));
            
        }


        setContentView(R.layout.activity_formulario_adopcion);
        Toolbar mToolBar = findViewById(R.id.toolbar);
        mToolBar.setTitle("Registro para adopción ");

        mToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        EditText txtName = (EditText) findViewById(R.id.editTextUserName);
        txtName.setEnabled(false);
        txtName.setInputType(InputType.TYPE_NULL);

        EditText txtEmail = (EditText) findViewById(R.id.editTextEmail);
        txtEmail.setEnabled(false);
        txtEmail.setInputType(InputType.TYPE_NULL);
        setSupportActionBar(mToolBar);


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

        EditText txtName = (EditText) findViewById(R.id.editTextUserName);
        String name = txtName.getText().toString().trim();

        EditText txtEmail = (EditText) findViewById(R.id.editTextEmail);
        String email = txtEmail.getText().toString().trim();

        EditText txtEdad = (EditText) findViewById(R.id.editEdad);
        String edad = txtEdad.getText().toString().trim();

        EditText txtNumMasc = (EditText) findViewById(R.id.numeroMasc);
        String NumMasc = txtNumMasc.getText().toString().trim();

        EditText txtCedula = (EditText) findViewById(R.id.editCedula);
        String cedula = txtCedula.getText().toString().trim();

        EditText txtCelular = (EditText) findViewById(R.id.editCelular);
        String celular = txtCelular.getText().toString().trim();

        EditText txtTelefono = (EditText) findViewById(R.id.editTelefono);
        String telefono = txtTelefono.getText().toString().trim();

        EditText txtDireccion = (EditText) findViewById(R.id.editDirección);
        String direccion = txtDireccion.getText().toString().trim();


        if (email.equals("") || edad.equals("") || name.equals("") || NumMasc.equals("") || cedula.equals("") || celular.equals("") || telefono.equals("") || direccion.equals("")) {
            Toast.makeText(getApplicationContext(), "No se han llenado todos los datos", Toast.LENGTH_LONG).show();
        } else {


        }
    }



}