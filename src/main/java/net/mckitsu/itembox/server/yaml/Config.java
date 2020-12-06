package net.mckitsu.itembox.server.yaml;

import lombok.Data;

@Data
public class Config {
    private String ipAddress;
    private int port;
    private String verifyKey;
}
