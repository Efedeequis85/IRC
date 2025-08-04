package com.da.irc;

import com.da.irc.Entidades.Canal;
import com.da.irc.Servicios.Controlador;
import com.da.irc.Servicios.Receptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException {
        List<Canal> canales = new ArrayList();
        Controlador controlador = new Controlador(canales);
        Receptor recv = new Receptor(controlador);
        recv.run();
    }
}
