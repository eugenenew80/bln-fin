package bln.fin.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DecimalFormat;

public class DoubleAdapter extends XmlAdapter<String, Double> {
    @Override
    public Double unmarshal(String v) throws Exception {
        if (v == null) return  null;
        return Double.valueOf(v);
    }

    @Override
    public String marshal(Double v) throws Exception {
        if (v == null) return  null;
        return new DecimalFormat("#").format(v);
    }
}
