package music.genre;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicGenre {

    /**
     *
     */
    public static CSVReader reader;
    private static List<String> genreType;
    private static List<String> moviesName;

    public static void main(String[] args) throws FileNotFoundException, IOException, CsvValidationException {
        genreType = readGenre(); //genre types 

        /*genreType.forEach((item) -> {
        System.out.println(item);
        });*/
       
        List<Integer> numberOfMovie = readNumberOfMovies(genreType); // total movie number each genre
        List<Integer> numberOfRecent5 = readMostRecent5Years(genreType); //movie number each genre for the most recent 5 years
        
        //movies are classified under each genre -->output1.txt
        HashMap<String, Integer> genre = new HashMap<>();
        hashmapGenerator(genre, genreType, numberOfMovie);
        displayHashMap(genre);
        
        
        //movies are classified most recent 5 years vs whole data -->output2
        HashMap<String,Integer> mostRecent5Year = new HashMap<>();
        hashmapGenerator(mostRecent5Year, genreType, numberOfRecent5);
        displayHashMap(mostRecent5Year);
        System.out.println();//formatting
        displayPercentage(numberOfRecent5,numberOfMovie);
        
        
        //year parsed
        List<Integer> year = generateYearArray();
        /*year.forEach((item) -> {
        System.out.println(item);
        });*/
        
        HashMap<Integer, HashMap> eachYearEachGenre = new HashMap<>();
        for (int i = 0; i < year.size() - 1; i++) {
        HashMap<String, Integer> hm = moviesEachYearEachGenre(year.get(i));
        eachYearEachGenre.put(year.get(i), hm);
        }
        for(Map.Entry<Integer, HashMap> entry:eachYearEachGenre.entrySet()){
        System.out.println(entry.getKey()+": "+entry.getValue().get("Total"));
        }//total movie number each year --> output4
        
        displayResult(eachYearEachGenre);//movies in each genre each year -->output3
    }

    public static List readMoviesName() throws FileNotFoundException, IOException, CsvValidationException {
        CSVReader reader;
        reader = new CSVReader(new FileReader("movies.csv"));
        String[] line = null;
        line = reader.readNext();
        List<String> title = new ArrayList<>();
        while ((line = reader.readNext()) != null) {
            title.add(line[1]);

        }
        return title;
    }

    //read each genre in one word
    public static List readGenre() throws FileNotFoundException, IOException, CsvValidationException {
        List<String> temp = readFileGenre();
        String[] temp1;
        List<String> fileGenre = new ArrayList<>();

        for (int i = 0; i < temp.size() - 1; i++) {
            temp1 = temp.get(i).split("\\|");
            for (String item : temp1) {
                if (!fileGenre.contains(item)) {
                    fileGenre.add(item);
                    //System.out.println(genreTypes.size());
                }
            }
        }
        return fileGenre;
    }

    public static List readFileGenre() throws FileNotFoundException, IOException, CsvValidationException {
        reader = new CSVReader(new FileReader("movies.csv"));
        String[] line = null;
        line = reader.readNext();
        List<String> genreTypes = new ArrayList<>();
        String temp;
        while ((line = reader.readNext()) != null) {
            temp = line[2];

            genreTypes.add(temp);
            //System.out.println(genreTypes.size());

        }

        //System.out.println(genreTypes.size());
        return genreTypes;
    }

    //read all data from file
    public static List readNumberOfMovies(List genreTypes) throws IOException, FileNotFoundException, CsvValidationException {
        List<String> fileGenre = readFileGenre();
        String[] temp = new String[genreTypes.size()];
        temp = fileGenre.toArray(temp);
        List<Integer> numberOfMovie = new ArrayList<>();
        int[] number = new int[genreTypes.size()];
        int j = 0;
        while (j < fileGenre.size()) {
            for (int i = 0; i < genreTypes.size(); i++) {
                if (temp[j].contains((CharSequence) genreTypes.get(i))) {
                    number[i]++;
                }
            }
            j++;
        }
        for (int num : number) {
            numberOfMovie.add(num);
            //System.out.println(num);
        }

        return numberOfMovie;

    }

    //read data with year > = 2015
    public static List readMostRecent5Years(List genreTypes) throws FileNotFoundException, IOException, CsvValidationException {
        int[] number = new int[genreTypes.size()];
        List<Integer> numberOfMovie = new ArrayList<>();
        reader = new CSVReader(new FileReader("movies.csv"));
        String[] line;
        line = reader.readNext();

        int year;

        while ((line = reader.readNext()) != null) {

            for (int i = 0; i < genreTypes.size(); i++) {
                year = parseYear(line[1]);
                if (line[2].contains((CharSequence) genreTypes.get(i)) && year >= 2015) {
                    number[i]++;
                }
            }
        }
        for (int num : number) {
            numberOfMovie.add(num);
            //System.out.println(num);
        }
        return numberOfMovie;
    }

    //display the percentage 
    public static void displayPercentage(List<Integer> recent, List<Integer> all) {
        double[] percentage = new double[genreType.size()];
        for (int i = 0; i < percentage.length; i++) {
            percentage[i] = ((double) recent.get(i) / (double) all.get(i)) * 100;
        }
        System.out.print("The percentage of data for most recent 5 years in "
                + "this whole data is: \n");
        System.out.print("Genre Type: percentage\n");
        for (int i = 0; i < genreType.size(); i++) {
            System.out.printf(genreType.get(i) + ": %.2f%%%n", percentage[i]);
        }
    }

    public static int parseYear(String str) {
        int year = 0;
        String temp = str.trim().substring(str.length() - 5, str.length() - 1);
        try {
            year = Integer.parseInt(temp);
        } catch (NumberFormatException ex) {
            ;
        }
        return year;
    }
    
    //in order to parse and store year
    public static List generateYearArray() throws FileNotFoundException, IOException, CsvValidationException {
        moviesName=readMoviesName();
        String[] temp = new String[moviesName.size()];
        List<Integer> year = new ArrayList<>();
        moviesName.toArray(temp);
        
        for (int i = 0; i <moviesName.size() ; i++) {
                int eachYear = parseYear(temp[i]);
                if (!year.contains(eachYear)) {
                    year.add(eachYear);
                }
            }
        
        return year;
        
    }

    public static HashMap moviesEachYearEachGenre(int year) throws FileNotFoundException, IOException, CsvValidationException {
        int[] number = new int[genreType.size()];
        List<Integer> numberOfMovie = new ArrayList<>();
        reader = new CSVReader(new FileReader("movies.csv"));
        String[] line;
        line = reader.readNext();

        int y;

        while ((line = reader.readNext()) != null) {

            for (int i = 0; i < genreType.size(); i++) {
                y = parseYear(line[1]);
                if (line[2].contains((CharSequence) genreType.get(i)) && y == year) {
                    number[i]++;
                }
            }
        }
        for (int num : number) {
            numberOfMovie.add(num);
            //System.out.println(num);
        }

        HashMap<String, Integer> hm = new HashMap<>();
        hashmapGenerator(hm, genreType, numberOfMovie);
        return hm;
    }
    
    //generate hashmap values
    public static void hashmapGenerator(HashMap hm, List<String> key, List<Integer> value) {
        for (int i = 0; i < key.size(); i++) {
            hm.put(key.get(i), value.get(i));
        }
        //for further visualization
        hm.put("Total", totalNumberOfMoviesPerYear(hm));

    }
    
    
    //display the total result
    public static void displayResult(HashMap<Integer, HashMap> hm) {
        for (Map.Entry<Integer, HashMap> entry : hm.entrySet()) {
            Integer k = entry.getKey();
            HashMap v = entry.getValue();
            System.out.println(k + ": ");
            displayHashMap(v);
            System.out.println();
        }
    }
    
    
    //display single hashmap
    public static void displayHashMap(HashMap<String, Integer> hm) {
        for (Map.Entry<String, Integer> entry : hm.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
    
    //calculate the total for further visualization
    public static int totalNumberOfMoviesPerYear(HashMap<String, Integer> hm) {
        int total = 0;
        for (Map.Entry<String, Integer> entry : hm.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }
}
