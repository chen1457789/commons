package github.chenjun.commons.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public final class SerializeUtil {
    private SerializeUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static final byte[] serialize(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {
                logger.error("close ObjectOutputStream error", e);
            }
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                logger.error("close ByteArrayOutputStream error", e);
            }
        }
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static final Object deserialize(byte[] bytes) {
        return deserialize(bytes, Object.class);
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    @SuppressWarnings("unchecked")
    public static final <K> K deserialize(byte[] bytes, Class<K> cls) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            return (K) ois.readObject();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
                logger.error("close ObjectInputStream error", e);
            }
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (Exception e) {
                logger.error("close ByteArrayInputStream error", e);
            }
        }
    }
}
