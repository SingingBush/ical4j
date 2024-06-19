package net.fortuna.ical4j.model;

/**
 * Created by fortuna on 12/09/14.
 */
public interface ComponentFactory<T extends Component> {

    T createComponent();

    T createComponent(PropertyList properties);

    default T createComponent(PropertyList properties, ComponentList<? extends Component> subComponents) {
        // ignore subcomponents by default. override this method for subclasses that support subcomponents..
        return createComponent(properties);
    }

    boolean supports(String name);
}
