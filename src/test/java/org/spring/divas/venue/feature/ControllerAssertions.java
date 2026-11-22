package org.spring.divas.venue.feature;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerAssertions {

    public static <T> void assertResult(T actual, T expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
