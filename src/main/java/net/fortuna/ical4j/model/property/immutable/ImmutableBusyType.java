/*
 *  Copyright (c) 2022, Ben Fortuna
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *   o Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 *   o Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *
 *   o Neither the name of Ben Fortuna nor the names of any other contributors
 *  may be used to endorse or promote products derived from this software
 *  without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package net.fortuna.ical4j.model.property.immutable;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.BusyType;
import net.fortuna.ical4j.model.property.ImmutableProperty;

/**
 * An immutable instance of BusyType.
 *
 * @author Ben Fortuna
 * @author Mike Douglass
 */
public final class ImmutableBusyType extends BusyType implements ImmutableProperty {

    /**
     * Constant for busy time.
     */
    public static final BusyType BUSY = new ImmutableBusyType(VALUE_BUSY);
    /**
* Constant for busy unavailable time.
*/
public static final BusyType BUSY_UNAVAILABLE = new ImmutableBusyType(VALUE_BUSY_UNAVAILABLE);
    /**
* Constant for tentatively busy time.
*/
public static final BusyType BUSY_TENTATIVE = new ImmutableBusyType(VALUE_BUSY_TENTATIVE);
    private static final long serialVersionUID = -2454749569982470433L;

    /**
     * @param value
     */
    public ImmutableBusyType(final String value) {
        super(value);
    }

    @Override
    public <T extends Property> T add(Parameter parameter) {
        return ImmutableProperty.super.add(parameter);
    }

    @Override
    public <T extends Property> T remove(Parameter parameter) {
        return ImmutableProperty.super.remove(parameter);
    }

    @Override
    public <T extends Property> T removeAll(String... parameterName) {
        return ImmutableProperty.super.removeAll(parameterName);
    }

    @Override
    public <T extends Property> T replace(Parameter parameter) {
        return ImmutableProperty.super.replace(parameter);
    }

    @Override
    public void setValue(final String aValue) {
        ImmutableProperty.super.setValue(aValue);
    }
}
