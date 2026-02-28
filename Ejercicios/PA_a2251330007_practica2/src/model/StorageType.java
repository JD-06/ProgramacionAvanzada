package model;

public enum StorageType {
    TXT,
    XML;

    public static StorageType fromString(String value) {
        if (value == null) {
            return TXT;
        }
        return "XML".equalsIgnoreCase(value.trim()) ? XML : TXT;
    }
}
