package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ControladorHabitaciones {
    private ArrayList<Habitacion> habitaciones;
    private HashMap<String,ArrayList<Tarifa>> tarifasExistentes;

    public ControladorHabitaciones(){
        this.habitaciones = new ArrayList<Habitacion>();
        this.tarifasExistentes = new HashMap<String,ArrayList<Tarifa>>();
        this.tarifasExistentes.put("estandar", new ArrayList<Tarifa>());
        this.tarifasExistentes.put("suite", new ArrayList<Tarifa>());
        this.tarifasExistentes.put("suite doble", new ArrayList<Tarifa>());
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

            int id = (habitaciones.size()+1);
            Habitacion habitacion = new Habitacion(id, ubicacion, balcon, vista, cocinaIntegrada, tipoHabitacion);
            for(int i=0; i<infoCamas.size();i++){
                ArrayList<String> info = infoCamas.get(i);
                Cama cama = new Cama(info.get(0),Integer.parseInt(info.get(1)),Boolean.parseBoolean(info.get(2)));
                habitacion.addCama(cama);
            }
            for(int i=0;i< habitacion.getCamas().size();i++){
                Cama cama = habitacion.getCamas().get(i);
                if(i==0){
                try {
                    Files.write(Paths.get("Proyecto1Entrega3/Datos/Camas.txt"),(id+";"+cama.getTamaño()+";"+cama.getCantidadPersonas()+";"+cama.isSoloNiños()).getBytes(), StandardOpenOption.APPEND );
                } catch (IOException e) {
                    e.printStackTrace();
                }}
                else if(i == habitacion.getCamas().size()-1){
                    try {
                        Files.write(Paths.get("Proyecto1Entrega3/Datos/Camas.txt"),("\n"+id+";"+cama.getTamaño()+";"+cama.getCantidadPersonas()+";"+cama.isSoloNiños()+"\n").getBytes(), StandardOpenOption.APPEND );
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        Files.write(Paths.get("Proyecto1Entrega3/Datos/Camas.txt"),("\n"+id+";"+cama.getTamaño()+";"+cama.getCantidadPersonas()+";"+cama.isSoloNiños()).getBytes(), StandardOpenOption.APPEND );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            this.habitaciones.add(habitacion);
            try {
                Files.write(Paths.get("Proyecto1Entrega3/Datos/Habitaciones.txt"),("\n"+id+";"+habitacion.getUbicacion()+";"+habitacion.isBalcon()+";"+habitacion.isVista()+";"+habitacion.isCocinaIntegrada()+";"+habitacion.getTipoHabitacion()).getBytes(), StandardOpenOption.APPEND );
            } catch (IOException e) {
                
                e.printStackTrace();
            }
            
    }
    public void cargarTarifaServicio(String tipoHabitacion, double valorTarifa, String fechaInicial, String fechaFinal,
            String dias) throws ParseException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateInicial = sdf.parse(fechaInicial);
                Date dateFinal = sdf.parse(fechaFinal);
                Tarifa tarifa = new Tarifa(dias, valorTarifa, tipoHabitacion, dateInicial, dateFinal);
                this.tarifasExistentes.get(tipoHabitacion).add(tarifa);   
                try {
                    Files.write(Paths.get("Proyecto1Entrega3/Datos/Tarifas.txt"),("\n"+dias+";"+valorTarifa+";"+tipoHabitacion+";"+fechaInicial+";"+fechaFinal).getBytes(), StandardOpenOption.APPEND );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
    }
    public String numDayToString(int numero){
        String String = "";
        if(numero == 1){String = "D";}
        else if(numero == 2){String = "L";}
        else if(numero == 3){String = "M";}
        else if(numero == 4){String = "X";}
        else if(numero == 5){String = "J";}
        else if(numero == 6){String = "V";}
        else if(numero == 7){String = "S";}
        return String;
    }
    public String tarifasSinDefinirProximoAño(){
        String[] keys = {"estandar","suite","suite doble"};
        HashMap<String, ArrayList<String>> tarifasSinDefinir = new HashMap<String, ArrayList<String>>();
        tarifasSinDefinir.put("estandar", new ArrayList<String>());
        tarifasSinDefinir.put("suite", new ArrayList<String>());
        tarifasSinDefinir.put("suite doble", new ArrayList<String>());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar hoy = Calendar.getInstance();
        for(int i=0; i<365;i++){
            String diaSemana = numDayToString(hoy.get(Calendar.DAY_OF_WEEK));
            for(int key = 0; key<keys.length;key++){
                boolean algunaAplica  = false;
                for(int pos = 0; pos< tarifasExistentes.get(keys[key]).size();pos++){
                    boolean enRango = tarifasExistentes.get(keys[key]).get(pos).getRangoFechas().fechaEnRango(hoy.getTime()); 
                    boolean aplicaDia = tarifasExistentes.get(keys[key]).get(pos).tarifaAplicaDia(diaSemana);
                    if(enRango == true && aplicaDia == true){
                            algunaAplica = true;}}
            if(algunaAplica == false){
                tarifasSinDefinir.get(keys[key]).add(" "+diaSemana +":"+ sdf.format(hoy.getTime()));}
            }
            hoy.add(Calendar.DAY_OF_YEAR, 1);}
        
        String retorno = "";
        for(int key = 0; key<keys.length;key++){
            retorno += keys[key] + ":";
            for(int pos = 0; pos<tarifasSinDefinir.get(keys[key]).size();pos++){
                retorno += tarifasSinDefinir.get(keys[key]).get(pos);
            }
            retorno += ".\n";
        }

        return retorno;
    }
    public Habitacion getHabitacion(int id){
        return this.habitaciones.get(id-1);
    }

    public String consultarInventario(){
        String retorno = "";
        for(int i=0;i<habitaciones.size();i++){
            retorno += habitaciones.get(i).textoInventario() + "\n";
        }

        return retorno;
    }
    public void cargarTarifas() throws FileNotFoundException, IOException, ParseException{
        try (BufferedReader br = new BufferedReader(new FileReader("Proyecto1Entrega3/Datos/Tarifas.txt"))) {
            String st;
            br.readLine();
            while ((st = br.readLine()) != null) {
                String[] split = st.split(";");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateInicial = sdf.parse(split[3]);
                Date dateFinal = sdf.parse(split[4]);
                Tarifa tarifa = new Tarifa(split[0], Double.parseDouble(split[1]), split[2], dateInicial, dateFinal);
                tarifasExistentes.get(split[2]).add(tarifa);}}
    }
}
