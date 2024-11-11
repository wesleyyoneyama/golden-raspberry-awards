package org.acme.repository;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.Movie;
import org.acme.dto.ProducerRanked;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MovieRepository implements PanacheRepositoryBase<Movie, UUID> {
    private static final String PRODUCERS_RANKED_QUERY = """
WITH producers_with_following_win AS (
    SELECT
        A.TITLE AS movie,
        c.NAME AS producer_name,
        a."year" AS previous_win,
        LEAD(A."year", 1) OVER (PARTITION BY c.ID ORDER BY a."year") AS following_win
    FROM movie a
    JOIN MOVIE_PRODUCER b ON b.MOVIE_ID = a.ID
    JOIN PRODUCER c ON c.id = b.PRODUCER_ID
    WHERE a.WINNER
), producers_ranked AS (
    SELECT
        movie,
        producer_name,
        previous_win,
        following_win,
        DENSE_RANK() OVER (ORDER BY following_win - previous_win asc) AS rank_asc,
        DENSE_RANK() OVER (ORDER BY following_win - previous_win desc) AS rank_desc
    FROM producers_with_following_win
    WHERE following_win IS NOT null
)
SELECT *, TRUE AS smallest_range
FROM producers_ranked
WHERE rank_asc = 1
UNION
SELECT *, FALSE AS smallest_range
FROM producers_ranked
WHERE rank_desc = 1""";


    @SuppressWarnings("unchecked")
    public List<ProducerRanked> getProducersRanked() {
        return (List<ProducerRanked>) Panache.getEntityManager().createNativeQuery(PRODUCERS_RANKED_QUERY, ProducerRanked.class).getResultList();
    }
}
