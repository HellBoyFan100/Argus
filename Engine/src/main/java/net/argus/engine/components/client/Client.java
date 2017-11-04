package net.argus.engine.components.client;

import java.util.UUID;

public class Client {

    private final UUID UUID;
    private final String name;

    private ClientRank rank;

    public Client(UUID UUID, String name, ClientRank rank) {
        this.UUID = UUID;
        this.name = name;
        this.rank = rank;
    }

    public UUID getUUID() {
        return UUID;
    }

    public String getName() {
        return name;
    }

    public ClientRank getRank() {
        return rank;
    }

}
