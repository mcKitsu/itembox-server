package net.mckitsu.itembox.server.terminal;

import net.mckitsu.lib.terminal.Terminal;

public interface CommandLineEvent {
    boolean onStart(Terminal terminal);

    void onLoad(Terminal terminal);

    void onFinish(Terminal terminal);

    void onStop(Terminal terminal);
}
