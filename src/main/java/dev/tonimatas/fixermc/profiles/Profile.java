package dev.tonimatas.fixermc.profiles;

public class Profile {
    public String name;
    public String version;
    public Loader loader;
    public String loaderVersion;
    public String description;
    
    public Profile(String name, String version, Loader loader, String loaderVersion, String description) {
        this.name = name;
        this.version = version;
        this.loader = loader;
        this.loaderVersion = loaderVersion;
        this.description = description;
    }
    
    public String getText() {
        return "Name: " + name + "\n" + "Loader: " + loader.name + "\n" + (loader != Loader.VANILLA ? "Loader version: " + loaderVersion + "\n" : "")  + "Version: " + version + "\n" + "Description: " + description + "\n";
    }
}
