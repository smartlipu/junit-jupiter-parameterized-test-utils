package com.chadingit.junit.jupiter.utils;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.DefaultArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JUnit5 jupiter Argument converter to convert String list to {@link List}. Intended to use with {@link CsvSource} data source for test method.
 * Example usage:
 * <pre>{@code
 *     @ParameterizedTest
 *     @CsvSource({"VAL1;VAL2, false"})
 *     void test(@ConvertWith(StringToArrayArgumentConverter.class) List<ValEnum> listVal, boolean anotherParam) {
 *     }
 * }</pre>
 * or
 * <pre>{@code
 *  *     @ParameterizedTest
 *  *     @CsvSource({"str1;str2, false"})
 *  *     void test(@ConvertWith(StringToArrayArgumentConverter.class) List<String> listVal, boolean anotherParam) {
 *  *     }
 *  * }</pre>
 */
public class StringToListArgumentConverter implements ArgumentConverter {

    private Object convert(Object source, Type type) throws ArgumentConversionException {
        return DefaultArgumentConverter.INSTANCE.convert(source, (Class<?>) type);
    }

    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        //check if parameter is actually list type
        if (!List.class.isAssignableFrom(context.getParameter().getType())) {
            throw new ParameterResolutionException("Parameter is not a Collection type.");
        }

        Type actualType = ((ParameterizedType) context.getParameter().getParameterizedType()).getActualTypeArguments()[0];

        //convert all element to this type and make a list
        return Stream.of(((String) source).split(";"))
                .map(strVal -> convert(strVal, actualType))
                .collect(Collectors.toList());
    }
}
