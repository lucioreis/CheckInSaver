package inf221.trabalho.com.checkinsaver.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import inf221.trabalho.com.checkinsaver.R;
import inf221.trabalho.com.checkinsaver.model.CheckIn;
import inf221.trabalho.com.checkinsaver.model.ControladoraFachadaSingleton;
import inf221.trabalho.com.checkinsaver.view.MyArrayAdapterGestaoDeCheckIn;

public class GestaoCheckinActivity extends AppCompatActivity {
    private final int VOLTAR = 12345;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestao_checkin);
        ControladoraFachadaSingleton controladora = ControladoraFachadaSingleton.getInstance();
        setTitle("Gestão de Check-In's");
        List<CheckIn> checkIns = controladora.getCheckins();
        for(CheckIn c : checkIns){
            TextView textView = new TextView(this);
            textView.setText(c.getNomeDoLocal());
        }

        ListView listView = (ListView) findViewById(R.id.gestao_lista_de_locais);
        MyArrayAdapterGestaoDeCheckIn myArrayAdapterGestaoDeCheckIn = new MyArrayAdapterGestaoDeCheckIn(checkIns, this);
        listView.setAdapter(myArrayAdapterGestaoDeCheckIn);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0,VOLTAR, 0, "Voltar");
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == VOLTAR){
            finish();
            return true;
        }
        return false;
    }
}
