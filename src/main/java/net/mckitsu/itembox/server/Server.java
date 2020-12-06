package net.mckitsu.itembox.server;


import net.mckitsu.itembox.server.database.Database;
import net.mckitsu.itembox.server.loader.ResourceLoader;
import net.mckitsu.itembox.server.network.ServiceServer;
import net.mckitsu.itembox.server.terminal.CommandLine;
import net.mckitsu.itembox.server.terminal.CommandLineEvent;
import net.mckitsu.lib.terminal.Terminal;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Server implements CommandLineEvent {
    private final ItemBox itemBox;
    private final CommandLine commandLine;
    private ResourceLoader resourceLoader;
    private ServiceServer serviceServer;
    private Database database;

    /* **************************************************************************************
     *  Construct method
     */

    public Server(){
        this.commandLine = new CommandLine();
        this.commandLine.event.setEvent(this);
        this.itemBox = new ItemBox();
        this.itemBox.logger = this.commandLine.getLogger();
        ItemBox.setItemBox(this.itemBox);
    }

    /* **************************************************************************************
     *  Override method
     */
    @Override
    public boolean onStart(Terminal terminal) {

        this.itemBox.stopService = commandLine::stop;
        ItemBox.getLogger().info("[Server] register ItemBox \"stopService\"");

        if(!onStartResourceLoader()){
            ItemBox.getLogger().severe("[StopIssue] ResourceLoader file load fail!");
            ItemBox.stopService();
            return false;
        }

        setItemBoxToken();

        this.database = new Database();

        return true;
    }

    @Override
    public void onLoad(Terminal terminal) {

    }

    @Override
    public void onFinish(Terminal terminal) {
        this.serviceServer = new ServiceServer(resourceLoader.config.yaml.getVerifyKey().getBytes());
        this.serviceServer.start(new InetSocketAddress(resourceLoader.config.yaml.getIpAddress(),
                resourceLoader.config.yaml.getPort()));

        if(!this.database.connect()){
            ItemBox.getLogger().severe("[StopIssue] Database connect fail.");
            ItemBox.stopService();
        }
    }

    @Override
    public void onStop(Terminal terminal) {
        if(this.serviceServer != null)
            this.serviceServer.stop();

        if(this.database != null)
            this.database.close();
    }

    /* **************************************************************************************
     *  Public method
     */
    public void start(){
        this.commandLine.start();
    }

    /* **************************************************************************************
     *  protected method
     */

    /* **************************************************************************************
     *  Private method
     */
    private boolean onStartResourceLoader(){
        this.resourceLoader = new ResourceLoader();
        return this.resourceLoader.load();
    }

    private void setItemBoxToken(){
        Map<String ,String> cache = new HashMap<>();
        for(String token : this.resourceLoader.token.yaml.getFull()){
            ItemBox.getLogger().info(String.format("[Server] add full token \"%s\"", token));
            cache.put(token, "full");
        }

        for(String token : this.resourceLoader.token.yaml.getReadOnly()){
            ItemBox.getLogger().info(String.format("[Server] add readOnly token \"%s\"", token));
            cache.put(token, "readOnly");
        }
        this.itemBox.tokens = cache;
    }

    /* **************************************************************************************
     *  Private method - getter
     */

    /* **************************************************************************************
     *  Private method - construct
     */

}
