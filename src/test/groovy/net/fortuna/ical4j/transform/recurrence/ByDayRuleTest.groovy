package net.fortuna.ical4j.transform.recurrence

import net.fortuna.ical4j.model.*
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.ExRule
import net.fortuna.ical4j.model.property.RRule
import net.fortuna.ical4j.util.RandomUidGenerator
import net.fortuna.ical4j.util.UidGenerator
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal
import java.util.stream.IntStream

import static net.fortuna.ical4j.model.WeekDay.*
import static net.fortuna.ical4j.transform.recurrence.Frequency.MONTHLY
import static net.fortuna.ical4j.transform.recurrence.Frequency.WEEKLY

class ByDayRuleTest extends Specification {

    def 'verify transformations by day'() {
        given: 'a BYDAY rule'
        ByDayRule rule = [new WeekDayList(rulePart), frequency, DayOfWeek.SUNDAY]

        and: 'a list of dates'
        def dates = []
        dateStrings.each {
            dates << TemporalAdapter.parse(it).temporal
        }

        def expected = []
        expectedResult.each {
            expected << TemporalAdapter.parse(it).temporal
        }

        expect: 'the rule transforms the dates correctly'
        rule.apply(dates) == expected

        where:
        rulePart | frequency | dateStrings  | expectedResult
        FR | WEEKLY | ['20150103'] | ['20150102']
        [SU, MO] as WeekDay[] | WEEKLY    | ['20110306'] | ['20110306', '20110307']
    }

    def 'test limit with FREQ=MINUTELY'() {
        given: 'a calendar definition'
        TemporalAdapter<LocalDateTime> dateTime = TemporalAdapter.parse("20210104T130000")
        VEvent e1 = new VEvent(dateTime.temporal, "even")
        UidGenerator ug = new RandomUidGenerator()
        e1.add(ug.generateUid())

        // recurency
        Recur recur = new Recur.Builder().frequency(Frequency.MINUTELY).interval(15).hourList(numberList(13, 17))
                .dayList(WE).build()
        e1.add(new RRule(recur))

        //exrules
        final NumberList hourExList = new NumberList('16')
        Recur recurEx = new Recur.Builder().frequency(Frequency.MINUTELY).interval(15).hourList(hourExList)
                .minuteList(numberList(30, 60)).build()

        Recur recurEx2 = new Recur.Builder().frequency(Frequency.MINUTELY).interval(15)
                .hourList(numberList(13, 14)).minuteList(numberList(0, 30)).build()

        Calendar calendar = new Calendar().withDefaults()
                .withProperty(new ExRule(recurEx))
                .withProperty(new ExRule(recurEx2))
                .withComponent(new VEvent(dateTime.temporal, "even")
                        .withProperty(new RandomUidGenerator().generateUid())
                        .getFluentTarget())
                .getFluentTarget()

        System.out.println(calendar)

        expect: 'dates are calculated successfully'
        System.out.println("--------------------------------------------------")
        TemporalAdapter<LocalDateTime> from = TemporalAdapter.parse("20200101T070000")
        TemporalAdapter<LocalDateTime> to = TemporalAdapter.parse("20210107T070000")

        Period period = new Period(from.temporal, to.temporal)
        for (Component c : calendar.getComponents(Component.VEVENT)) {
            PeriodList<LocalDateTime> list = c.calculateRecurrenceSet(period)
            for (Period<LocalDateTime> p : list.periods) {
                System.out.println(p)
            }
        }
    }

    def 'test offset day rules'() {
        given: 'a BYDAY rule'
        def rule = new ByDayRule(weekDays, frequency, DayOfWeek.SUNDAY)

        expect: 'rule transformations are correct'
        def dates = []
        dateStrings.each {
            dates << TemporalAdapter.parse(it).temporal
        }

        def expected = []
        expectedStrings.each {
            expected << TemporalAdapter.parse(it).temporal
        }

        rule.apply(dates) == expected

        where:
        weekDays                | frequency | dateStrings   | expectedStrings
        [new WeekDay(FR, -1)]   | MONTHLY   | ['20140131']  | ['20140131']
        [new WeekDay(FR, -1)]   | MONTHLY   | ['20140131', '20140228']  | ['20140131', '20140228']
    }

    def 'test offset day rules with count'() {
        given: 'a BYDAY rule'
        def rule = new ByDayRule(weekDays, frequency, DayOfWeek.SUNDAY)

        expect: 'rule transformations are correct'
        List<Temporal> dates = []
        dates << TemporalAdapter.parse(seedString).temporal

        def expected = []
        expectedStrings.each {
            expected << TemporalAdapter.parse(it).temporal
        }

        for (int i = 1; i < count; i++) {
            dates << dates[0].plus(i, ChronoUnit.MONTHS)
            dates = rule.apply(dates)
        }
        dates == expected

        where:
        weekDays                | frequency | seedString    | count   | expectedStrings
        [new WeekDay(FR, -1)]   | MONTHLY   | '20140131'    | 2       | ['20140131', '20140228']
        [new WeekDay(FR, -1)]   | MONTHLY   | '20140131'    | 4       | ['20140131', '20140228', '20140328', '20140425']
        [new WeekDay(FR, -1)]   | MONTHLY   | '20250131'    | 4       | ['20250131', '20250228', '20250328', '20250425']
    }

    private static NumberList numberList(int startInclusive, int endExclusive) {
        final NumberList integers = new NumberList()
        IntStream.range(startInclusive, endExclusive).forEach(integers::add)
        return integers
    }
}
