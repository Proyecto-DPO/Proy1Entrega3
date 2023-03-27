package Clases;

public class Servicio {
    private String nombreServicio;
    private String tipoCobro;
    private String lugarServicio;
    private double precio;
    private boolean pagado = false;

    public Servicio(String nombreServicio, String tipoCobro, String lugarServicio, double precio) {
        this.nombreServicio = nombreServicio;
        this.tipoCobro = tipoCobro;
        this.lugarServicio = lugarServicio;
        this.precio = precio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getTipoCobro() {
        return tipoCobro;
    }

    public void setTipoCobro(String tipoCobro) {
        this.tipoCobro = tipoCobro;
    }
    public String getLugarServicio() {
        return lugarServicio;
    }

    public void setLugarServicio(String lugarServicio) {
        this.lugarServicio = lugarServicio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    
    

}
