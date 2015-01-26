package de.crunc.hamcrest.json.matcher;

import com.google.gson.JsonPrimitive;
import org.hamcrest.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Unit test for {@link IsJsonNumber}
 *
 * @author Hauke Jaeger, hauke.jaeger@googlemail.com
 */
@RunWith(Parameterized.class)
public class IsJsonNumberMatcherTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {4, greaterThan(new BigDecimal("3.5")), true},
                {3, greaterThan(new BigDecimal("3.5")), false},
                {8L, closeTo(new BigDecimal("7.5"), new BigDecimal("0.5")), true},
                {9L, closeTo(new BigDecimal("7.5"), new BigDecimal("0.4")), false},
                {3.51, greaterThan(new BigDecimal("3.5")), true},
                {3.49, greaterThan(new BigDecimal("3.5")), false},
        });
    }

    @Parameterized.Parameter(0)
    public Number actual;

    @Parameterized.Parameter(1)
    public Matcher<? super BigDecimal> numberMatcher;

    @Parameterized.Parameter(2)
    public boolean result;

    @Test
    public void shouldMatchIntegerNumber() {
        IsJsonNumber matcher = new IsJsonNumber(numberMatcher);

        Description description = new StringDescription();
        numberMatcher.describeTo(description);

        assertThat("<" + actual + "> " + description.toString(), matcher.matches(new JsonPrimitive(actual)), equalTo(result));
    }
}
