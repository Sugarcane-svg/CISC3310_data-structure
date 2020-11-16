package moviebst;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Movie {

    protected int releaseYear;
    String movieTitle;
    Movie left;
    Movie right;

    public Movie(int year, String title) {
        releaseYear = year;
        movieTitle = title;
    }

    public String toString() {
        return movieTitle + " " + releaseYear;
    }

}

public class MovieBST {

    Movie root;

    //add movies into a tree structure
    public void addMovie(String title, int year) {
        Movie newMovie = new Movie(year, title);

        if (root == null) {
            root = newMovie;
        } else {
            Movie current = root; // set current location in root

            Movie parent;

            while (true) {
                parent = current;  //make current node to the parent

                //if the key is smaller than parent, put it in left
                //otherwise, put in right
                if (title.compareTo(current.movieTitle) <= 0) {
                    current = current.left;
                    if (current == null) {
                        parent.left = newMovie;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parent.right = newMovie;
                        return;
                    }
                }
            }
        }
    }

    //inorder traversal: start from the smallest value
    void inOrderTraverse(Movie parent) {
        if (parent != null) {
            inOrderTraverse(parent.left);
            System.out.println(parent);
            inOrderTraverse(parent.right);
        }
    }

    /* //preorder traversal: start from root to the left side
    void preOrdertTraverse(Movie parent) {
    if (parent != null) {
    
    System.out.println(parent);
    preOrdertTraverse(parent.left);
    preOrdertTraverse(parent.right);
    }
    }
    
    //postorder traversal: start from leftchild to the rightchild
    //finally back to root
    void postOrdertTraverse(Movie parent) {
    if (parent != null) {
    postOrdertTraverse(parent.left);
    postOrdertTraverse(parent.right);
    System.out.println(parent);
    }
    }*/

    public void subSet(Movie parent, String s1, String s2) {
        if (parent != null) {

            if (parent.movieTitle.compareTo(s1) <= 0) {//if current is smaller then traverse right

                subSet(parent.right, s1, s2);

            }
            if (parent.movieTitle.compareTo(s2) >= 0) {//if current is greater then traverse left

                subSet(parent.left, s1, s2);

            }
            //if current is in the interval
            if (parent.movieTitle.compareTo(s1) >= 0 && parent.movieTitle.compareTo(s2) <= 0) {

                System.out.println(parent.movieTitle + parent.releaseYear);

            }
        }

    }

    //read file and store movie title without year into Arraylist
    public static List readFile(String filename) throws FileNotFoundException, IOException, CsvValidationException {
        CSVReader reader;
        reader = new CSVReader(new FileReader(filename));
        String[] line = null;
        line = reader.readNext();
        List<String> title = new ArrayList<>();
        while ((line = reader.readNext()) != null) {
            title.add(line[1]);

        }
        return title;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, CsvValidationException {

        List<String> movieTitle = new ArrayList<>();
        movieTitle = readFile("movies.csv");

        MovieBST movieTree = new MovieBST();
        int year = 0;
        for (int i = 0; i < movieTitle.size(); i++) {
            String title = movieTitle.get(i).substring(0, movieTitle.get(i).length() - 6);
            String y = movieTitle.get(i).substring(movieTitle.get(i).length() - 6);
            try {
                year = Integer.parseInt(y.replaceAll("\\(|\\)", ""));
            } catch (NumberFormatException ex) {
                i++;
            }
            movieTree.addMovie(title, year);

        }

        //movieTree.inOrderTraverse(movieTree.root);//output1.txt
        movieTree.subSet(movieTree.root,"Bug's Life", "Harry Potter");//output2.txt
    }

}
