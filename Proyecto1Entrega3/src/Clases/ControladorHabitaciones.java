package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ControladorHabitaciones {
    private ArrayList<Habitacion> habitaciones;

    public ControladorHabitaciones(){
        this.habitaciones = new ArrayList<Habitacion>();
    }
    public void cargarArchivoHabitaciones(File ruta_archivoHabitacion, File ruta_archivoCamas) throws NumberFormatException, IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(ruta_archivoHabitacion))) {
            String st;
            br.readLine();
            while ((st = br.readLine()) != null) {
                String[] split = st.split(";");
                Habitacion habitacion = new Habitacion(Integer.parseInt(split[0]), split[1], Boolean.parseBoolean(split[2]), 
                    Boolean.parseBoolean(split[3]),Boolean.parseBoolean(split[4]),split[5]);
                this.habitaciones.add(habitacion);}}
        try (BufferedReader br = new BufferedReader(new FileReader(ruta_archivoCamas))) {
            String st;
            br.readLine();
            while ((st = br.readLine()) != null) {
                String[] split = st.split(";");
                int id = Integer.parseInt(split[0]);
                Cama cama = new Cama(split[1],Integer.parseInt(split[2]),Boolean.parseBoolean(split[3]));
                this.habitaciones.get(id-1).addCama(cama);}}
    }
    public void crearHabitacion(String ubicacion, boolean balcon, boolean vista, boolean cocinaIntegrada,
        String tipoHabitacion, ArrayList<ArrayList<String>> infoCamas) {
            Habitacion habitacion = new Habitacion((habitaciones.size()+1), ubicacion, balcon, vista, cocinaIntegrada, tipoHabitacion);
            for(int i=0; i<infoCamas.size();i++){
                ArrayList<String> info = infoCamas.get(i);
                Cama cama = new Cama(info.get(0),Integer.parseInt(info.get(1)),Boolean.parseBoolean(info.get(2)));
                habitacion.addCama(cama);}
            this.habitaciones.add(habitacion);
    }
}
