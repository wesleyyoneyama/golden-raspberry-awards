package db.migration;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


public class V2_0__SeedFromMoviesCsv extends BaseJavaMigration {

    public static final String YEAR_COLUMN = "year";
    public static final String TITLE_COLUMN = "title";
    public static final String STUDIOS_COLUMN = "studios";
    public static final String PRODUCERS_COLUMN = "producers";
    public static final String WINNER_COLUMN = "winner";

    @Override
    public void migrate(Context context) throws IOException, CsvException, SQLException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movielist.csv");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
            CSVReaderHeaderAware csvReader = new CSVReaderHeaderAwareBuilder(inputStreamReader).withCSVParser(csvParser).build();

            Map<String, UUID> producers = new HashMap<>();
            Map<String, UUID> studios = new HashMap<>();
            Map<String, String> line;
            while ((line = csvReader.readMap()) != null) {
                processLine(line, producers, studios, context.getConnection());
            }
            csvReader.close();
        }
    }

    private void processLine(Map<String, String> line, Map<String, UUID> producersMap, Map<String, UUID> studiosMap, Connection connection) throws SQLException {
        var year = Integer.parseInt(line.get(YEAR_COLUMN));
        var title = line.get(TITLE_COLUMN);
        var studios = Arrays.stream(line.get(STUDIOS_COLUMN).split(",|\\band\\b")).map(String::trim).filter(StringUtils::isNotBlank).toList();
        var producers = Arrays.stream(line.get(PRODUCERS_COLUMN).split(",|\\band\\b")).map(String::trim).filter(StringUtils::isNotBlank).toList();
        var winner = BooleanUtils.toBoolean(line.get(WINNER_COLUMN));

        var movieId = UUID.randomUUID();
        insertMovie(connection, movieId, title, year, winner);
        insertStudios(connection, movieId, studiosMap, studios);
        insertProducers(connection, movieId, producersMap, producers);
    }

    private void insertMovie(Connection connection, UUID movieId, String title, int year, boolean winner) throws SQLException {
        try (var statement = connection.prepareStatement("insert into movie (id, title, \"year\", winner) values (?, ?, ?, ?)")) {
            statement.setString(1, movieId.toString());
            statement.setString(2, title);
            statement.setInt(3, year);
            statement.setBoolean(4, winner);
            statement.execute();
        }
    }

    private void insertStudios(Connection connection, UUID movieId, Map<String, UUID> studiosMap, List<String> studios) throws SQLException {
        try (var statement = connection.prepareStatement("insert into studio (id, name) values (?, ?)")) {
            for (var studio : studios) {
                if (!studiosMap.containsKey(studio)) {
                    UUID studioUuid = UUID.randomUUID();
                    statement.setString(1, studioUuid.toString());
                    statement.setString(2, studio);
                    statement.addBatch();

                    studiosMap.put(studio, studioUuid);
                }
            }
            statement.executeBatch();
        }

        try (var statement = connection.prepareStatement("insert into movie_studio (movie_id, studio_id) values (?, ?)")) {
            for (var studio : studios) {
                UUID studioId = studiosMap.get(studio);
                statement.setString(1, movieId.toString());
                statement.setString(2, studioId.toString());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void insertProducers(Connection connection, UUID movieId, Map<String, UUID> producersMap, List<String> producers) throws SQLException {
        try (var statement = connection.prepareStatement("insert into producer (id, name) values (?, ?)")) {
            for (var producer : producers) {
                if (!producersMap.containsKey(producer)) {
                    UUID producerId = UUID.randomUUID();
                    statement.setString(1, producerId.toString());
                    statement.setString(2, producer);
                    statement.addBatch();

                    producersMap.put(producer, producerId);
                }
            }
            statement.executeBatch();
        }

        try (var statement = connection.prepareStatement("insert into movie_producer (movie_id, producer_id) values (?, ?)")) {
            for (var producer : producers) {
                UUID producerId = producersMap.get(producer);
                statement.setString(1, movieId.toString());
                statement.setString(2, producerId.toString());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }
}
