package top.outlands.nonupdate;

public class NonUpdateConfig {
    public NonUpdateConfig(String[] targets, boolean debugMode) {
        this.targets = targets;
        this.debugMode = debugMode;
    }
    public String[] targets;
    public boolean debugMode;
}
