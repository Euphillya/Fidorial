package fr.fidorial.protocol;

public final class PacketTypes {

    private PacketTypes() {
    }

    /**
     * Packets of the HANDSHAKE phase.
     */
    public static final class Handshake {
        private Handshake() {
        }

        /**
         * serverbound packets of the HANDSHAKE phase.
         */
        public static final class Serverbound {
            private Serverbound() {
            }

            /**
             * minecraft:intention (id 0, 0x00).
             */
            public static final PacketType INTENTION =
                    PacketType.of(ConnectionPhase.HANDSHAKE, PacketDirection.SERVERBOUND, "minecraft:intention");
        }
    }

    /**
     * Packets of the STATUS phase.
     */
    public static final class Status {
        private Status() {
        }

        /**
         * clientbound packets of the STATUS phase.
         */
        public static final class Clientbound {
            private Clientbound() {
            }

            /**
             * minecraft:status_response (id 0, 0x00).
             */
            public static final PacketType STATUS_RESPONSE =
                    PacketType.of(ConnectionPhase.STATUS, PacketDirection.CLIENTBOUND, "minecraft:status_response");
            /**
             * minecraft:pong_response (id 1, 0x01).
             */
            public static final PacketType PONG_RESPONSE =
                    PacketType.of(ConnectionPhase.STATUS, PacketDirection.CLIENTBOUND, "minecraft:pong_response");
        }

        /**
         * serverbound packets of the STATUS phase.
         */
        public static final class Serverbound {
            private Serverbound() {
            }

            /**
             * minecraft:status_request (id 0, 0x00).
             */
            public static final PacketType STATUS_REQUEST =
                    PacketType.of(ConnectionPhase.STATUS, PacketDirection.SERVERBOUND, "minecraft:status_request");
            /**
             * minecraft:ping_request (id 1, 0x01).
             */
            public static final PacketType PING_REQUEST =
                    PacketType.of(ConnectionPhase.STATUS, PacketDirection.SERVERBOUND, "minecraft:ping_request");
        }
    }

    /**
     * Packets of the LOGIN phase.
     */
    public static final class Login {
        private Login() {
        }

        /**
         * clientbound packets of the LOGIN phase.
         */
        public static final class Clientbound {
            private Clientbound() {
            }

            /**
             * minecraft:login_disconnect (id 0, 0x00).
             */
            public static final PacketType LOGIN_DISCONNECT =
                    PacketType.of(ConnectionPhase.LOGIN, PacketDirection.CLIENTBOUND, "minecraft:login_disconnect");
            /**
             * minecraft:hello (id 1, 0x01).
             */
            public static final PacketType HELLO =
                    PacketType.of(ConnectionPhase.LOGIN, PacketDirection.CLIENTBOUND, "minecraft:hello");
            /**
             * minecraft:login_finished (id 2, 0x02).
             */
            public static final PacketType LOGIN_FINISHED =
                    PacketType.of(ConnectionPhase.LOGIN, PacketDirection.CLIENTBOUND, "minecraft:login_finished");
            /**
             * minecraft:login_compression (id 3, 0x03).
             */
            public static final PacketType LOGIN_COMPRESSION =
                    PacketType.of(ConnectionPhase.LOGIN, PacketDirection.CLIENTBOUND, "minecraft:login_compression");
            /**
             * minecraft:custom_query (id 4, 0x04).
             */
            public static final PacketType CUSTOM_QUERY =
                    PacketType.of(ConnectionPhase.LOGIN, PacketDirection.CLIENTBOUND, "minecraft:custom_query");
            /**
             * minecraft:cookie_request (id 5, 0x05).
             */
            public static final PacketType COOKIE_REQUEST =
                    PacketType.of(ConnectionPhase.LOGIN, PacketDirection.CLIENTBOUND, "minecraft:cookie_request");
        }

        /**
         * serverbound packets of the LOGIN phase.
         */
        public static final class Serverbound {
            private Serverbound() {
            }

            /**
             * minecraft:hello (id 0, 0x00).
             */
            public static final PacketType HELLO =
                    PacketType.of(ConnectionPhase.LOGIN, PacketDirection.SERVERBOUND, "minecraft:hello");
            /**
             * minecraft:key (id 1, 0x01).
             */
            public static final PacketType KEY =
                    PacketType.of(ConnectionPhase.LOGIN, PacketDirection.SERVERBOUND, "minecraft:key");
            /**
             * minecraft:custom_query_answer (id 2, 0x02).
             */
            public static final PacketType CUSTOM_QUERY_ANSWER =
                    PacketType.of(ConnectionPhase.LOGIN, PacketDirection.SERVERBOUND, "minecraft:custom_query_answer");
            /**
             * minecraft:login_acknowledged (id 3, 0x03).
             */
            public static final PacketType LOGIN_ACKNOWLEDGED =
                    PacketType.of(ConnectionPhase.LOGIN, PacketDirection.SERVERBOUND, "minecraft:login_acknowledged");
            /**
             * minecraft:cookie_response (id 4, 0x04).
             */
            public static final PacketType COOKIE_RESPONSE =
                    PacketType.of(ConnectionPhase.LOGIN, PacketDirection.SERVERBOUND, "minecraft:cookie_response");
        }
    }

    /**
     * Packets of the CONFIGURATION phase.
     */
    public static final class Configuration {
        private Configuration() {
        }

        /**
         * clientbound packets of the CONFIGURATION phase.
         */
        public static final class Clientbound {
            private Clientbound() {
            }

            /**
             * minecraft:cookie_request (id 0, 0x00).
             */
            public static final PacketType COOKIE_REQUEST =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:cookie_request");
            /**
             * minecraft:custom_payload (id 1, 0x01).
             */
            public static final PacketType CUSTOM_PAYLOAD =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:custom_payload");
            /**
             * minecraft:disconnect (id 2, 0x02).
             */
            public static final PacketType DISCONNECT =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:disconnect");
            /**
             * minecraft:finish_configuration (id 3, 0x03).
             */
            public static final PacketType FINISH_CONFIGURATION =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:finish_configuration");
            /**
             * minecraft:keep_alive (id 4, 0x04).
             */
            public static final PacketType KEEP_ALIVE =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:keep_alive");
            /**
             * minecraft:ping (id 5, 0x05).
             */
            public static final PacketType PING =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:ping");
            /**
             * minecraft:reset_chat (id 6, 0x06).
             */
            public static final PacketType RESET_CHAT =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:reset_chat");
            /**
             * minecraft:registry_data (id 7, 0x07).
             */
            public static final PacketType REGISTRY_DATA =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:registry_data");
            /**
             * minecraft:resource_pack_pop (id 8, 0x08).
             */
            public static final PacketType RESOURCE_PACK_POP =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:resource_pack_pop");
            /**
             * minecraft:resource_pack_push (id 9, 0x09).
             */
            public static final PacketType RESOURCE_PACK_PUSH =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:resource_pack_push");
            /**
             * minecraft:store_cookie (id 10, 0x0A).
             */
            public static final PacketType STORE_COOKIE =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:store_cookie");
            /**
             * minecraft:transfer (id 11, 0x0B).
             */
            public static final PacketType TRANSFER =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:transfer");
            /**
             * minecraft:update_enabled_features (id 12, 0x0C).
             */
            public static final PacketType UPDATE_ENABLED_FEATURES =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:update_enabled_features");
            /**
             * minecraft:update_tags (id 13, 0x0D).
             */
            public static final PacketType UPDATE_TAGS =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:update_tags");
            /**
             * minecraft:select_known_packs (id 14, 0x0E).
             */
            public static final PacketType SELECT_KNOWN_PACKS =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:select_known_packs");
            /**
             * minecraft:custom_report_details (id 15, 0x0F).
             */
            public static final PacketType CUSTOM_REPORT_DETAILS =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:custom_report_details");
            /**
             * minecraft:server_links (id 16, 0x10).
             */
            public static final PacketType SERVER_LINKS =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:server_links");
            /**
             * minecraft:clear_dialog (id 17, 0x11).
             */
            public static final PacketType CLEAR_DIALOG =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:clear_dialog");
            /**
             * minecraft:show_dialog (id 18, 0x12).
             */
            public static final PacketType SHOW_DIALOG =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:show_dialog");
            /**
             * minecraft:code_of_conduct (id 19, 0x13).
             */
            public static final PacketType CODE_OF_CONDUCT =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.CLIENTBOUND, "minecraft:code_of_conduct");
        }

        /**
         * serverbound packets of the CONFIGURATION phase.
         */
        public static final class Serverbound {
            private Serverbound() {
            }

            /**
             * minecraft:client_information (id 0, 0x00).
             */
            public static final PacketType CLIENT_INFORMATION =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.SERVERBOUND, "minecraft:client_information");
            /**
             * minecraft:cookie_response (id 1, 0x01).
             */
            public static final PacketType COOKIE_RESPONSE =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.SERVERBOUND, "minecraft:cookie_response");
            /**
             * minecraft:custom_payload (id 2, 0x02).
             */
            public static final PacketType CUSTOM_PAYLOAD =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.SERVERBOUND, "minecraft:custom_payload");
            /**
             * minecraft:finish_configuration (id 3, 0x03).
             */
            public static final PacketType FINISH_CONFIGURATION =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.SERVERBOUND, "minecraft:finish_configuration");
            /**
             * minecraft:keep_alive (id 4, 0x04).
             */
            public static final PacketType KEEP_ALIVE =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.SERVERBOUND, "minecraft:keep_alive");
            /**
             * minecraft:pong (id 5, 0x05).
             */
            public static final PacketType PONG =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.SERVERBOUND, "minecraft:pong");
            /**
             * minecraft:resource_pack (id 6, 0x06).
             */
            public static final PacketType RESOURCE_PACK =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.SERVERBOUND, "minecraft:resource_pack");
            /**
             * minecraft:select_known_packs (id 7, 0x07).
             */
            public static final PacketType SELECT_KNOWN_PACKS =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.SERVERBOUND, "minecraft:select_known_packs");
            /**
             * minecraft:custom_click_action (id 8, 0x08).
             */
            public static final PacketType CUSTOM_CLICK_ACTION =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.SERVERBOUND, "minecraft:custom_click_action");
            /**
             * minecraft:accept_code_of_conduct (id 9, 0x09).
             */
            public static final PacketType ACCEPT_CODE_OF_CONDUCT =
                    PacketType.of(ConnectionPhase.CONFIGURATION, PacketDirection.SERVERBOUND, "minecraft:accept_code_of_conduct");
        }
    }

    /**
     * Packets of the PLAY phase.
     */
    public static final class Play {
        private Play() {
        }

        /**
         * clientbound packets of the PLAY phase.
         */
        public static final class Clientbound {
            private Clientbound() {
            }

            /**
             * minecraft:bundle_delimiter (id 0, 0x00).
             */
            public static final PacketType BUNDLE_DELIMITER =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:bundle_delimiter");
            /**
             * minecraft:add_entity (id 1, 0x01).
             */
            public static final PacketType ADD_ENTITY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:add_entity");
            /**
             * minecraft:animate (id 2, 0x02).
             */
            public static final PacketType ANIMATE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:animate");
            /**
             * minecraft:award_stats (id 3, 0x03).
             */
            public static final PacketType AWARD_STATS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:award_stats");
            /**
             * minecraft:block_changed_ack (id 4, 0x04).
             */
            public static final PacketType BLOCK_CHANGED_ACK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:block_changed_ack");
            /**
             * minecraft:block_destruction (id 5, 0x05).
             */
            public static final PacketType BLOCK_DESTRUCTION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:block_destruction");
            /**
             * minecraft:block_entity_data (id 6, 0x06).
             */
            public static final PacketType BLOCK_ENTITY_DATA =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:block_entity_data");
            /**
             * minecraft:block_event (id 7, 0x07).
             */
            public static final PacketType BLOCK_EVENT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:block_event");
            /**
             * minecraft:block_update (id 8, 0x08).
             */
            public static final PacketType BLOCK_UPDATE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:block_update");
            /**
             * minecraft:boss_event (id 9, 0x09).
             */
            public static final PacketType BOSS_EVENT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:boss_event");
            /**
             * minecraft:change_difficulty (id 10, 0x0A).
             */
            public static final PacketType CHANGE_DIFFICULTY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:change_difficulty");
            /**
             * minecraft:chunk_batch_finished (id 11, 0x0B).
             */
            public static final PacketType CHUNK_BATCH_FINISHED =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:chunk_batch_finished");
            /**
             * minecraft:chunk_batch_start (id 12, 0x0C).
             */
            public static final PacketType CHUNK_BATCH_START =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:chunk_batch_start");
            /**
             * minecraft:chunks_biomes (id 13, 0x0D).
             */
            public static final PacketType CHUNKS_BIOMES =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:chunks_biomes");
            /**
             * minecraft:clear_titles (id 14, 0x0E).
             */
            public static final PacketType CLEAR_TITLES =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:clear_titles");
            /**
             * minecraft:command_suggestions (id 15, 0x0F).
             */
            public static final PacketType COMMAND_SUGGESTIONS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:command_suggestions");
            /**
             * minecraft:commands (id 16, 0x10).
             */
            public static final PacketType COMMANDS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:commands");
            /**
             * minecraft:container_close (id 17, 0x11).
             */
            public static final PacketType CONTAINER_CLOSE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:container_close");
            /**
             * minecraft:container_set_content (id 18, 0x12).
             */
            public static final PacketType CONTAINER_SET_CONTENT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:container_set_content");
            /**
             * minecraft:container_set_data (id 19, 0x13).
             */
            public static final PacketType CONTAINER_SET_DATA =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:container_set_data");
            /**
             * minecraft:container_set_slot (id 20, 0x14).
             */
            public static final PacketType CONTAINER_SET_SLOT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:container_set_slot");
            /**
             * minecraft:cookie_request (id 21, 0x15).
             */
            public static final PacketType COOKIE_REQUEST =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:cookie_request");
            /**
             * minecraft:cooldown (id 22, 0x16).
             */
            public static final PacketType COOLDOWN =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:cooldown");
            /**
             * minecraft:custom_chat_completions (id 23, 0x17).
             */
            public static final PacketType CUSTOM_CHAT_COMPLETIONS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:custom_chat_completions");
            /**
             * minecraft:custom_payload (id 24, 0x18).
             */
            public static final PacketType CUSTOM_PAYLOAD =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:custom_payload");
            /**
             * minecraft:damage_event (id 25, 0x19).
             */
            public static final PacketType DAMAGE_EVENT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:damage_event");
            /**
             * minecraft:debug/block_value (id 26, 0x1A).
             */
            public static final PacketType DEBUG_BLOCK_VALUE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:debug/block_value");
            /**
             * minecraft:debug/chunk_value (id 27, 0x1B).
             */
            public static final PacketType DEBUG_CHUNK_VALUE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:debug/chunk_value");
            /**
             * minecraft:debug/entity_value (id 28, 0x1C).
             */
            public static final PacketType DEBUG_ENTITY_VALUE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:debug/entity_value");
            /**
             * minecraft:debug/event (id 29, 0x1D).
             */
            public static final PacketType DEBUG_EVENT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:debug/event");
            /**
             * minecraft:debug_sample (id 30, 0x1E).
             */
            public static final PacketType DEBUG_SAMPLE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:debug_sample");
            /**
             * minecraft:delete_chat (id 31, 0x1F).
             */
            public static final PacketType DELETE_CHAT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:delete_chat");
            /**
             * minecraft:disconnect (id 32, 0x20).
             */
            public static final PacketType DISCONNECT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:disconnect");
            /**
             * minecraft:disguised_chat (id 33, 0x21).
             */
            public static final PacketType DISGUISED_CHAT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:disguised_chat");
            /**
             * minecraft:entity_event (id 34, 0x22).
             */
            public static final PacketType ENTITY_EVENT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:entity_event");
            /**
             * minecraft:entity_position_sync (id 35, 0x23).
             */
            public static final PacketType ENTITY_POSITION_SYNC =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:entity_position_sync");
            /**
             * minecraft:explode (id 36, 0x24).
             */
            public static final PacketType EXPLODE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:explode");
            /**
             * minecraft:forget_level_chunk (id 37, 0x25).
             */
            public static final PacketType FORGET_LEVEL_CHUNK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:forget_level_chunk");
            /**
             * minecraft:game_event (id 38, 0x26).
             */
            public static final PacketType GAME_EVENT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:game_event");
            /**
             * minecraft:game_rule_values (id 39, 0x27).
             */
            public static final PacketType GAME_RULE_VALUES =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:game_rule_values");
            /**
             * minecraft:game_test_highlight_pos (id 40, 0x28).
             */
            public static final PacketType GAME_TEST_HIGHLIGHT_POS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:game_test_highlight_pos");
            /**
             * minecraft:mount_screen_open (id 41, 0x29).
             */
            public static final PacketType MOUNT_SCREEN_OPEN =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:mount_screen_open");
            /**
             * minecraft:hurt_animation (id 42, 0x2A).
             */
            public static final PacketType HURT_ANIMATION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:hurt_animation");
            /**
             * minecraft:initialize_border (id 43, 0x2B).
             */
            public static final PacketType INITIALIZE_BORDER =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:initialize_border");
            /**
             * minecraft:keep_alive (id 44, 0x2C).
             */
            public static final PacketType KEEP_ALIVE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:keep_alive");
            /**
             * minecraft:level_chunk_with_light (id 45, 0x2D).
             */
            public static final PacketType LEVEL_CHUNK_WITH_LIGHT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:level_chunk_with_light");
            /**
             * minecraft:level_event (id 46, 0x2E).
             */
            public static final PacketType LEVEL_EVENT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:level_event");
            /**
             * minecraft:level_particles (id 47, 0x2F).
             */
            public static final PacketType LEVEL_PARTICLES =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:level_particles");
            /**
             * minecraft:light_update (id 48, 0x30).
             */
            public static final PacketType LIGHT_UPDATE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:light_update");
            /**
             * minecraft:login (id 49, 0x31).
             */
            public static final PacketType LOGIN =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:login");
            /**
             * minecraft:low_disk_space_warning (id 50, 0x32).
             */
            public static final PacketType LOW_DISK_SPACE_WARNING =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:low_disk_space_warning");
            /**
             * minecraft:map_item_data (id 51, 0x33).
             */
            public static final PacketType MAP_ITEM_DATA =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:map_item_data");
            /**
             * minecraft:merchant_offers (id 52, 0x34).
             */
            public static final PacketType MERCHANT_OFFERS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:merchant_offers");
            /**
             * minecraft:move_entity_pos (id 53, 0x35).
             */
            public static final PacketType MOVE_ENTITY_POS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:move_entity_pos");
            /**
             * minecraft:move_entity_pos_rot (id 54, 0x36).
             */
            public static final PacketType MOVE_ENTITY_POS_ROT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:move_entity_pos_rot");
            /**
             * minecraft:move_minecart_along_track (id 55, 0x37).
             */
            public static final PacketType MOVE_MINECART_ALONG_TRACK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:move_minecart_along_track");
            /**
             * minecraft:move_entity_rot (id 56, 0x38).
             */
            public static final PacketType MOVE_ENTITY_ROT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:move_entity_rot");
            /**
             * minecraft:move_vehicle (id 57, 0x39).
             */
            public static final PacketType MOVE_VEHICLE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:move_vehicle");
            /**
             * minecraft:open_book (id 58, 0x3A).
             */
            public static final PacketType OPEN_BOOK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:open_book");
            /**
             * minecraft:open_screen (id 59, 0x3B).
             */
            public static final PacketType OPEN_SCREEN =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:open_screen");
            /**
             * minecraft:open_sign_editor (id 60, 0x3C).
             */
            public static final PacketType OPEN_SIGN_EDITOR =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:open_sign_editor");
            /**
             * minecraft:ping (id 61, 0x3D).
             */
            public static final PacketType PING =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:ping");
            /**
             * minecraft:pong_response (id 62, 0x3E).
             */
            public static final PacketType PONG_RESPONSE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:pong_response");
            /**
             * minecraft:place_ghost_recipe (id 63, 0x3F).
             */
            public static final PacketType PLACE_GHOST_RECIPE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:place_ghost_recipe");
            /**
             * minecraft:player_abilities (id 64, 0x40).
             */
            public static final PacketType PLAYER_ABILITIES =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:player_abilities");
            /**
             * minecraft:player_chat (id 65, 0x41).
             */
            public static final PacketType PLAYER_CHAT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:player_chat");
            /**
             * minecraft:player_combat_end (id 66, 0x42).
             */
            public static final PacketType PLAYER_COMBAT_END =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:player_combat_end");
            /**
             * minecraft:player_combat_enter (id 67, 0x43).
             */
            public static final PacketType PLAYER_COMBAT_ENTER =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:player_combat_enter");
            /**
             * minecraft:player_combat_kill (id 68, 0x44).
             */
            public static final PacketType PLAYER_COMBAT_KILL =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:player_combat_kill");
            /**
             * minecraft:player_info_remove (id 69, 0x45).
             */
            public static final PacketType PLAYER_INFO_REMOVE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:player_info_remove");
            /**
             * minecraft:player_info_update (id 70, 0x46).
             */
            public static final PacketType PLAYER_INFO_UPDATE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:player_info_update");
            /**
             * minecraft:player_look_at (id 71, 0x47).
             */
            public static final PacketType PLAYER_LOOK_AT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:player_look_at");
            /**
             * minecraft:player_position (id 72, 0x48).
             */
            public static final PacketType PLAYER_POSITION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:player_position");
            /**
             * minecraft:player_rotation (id 73, 0x49).
             */
            public static final PacketType PLAYER_ROTATION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:player_rotation");
            /**
             * minecraft:recipe_book_add (id 74, 0x4A).
             */
            public static final PacketType RECIPE_BOOK_ADD =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:recipe_book_add");
            /**
             * minecraft:recipe_book_remove (id 75, 0x4B).
             */
            public static final PacketType RECIPE_BOOK_REMOVE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:recipe_book_remove");
            /**
             * minecraft:recipe_book_settings (id 76, 0x4C).
             */
            public static final PacketType RECIPE_BOOK_SETTINGS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:recipe_book_settings");
            /**
             * minecraft:remove_entities (id 77, 0x4D).
             */
            public static final PacketType REMOVE_ENTITIES =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:remove_entities");
            /**
             * minecraft:remove_mob_effect (id 78, 0x4E).
             */
            public static final PacketType REMOVE_MOB_EFFECT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:remove_mob_effect");
            /**
             * minecraft:reset_score (id 79, 0x4F).
             */
            public static final PacketType RESET_SCORE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:reset_score");
            /**
             * minecraft:resource_pack_pop (id 80, 0x50).
             */
            public static final PacketType RESOURCE_PACK_POP =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:resource_pack_pop");
            /**
             * minecraft:resource_pack_push (id 81, 0x51).
             */
            public static final PacketType RESOURCE_PACK_PUSH =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:resource_pack_push");
            /**
             * minecraft:respawn (id 82, 0x52).
             */
            public static final PacketType RESPAWN =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:respawn");
            /**
             * minecraft:rotate_head (id 83, 0x53).
             */
            public static final PacketType ROTATE_HEAD =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:rotate_head");
            /**
             * minecraft:section_blocks_update (id 84, 0x54).
             */
            public static final PacketType SECTION_BLOCKS_UPDATE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:section_blocks_update");
            /**
             * minecraft:select_advancements_tab (id 85, 0x55).
             */
            public static final PacketType SELECT_ADVANCEMENTS_TAB =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:select_advancements_tab");
            /**
             * minecraft:server_data (id 86, 0x56).
             */
            public static final PacketType SERVER_DATA =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:server_data");
            /**
             * minecraft:set_action_bar_text (id 87, 0x57).
             */
            public static final PacketType SET_ACTION_BAR_TEXT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_action_bar_text");
            /**
             * minecraft:set_border_center (id 88, 0x58).
             */
            public static final PacketType SET_BORDER_CENTER =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_border_center");
            /**
             * minecraft:set_border_lerp_size (id 89, 0x59).
             */
            public static final PacketType SET_BORDER_LERP_SIZE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_border_lerp_size");
            /**
             * minecraft:set_border_size (id 90, 0x5A).
             */
            public static final PacketType SET_BORDER_SIZE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_border_size");
            /**
             * minecraft:set_border_warning_delay (id 91, 0x5B).
             */
            public static final PacketType SET_BORDER_WARNING_DELAY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_border_warning_delay");
            /**
             * minecraft:set_border_warning_distance (id 92, 0x5C).
             */
            public static final PacketType SET_BORDER_WARNING_DISTANCE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_border_warning_distance");
            /**
             * minecraft:set_camera (id 93, 0x5D).
             */
            public static final PacketType SET_CAMERA =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_camera");
            /**
             * minecraft:set_chunk_cache_center (id 94, 0x5E).
             */
            public static final PacketType SET_CHUNK_CACHE_CENTER =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_chunk_cache_center");
            /**
             * minecraft:set_chunk_cache_radius (id 95, 0x5F).
             */
            public static final PacketType SET_CHUNK_CACHE_RADIUS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_chunk_cache_radius");
            /**
             * minecraft:set_cursor_item (id 96, 0x60).
             */
            public static final PacketType SET_CURSOR_ITEM =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_cursor_item");
            /**
             * minecraft:set_default_spawn_position (id 97, 0x61).
             */
            public static final PacketType SET_DEFAULT_SPAWN_POSITION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_default_spawn_position");
            /**
             * minecraft:set_display_objective (id 98, 0x62).
             */
            public static final PacketType SET_DISPLAY_OBJECTIVE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_display_objective");
            /**
             * minecraft:set_entity_data (id 99, 0x63).
             */
            public static final PacketType SET_ENTITY_DATA =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_entity_data");
            /**
             * minecraft:set_entity_link (id 100, 0x64).
             */
            public static final PacketType SET_ENTITY_LINK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_entity_link");
            /**
             * minecraft:set_entity_motion (id 101, 0x65).
             */
            public static final PacketType SET_ENTITY_MOTION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_entity_motion");
            /**
             * minecraft:set_equipment (id 102, 0x66).
             */
            public static final PacketType SET_EQUIPMENT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_equipment");
            /**
             * minecraft:set_experience (id 103, 0x67).
             */
            public static final PacketType SET_EXPERIENCE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_experience");
            /**
             * minecraft:set_health (id 104, 0x68).
             */
            public static final PacketType SET_HEALTH =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_health");
            /**
             * minecraft:set_held_slot (id 105, 0x69).
             */
            public static final PacketType SET_HELD_SLOT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_held_slot");
            /**
             * minecraft:set_objective (id 106, 0x6A).
             */
            public static final PacketType SET_OBJECTIVE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_objective");
            /**
             * minecraft:set_passengers (id 107, 0x6B).
             */
            public static final PacketType SET_PASSENGERS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_passengers");
            /**
             * minecraft:set_player_inventory (id 108, 0x6C).
             */
            public static final PacketType SET_PLAYER_INVENTORY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_player_inventory");
            /**
             * minecraft:set_player_team (id 109, 0x6D).
             */
            public static final PacketType SET_PLAYER_TEAM =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_player_team");
            /**
             * minecraft:set_score (id 110, 0x6E).
             */
            public static final PacketType SET_SCORE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_score");
            /**
             * minecraft:set_simulation_distance (id 111, 0x6F).
             */
            public static final PacketType SET_SIMULATION_DISTANCE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_simulation_distance");
            /**
             * minecraft:set_subtitle_text (id 112, 0x70).
             */
            public static final PacketType SET_SUBTITLE_TEXT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_subtitle_text");
            /**
             * minecraft:set_time (id 113, 0x71).
             */
            public static final PacketType SET_TIME =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_time");
            /**
             * minecraft:set_title_text (id 114, 0x72).
             */
            public static final PacketType SET_TITLE_TEXT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_title_text");
            /**
             * minecraft:set_titles_animation (id 115, 0x73).
             */
            public static final PacketType SET_TITLES_ANIMATION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:set_titles_animation");
            /**
             * minecraft:sound_entity (id 116, 0x74).
             */
            public static final PacketType SOUND_ENTITY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:sound_entity");
            /**
             * minecraft:sound (id 117, 0x75).
             */
            public static final PacketType SOUND =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:sound");
            /**
             * minecraft:start_configuration (id 118, 0x76).
             */
            public static final PacketType START_CONFIGURATION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:start_configuration");
            /**
             * minecraft:stop_sound (id 119, 0x77).
             */
            public static final PacketType STOP_SOUND =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:stop_sound");
            /**
             * minecraft:store_cookie (id 120, 0x78).
             */
            public static final PacketType STORE_COOKIE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:store_cookie");
            /**
             * minecraft:system_chat (id 121, 0x79).
             */
            public static final PacketType SYSTEM_CHAT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:system_chat");
            /**
             * minecraft:tab_list (id 122, 0x7A).
             */
            public static final PacketType TAB_LIST =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:tab_list");
            /**
             * minecraft:tag_query (id 123, 0x7B).
             */
            public static final PacketType TAG_QUERY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:tag_query");
            /**
             * minecraft:take_item_entity (id 124, 0x7C).
             */
            public static final PacketType TAKE_ITEM_ENTITY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:take_item_entity");
            /**
             * minecraft:teleport_entity (id 125, 0x7D).
             */
            public static final PacketType TELEPORT_ENTITY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:teleport_entity");
            /**
             * minecraft:test_instance_block_status (id 126, 0x7E).
             */
            public static final PacketType TEST_INSTANCE_BLOCK_STATUS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:test_instance_block_status");
            /**
             * minecraft:ticking_state (id 127, 0x7F).
             */
            public static final PacketType TICKING_STATE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:ticking_state");
            /**
             * minecraft:ticking_step (id 128, 0x80).
             */
            public static final PacketType TICKING_STEP =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:ticking_step");
            /**
             * minecraft:transfer (id 129, 0x81).
             */
            public static final PacketType TRANSFER =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:transfer");
            /**
             * minecraft:update_advancements (id 130, 0x82).
             */
            public static final PacketType UPDATE_ADVANCEMENTS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:update_advancements");
            /**
             * minecraft:update_attributes (id 131, 0x83).
             */
            public static final PacketType UPDATE_ATTRIBUTES =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:update_attributes");
            /**
             * minecraft:update_mob_effect (id 132, 0x84).
             */
            public static final PacketType UPDATE_MOB_EFFECT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:update_mob_effect");
            /**
             * minecraft:update_recipes (id 133, 0x85).
             */
            public static final PacketType UPDATE_RECIPES =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:update_recipes");
            /**
             * minecraft:update_tags (id 134, 0x86).
             */
            public static final PacketType UPDATE_TAGS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:update_tags");
            /**
             * minecraft:projectile_power (id 135, 0x87).
             */
            public static final PacketType PROJECTILE_POWER =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:projectile_power");
            /**
             * minecraft:custom_report_details (id 136, 0x88).
             */
            public static final PacketType CUSTOM_REPORT_DETAILS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:custom_report_details");
            /**
             * minecraft:server_links (id 137, 0x89).
             */
            public static final PacketType SERVER_LINKS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:server_links");
            /**
             * minecraft:waypoint (id 138, 0x8A).
             */
            public static final PacketType WAYPOINT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:waypoint");
            /**
             * minecraft:clear_dialog (id 139, 0x8B).
             */
            public static final PacketType CLEAR_DIALOG =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:clear_dialog");
            /**
             * minecraft:show_dialog (id 140, 0x8C).
             */
            public static final PacketType SHOW_DIALOG =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.CLIENTBOUND, "minecraft:show_dialog");
        }

        /**
         * serverbound packets of the PLAY phase.
         */
        public static final class Serverbound {
            private Serverbound() {
            }

            /**
             * minecraft:accept_teleportation (id 0, 0x00).
             */
            public static final PacketType ACCEPT_TELEPORTATION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:accept_teleportation");
            /**
             * minecraft:attack (id 1, 0x01).
             */
            public static final PacketType ATTACK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:attack");
            /**
             * minecraft:block_entity_tag_query (id 2, 0x02).
             */
            public static final PacketType BLOCK_ENTITY_TAG_QUERY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:block_entity_tag_query");
            /**
             * minecraft:bundle_item_selected (id 3, 0x03).
             */
            public static final PacketType BUNDLE_ITEM_SELECTED =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:bundle_item_selected");
            /**
             * minecraft:change_difficulty (id 4, 0x04).
             */
            public static final PacketType CHANGE_DIFFICULTY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:change_difficulty");
            /**
             * minecraft:change_game_mode (id 5, 0x05).
             */
            public static final PacketType CHANGE_GAME_MODE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:change_game_mode");
            /**
             * minecraft:chat_ack (id 6, 0x06).
             */
            public static final PacketType CHAT_ACK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:chat_ack");
            /**
             * minecraft:chat_command (id 7, 0x07).
             */
            public static final PacketType CHAT_COMMAND =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:chat_command");
            /**
             * minecraft:chat_command_signed (id 8, 0x08).
             */
            public static final PacketType CHAT_COMMAND_SIGNED =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:chat_command_signed");
            /**
             * minecraft:chat (id 9, 0x09).
             */
            public static final PacketType CHAT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:chat");
            /**
             * minecraft:chat_session_update (id 10, 0x0A).
             */
            public static final PacketType CHAT_SESSION_UPDATE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:chat_session_update");
            /**
             * minecraft:chunk_batch_received (id 11, 0x0B).
             */
            public static final PacketType CHUNK_BATCH_RECEIVED =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:chunk_batch_received");
            /**
             * minecraft:client_command (id 12, 0x0C).
             */
            public static final PacketType CLIENT_COMMAND =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:client_command");
            /**
             * minecraft:client_tick_end (id 13, 0x0D).
             */
            public static final PacketType CLIENT_TICK_END =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:client_tick_end");
            /**
             * minecraft:client_information (id 14, 0x0E).
             */
            public static final PacketType CLIENT_INFORMATION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:client_information");
            /**
             * minecraft:command_suggestion (id 15, 0x0F).
             */
            public static final PacketType COMMAND_SUGGESTION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:command_suggestion");
            /**
             * minecraft:configuration_acknowledged (id 16, 0x10).
             */
            public static final PacketType CONFIGURATION_ACKNOWLEDGED =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:configuration_acknowledged");
            /**
             * minecraft:container_button_click (id 17, 0x11).
             */
            public static final PacketType CONTAINER_BUTTON_CLICK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:container_button_click");
            /**
             * minecraft:container_click (id 18, 0x12).
             */
            public static final PacketType CONTAINER_CLICK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:container_click");
            /**
             * minecraft:container_close (id 19, 0x13).
             */
            public static final PacketType CONTAINER_CLOSE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:container_close");
            /**
             * minecraft:container_slot_state_changed (id 20, 0x14).
             */
            public static final PacketType CONTAINER_SLOT_STATE_CHANGED =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:container_slot_state_changed");
            /**
             * minecraft:cookie_response (id 21, 0x15).
             */
            public static final PacketType COOKIE_RESPONSE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:cookie_response");
            /**
             * minecraft:custom_payload (id 22, 0x16).
             */
            public static final PacketType CUSTOM_PAYLOAD =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:custom_payload");
            /**
             * minecraft:debug_subscription_request (id 23, 0x17).
             */
            public static final PacketType DEBUG_SUBSCRIPTION_REQUEST =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:debug_subscription_request");
            /**
             * minecraft:edit_book (id 24, 0x18).
             */
            public static final PacketType EDIT_BOOK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:edit_book");
            /**
             * minecraft:entity_tag_query (id 25, 0x19).
             */
            public static final PacketType ENTITY_TAG_QUERY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:entity_tag_query");
            /**
             * minecraft:interact (id 26, 0x1A).
             */
            public static final PacketType INTERACT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:interact");
            /**
             * minecraft:jigsaw_generate (id 27, 0x1B).
             */
            public static final PacketType JIGSAW_GENERATE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:jigsaw_generate");
            /**
             * minecraft:keep_alive (id 28, 0x1C).
             */
            public static final PacketType KEEP_ALIVE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:keep_alive");
            /**
             * minecraft:lock_difficulty (id 29, 0x1D).
             */
            public static final PacketType LOCK_DIFFICULTY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:lock_difficulty");
            /**
             * minecraft:move_player_pos (id 30, 0x1E).
             */
            public static final PacketType MOVE_PLAYER_POS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:move_player_pos");
            /**
             * minecraft:move_player_pos_rot (id 31, 0x1F).
             */
            public static final PacketType MOVE_PLAYER_POS_ROT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:move_player_pos_rot");
            /**
             * minecraft:move_player_rot (id 32, 0x20).
             */
            public static final PacketType MOVE_PLAYER_ROT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:move_player_rot");
            /**
             * minecraft:move_player_status_only (id 33, 0x21).
             */
            public static final PacketType MOVE_PLAYER_STATUS_ONLY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:move_player_status_only");
            /**
             * minecraft:move_vehicle (id 34, 0x22).
             */
            public static final PacketType MOVE_VEHICLE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:move_vehicle");
            /**
             * minecraft:paddle_boat (id 35, 0x23).
             */
            public static final PacketType PADDLE_BOAT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:paddle_boat");
            /**
             * minecraft:pick_item_from_block (id 36, 0x24).
             */
            public static final PacketType PICK_ITEM_FROM_BLOCK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:pick_item_from_block");
            /**
             * minecraft:pick_item_from_entity (id 37, 0x25).
             */
            public static final PacketType PICK_ITEM_FROM_ENTITY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:pick_item_from_entity");
            /**
             * minecraft:ping_request (id 38, 0x26).
             */
            public static final PacketType PING_REQUEST =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:ping_request");
            /**
             * minecraft:place_recipe (id 39, 0x27).
             */
            public static final PacketType PLACE_RECIPE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:place_recipe");
            /**
             * minecraft:player_abilities (id 40, 0x28).
             */
            public static final PacketType PLAYER_ABILITIES =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:player_abilities");
            /**
             * minecraft:player_action (id 41, 0x29).
             */
            public static final PacketType PLAYER_ACTION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:player_action");
            /**
             * minecraft:player_command (id 42, 0x2A).
             */
            public static final PacketType PLAYER_COMMAND =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:player_command");
            /**
             * minecraft:player_input (id 43, 0x2B).
             */
            public static final PacketType PLAYER_INPUT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:player_input");
            /**
             * minecraft:player_loaded (id 44, 0x2C).
             */
            public static final PacketType PLAYER_LOADED =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:player_loaded");
            /**
             * minecraft:pong (id 45, 0x2D).
             */
            public static final PacketType PONG =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:pong");
            /**
             * minecraft:recipe_book_change_settings (id 46, 0x2E).
             */
            public static final PacketType RECIPE_BOOK_CHANGE_SETTINGS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:recipe_book_change_settings");
            /**
             * minecraft:recipe_book_seen_recipe (id 47, 0x2F).
             */
            public static final PacketType RECIPE_BOOK_SEEN_RECIPE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:recipe_book_seen_recipe");
            /**
             * minecraft:rename_item (id 48, 0x30).
             */
            public static final PacketType RENAME_ITEM =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:rename_item");
            /**
             * minecraft:resource_pack (id 49, 0x31).
             */
            public static final PacketType RESOURCE_PACK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:resource_pack");
            /**
             * minecraft:seen_advancements (id 50, 0x32).
             */
            public static final PacketType SEEN_ADVANCEMENTS =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:seen_advancements");
            /**
             * minecraft:select_trade (id 51, 0x33).
             */
            public static final PacketType SELECT_TRADE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:select_trade");
            /**
             * minecraft:set_beacon (id 52, 0x34).
             */
            public static final PacketType SET_BEACON =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:set_beacon");
            /**
             * minecraft:set_carried_item (id 53, 0x35).
             */
            public static final PacketType SET_CARRIED_ITEM =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:set_carried_item");
            /**
             * minecraft:set_command_block (id 54, 0x36).
             */
            public static final PacketType SET_COMMAND_BLOCK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:set_command_block");
            /**
             * minecraft:set_command_minecart (id 55, 0x37).
             */
            public static final PacketType SET_COMMAND_MINECART =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:set_command_minecart");
            /**
             * minecraft:set_creative_mode_slot (id 56, 0x38).
             */
            public static final PacketType SET_CREATIVE_MODE_SLOT =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:set_creative_mode_slot");
            /**
             * minecraft:set_game_rule (id 57, 0x39).
             */
            public static final PacketType SET_GAME_RULE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:set_game_rule");
            /**
             * minecraft:set_jigsaw_block (id 58, 0x3A).
             */
            public static final PacketType SET_JIGSAW_BLOCK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:set_jigsaw_block");
            /**
             * minecraft:set_structure_block (id 59, 0x3B).
             */
            public static final PacketType SET_STRUCTURE_BLOCK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:set_structure_block");
            /**
             * minecraft:set_test_block (id 60, 0x3C).
             */
            public static final PacketType SET_TEST_BLOCK =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:set_test_block");
            /**
             * minecraft:sign_update (id 61, 0x3D).
             */
            public static final PacketType SIGN_UPDATE =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:sign_update");
            /**
             * minecraft:spectator_action (id 62, 0x3E).
             */
            public static final PacketType SPECTATOR_ACTION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:spectator_action");
            /**
             * minecraft:swing (id 63, 0x3F).
             */
            public static final PacketType SWING =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:swing");
            /**
             * minecraft:teleport_to_entity (id 64, 0x40).
             */
            public static final PacketType TELEPORT_TO_ENTITY =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:teleport_to_entity");
            /**
             * minecraft:test_instance_block_action (id 65, 0x41).
             */
            public static final PacketType TEST_INSTANCE_BLOCK_ACTION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:test_instance_block_action");
            /**
             * minecraft:use_item_on (id 66, 0x42).
             */
            public static final PacketType USE_ITEM_ON =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:use_item_on");
            /**
             * minecraft:use_item (id 67, 0x43).
             */
            public static final PacketType USE_ITEM =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:use_item");
            /**
             * minecraft:custom_click_action (id 68, 0x44).
             */
            public static final PacketType CUSTOM_CLICK_ACTION =
                    PacketType.of(ConnectionPhase.PLAY, PacketDirection.SERVERBOUND, "minecraft:custom_click_action");
        }
    }
}
