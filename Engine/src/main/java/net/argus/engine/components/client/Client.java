package net.argus.engine.components.client;

import java.util.UUID;

public class Client {

    private UUID UUID;
    private String name;

    private ClientRank rank;

    public Client() {}

    public Client(UUID UUID, String name, ClientRank rank) {
        this.UUID = UUID;
        this.name = name;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "client[uuid=" + UUID.toString() + ", name=" + name + ", rank=" + rank.getName().toUpperCase() + "]";
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
