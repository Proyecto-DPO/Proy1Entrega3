package Clases;
import java.util.ArrayList;

public class ControladorReservas {
    
    private ArrayList<Reserva> reservas;

    public ControladorReservas(){

        

    }

    public void crearReserva(int rangoFecha){

        Reserva reserva = new Reserva(rangoFecha);
        reservas.add(reserva);

    }

    public String generarFactura(){

        String factura = "";
        int len = reservas.size();

        for(int i = 0;i<len;i++){


            Reserva reserva = reservas.get(i);
            ArrayList<Huesped> huespedes = reserva.getHuespedes();
            int len2 = huespedes.size();

            for(int j = 0;j<len2;j++){

                Huesped huesped = huespedes.get(j);
                ArrayList<String> huespedInfo = huesped.getInfo();

                int len3 = huespedInfo.size();

                for(int z = 0;z<len3;z++){

                    String line = huespedInfo.get(z);
                    factura += line; 
                    factura += "\n";

                    
                }
                
            }

        }

        //Falta inlcuir la información de los servicios utilizados por el huesped

        return factura;
    }
}

