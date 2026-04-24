package de.madetv1280.craftD3K.managers;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    public boolean isOnCooldown(UUID uuid, long cooldownMillis) {
        if (!cooldowns.containsKey(uuid)) return false;

        long timeElapsed = System.currentTimeMillis() - cooldowns.get(uuid);
        return timeElapsed < cooldownMillis;
    }

    public long getRemainingSeconds(UUID uuid, long cooldownMillis) {
        return getRemainingMilliseconds(uuid, cooldownMillis) / 1000;
    }

    public long getRemainingMilliseconds(UUID uuid, long cooldownMillis) {
        long timeElapsed = System.currentTimeMillis() - cooldowns.get(uuid);
        return cooldownMillis - timeElapsed;
    }

    public void setCooldown(UUID uuid) {
        cooldowns.put(uuid, System.currentTimeMillis());
    }

    // TODO: Call method on PlayerQuitEvent.
    public void cleanUp(UUID uuid) {
        cooldowns.remove(uuid);
    }
}
