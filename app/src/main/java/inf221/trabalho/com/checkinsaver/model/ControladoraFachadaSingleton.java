package inf221.trabalho.com.checkinsaver.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import inf221.trabalho.com.checkinsaver.util.BancoDadosSingleton;

/**
 * Created by lucio on 7/9/2017.
 */

public class ControladoraFachadaSingleton {
    private static final ControladoraFachadaSingleton ourInstance = new ControladoraFachadaSingleton();
    private List<CheckIn> checkIns;
    private List<Categoria> categorias;
    private BancoDadosSingleton bancoDadosSingleton = BancoDadosSingleton.getInstance();

    public static ControladoraFachadaSingleton getInstance() {
        return ourInstance;
    }

    private ControladoraFachadaSingleton() {
        daoCheckins();
        daoCategorias();
    }

    private void daoCheckins() {
        Cursor c = bancoDadosSingleton.buscar("Checkin",
                new String[]{"Local", "qtdVisitas", "cat", "latitude", "longitude"},
                "", "");
        if (checkIns == null) checkIns = new ArrayList<>();
        while (c.moveToNext()) {
            CheckIn checkIn = new CheckIn();
            checkIn.setNomeDoLocal(c.getString(c.getColumnIndex("Local")));
            checkIn.setCategoria(new Categoria(c.getString(c.getColumnIndex("cat"))));
            checkIn.setQtdVisitas(c.getInt(c.getColumnIndex("qtdVisitas")));
            checkIn.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
            checkIn.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
            checkIns.add(checkIn);
        }
    }

    private void daoCategorias() {
        Cursor c = bancoDadosSingleton.buscar("Categoria", new String[]{"nome"}, "", "");
        if (categorias == null) categorias = new ArrayList<>();
        while (c.moveToNext()) {
            Log.i("categoria", c.getString(c.getColumnIndex("nome")));
            Categoria categoria = new Categoria(c.getString(c.getColumnIndex("nome")));
            categorias.add(categoria);
        }
    }

    public List<CheckIn> getCheckins() {
        return checkIns;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    private void persistirCheckIn(CheckIn checkIn) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("Local", checkIn.getNomeDoLocal());
        contentValues.put("qtdVisitas", checkIn.getQtdVisitas());
        contentValues.put("cat", checkIn.getCategoria().getNome());
        contentValues.put("latitude", checkIn.getLatitude());
        contentValues.put("longitude", checkIn.getLongitude());

        bancoDadosSingleton.inserir("Checkin", contentValues);
    }

    public void addCheckin(CheckIn checkIn) {
        if (checkIns == null) checkIns = new ArrayList<>();
        checkIns.add(checkIn);
        persistirCheckIn(checkIn);
    }

    public void removeCheckin(CheckIn checkIn) {
        if (checkIns.isEmpty()) return;
        checkIns.remove(checkIn);
        String checkInLocal = checkIn.getNomeDoLocal();
        bancoDadosSingleton.deletar("Checkin", "Local = " + checkInLocal);
    }
}
