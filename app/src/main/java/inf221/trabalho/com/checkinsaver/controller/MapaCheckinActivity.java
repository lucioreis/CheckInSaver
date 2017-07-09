package inf221.trabalho.com.checkinsaver.controller;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import inf221.trabalho.com.checkinsaver.R;
import inf221.trabalho.com.checkinsaver.model.CheckIn;
import inf221.trabalho.com.checkinsaver.model.ControladoraFachadaSingleton;

public class MapaCheckinActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final int TIPOS_DE_MAPAS = 150;
    private final int HIBRIDO = 0, NORMAL = 1;
    private ControladoraFachadaSingleton controldora = ControladoraFachadaSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_checkin);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        List<CheckIn> locais = controldora.getCheckins();
        Log.i("debug", "locai.isEmpty = " + locais.isEmpty());
        for(CheckIn c: locais){
            LatLng localizacaoDeC = new LatLng(c.getLatitude(), c.getLongitude());
            mMap.addMarker(new MarkerOptions().position(localizacaoDeC).title(c.getNomeDoLocal()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacaoDeC));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu subMenu = menu.addSubMenu(0, 2, 500, "Tipos de Mapas");
        subMenu.add(0, HIBRIDO, 0, "Hibrido");
        subMenu.add(0, NORMAL, 1, "Normal");
        getMenuInflater().inflate(R.menu.menu_mapa_chekin_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.voltar:
                finish();
                break;
            case R.id.gestao_de_checkin:
                startActivity(new Intent(this, GestaoCheckinActivity.class));
                finish();
                break;
            case R.id.lugares_mais_visitdos:
                startActivity(new Intent(this, LugaresMaisVisitadosActivity.class));
                finish();
                break;
            case HIBRIDO:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case NORMAL:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
        }
        return true;
    }
}
