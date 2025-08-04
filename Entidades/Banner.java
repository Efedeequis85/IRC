package com.da.irc.Entidades;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public abstract class Banner {

    public static ByteBuffer getBanner() {
        String banner = """
                ,------.            ,---.                                       \n"
                |  .-.  \\  ,--,--. '   .-' ,---. ,--.--.,--.  ,--. ,---. ,--.--. \n" 
                |  |  \\  :' ,-.  | `.  `-.| .-. :|  .--' \\  `'  /|  .-.:|  .--' \n"
                |  '--'  /\\ '-'  | .-'    \\  --.|  |     \\    / \\  --.|  |    \n"
                `-------'  `--`--' `-----'  `----'`--'       `--'   `----'`--'  ";
                 
                 Ingresa tu nombre de usuario:
                 """;
        
        ByteBuffer byteBuffer = ByteBuffer.wrap(banner.getBytes(StandardCharsets.UTF_8));
        return byteBuffer;
    }
}
