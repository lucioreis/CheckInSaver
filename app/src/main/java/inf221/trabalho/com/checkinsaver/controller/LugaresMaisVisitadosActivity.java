package inf221.trabalho.com.checkinsaver.controller;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import inf221.trabalho.com.checkinsaver.R;
import inf221.trabalho.com.checkinsaver.model.CheckIn;
import inf221.trabalho.com.checkinsaver.model.ControladoraFachadaSingleton;
import inf221.trabalho.com.checkinsaver.view.MyArrayAdapterLocaisMaisVisitados;

public class LugaresMaisVisitadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares_mais_visitados);
        setTitle("Lugares Mais Visitados");
        ControladoraFachadaSingleton controladora = ControladoraFachadaSingleton.getInstance();
        List<CheckIn> checkIns = new ArrayList<>();
        for(CheckIn c : controladora.getCheckins()){
            checkIns.add(c);
        }

        Collections.sort(checkIns, new Comparator<CheckIn>() {
            @Override
            public int compare(CheckIn o1, CheckIn o2) {
                return o2.getQtdVisitas() - o1.getQtdVisitas();
            }
        });
        MyArrayAdapterLocaisMaisVisitados myArray = new MyArrayAdapterLocaisMaisVisitados(checkIns, this);
        ListView listView = (ListView) findViewById(R.id.lista_de_lugaes_mais_visitados);
        listView.setAdapter(myArray);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_lugares_mais_visitados, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.voltar_lugares_mais_visitados){
            finish();
            return true;
        }
        return false;
    }

}
