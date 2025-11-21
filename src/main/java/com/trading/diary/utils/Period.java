package com.trading.diary.utils;

public class Period {

    private final long years;

    private final long months;

    private final long weeks;

    private final long days;

    public Period(long years, long months, long weeks, long days) {
        this.years = years;
        this.months = months;
        this.weeks = weeks;
        this.days = days;
    }

    public long convertToDays(){
        return this.years*365 + this.months*30 + this.weeks*7 + this.days;
    }
}
