package repository;

import model.StorageType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigRepository {
    private final Path dataDir;
    private final Path configFile;

    public ConfigRepository() {
        this.dataDir = Path.of(System.getProperty("user.dir"), "data");
        this.configFile = this.dataDir.resolve("config.properties");
        try {
            Files.createDirectories(this.dataDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StorageType getStorageType() {
        if (!Files.exists(this.configFile)) {
            setStorageType(StorageType.TXT);
            return StorageType.TXT;
        }
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(this.configFile)) {
            properties.load(inputStream);
            return StorageType.fromString(properties.getProperty("storage"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStorageType(StorageType storageType) {
        Properties properties = new Properties();
        properties.setProperty("storage", storageType.name());
        try (OutputStream outputStream = Files.newOutputStream(this.configFile)) {
            properties.store(outputStream, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
