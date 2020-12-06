package net.mckitsu.itembox.server;

import net.mckitsu.itembox.server.yaml.Token;
import net.mckitsu.lib.file.YamlManager;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String format = "%1$tF %1$tT [%4$s] %5$s%6$s%n";

    public static void main(String[] args){
        System.setProperty("java.util.logging.SimpleFormatter.format", format);
        Server server = new Server();
        server.start();
    }
}
