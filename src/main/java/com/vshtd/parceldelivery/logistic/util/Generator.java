package com.vshtd.parceldelivery.logistic.util;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Generator {

    private static final NoArgGenerator UUID_GENERATOR = Generators.timeBasedGenerator();

    public static UUID from(String uuid) {
        if (Objects.isNull(uuid)) {
            return null;
        }
        return UUID.fromString(uuid);
    }

    public static UUID uuid() {
        return UUID_GENERATOR.generate();
    }
}
