package github.chenjun.commons.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by chenjun on 2017/2/3.
 */
public class XmlUtil {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    public static List<Map<String, Object>> parseXml(String url) {
        List<Map<String, Object>> lstProduct = new ArrayList<>();
        try {
            SAXReader sr = new SAXReader();
            Document doc = sr.read(url);
            Element root = doc.getRootElement();
            Iterator<Element> it = root.elementIterator();
            while (it.hasNext()) {
                Map<String, Object> result = new HashMap<>();
                read(it.next(), result);
                lstProduct.add(result);
            }
        } catch (Exception e) {
            logger.error("parse xml error", e);
        }
        return lstProduct;
    }

    private static void read(Element element, Map<String, Object> result) {
        List<Element> elementList = element.elements();
        if (elementList.size() > 0) {
            if (elementList.size() >= 2 && elementList.get(0).getName().equals(elementList.get(1).getName())) {
                List<Map<String, Object>> children = new ArrayList<>();
                for (Element e : elementList) {
                    Map<String, Object> child = new HashMap<>();
                    read(e, child);
                    children.add(child);
                }
                result.put(element.getName(), children);
            } else {
                Map<String, Object> child = new HashMap<>();
                for (Element e : elementList) {
                    read(e, child);
                }
                result.put(element.getName(), child);
            }
        } else {
            result.put(element.getName(), element.getText());
        }

    }
}
