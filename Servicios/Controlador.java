package com.da.irc.Servicios;

import com.da.irc.Entidades.Canal;
import com.da.irc.Entidades.Data;
import com.da.irc.Entidades.Usuario;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Optional;

public class Controlador {  
    private final List<Canal> canales;
     
    public Controlador(List<Canal> canales) {
        this.canales = canales;
    }
    
    public void registrar(Data data, SocketChannel s) {
        Usuario usuario = new Usuario(data.username, s);
        
        for(Canal canal:canales){
            if(canal.name.equals(data.canal)){
                canal.registrar(usuario);
                return;
            }
        }
    }
    
    public void eliminar(SocketChannel s) {

    }
    
    public void enviar(Data data) {
        Optional<Canal> resultado = canales.stream()
            .filter(p -> data.canal.equals(p.name))
            .findFirst();
    
        if (resultado.isPresent())
            resultado.get().broadcast(data);
        else
            System.out.println("No se encontró la persona.");
    }
}
