package http.request;

public enum Protocol {
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0"),
    HTTP_3_0("HTTP/3.0");

    private final String version;

    Protocol(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public static Protocol from(String version) {
        for (Protocol protocol : Protocol.values()) {
            if (protocol.getVersion().equalsIgnoreCase(version)) {
                return protocol;
            }
        }
        throw new IllegalArgumentException("Unexpected version: " + version);
    }
}
