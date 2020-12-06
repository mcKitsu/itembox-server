package net.mckitsu.itembox.server.loader;

import net.mckitsu.itembox.server.ItemBox;
import net.mckitsu.itembox.server.yaml.Config;
import net.mckitsu.itembox.server.yaml.Token;
import net.mckitsu.lib.file.FileManager;
import net.mckitsu.lib.file.ResourceManager;

import java.net.URISyntaxException;

public class ResourceLoader {
    public YamlLoader<Config> config;
    public YamlLoader<Token> token;

    public ResourceLoader(){
        try {
            this.config = new YamlLoader<Config>(Config.class,
                    new ResourceManager(ClassLoader.getSystemClassLoader(), "",  "config.yml"),
                    new FileManager("", "config.yml")){
                @Override
                public boolean load(){
                    boolean result = super.load();

                    if(!result)
                        return false;

                    if(this.yaml.getIpAddress()==null)
                        this.yaml.setIpAddress("localhost");

                    return true;
                }
            };

            this.token = new YamlLoader<Token>(Token.class,
                    new ResourceManager(ClassLoader.getSystemClassLoader(), "",  "token.yml"),
                    new FileManager("", "token.yml"));

        } catch (URISyntaxException e) {
            this.config = null;
            e.printStackTrace();
        }
    }

    public boolean load(){
        if(!config.load()){
            ItemBox.getLogger().severe("[ResourceLoader] load \"config.yml\" fail, format not match!");
            return false;
        }
        ItemBox.getLogger().info("[ResourceLoader] load \"config.yml\" success");

        if(!token.load()){
            ItemBox.getLogger().severe("[ResourceLoader] load \"token.yml\" fail, format not match!");
            return false;
        }
        ItemBox.getLogger().info("[ResourceLoader] load \"token.yml\" success");
        return true;
    }
}
