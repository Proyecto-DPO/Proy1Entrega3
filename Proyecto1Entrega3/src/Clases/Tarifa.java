package Clases;

import java.util.Date;

public class Tarifa {
    private String diasSemana;
    private double valorTarifa;
    private String tipoHabitacion;
    
    private RangoFechas rangoFechas;

    public Tarifa(String diasSemana, double valorTarifa, String tipoHabitacion, Date fechaInicial, Date fechaFinal) {
            this.diasSemana = diasSemana;
            this.valorTarifa = valorTarifa;
            this.tipoHabitacion = tipoHabitacion;
            this.rangoFechas = new RangoFechas(fechaInicial,fechaFinal);
        }

    public String getDiasSemana() {
        return diasSemana;
    }

    public double getValorTarifa() {
        return valorTarifa;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public RangoFechas getRangoFechas() {
        return rangoFechas;
    }
    public boolean tarifaAplicaDia(String dia){
        boolean aplica = false;
        for(int i=0;i<this.diasSemana.length();i++){
            if(dia.equals("" + diasSemana.charAt(i))){
                aplica = true;
            } 
        }  
        return aplica;
    }
    
}
