package Database.model;

import Database.dao.DaoI;
import Database.dao.LikedDaoi;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "public", catalog = "movie1")
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "role")
    private Boolean role;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }
    public UserEntity() {
    }
    public UserEntity(String username, String password, Boolean role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public void LikeMovie(String title)
    {
        DaoI dao = new DaoI();
        MovieTableEntity movie = dao.searchMovie(title);
        if(movie==null)
        {
            System.out.println("Movie not found");
            return;
        }
       //create a try catch block if movie is null
        LikedEntity liked = new LikedEntity();
        liked.setMovie(movie.getId());
        System.out.println("Movie id is "+movie.getId());
        System.out.println("User id is "+this.getId());
        liked.setUserId(this.getId());
        LikedDaoi likedDaoi = new LikedDaoi();
        likedDaoi.create(liked);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;

        return true;
    }
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
