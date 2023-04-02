package Clases;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class ControladorHuespedes {
    private ArrayList<Huesped> huespedes;

    public ControladorHuespedes() {
        this.huespedes = new ArrayList<Huesped>();
    }
    public void cargarHuespedes() throws FileNotFoundException, IOException{
        try (BufferedReader br = new BufferedReader(new FileReader("Proyecto1Entrega3/Datos/Huespedes.txt"))) {
            String st;
            br.readLine();
            while ((st = br.readLine()) != null) {
                String[] split = st.split(";");
                Huesped huesped = new Huesped(split[0], Long.parseLong(split[1]), split[2], split[3], Boolean.parseBoolean(split[4]));
                this.huespedes.add(huesped);}}
    }
    public Huesped getHuesped(String nombre, long documento, String email, String celular, boolean necesitaCama){
        Huesped huesped = null;
        for(int i = 0; i < huespedes.size();i++){
            if(huespedes.get(i).getNombre().equals(nombre) && (huespedes.get(i).getDocumento() == documento)){
                huesped = huespedes.get(i);
            }
        }
        if(huesped == null){
            huesped = new Huesped(nombre, documento, email, celular, necesitaCama);
            try {
                Files.write(Paths.get("Proyecto1Entrega3/Datos/Huespedes.txt"),("\n"+nombre+";"+documento+";"+email+";"+celular+";"+necesitaCama).getBytes(), StandardOpenOption.APPEND );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return huesped;
    }
    public String generarArchivoLog(ArrayList<Huesped> huespedes){
        String str = "";
        for(int i=0;i<huespedes.size();i++){
            Huesped huesped = huespedes.get(i);
            str += "Huesped " + (i+1) + ":\n";
            for(int rsv = 0; rsv< huesped.getHistorialReserva().size();rsv++){
                Reserva reserva = huesped.getHistorialReserva().get(rsv);
                Habitacion habitacion = reserva.getHabitacion();
                str += "    Reserva " + (rsv +1) + ":\n";
                str += "    Habitación " + habitacion.getTipoHabitacion() + " para " + habitacion.getEspacio() + " personas.\n";
                str += "    Reserva en el rango de fechas: " + reserva.getRangoFecha() +".\n    Huespedes de la reserva:\n";
                for(int hsp = 0; hsp< reserva.getHuespedes().size();hsp++){
                    Huesped huesped2 = reserva.getHuespedes().get(hsp);
                    str += "        Huesped " + (hsp+1) +":\n   Nombre:" + huesped2.getNombre() + ".\n      " + huesped2.getDocumento()+".\n";
                }
            }
        }
        return str;
    }
    
}
