package com.vshtd.parceldelivery.logistic.service.jwt;

import io.jsonwebtoken.Claims;

public interface JwtService {

    Claims getClaims(String jwt);

    boolean isValidJwt(String jwt);
}
