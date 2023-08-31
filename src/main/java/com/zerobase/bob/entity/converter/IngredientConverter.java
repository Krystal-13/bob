package com.zerobase.bob.entity.converter;


import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Convert
public class IngredientConverter implements AttributeConverter<List<String>, String> {

    private static final String EMPTYSTRING = "";
    private static final char SEPARATOR = ',';

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {

        if (CollectionUtils.isEmpty(attribute)) {
            return EMPTYSTRING;
        }

        return StringUtils.join(attribute, SEPARATOR);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {

        List<String> list = new ArrayList<>();

        if (Objects.isNull(dbData)) {
            return list;
        }

        String[] strings = StringUtils.split(dbData, SEPARATOR);

        return Arrays.asList(strings);
    }
}
