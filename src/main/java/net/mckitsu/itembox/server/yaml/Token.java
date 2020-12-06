package net.mckitsu.itembox.server.yaml;

import lombok.Data;

import java.util.List;

@Data
public class Token {
    private List<String> full;
    private List<String> readOnly;
}
