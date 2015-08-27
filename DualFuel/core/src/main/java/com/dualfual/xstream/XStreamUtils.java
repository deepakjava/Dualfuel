package com.dualfual.xstream;


import com.dualfual.google.GeocodeResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XStreamUtils {

    public static XStream getXStream(){
        XStream xStream = new XStream(){
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @Override
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        if (definedIn == Object.class) {
                            try {
                                return this.realClass(fieldName) != null;
                            } catch(Exception e) {
                                return false;
                            }
                        } else {
                            return super.shouldSerializeMember(definedIn, fieldName);
                        }
                    }
                };
            }
        };
        xStream.autodetectAnnotations(true);
        xStream.alias("GeocodeResponse", GeocodeResponse.class);

        return xStream;
    }
}
