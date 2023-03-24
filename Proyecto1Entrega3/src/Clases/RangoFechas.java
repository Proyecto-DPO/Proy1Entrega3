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
        return fecha.getTime() >= fechaInicial.getTime() && fecha.getTime() <= fechaFinal.getTime();
}
}
