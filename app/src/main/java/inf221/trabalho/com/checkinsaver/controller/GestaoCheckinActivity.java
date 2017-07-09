package inf221.trabalho.com.checkinsaver.controller;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import inf221.trabalho.com.checkinsaver.R;

public class GestaoCheckinActivity extends AppCompatActivity {
    private final int VOLTAR = 12345;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestao_checkin);

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
