package net.mckitsu.itembox.server.network;

import net.mckitsu.itembox.server.ItemBox;
import net.mckitsu.lib.network.net.NetClient;
import net.mckitsu.lib.network.net.NetServer;

import java.net.InetSocketAddress;

public class ServiceServer extends NetServer {

    public ServiceServer(byte[] verifyKey) {
        super(verifyKey);
    }

    @Override
    public void start(InetSocketAddress hostAddress) {
        ItemBox.getLogger().info("[Network] begin listener address " + hostAddress);
        super.start(hostAddress);
    }

    protected void onAccept(NetClient netClient) {
        ItemBox.getLogger().info("[Network] new connect from " + netClient.getRemoteAddress());
    }

    @Override
    public void stop() {
        ItemBox.getLogger().info("[Network] stop listener");
        super.stop();
    }
}
