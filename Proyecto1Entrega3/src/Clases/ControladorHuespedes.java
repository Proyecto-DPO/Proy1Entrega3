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
            //nombre;documento;email;celular;necesitaCama
            String st;
            br.readLine();
            while ((st = br.readLine()) != null) {
                String[] split = st.split(";");
                Huesped huesped = new Huesped(split[0], Integer.parseInt(split[1]), split[2], split[3], Boolean.parseBoolean(split[4]));
                this.huespedes.add(huesped);}}
    }
    public Huesped getHuesped(String nombre, int documento, String email, String celular, boolean necesitaCama){
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
    public void generarArchivoLog(ArrayList<Huesped> huespedes){
        for(int i=0;i<huespedes.size();i++){
            //Huesped huesped = huespedes.get(i);

        }
    }
    
}
