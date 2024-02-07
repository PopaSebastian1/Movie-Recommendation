package Database.dao;

import Database.model.DatabaseConnection;
import Database.model.MovieTableEntity;
import java.util.Optional;
import javax.persistence.TypedQuery;
import java.sql.SQLOutput;
import java.util.List;

public class DaoI {
    private DatabaseConnection connection = new DatabaseConnection();

    public MovieTableEntity get(int id) {
        return connection.getEntityManager().find(MovieTableEntity.class, id);
    }

    public List<MovieTableEntity> getAll() {
        if(!connection.getEntityManager().isOpen()) {
            System.out.println("Connection is closed");
        }
        TypedQuery<MovieTableEntity> query = connection.getEntityManager().createQuery("SELECT a FROM MovieTableEntity a", MovieTableEntity.class);
        return query.getResultList();
    }
    public void create(MovieTableEntity movie) {
        connection.executeTransaction(entityManager -> entityManager.persist(movie));
    }
    public void add(MovieTableEntity movie) {
        connection.executeTransaction(entityManager -> entityManager.merge(movie));
    }
    public MovieTableEntity searchMovie(String title)
    {
        List<MovieTableEntity> movies = getAll();
        for (MovieTableEntity movie : movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }
    public MovieTableEntity searchMovie(int id)
    {
        List<MovieTableEntity> movies = getAll();
        for (MovieTableEntity movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }
    public MovieTableEntity searchMovieByTitle(String title)
    {
        return getAll().stream()
                .filter(movie -> movie.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }
    public void deleteMovie(String Movie)
    {
        MovieTableEntity movie = searchMovieByTitle(Movie);
        connection.executeTransaction(entityManager -> entityManager.remove(movie));
    }
}