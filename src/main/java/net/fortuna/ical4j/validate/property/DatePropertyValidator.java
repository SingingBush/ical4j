/*
 *  Copyright (c) 2021, Ben Fortuna
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

package net.fortuna.ical4j.validate.property;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.validate.*;

import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.None;
import static net.fortuna.ical4j.validate.ValidationRule.ValidationType.OneOrLess;

@Deprecated
public class DatePropertyValidator<T extends DateProperty> implements Validator<T> {

    private static final ValidationRule OPTIONAL_PARAMS = new ValidationRule(OneOrLess, Parameter.VALUE, Parameter.TZID);

    private static final ValidationRule UTC_PARAMS = new ValidationRule(None, Parameter.TZID);

    @Override
    public ValidationResult validate(T target) throws ValidationException {
        ValidationResult result = new ValidationResult();
        /*
         * ; the following are optional, ; but MUST NOT occur more than once (";" "VALUE" "=" ("DATE-TIME" / "DATE")) /
         * (";" tzidparam) /
         */

        /*
         * ; the following is optional, ; and MAY occur more than once (";" xparam)
         */

        result.getEntries().addAll(new PropertyRuleSet<T>(OPTIONAL_PARAMS).apply(target.getName(), target));
        if (target.isUtc()) {
            result.getEntries().addAll(new PropertyRuleSet<T>(UTC_PARAMS).apply(target.getName(), target));
        }
        return result;
    }
}
