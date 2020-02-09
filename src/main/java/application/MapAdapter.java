package application;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;

public class MapAdapter extends XmlAdapter<MapWrapper, Map<String, Object>> {
    @Override
    public MapWrapper marshal(Map<String, Object> m) throws Exception {
        MapWrapper wrapper = new MapWrapper();
        List elements = new ArrayList();
        for (Map.Entry<String, Object> property : m.entrySet()) {

            if (property.getValue() instanceof Map)
                elements.add(new JAXBElement<MapWrapper>(new QName(getCleanLabel(property.getKey())),
                        MapWrapper.class, marshal((Map) property.getValue())));
            else
                elements.add(new JAXBElement<String>(new QName(getCleanLabel(property.getKey())),
                        String.class, property.getValue().toString()));
        }
        wrapper.elements = elements;
        return wrapper;
    }

    @Override
    public Map<String, Object> unmarshal(MapWrapper v) throws Exception {

        throw new OperationNotSupportedException();
    }

    // Return a XML-safe attribute.  Might want to add camel case support
    private String getCleanLabel(String attributeLabel) {
        attributeLabel = attributeLabel.replaceAll("[()]", "").replaceAll("[^\\w\\s]", "_").replaceAll(" ", "_");
        return attributeLabel;
    }
}
class MapWrapper {
    @XmlAnyElement
    List elements;
}