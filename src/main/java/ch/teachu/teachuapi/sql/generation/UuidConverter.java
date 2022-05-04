package ch.teachu.teachuapi.sql.generation;

import org.jooq.Converter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class UuidConverter implements Converter<byte[], UUID> {
    // from https://stackoverflow.com/questions/17726682/read-mysql-binary16-uuid-with-java
    @Override
    public UUID from(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        int i = 0;
        long msl = 0;
        for (; i < 8; i++) {
            msl = (msl << 8) | (bytes[i] & 0xFF);
        }
        long lsl = 0;
        for (; i < 16; i++) {
            lsl = (lsl << 8) | (bytes[i] & 0xFF);
        }
        return new UUID(msl, lsl);
    }

    // from https://stackoverflow.com/questions/40297600/how-to-save-a-uuid-as-binary16-in-java
    @Override
    public byte[] to(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        byte[] uuidBytes = new byte[16];
        ByteBuffer.wrap(uuidBytes)
                .order(ByteOrder.BIG_ENDIAN)
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits());
        return uuidBytes;
    }

    @Override
    public Class<byte[]> fromType() {
        return byte[].class;
    }

    @Override
    public Class<UUID> toType() {
        return UUID.class;
    }
}
