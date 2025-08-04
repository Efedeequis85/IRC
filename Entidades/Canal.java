package com.da.irc.Entidades;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Canal {
        public String name;
        private final List<Usuario> usuarios;
        
        public Canal(String name, List<Usuario> usuarios) 
        {
            this.name = name;
            this.usuarios = usuarios;
        }
        
        public void registrar(Usuario usuario) 
        {
            usuarios.add(usuario);
        }
        
        public void eliminar(Usuario usuario) 
        {
            usuarios.remove(usuario);
        }
        
        public void broadcast(Data data)
        {
            LocalTime hora = LocalTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
            
            for(Usuario usuario:usuarios)
                if(!usuario.username.equals(data.username))
                    usuario.send(hora.format(formato) + " " + data.username + " : " + data.mensaje);
        }
}
