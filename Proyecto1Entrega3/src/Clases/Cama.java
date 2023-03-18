package Clases;

public class Cama {
    private String tamaño;
    private boolean soloNiños;
    private int cantidadPersonas;

    public Cama(String tamaño,int cantidadPersonas, boolean soloNiños) {
        this.tamaño = tamaño;
        this.soloNiños = soloNiños;
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getTamaño() {
        return this.tamaño;
    }

    public boolean isSoloNiños() {
        return this.soloNiños;
    }

    public int getCantidadPersonas() {
        return this.cantidadPersonas;
    }
    

    
}
