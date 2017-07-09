package inf221.trabalho.com.checkinsaver.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import inf221.trabalho.com.checkinsaver.R;

public class CheckInLocais extends AppCompatActivity {
    private final String categorias[] = {"Restaurante", "Bar", "Cinema", "Universidade", "Estádio", "Parque", "Outros"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_locais);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Spinner spinner = (Spinner) findViewById(R.id.spinner_categoria);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categorias);
        spinner.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_in_locais, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.gestao_de_checkin) {
            Intent it = new Intent(this, GestaoCheckinActivity.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.lugares_mais_visitdos) {
            Intent it = new Intent(this, LugaresMaisVisitadosActivity.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.action_mapa_de_checkin) {
            Intent it = new Intent(this, MapaCheckinActivity.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void persistirNoBanco(View v) {
        Log.i("nome", this.toString());
        startActivity(new Intent(this, CheckInLocais.class));
        finish();
    }
}
