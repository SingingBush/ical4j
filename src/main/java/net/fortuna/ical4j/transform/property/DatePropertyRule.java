package net.fortuna.ical4j.transform.property;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.property.DateProperty;

/**
 * 
 * @author daniel grigore
 * @author corneliu dobrota
 */
public class DatePropertyRule implements Rfc5545PropertyRule<DateProperty> {

    @Override
    public void applyTo(DateProperty element) {
        TzHelper.correctTzParameterFrom(element);
        if (!element.isUtc() || !element.getParameter(Parameter.TZID).isPresent()) {
            return;
        }
        element.getParameters().removeIf(p -> p.getName().equals(Parameter.TZID));
    }

    @Override
    public Class<DateProperty> getSupportedType() {
        return DateProperty.class;
    }

}
