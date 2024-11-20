package dev.tonimatas.fixermc.sessions;

import fr.theshark34.openlauncherlib.minecraft.AuthInfos;

public record OfflineAccount(String name) implements Account {
    @Override
    public AuthInfos getAuthInfos() {
        return new AuthInfos(name, "", "");
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public boolean isOnline() {
        return false;
    }
}
