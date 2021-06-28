import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Permutation {
    int k;
    public Permutation(int ka){
        k = ka;
    }
    
    try{
        File myObj = new File("word.txt");
        Scanner reader = new Scanner(myObj);
    while (reader.hasNext()){
        fileLength++;
    }
    } catch (FileNotFoundException e) {
        System.out.println("cant find the file bruh bruh");
    }
}
