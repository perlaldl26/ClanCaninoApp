package mx.itson.clancanino.adapters;

import android.annotation.SuppressLint;
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

import mx.itson.clancanino.Entidades.Tramite;
import mx.itson.clancanino.R;

public class TramiteAdapter extends BaseAdapter {

    private Context context;
    private List<Tramite> tramites;

    public TramiteAdapter(Context context, List<Tramite> tramites){
        this.tramites = tramites;
        this.context = context;

    }
    @Override
    public int getCount() {
        return tramites.size();
    }

    @Override
    public Object getItem(int position) {
        return tramites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tramite tramite = (Tramite) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_tramite, null);

        TextView txtId = convertView.findViewById(R.id.txtId);
        txtId.setText(String.valueOf(tramite.getId()));

        TextView txtNombre = convertView.findViewById(R.id.txtNombreMascota);
        txtNombre.setText(tramite.getNombreMascota());

        TextView txtEdad = convertView.findViewById(R.id.txtEdad);
        txtEdad.setText(String.valueOf("Edad: "+tramite.getEdad() + " años"));
        if (tramite.getEdad() == 0){

            txtEdad.setText(String.valueOf("Edad: cachorro"));

        }

        TextView txtTipo = convertView.findViewById(R.id.txtTipo);
        txtTipo.setText("Raza: "+tramite.getEspecie());

        TextView txtEstado = convertView.findViewById(R.id.txtEstado);
        txtEstado.setText("Estado: "+tramite.getEstado());
        switch (tramite.getEstado()){
            case "procesando":
                txtEstado.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pros_style) );
                break;
            case "cancelado":
                txtEstado.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.canc_style) );
                break;
            case "aceptado":
                txtEstado.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_style) );
                break;
        }


        TextView txtFecha = convertView.findViewById(R.id.txtFecha);
        txtFecha.setText("Fecha: "+tramite.getFecha());


        ImageView imageMascota = convertView.findViewById(R.id.imageMascota);
        String url = "https://clancanino.000webhostapp.com/" +tramite.getFoto();
        System.out.println(url);
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().override(800,1300))
                .into(imageMascota);




        return convertView;




    }
}
