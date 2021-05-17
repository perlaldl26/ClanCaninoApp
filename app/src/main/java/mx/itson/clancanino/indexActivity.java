package mx.itson.clancanino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class indexActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        ListView listView = findViewById(R.id.listaMascotas);
        List<String> mylist = new ArrayList<>();
        mylist.add("Goofy");
        mylist.add("Firulais");
        mylist.add("Micha");
        mylist.add("Chopper");

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("lel");
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searh_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Buscar");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return true;
            }
        });

    return super.onCreateOptionsMenu(menu);
    }
}