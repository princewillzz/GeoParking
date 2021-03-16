package xyz.willz.geoparking.service.storage;

public class StorageFileNotFoundException extends StorageException {

    private static final long serialVersionUID = 1L;

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}