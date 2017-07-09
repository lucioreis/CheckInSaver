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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

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
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categorias);
        spinner.setAdapter(arrayAdapter);

        for(Categoria c : categorias){
            Log.i("cats", c.getNome());
        }

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
            Intent it = new Intent(this, MapaCheckinActivity.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void persistirNoBanco(View v) {
        Log.i("nome", this.toString());
        List<CheckIn> locais = controladora.getCheckins();
        EditText nomeDoLocal = (EditText) findViewById(R.id.nome_do_local);
        Categoria categoria = (Categoria) spinner.getSelectedItem();
        Log.i("latlong", "latitude.toStrng() = " + latitude.getText().toString() + " longitude.toString() = " + longitude.getText().toString());
        CheckIn newCheckin = new CheckIn(nomeDoLocal.getText().toString(), 1, location.getLatitude(), location.getLongitude(), categoria);
        for(CheckIn c : locais){
            if(c.equals(newCheckin)) {
                c.setQtdVisitas(c.getQtdVisitas() + 1);
                startActivity(new Intent(this, CheckInLocais.class));
                finish();
            }
        }
        controladora.addCheckin(newCheckin);
        startActivity(new Intent(this, CheckInLocais.class));
        finish();
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
