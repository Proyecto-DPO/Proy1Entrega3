package Clases;

import java.util.Date;

public class RangoFechas {

    private Date fechaInicial;
    private Date fechaFinal;
    
    public RangoFechas(Date fechaInicial, Date fechaFinal) {
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }
    
    public boolean fechaEnRango(Date fecha){
        boolean enRango = false;
        if(this.fechaInicial.after(fecha) && this.fechaFinal.before(fecha)){
            enRango = true;
        }
        return enRango;
    }
}
