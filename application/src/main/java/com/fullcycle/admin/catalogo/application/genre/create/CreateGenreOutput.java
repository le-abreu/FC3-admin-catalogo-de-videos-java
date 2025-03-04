package com.fullcycle.admin.catalogo.application.genre.create;

import com.fullcycle.admin.catalogo.domain.genre.Genre;
import com.fullcycle.admin.catalogo.domain.genre.GenreID;

public record CreateGenreOutput(
        String id
) {

    public static CreateGenreOutput from(final String anId) {
        return new CreateGenreOutput(anId);
    }


    public static CreateGenreOutput from(final GenreID aGenreId) {
        return new CreateGenreOutput(aGenreId.getValue());
    }

    public static CreateGenreOutput from(final Genre aGenre) {
        return new CreateGenreOutput(aGenre.getId().getValue());
    }
}