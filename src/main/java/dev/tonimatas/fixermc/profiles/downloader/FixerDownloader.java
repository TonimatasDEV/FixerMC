package dev.tonimatas.fixermc.profiles.downloader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.tonimatas.fixermc.Constants;
import dev.tonimatas.fixermc.util.FixerUtils;

import java.nio.file.Path;

public class FixerDownloader {
    public static boolean downloadLibraries(JsonArray libraries) {
        for (JsonElement library : libraries) {
            JsonObject downloadJson = library.getAsJsonObject().getAsJsonObject("downloads");
            JsonObject artifactJson = downloadJson.getAsJsonObject("artifact");
            
            String hash = artifactJson.get("sha1").getAsString();
            String url = artifactJson.get("url").getAsString();
            Path path = Path.of(artifactJson.get("path").getAsString());
            
            if (!FixerUtils.downloadFile(url, Constants.MINECRAFT_LIBRARIES.resolve(path), hash)) {
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean downloadAssets(JsonObject assets) {
        String id = assets.get("id").getAsString();
        String hash = assets.get("sha1").getAsString();
        String url = assets.get("url").getAsString();
        
        if (!FixerUtils.downloadFile(url, Constants.MINECRAFT_ASSETS_INDEXES.resolve(id), hash)) {
            return false;
        }

        JsonObject assetIndex = FixerUtils.getURLAsJsonObject(url);
        
        if (assetIndex == null) return false;
        
        JsonObject objects = assetIndex.getAsJsonObject("objects");
        
        for (String object : objects.keySet()) {
            JsonObject assetJson = objects.getAsJsonObject(object);
            String assetHash = assetJson.get("hash").getAsString();
            
            if (!FixerUtils.downloadAsset(assetHash)) {
                return false;
            }
        }
        
        return true;
    }
}
