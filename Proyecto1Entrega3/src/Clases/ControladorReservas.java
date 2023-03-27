package Clases;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ControladorReservas {
    
    private ArrayList<Reserva> reservas;
    
    

    public ControladorReservas(){

        this.reservas = new ArrayList<Reserva>();
        

    }

    public Reserva crearReserva(ArrayList<ArrayList<String>> infoHuespedes, String fechaInicial, String fechaFinal, Habitacion habitacion) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        RangoFechas rangoFecha = new RangoFechas(sdf.parse(fechaInicial), sdf.parse(fechaFinal));
        ArrayList<Huesped> huespedes = new ArrayList<Huesped>();
        for(int i=0;i<infoHuespedes.size();i++){
            String nombre = infoHuespedes.get(i).get(0);
            int documento = Integer.parseInt(infoHuespedes.get(i).get(1));
            String email = infoHuespedes.get(i).get(2);
            String celular = (infoHuespedes.get(i).get(3));
            boolean necesitaCama = Boolean.parseBoolean(infoHuespedes.get(i).get(4));
            Huesped huesped = new Huesped(nombre, documento, email, celular, necesitaCama);
            huespedes.add(huesped);
        }
        Reserva reserva = new Reserva(rangoFecha,huespedes,habitacion, reservas.size());
        reservas.add(reserva);

        return reserva;
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

    public Reserva getReservaId(int id){
        return reservas.get(id-1);
    }
    public ArrayList<Reserva> getReservaDocumento(int documento){
        ArrayList<Reserva> reservasDoc = new ArrayList<Reserva>();
        for(int i=0;i<reservas.size();i++){
            for(int r = 0; r < reservas.get(i).getHuespedes().size();r++){
                if(reservas.get(i).getHuespedes().get(r).getDocumento() == documento){
                    reservasDoc.add(reservas.get(i));
                }
            }
        }
        return reservasDoc;
    }
}


