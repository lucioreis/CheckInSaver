package inf221.trabalho.com.checkinsaver.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Provider;
import java.util.List;
import java.util.Locale;
import inf221.trabalho.com.checkinsaver.R;
import inf221.trabalho.com.checkinsaver.model.Categoria;
import inf221.trabalho.com.checkinsaver.model.CheckIn;
import inf221.trabalho.com.checkinsaver.model.ControladoraFachadaSingleton;

public class CheckInLocais extends AppCompatActivity implements LocationListener {

    private ControladoraFachadaSingleton controladora = ControladoraFachadaSingleton.getInstance();
    private List<Categoria> categorias = controladora.getCategorias();
    private Location location;
    private Spinner spinner;
    private TextView latitude;
    private TextView longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_locais);

        spinner = (Spinner) findViewById(R.id.spinner_categoria);
        ArrayAdapter<Categoria> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categorias);
        spinner.setAdapter(arrayAdapter);
        setTitle("Check-In");
        for(Categoria c : categorias){
            Log.i("cats", c.getNome());
        }
        ControladoraFachadaSingleton controladora = ControladoraFachadaSingleton.getInstance();

        ArrayAdapter<CheckIn> arrayAdapter1 = new ArrayAdapter<>(this,  android.R.layout.simple_dropdown_item_1line, controladora.getCheckins());
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.nome_do_local);
        autoCompleteTextView.setAdapter(arrayAdapter1);
        autoCompleteTextView.setThreshold(3);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        PackageManager pm = getPackageManager();
        boolean hasGPS = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        if (hasGPS) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            Log.i("LOCATION", "Usando GPS");
        } else {
            Log.i("LOCATION", "Usando servicos de internet");
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;


        String provider = locationManager.getBestProvider(criteria, true);

        if (provider != null) {

            locationManager.requestLocationUpdates(provider, 5000, 50, this);
            location = locationManager.getLastKnownLocation(provider);
            latitude = (TextView) findViewById(R.id.latitude);
            longitude = (TextView) findViewById(R.id.longitude);

            latitude.setText( String.format(Locale.getDefault(),location.getLatitude()+"") );
            longitude.setText( String.format(Locale.getDefault(), ""+location.getLongitude()) );
            Log.i("latlong", "latitude = "+ latitude + " longitude = " + longitude);
        } else {
            Toast.makeText(this, "Não há localizacção disponivel!", Toast.LENGTH_SHORT).show();
        }

        Log.i("latlong", "latitude = "+ latitude + " longitude = " + longitude);


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
            if(location == null){
                Toast.makeText(this, "Não foi possivel obter localização", Toast.LENGTH_SHORT).show();
                return true;
            }
            Intent it = new Intent(this, MapaCheckinActivity.class);
            it.putExtra("latitude", location.getLatitude());
            it.putExtra("longitude", location.getLongitude());
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void persistirNoBanco(View v) {
        Log.i("nome", this.toString());
        EditText nomeDoLocal = (EditText) findViewById(R.id.nome_do_local);
        Categoria categoria = (Categoria) spinner.getSelectedItem();
        Log.i("latlong", "latitude.toStrng() = " + latitude.getText().toString() + " longitude.toString() = " + longitude.getText().toString());
        if(!nomeDoLocal.getText().toString().equals("")) {
            CheckIn newCheckin = new CheckIn(nomeDoLocal.getText().toString(), 1, location.getLatitude(), location.getLongitude(), categoria);
            controladora.addCheckin(newCheckin);
        }else {
            Toast.makeText(this, "Insira o nome de um lugar para fazer Check-In.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
