import org.jspecify.annotations.NullMarked;

@NullMarked
module fr.fidorial.auth {
    exports fr.euphyllia.fidorial.auth;

    requires com.google.gson;
    requires java.net.http;

    requires static org.jspecify;
}