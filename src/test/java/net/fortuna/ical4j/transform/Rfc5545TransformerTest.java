package net.fortuna.ical4j.transform;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.transform.calendar.Rfc5545Transformer;
import net.fortuna.ical4j.validate.ValidationException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Rfc5545TransformerTest {

    private Rfc5545Transformer transformer;

    @Before
    public void setUp() {
        transformer = new Rfc5545Transformer();
    }

    @Test
    @Ignore
    public void shouldCorrectCalendarBody() throws IOException, ParserException {

        String[] calendarNames = { "yahoo1.txt", "yahoo2.txt", "outlook1.txt", "outlook2.txt", "apple.txt" };
        for (String calendarName : calendarNames) {
            Calendar calendar = buildCalendar(calendarName);
            calendar = transformer.apply(calendar);
            try {
                calendar.validate();
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Validation failed for " + calendarName);
            }
        }
    }

    @Test
    @Ignore
    public void shouldCorrectMsSpecificTimeZones() throws IOException, ParserException {
        String actuals[] = { "timezones/outlook1.txt", "timezones/outlook2.txt" };
        String expecteds[] = { "timezones/outlook1_expected.txt", "timezones/outlook2_expected.txt" };

        for (int i = 0; i < actuals.length; i++) {
            Calendar actual = buildCalendar(actuals[i]);
            actual = transformer.apply(actual);
            Calendar expected = buildCalendar(expecteds[i]);
            assertEquals("on from " + expecteds[i] + " and " + actuals[i] + " failed.", expected, actual);
        }
    }

    @Test
    @Ignore("Unable to parse invalid UTC properties using new date/time API")
    public void shouldCorrectDTStampByAddingUTCTimezone() {
        String calendarName = "dtstamp/invalid.txt";
        try {
            Calendar actual = buildCalendar(calendarName);
            actual = transformer.apply(actual);
        } catch (RuntimeException | IOException | ParserException e) {
            e.printStackTrace();
            fail("RFC transformation failed for " + calendarName);
        }
    }

    @Test
    @Ignore
    public void shouldSetTimezoneToUtcForNoTZdescription() {
        String actualCalendar = "outlook/TZ-no-description.txt";
        try {
            Calendar actual = buildCalendar(actualCalendar);
            actual = transformer.apply(actual);
            Calendar expected = buildCalendar("outlook/TZ-set-to-utc.txt");
            assertEquals(expected.toString(), actual.toString());
            assertEquals(expected, actual);
        } catch (Exception e) {
            e.printStackTrace();
            fail("RFC transformation failed for " + actualCalendar);
        }
    }

    private Calendar buildCalendar(String file) throws IOException, ParserException {
        InputStream is = getClass().getResourceAsStream(file);
        CalendarBuilder cb = new CalendarBuilder();
        Calendar calendar = cb.build(is);
        is.close();
        return calendar;
    }
}