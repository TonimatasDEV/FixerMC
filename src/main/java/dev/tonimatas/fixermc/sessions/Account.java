package dev.tonimatas.fixermc.sessions;

import fr.theshark34.openlauncherlib.minecraft.AuthInfos;

public interface Account {
    AuthInfos getAuthInfos();

    boolean isExpired();

    boolean isOnline();
}
