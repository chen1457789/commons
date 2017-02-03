package github.chenjun.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by chenjun on 2017/2/3.
 */
public class MapBeanConverseUtils {
    private static final Logger logger = LoggerFactory.getLogger(MapBeanConverseUtils.class);

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        T bean = null;
        try {
            bean = clazz.newInstance();
            if (map == null) return bean;
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            PropertyDescriptor pd;
            String key;
            Object value;
            Class<?> propertyType;
            for (Map.Entry<String, Object> entry : entries) {
                key = entry.getKey();
                value = entry.getValue();
                pd = new PropertyDescriptor(key, clazz);
                propertyType = pd.getPropertyType();
                Method method = pd.getWriteMethod();
                if (method != null) {
                    method.invoke(bean, converseValue(value, propertyType));
                }
            }
        } catch (InstantiationException e) {
            logger.error("map to bean error", e);
        } catch (IllegalAccessException e) {
            logger.error("map to bean error", e);
        } catch (IntrospectionException e) {
            logger.error("map to bean error", e);
        } catch (InvocationTargetException e) {
            logger.error("map to bean error", e);
        }
        return bean;
    }

    private static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean == null) return map;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                Method method = pd.getReadMethod();
                if (method != null) {
                    String key = pd.getName();
                    if (method.getName().endsWith("Class")) {
                        continue;
                    }
                    Object value = method.invoke(bean);
                    if (value != null) {
                        map.put(key, value);
                    }
                }
            }
        } catch (IntrospectionException e) {
            logger.error("bean to map error", e);
        } catch (IllegalAccessException e) {
            logger.error("bean to map error", e);
        } catch (InvocationTargetException e) {
            logger.error("bean to map error", e);
        }
        return map;
    }

    private static Object converseValue(Object value, Class<?> clazz) {
        if (value == null || value.getClass().isAssignableFrom(clazz)) return value;
        if (clazz.isAssignableFrom(Integer.class)) {
            return new Integer(String.valueOf(value));
        } else if (clazz.isAssignableFrom(Double.class)) {
            return new Double(String.valueOf(value));
        } else if (clazz.isAssignableFrom(Date.class)) {
            try {
                String patten = "yyyy-MM-dd HH:mm:ss";
                patten = patten.substring(0, String.valueOf(value).length());
                return new SimpleDateFormat(patten).parse(String.valueOf(value));
            } catch (ParseException e) {
                return null;
            }
        } else {
            return value;
        }
    }
}
