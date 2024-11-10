package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.Movie;

import java.util.UUID;

@ApplicationScoped
public class MovieRepository implements PanacheRepositoryBase<Movie, UUID> {
}
