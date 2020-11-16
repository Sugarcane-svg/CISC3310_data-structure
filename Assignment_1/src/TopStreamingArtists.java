package topstreamingartists;

import java.io.*;
import java.util.*;

public class TopStreamingArtists {

    public static final int ROW = 195, COL = 5;

    public static void main(String[] args) throws FileNotFoundException {
        boolean duplicated;
        String[][] array = new String[ROW][2];
        array = array();
        array = artist(array);
        //report for artist and streamings
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i][0] != null) {
                if(!array[i][0].equalsIgnoreCase(array[i+1][0]))
                System.out.print("\"" + array[i][0].replace("\"", "") + "\": " + array[i][1] + ".\n");
            }
        }
        System.out.println();

        VIPReport vip = new VIPReport();
        for (int i = 0; i < array.length; i++) {
            if (array[i][0] != null) {
                vip.add(array[i][0]);
            }
        }
        System.out.println("VIP Client Report");
        vip.alphaReport();

    }

    //create a new array with only the name of artist and streaming
    public static String[][] artist(String[][] arr) {
        
        String[][] artist = new String[ROW][2];
        for (int i = 0; i < ROW - 1; i++) {
            for (int j = 0; j < artist[i].length; j++) {  
                artist[i][j] = arr[i][j + 2];
            }
        }
        return artist;
    }

    public static String[][] array() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("TopStreaming.csv"), "UTF-8");
        String firstLine = sc.nextLine();//skip the first useless line
        String title = sc.nextLine();//skip title

        String[][] table = new String[ROW][COL];
        String[] read = sc.nextLine().split(",");
        //System.out.println(read.length);//check the length is 5

        //read file
        while (sc.hasNextLine()) {

            for (int i = 0; i < ROW - 1; i++) {
                //ignore rows with multiple commas
                if (read.length != 5) {
                    read = sc.nextLine().split(",");
                } else {
                    for (int j = 0; j < read.length; j++) {
                        table[i][j] = read[j];
                        //System.out.print(table[i][j] + " ");//check output
                    }
                    //System.out.print("\n");//formatting
                }
                try {
                    read = sc.nextLine().split(",");
                } catch (NoSuchElementException ex) {
                    i++;
                }

            }

        }//end of while
        return table;
    }

}

class Artist {

    protected String name;
    Artist next;

    public Artist(String s) {
        name = s;
    }
}

class VIPReport {

    private Artist first;

    public VIPReport() {
        first = null;
    }


    public void alphaReport() {
        Artist move = first;
        ArrayList<String> report = new ArrayList<>();
        while (move != null) {
            report.add(move.name);
            move = move.next;
        }
        Collections.sort(report);
        ArrayList<String> newList=removeDuplicates(report);
        for (String s : newList) {
            
            System.out.println(s.replace("\"",""));
        }
    }
    public static ArrayList<String> removeDuplicates(ArrayList<String> list) 
    { 
  
        // Create a new ArrayList 
        ArrayList<String> newList = new ArrayList<>(); 
  
        // Traverse through the first list 
        for (String element : list) { 
  
            // If this element is not present in newList 
            // then add it 
            if (!newList.contains(element)) { 
  
                newList.add(element); 
            } 
        } 
  
        // return the new list 
        return newList; 
    } 
  
    //put name into artist
    public void add(String a) {
        Artist node = new Artist(a);
        node.next = first;
        first = node;
    }

}
