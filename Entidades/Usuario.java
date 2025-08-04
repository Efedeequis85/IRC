package com.da.irc.Entidades;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Usuario {
    public SocketChannel socketChannel;
    public String username;

    public Usuario(String username, SocketChannel socket) {
        this.socketChannel = socket;
        this.username = username;
    }
    
    public void send(String mensaje) 
    {
        byte[] datos = mensaje.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(datos);
        try {
            socketChannel.write(buffer);
        } catch (IOException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
