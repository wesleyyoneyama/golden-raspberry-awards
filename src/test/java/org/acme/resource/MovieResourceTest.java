package org.acme.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class MovieResourceTest {

    @Test
    void testMovieMinMaxWinnerInterval() {
        when().get("/movie/min-max-winner-interval")
        .then()
            .statusCode(200)
                .body("min", contains(
                    allOf(
                        hasEntry(equalTo("producer"), equalToObject("Joel Silver")),
                        hasEntry(equalTo("interval"), equalToObject(1)),
                        hasEntry(equalTo("previousWin"), equalToObject(1990)),
                        hasEntry(equalTo("followingWin"), equalToObject(1991))
                    )), "max", contains(
                    allOf(
                        hasEntry(equalTo("producer"), equalToObject("Matthew Vaughn")),
                        hasEntry(equalTo("interval"), equalToObject(13)),
                        hasEntry(equalTo("previousWin"), equalToObject(2002)),
                        hasEntry(equalTo("followingWin"), equalToObject(2015))
                    )
                ));
    }

}
