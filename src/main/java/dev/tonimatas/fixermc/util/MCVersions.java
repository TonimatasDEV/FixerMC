package dev.tonimatas.fixermc.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.tonimatas.fixermc.Constants;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCVersions {
    public static JsonObject getMinecraftVersion(String versionId) {
        return getMinecraftVersions().get(versionId);
    }

    public static List<String> getMinecraftVersionList() {
        return getMinecraftVersions()
                .values()
                .stream()
                .sorted((o1, o2) -> {
                    String o1releaseTime = o1.get("releaseTime").getAsString();
                    String o2releaseTime = o2.get("releaseTime").getAsString();

                    return ZonedDateTime.parse(o2releaseTime).compareTo(ZonedDateTime.parse(o1releaseTime));
                })
                .map(jsonObject -> jsonObject.get("id").getAsString())
                .toList();
    }

    public static Map<String, JsonObject> getMinecraftVersions() {
        Map<String, JsonObject> versions = new HashMap<>();
        JsonObject minecraftManifest = FixerUtils.getURLAsJsonObject(Constants.MINECRAFT_VERSION_MANIFEST);

        if (minecraftManifest == null) {
            FixerDialogs.showError("Error getting the Minecraft versions. Please try again later.");
            return Map.of();
        }

        try {
            for (JsonElement versionJsonElement : minecraftManifest.getAsJsonArray("versions").asList()) {
                JsonObject versionJson = versionJsonElement.getAsJsonObject();

                String version = versionJson.get("id").getAsString();

                if (!FixerUtils.hasLetter(version)) {
                    versions.put(version, versionJson);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return versions;
    }
}
