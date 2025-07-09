package top.outlands.nonupdate;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Set;

public class NonUpdateService implements ITransformationService {

    public static File configFile = null;
    public static NonUpdateConfig config = null;
    public static final Logger logger = LogManager.getLogger("NonUpdate Modern");

    @Override
    public @NotNull String name() {
        return "NonUpdate Modern";
    }

    @Override
    public void initialize(IEnvironment iEnvironment) {
        logger.info("NonUpdate initializing");
        iEnvironment.getProperty(IEnvironment.Keys.GAMEDIR.get()).ifPresent(path -> configFile = new File(new File(path.toFile(), "config"), "nonupdate-modern.json"));
        if (configFile != null) {
            logger.info("Using config file {}", configFile.getPath());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if (configFile.exists()) {
                try {
                    config = gson.fromJson(new FileReader(configFile), NonUpdateConfig.class);
                } catch (FileNotFoundException e) {
                    logger.error("Failed to read config json", e);
                }
            } else {
                try {
                    Files.touch(configFile);
                    config = new NonUpdateConfig(new String[]{}, false);
                    FileWriter writer = new FileWriter(configFile);
                    gson.toJson(config, writer);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    logger.error("Failed to create json config", e);
                }
            }
        }

    }

    @Override
    public void onLoad(IEnvironment iEnvironment, Set<String> set) throws IncompatibleEnvironmentException {

    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull List<ITransformer> transformers() {
        return List.of(new URLTransformer());
    }

    public static URLConnection openConnection(URL url) throws IOException {
        if (config.debugMode) {
            StackTraceElement[] array = Thread.currentThread().getStackTrace();
            logger.info("Connection to {}, called by {} using openConnection", url.toString(), array[array.length - 2].getClassName());
        }
        throw new IOException("Connection blocked by NonUpdate");

    }

    public static InputStream openStream(URL url) throws IOException {
        if (config.debugMode) {
            StackTraceElement[] array = Thread.currentThread().getStackTrace();
            logger.info("Connection to {}, called by {} using openStream", url.toString(), array[array.length - 2].getClassName());
        }
        throw new IOException("Connection blocked by NonUpdate");

    }
}
