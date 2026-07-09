package fr.euphyllia.fidorial.server.protocol;

public final class PacketIds {

    private PacketIds() {
    }

    public static final class Handshake {
        public static final int INTENTION = 0x00;
    }

    public static final class Status {
        public static final class Serverbound {
            public static final int REQUEST = 0x00;
            public static final int PING = 0x01;
        }

        public static final class Clientbound {
            public static final int RESPONSE = 0x00;
            public static final int PONG = 0x01;
        }
    }

    public static final class Login {
        public static final class Serverbound {
            public static final int LOGIN_START = 0x00;
            public static final int ENCRYPTION_RESPONSE = 0x01;
            public static final int LOGIN_ACKNOWLEDGED = 0x03;
        }

        public static final class Clientbound {
            public static final int DISCONNECT = 0x00;
            public static final int ENCRYPTION_REQUEST = 0x01;
            public static final int LOGIN_SUCCESS = 0x02;
            public static final int SET_COMPRESSION = 0x03;
        }
    }
}
