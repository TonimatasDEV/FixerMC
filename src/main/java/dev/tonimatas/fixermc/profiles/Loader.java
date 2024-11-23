package dev.tonimatas.fixermc.profiles;

public enum Loader {
    VANILLA("Vanilla"),
    FORGE("Forge"),
    NEOFORGE("NeoForge"),
    FABRIC("Fabric"),
    QUILT("Quilt");
    
    
    public final String name;
    Loader(String name) {
        this.name = name;
    }
}
