package xyz.willz.geoparking.model;

public enum BookingStatus {
    
    PENDING(Code.PENDING);

    public final String code;

    BookingStatus(String code) {
        this.code = code;
    }

    public class Code {
        public static final String PENDING = "PENDING";
    }

}
