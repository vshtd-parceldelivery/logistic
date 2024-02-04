package com.vshtd.parceldelivery.logistic.mapper;

public interface Mapper<FROM, TO> {

    TO map(FROM value);
}
