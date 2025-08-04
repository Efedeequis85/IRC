package com.da.irc.Entidades;

public class Data {
    public char op;
    public String username;
    public String canal;
    public String mensaje;
    
    public Data(char op, String username, String canal, String mensaje) 
    {
        this.op = op;
        this.username = username;
        this.canal = canal;
        this.mensaje = mensaje;
    }
}
