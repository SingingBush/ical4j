package net.fortuna.ical4j.model.property


import spock.lang.Specification

import static net.fortuna.ical4j.model.property.immutable.ImmutableBusyType.BUSY_TENTATIVE
import static net.fortuna.ical4j.model.property.immutable.ImmutableBusyType.BUSY_UNAVAILABLE

class BusyTypeSpec extends Specification {

    BusyType.Factory factory = []

    def 'test factory use of constants'() {
        when: 'factory is invoked with a constant value'
        def busyType = factory.createProperty(value)

        then: 'the returned value is the constant instance'
        busyType.is(constantInstance)

        where:
        value   | constantInstance
        'BUSY-UNAVAILABLE' | BUSY_UNAVAILABLE
        'BUSY-TENTATIVE' | BUSY_TENTATIVE
    }
}
