package com.da.irc.Servicios;

import com.google.gson.Gson;
import com.da.irc.Entidades.Banner;
import com.da.irc.Entidades.Data;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class Receptor{
    
    private final Controlador controlador;
    
    public Receptor(Controlador controlador) {
        this.controlador = controlador;
    }
    
    public void run() {
        try {
            
            String ip = Config.get("server.ip");
            int puerto = Integer.parseInt(Config.get("server.port"));
            
            Selector selector = Selector.open();
            
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(ip, puerto));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            
            while(true) {
                // El metodo select bloquea la ejecucion del programa hasta que se reciba una interaccion por socket
                selector.select();

                // Obtener las claves relacionadas a los socket
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    
                    // Recibir nueva conexion
                    if (key.isAcceptable()) {
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverChannel.accept();
                        socketChannel.configureBlocking(false);
                        
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        socketChannel.write(Banner.getBanner());
                        System.out.println(socketChannel.getRemoteAddress());
                    } 
                    // Leer datos de un socket
                    else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int bytesRead = socketChannel.read(buffer);
                        
                        // Verificar si se cerro la conexion
                        if (bytesRead == -1) {
                            key.cancel();
                            socketChannel.close();
                            controlador.eliminar(socketChannel);
                            continue;
                        }
                        
                        // Procesar los datos recibidos
                        buffer.flip();
                        byte[] bytes = new byte[bytesRead];
                        
                        String json = new String(bytes, StandardCharsets.UTF_8);
                        Gson gson = new Gson();
                        Data data = gson.fromJson(json, Data.class);
                        
                        switch (data.op){
                            case '0' -> controlador.registrar(data, socketChannel);
                            case '1' -> controlador.enviar(data);
                        }
                    }
                    // Eliminar la clave seleccionada para que no se vuelva a procesar
                    keyIterator.remove();
                }
            }
        } catch (IOException ex) {
            System.out.println("Error en el receptor. ERROR: " + ex);
        }
    }
}
