import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File fl;
        BufferedReader br = null;
        String str=null;
        LinkedList<LinkedList<Integer>> data=new LinkedList<LinkedList<Integer>>();
        LinkedList<Integer> sample;

        for(int i=1;i<=10;i++)
        {
            sample=new LinkedList<Integer>();
           // System.out.println(i+".txt");
             fl=new File(i+".txt");
            // br=new BufferedReader(new FileReader(fl));
            Scanner sc=new Scanner(fl);

            while(sc.hasNextLine()) {
                sample.add(Integer.parseInt(sc.nextLine()));
            }

            data.add(sample);
        }
        /*
        for(int i=0;i<data.size();i++)
        {
            System.out.println(data.get(i));
            System.out.println("-----------------------------------");
        }
            */
        int[] output = new int[10];
        Integer[] goal;
        Integer[] start;
        Permutation pr;
        System.out.println("OUTPUT");
        for(int i=0;i<data.size();i++)
        {
            goal=data.get(i).toArray(new Integer[data.get(i).size()]);
            start=goal.clone();
            Arrays.sort(goal);
           // System.out.println(Arrays.toString(start)+" "+Arrays.toString(goal));
            pr=new Permutation(start,goal);






        }



    }
}
