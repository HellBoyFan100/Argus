package net.argus.engine.components.redisson;

public enum RedissonKey {

    NOTIFY_CHAT("notify.chat"),
    NOTIFY_CLIENT("notify.client");

    private final String message;

    RedissonKey(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
