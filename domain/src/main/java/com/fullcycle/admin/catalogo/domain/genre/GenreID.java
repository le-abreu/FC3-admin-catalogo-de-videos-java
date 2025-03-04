package com.fullcycle.admin.catalogo.domain.genre;

import com.fullcycle.admin.catalogo.domain.Identifier;
import com.fullcycle.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;

public class GenreID extends Identifier {
    private final String value;

    private GenreID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static GenreID unique(){
        return GenreID.from(IdUtils.uuid());
    }

    public static GenreID from(final String anId){
        return new GenreID(anId);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GenreID that = (GenreID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
