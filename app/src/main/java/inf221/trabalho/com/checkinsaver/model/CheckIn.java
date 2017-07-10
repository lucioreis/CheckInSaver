package inf221.trabalho.com.checkinsaver.model;

/**
 * Created by lucio on 7/9/2017.
 */

public class CheckIn {
    private String nomeDoLocal;
    private int qtdVisitas;
    private double latitude;
    private double longitude;
    private Categoria categoria;

    public CheckIn(){}

    public CheckIn(String nomeDoLocal, int qtdVisitas, double latitude, double longitude, Categoria categoria){
        this.nomeDoLocal = nomeDoLocal;
        this.qtdVisitas = qtdVisitas;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categoria = categoria;
    }
    public String getNomeDoLocal() {
        return nomeDoLocal;
    }

    public void setNomeDoLocal(String nomeDoLocal) {
        this.nomeDoLocal = nomeDoLocal;
    }

    public int getQtdVisitas() {
        return qtdVisitas;
    }

    public void setQtdVisitas(int qtdVisitas) {
        this.qtdVisitas = qtdVisitas;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof CheckIn){
            return this.nomeDoLocal.equals(((CheckIn) obj).getNomeDoLocal());
        } else return false;
    }

    @Override
    public String toString(){
        return nomeDoLocal;
    }
}
