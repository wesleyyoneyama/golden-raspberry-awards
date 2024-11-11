package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.Movie;
import org.acme.dto.ProducerWinnerIntervalDto;
import org.acme.dto.ProducerWinnerMinMaxIntervalDto;
import org.acme.dto.ProducerRanked;
import org.acme.repository.MovieRepository;

import java.util.List;

@ApplicationScoped
public class MovieService {

    @Inject
    MovieRepository movieRepository;

    public List<Movie> listAll() {
        return movieRepository.listAll();
    }

    public ProducerWinnerMinMaxIntervalDto getProducerWinnerMinMaxInterval() {
        ProducerWinnerMinMaxIntervalDto producerWinnerMinMaxIntervalDto = new ProducerWinnerMinMaxIntervalDto();
        List<ProducerRanked> producerWinnerMinMaxInterval = movieRepository.getProducersRanked();
        List<ProducerWinnerIntervalDto> min = producerWinnerMinMaxInterval.stream()
                .filter(ProducerRanked::getSmallestRange)
                .map(MovieService::toProducerWinnerIntervalDto)
                .toList();

        List<ProducerWinnerIntervalDto> max = producerWinnerMinMaxInterval.stream()
                .filter(producerRanked -> !producerRanked.getSmallestRange())
                .map(MovieService::toProducerWinnerIntervalDto)
                .toList();

        producerWinnerMinMaxIntervalDto.setMin(min);
        producerWinnerMinMaxIntervalDto.setMax(max);
        return producerWinnerMinMaxIntervalDto;
    }

    private static ProducerWinnerIntervalDto toProducerWinnerIntervalDto(ProducerRanked producerRanked) {
        ProducerWinnerIntervalDto producerWinnerIntervalDto = new ProducerWinnerIntervalDto();
        producerWinnerIntervalDto.setProducer(producerRanked.getProducerName());
        producerWinnerIntervalDto.setInterval(producerRanked.getFollowingWin() - producerRanked.getPreviousWin());
        producerWinnerIntervalDto.setPreviousWin(producerRanked.getPreviousWin());
        producerWinnerIntervalDto.setFollowingWin(producerRanked.getFollowingWin());
        return producerWinnerIntervalDto;
    }
}
