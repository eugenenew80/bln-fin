package bln.fin.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DoubleAdapter extends XmlAdapter<String, Double> {
    @Override
    public Double unmarshal(String v) throws Exception {
        if (v == null) return  null;
        return Double.valueOf(v);
    }

    @Override
    public String marshal(Double v) throws Exception {
        if (v == null) return  null;
        DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.setMaximumFractionDigits(8);
        DecimalFormatSymbols symbol = new DecimalFormatSymbols();
        symbol.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbol);
        return decimalFormat.format(v);
    }
}
