package org.example;
import Database.dao.DaoI;
import Database.dao.LikedDaoi;
import Database.dao.UserDaoi;
import Database.model.LikedEntity;
import Database.model.MovieTableEntity;
import Database.model.UserEntity;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

public class Menu {
    UserEntity userEntity = new UserEntity();
    DaoI daoI = new DaoI();
    String username;
    String password;
    public void MenuShow()
    {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
    }
    public void MenuForUser()
    {
        System.out.println("1. Show all movies");
        System.out.println("2. Search movie");
        System.out.println("3. Like a movie");
        System.out.println("4. Dislike a movie");
        System.out.println("5. Show all liked movies");
        System.out.println("6. Show feed");
        System.out.println("7. Exit");
    }
    public void chooseOption() {
        //create infinite menu
        int option=0;
        while (option != 3) {
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

    }
    public void chooseUserOption() {
        int option = 0;
        while (option != 7) {
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showAllMovies();
                    break;
                case 2:
                    searchMovie();
                    break;
                case 3:
                    likeAMovie();
                    break;
                case 4:
                    break;
                case 5:
                    likedMovies();
                    break;
                case 6:
                    showFeed();
                    break;
                case 7:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }
    public void MenuForAdmin()
    {
        System.out.println("1. Show all movies");
        System.out.println("2. Search movie");
        System.out.println("3. Add a movie");
        System.out.println("4. Delete a movie");
        System.out.println("6. Exit");
    }
    public void chooseAdminOption() {
        int option = 0;
        while (option != 6) {
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    showAllMovies();
                    break;
                case 2:
                    searchMovie();
                    break;
                case 3:
                    DaoI daoI = new DaoI();
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Enter title");
                    String title = scanner1.nextLine();
                    System.out.println("Enter director");
                    String director = scanner1.nextLine();
                    System.out.println("Enter release date");
                    String releaseDate = scanner1.nextLine();
                    System.out.println("Enter genre");
                    String genre = scanner1.nextLine();
                    MovieTableEntity movieTableEntity = new MovieTableEntity(title, director, releaseDate, genre);
                    daoI.create(movieTableEntity);
                    break;
                case 4:
                    DaoI daoI1 = new DaoI();
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.println("Enter titie");
                    String movie = scanner2.nextLine();
                    daoI1.deleteMovie(movie);
                    break;
                case 5:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }
        public void clearScreen() {
            try {
                if (System.getProperty("os.name").contains("Windows"))
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                else
                    Runtime.getRuntime().exec("clear");
            } catch (IOException | InterruptedException ex) {}
        }

        public void login()
        {
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Enter username: ");
            String username = scanner1.nextLine();
            System.out.println("Enter password: ");
            String password = scanner1.nextLine();
            UserDaoi userDaoi = new UserDaoi();
            userEntity = userDaoi.searchUserByUsername(username);
            if(userDaoi.searchUser(username,password))
            {
                if(userEntity.getRole().equals(true))
                {
                    MenuForAdmin();
                    chooseAdminOption();
                }
                else
                {
                    MenuForUser();
                    chooseUserOption();
                }
            }
            else {
                System.out.println("Invalid username or password");
            }
        }
        public void register()
        {
            Scanner scanenr1= new Scanner(System.in);
            System.out.println("Enter username: ");
            username = scanenr1.nextLine();
            System.out.println("Enter password: ");
            password = scanenr1.nextLine();
            UserDaoi userDaoi = new UserDaoi();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userEntity.setRole(false);
            if(userDaoi.searchUser(username,password))
            {
                System.out.println("User already exists");
            }
            else {

                userDaoi.create(userEntity);
                userEntity=userDaoi.searchUserByUsername(username);
                System.out.println(userEntity.getId());
                Menu menu = new Menu();
                menu.MenuForUser();
                menu.chooseUserOption();
            }
        }
        public void showAllMovies()
        {

                List<MovieTableEntity> movieTableEntityList = daoI.getAll();
                for(MovieTableEntity movieTableEntity: movieTableEntityList)
                {
                    System.out.println(movieTableEntity.getTitle());
                }


        }
      public void readFromFile()
      {
          File file=new File("Movies.txt");
            try {
                Scanner scanner=new Scanner(file);
                while (scanner.hasNextLine())
                {
                    String line=scanner.nextLine();
                    String[] parts=line.split(",");
                    String title=parts[0];
                    String director=parts[1];
                    String releaseDate=parts[2];
                    String genre=parts[3];
                    MovieTableEntity movieTableEntity = new MovieTableEntity();
                    movieTableEntity.setTitle(title);
                    movieTableEntity.setDirector(director);
                    movieTableEntity.setReleaseDate(Date.valueOf(releaseDate));
                    movieTableEntity.setGenre(genre);
                    daoI.create(movieTableEntity);

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

      }
      public void searchMovie()
      {
          Scanner scanner = new Scanner(System.in);
          System.out.println("Enter title: ");
          String title = scanner.nextLine();
          DaoI daoI = new DaoI();
            daoI.searchMovie(title);
            if( daoI.searchMovie(title)!=null)
            {
                System.out.println("Movie found");
            }
            else {
                System.out.println("Movie not found");
            }
      }
      public void likedMovies()
      {
          LikedDaoi likedDaoi = new LikedDaoi();
          long id=userEntity.getId();
          DaoI daoi=new DaoI();
          List<LikedEntity>allLikedMovies=likedDaoi.getAllByUserId(id);
          for(LikedEntity a: allLikedMovies)
              System.out.println(daoi.searchMovie(Math.toIntExact(a.getMovie())).getTitle());

      }
      public void likeAMovie()
      {
           UserDaoi userDaoi = new UserDaoi();
            Scanner scanner = new Scanner(System.in);
            userEntity = userDaoi.searchUserByUsername(userEntity.getUsername());
            System.out.println("Enter title: ");
            String title = scanner.nextLine();
            System.out.println(userEntity.getId());
            userEntity.LikeMovie(title);
      }
      public List<MovieTableEntity> listDiff(List<MovieTableEntity> a, List<MovieTableEntity>b)
      {
          Set<MovieTableEntity> set1 = new HashSet<>(a);
          Set<MovieTableEntity> set2 = new HashSet<>(b);
          set1.removeAll(set2);
          List<MovieTableEntity>filmeNevizionate=new ArrayList<>(set1);
          return filmeNevizionate;
      }
      public void showFeed()
      {
          List<MovieTableEntity> movieTableEntities = new ArrayList<>();
          DaoI daoI = new DaoI();
            movieTableEntities = daoI.getAll();
            LikedDaoi likedDaoi = new LikedDaoi();
            likedDaoi.getAllByUserId(userEntity.getId());
            List<String> genres = new ArrayList<>();
            List<MovieTableEntity> watchedMovies=new ArrayList<>();
            for(LikedEntity a: likedDaoi.getAllByUserId(userEntity.getId()))
            {
                genres.add(daoI.searchMovie(Math.toIntExact(a.getMovie())).getGenre());
                watchedMovies.add(daoI.searchMovie(Math.toIntExact(a.getMovie())));
            }
            movieTableEntities=listDiff(movieTableEntities,watchedMovies);
            sortMoviesByLikedGenres(movieTableEntities,genres);
          try (FileWriter writer = new FileWriter("feed.txt")) {
              for (MovieTableEntity movieTableEntity : movieTableEntities) {
                  writer.write(movieTableEntity.getTitle() + " "+ movieTableEntity.getGenre()+"\n");
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
            }
    public static void sortMoviesByLikedGenres(List<MovieTableEntity> movies, List<String> likedGenres) {
        Collections.sort(movies, (m1, m2) -> {
            int genre1Index = likedGenres.indexOf(m1.getGenre());
            int genre2Index = likedGenres.indexOf(m2.getGenre());

            if (genre1Index == -1 && genre2Index == -1) {
                return m1.getGenre().compareTo(m2.getGenre());
            } else if (genre1Index == -1) {
                return 1;
            } else if (genre2Index == -1) {
                return -1;
            } else {
                return Integer.compare(genre1Index, genre2Index);
            }
        });
    }
    }

