package edu.studiyaTG.minesweeper.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class ListListStringConverter implements AttributeConverter<List<List<String>>, String> {

    private static final String DELIMITER_ROW = ";";
    private static final String DELIMITER_CELL = ",";

    @Override
    public String convertToDatabaseColumn(List<List<String>> attribute) {
        return attribute.stream()
                .map(row -> String.join(DELIMITER_CELL, row))
                .collect(Collectors.joining(DELIMITER_ROW));
    }

    @Override
    public List<List<String>> convertToEntityAttribute(String dbString) {
        return Arrays.stream(dbString.split(DELIMITER_ROW))
                .map(row -> Arrays.asList(row.split(DELIMITER_CELL)))
                .collect(Collectors.toList());
    }
}