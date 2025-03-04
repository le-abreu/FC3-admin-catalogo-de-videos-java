package com.fullcycle.admin.catalogo.infrastructure.category.models;

import com.fullcycle.admin.catalogo.JacksonTest;
import com.fullcycle.admin.catalogo.domain.utils.InstantUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

@JacksonTest
public class CategoryResponseTest {

    @Autowired
    private JacksonTester<CategoryResponse> json;


    @Test
    public void testMarshall() throws IOException {
        final var expectId = "123";
        final var experctName = "Filmes";
        final var experctDescription = "A categoria mais assistida";
        final var experctCreatedAt = InstantUtils.now();
        final var experctIsActive = false;
        final var experctUpdatedAt = InstantUtils.now();
        final var experctDeletedAt = InstantUtils.now();

        final var response = new CategoryResponse(expectId,
                experctName,
                experctDescription,
                experctIsActive,
                experctCreatedAt,
                experctUpdatedAt,
                experctDeletedAt);

        final var actualJson = this.json.write(response);

        Assertions.assertThat(actualJson)
            .hasJsonPathValue("$.id", expectId)
            .hasJsonPathValue("$.name", experctName)
            .hasJsonPathValue("$.description", experctDescription)
            .hasJsonPathValue("$.is_active", experctIsActive)
            .hasJsonPathValue("$.created_at", experctCreatedAt)
            .hasJsonPathValue("$.deleted_at", experctDeletedAt)
            .hasJsonPathValue("$.updated_at", experctUpdatedAt)
        ;
    }

    @Test
    public void testUnmarshall() throws IOException {
        final var expectId = "123";
        final var experctName = "Filmes";
        final var experctDescription = "A categoria mais assistida";
        final var experctCreatedAt = InstantUtils.now();
        final var experctIsActive = false;
        final var experctUpdatedAt = InstantUtils.now();
        final var experctDeletedAt = InstantUtils.now();
        final var json = """
                {
                    "id": "%s",
                    "name": "%s",
                    "description": "%s",
                    "is_active": "%s",
                    "created_at": "%s",
                    "deleted_at": "%s",
                    "updated_at": "%s"
                }
                """.formatted(
                        expectId,
                        experctName,
                        experctDescription,
                        experctIsActive,
                        experctCreatedAt.toString(),
                        experctDeletedAt.toString(),
                        experctUpdatedAt.toString()
                );


        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("id", expectId)
                .hasFieldOrPropertyWithValue("name", experctName)
                .hasFieldOrPropertyWithValue("description", experctDescription)
                .hasFieldOrPropertyWithValue("active", experctIsActive)
                .hasFieldOrPropertyWithValue("createdAt", experctCreatedAt)
                .hasFieldOrPropertyWithValue("deletedAt", experctDeletedAt)
                .hasFieldOrPropertyWithValue("updatedAt", experctUpdatedAt)
        ;
    }
}
