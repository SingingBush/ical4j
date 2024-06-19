package net.fortuna.ical4j.transform.recurrence

import net.fortuna.ical4j.model.NumberList
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static Frequency.DAILY
import static Frequency.YEARLY

class ByWeekNoRuleTest extends Specification {

    @Shared
    DateTimeFormatter dateFormat = DateTimeFormatter.BASIC_ISO_DATE

    @Unroll
    def 'verify transformations by week number'() {
        given: 'a BYWEEKNO rule'
        ByWeekNoRule rule = [new NumberList(byWeekNoPart), DAILY]

        expect: 'the rule transforms the dates correctly'
        rule.apply(dates) == expectedResult

        where:
        byWeekNoPart     | dates                                   | expectedResult
        '1,2,3'         | [LocalDate.parse('20150103', dateFormat)] | [LocalDate.parse('20150103', dateFormat), LocalDate.parse('20150110', dateFormat), LocalDate.parse('20150117', dateFormat)]
        '1,3,5'         | [LocalDate.parse('20150630', dateFormat)]           | [LocalDate.parse('20141230', dateFormat), LocalDate.parse('20150113', dateFormat), LocalDate.parse('20150127', dateFormat)]
    }

    @Unroll
    def 'verify transformations by week number (yearly)'() {
        given: 'a BYWEEKNO rule'
        ByWeekNoRule rule = [new NumberList(byWeekNoPart), YEARLY]

        expect: 'the rule transforms the dates correctly'
        rule.apply(dates.collect {LocalDate.parse(it, dateFormat)}) == expectedResult.collect { LocalDate.parse(it, dateFormat) }

        where:
        byWeekNoPart     | dates                                   | expectedResult
        // due to ISO 8601 definition '2011-01-01' is in the 52nd week of 2010..
        '2,52,53'       | ['20110101']  | ['20110108', '20111224', '20111231']
    }
}
