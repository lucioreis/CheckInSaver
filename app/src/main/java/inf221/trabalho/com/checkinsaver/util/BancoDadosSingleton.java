package inf221.trabalho.com.checkinsaver.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by usuario on 20/04/2017.
 */

public final class BancoDadosSingleton {
    private final String NOME_BANCO = new String("checkin_saver_db");

    public static BancoDadosSingleton getInstance() {
        return ourInstance;
    }

    private static BancoDadosSingleton ourInstance = new BancoDadosSingleton();
    private final String[] SCRIPT_DATA_BASE_CREATE = new String[]{
            "CREATE TABLE Checkin (" +
                    "Local TEXT PRIMARY KEY," +
                    "qtdVisitas INTEGER NOT NULL, " +
                    "cat INTEGER NOT NULL, " +
                    "latitude TEXT NOT NULL," +
                    "longitude TEXT NOT NULL,"+
                    "CONSTRAINT fkey0 FOREIGN KEY (cat) REFERENCES Categoria (idCategoria) );",
            "CREATE TABLE Categoria ("+
                    "idCategoria INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL );",
            "INSERT INTO Categoria (nome) VALUES ('Restaurante'),"+
                                                "('Bar'),"+
                                                "('Cinema'),"+
                                                "('Universidade')," +
                                                "('Estádio')," +
                                                "('Parque')," +
                                                "('Outros');"};
    SQLiteDatabase db;

    private BancoDadosSingleton() {
        Log.i("INFORMACAO BD", "entrou construtor");
        db = MyApp.getContext().openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
        Log.i("INFORMACAO BD", "criou ou abriu");
        Cursor c = buscar("sqlite_master", null, "type = 'table'", "");
        if (c.getCount() == 1) {
            for (int i = 0; i < SCRIPT_DATA_BASE_CREATE.length; i++)
                db.execSQL(SCRIPT_DATA_BASE_CREATE[i]);
            Log.i("BANCO_DADOS", "Criou tabelas e as populou");
        }
        c.close();
        Log.i("BANCO_DADOS", "Abriu conexao com o bd");
    }

    public long inserir(String tabelas, ContentValues valores) {
        long id = db.insert(tabelas, null, valores);
        return id;
    }

    public int atualizar(String tabelas, ContentValues valores, String where) {
        int count = db.update(tabelas, valores, where, null);
        Log.i("BANCO_DADOS", "Atualizou [" + count + "] registros");
        return count;
    }

    public int deletar(String tabelas, String where) {
        int id = db.delete(tabelas, where, null);
        return id;
    }

    public Cursor buscar(String tabelas, String[] colunas, String where, String orderBy) {
        Cursor c;
        if (!where.equals(""))
            c = db.query(tabelas, colunas, where, null, null, null, null, orderBy);
        else
            c = db.query(tabelas, colunas, null, null, null, null, orderBy);
        return c;
    }

    public void abrir() {

    }

    public void fechar() {

    }

}
