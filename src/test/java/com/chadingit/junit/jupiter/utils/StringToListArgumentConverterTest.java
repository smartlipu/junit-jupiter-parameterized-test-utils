package com.chadingit.junit.jupiter.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

/**
 * Usage test {@link StringToListArgumentConverter}
 */
class StringToListArgumentConverterTest {

    @ParameterizedTest
    @CsvSource({
            "VAL_1, 1",
            "VAL_1;VAL_2, 2"
    })
    void enumList(@ConvertWith(StringToListArgumentConverter.class) List<ValueEnum> enumList, int length) {
        assertEquals(enumList.size(), length);
    }

    @ParameterizedTest
    @CsvSource({
            "VAL_1, 1",
            "VAL_1;VAL_2, 2"
    })
    void stringList(@ConvertWith(StringToListArgumentConverter.class) List<String> stringList, int length) {
        assertEquals(stringList.size(), length);
    }

    private enum ValueEnum {
        VAL_1, VAL_2
    }
}

