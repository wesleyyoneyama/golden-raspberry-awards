package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.acme.domain.Movie;
import org.acme.service.MovieService;

import java.util.List;

@Path("movie")
public class MovieResource {

    @Inject
    MovieService movieService;

    @GET
    public List<Movie> listAll() {
        return movieService.listAll();
    }
}
