package dev.tonimatas.fixermc.sessions;

import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import net.raphimc.minecraftauth.step.java.session.StepFullJavaSession;

public record OnlineAccount(StepFullJavaSession.FullJavaSession session) implements Account {
    @Override
    public AuthInfos getAuthInfos() {
        return new AuthInfos(session.getMcProfile().getName(), session.getMcProfile().getMcToken().getAccessToken(), session.getMcProfile().getId().toString());
    }

    @Override
    public boolean isExpired() {
        return session.isExpiredOrOutdated() && session.getMcProfile().isExpired() && session.getMcProfile().getMcToken().isExpired();
    }

    @Override
    public boolean isOnline() {
        return true;
    }
}
