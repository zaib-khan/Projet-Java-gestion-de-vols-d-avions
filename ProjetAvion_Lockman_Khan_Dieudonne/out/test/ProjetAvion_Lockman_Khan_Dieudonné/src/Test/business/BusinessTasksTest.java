package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BusinessTasksTest {
    private Date date1, date2, date3, date4;
    private Calendar cal;

    @BeforeEach
    void setUp() {
        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -8);
        date1 = cal.getTime();
        cal.add(Calendar.YEAR, 3);
        date2 = cal.getTime();
        cal.add(Calendar.MONTH, 8);
        date3 = cal.getTime();
        cal.add(Calendar.YEAR, 3);
        date4 = cal.getTime();
    }

    @Test
    void numberAdultAndChildOnPlane() {
        assertEquals(12, BusinessFlight.numberAdultAndChildOnPlane(4, 8));
        assertEquals(23, BusinessFlight.numberAdultAndChildOnPlane(11, 12));
        assertNotEquals(6, BusinessFlight.numberAdultAndChildOnPlane(6, 8));
    }

    @Test
    void delay() {
        assertEquals(9, BusinessFlight.delay(45, 54));
        assertNotEquals(85, BusinessFlight.delay(45, 20));
        assertEquals(0, BusinessFlight.delay(60, 40));
    }

    @Test
    void isDateBetweenTwoOther() {
        assertTrue(BusinessFlight.isDateBetweenTwoOther(date3, date1, date4));
        assertTrue(BusinessFlight.isDateBetweenTwoOther(date2, date1, date3));
        assertFalse(BusinessFlight.isDateBetweenTwoOther(date4, date1, date3));
    }
}