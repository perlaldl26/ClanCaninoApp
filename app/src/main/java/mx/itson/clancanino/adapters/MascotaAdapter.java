package mx.itson.clancanino.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import mx.itson.clancanino.Entidades.Mascotas;
import mx.itson.clancanino.R;

public class MascotaAdapter extends BaseAdapter {

    private Context context;
    private List<Mascotas> mascotas;

    public MascotaAdapter(Context context, List<Mascotas> mascotas){
        this.mascotas = mascotas;
        this.context = context;

    }

    @Override
    public int getCount() {
        return mascotas.size();
    }

    @Override
    public Object getItem(int position) {
        return mascotas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Mascotas mascota = (Mascotas) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_mascota, null);

        TextView txtId = convertView.findViewById(R.id.txtId);
        txtId.setText(String.valueOf(mascota.getId()));

        TextView txtNombre = convertView.findViewById(R.id.txtNombre);
        txtNombre.setText("Mascota: "+mascota.getNombre() );

        TextView txtEdad = convertView.findViewById(R.id.txtEdad);
        txtEdad.setText(String.valueOf("Edad: "+mascota.getEdad() +" año(s)"));
        if(mascota.getEdad() < 1){
            txtEdad.setText(String.valueOf("cachorro"));
        }

        TextView txtTipo = convertView.findViewById(R.id.txtTipo);
        txtTipo.setText("Tipo: "+mascota.getEspecie());


        ImageView imageMascota = convertView.findViewById(R.id.imageMascota);
        String url = "https://clancanino.000webhostapp.com/" +mascota.getFoto();
        System.out.println(url);
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().override(800,1300))
                .into(imageMascota);




        return convertView;
    }
}
