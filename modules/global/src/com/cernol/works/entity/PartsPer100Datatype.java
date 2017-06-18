/*
 * Copyright (c) 2017 CubeIT
 */

package com.cernol.works.entity;

import com.google.common.base.Strings;
import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.chile.core.datatypes.impl.NumberDatatype;
import org.dom4j.Element;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by skopp on 02/03/2017.
 */
public class PartsPer100Datatype extends NumberDatatype implements Datatype<BigDecimal> {

    public static final String NAME = "partsPer100";

    private static final String FORMAT = "#0.0000";

    //private static MathContext context = new MathContext(4, RoundingMode.HALF_UP);

    public PartsPer100Datatype(Element element) {
        super(element);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Class getJavaClass() {
        return BigDecimal.class;
    }

    @Nonnull
    @Override
    public String format(@Nullable Object value) {
        if (value == null)
            return "";

        DecimalFormat format = new DecimalFormat(FORMAT);
        return format.format(value);
    }

    @Nonnull
    @Override
    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }

    @Nullable
    @Override
    public BigDecimal parse(@Nullable String value) throws ParseException {
        if (Strings.isNullOrEmpty(value))
            return null;

        DecimalFormat format = new DecimalFormat(FORMAT);
        format.setParseBigDecimal(true);
        BigDecimal result;
        try {
            result = (BigDecimal) format.parse(value);
        } catch (ParseException e) {
            try {
                result = new BigDecimal(value);
            } catch (Exception e1) {
                throw new ParseException("Error parsing " + value, 0);
            }
        }
        return result;
    }

    @Nullable
    @Override
    public BigDecimal parse(@Nullable String value, Locale locale) throws ParseException {
        return parse(value);
    }

    @Override
    public String toString() {
        return NAME;
    }
}
