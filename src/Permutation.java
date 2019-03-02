import java.util.*;

public class Permutation {

    ArrayList<Integer> initial;
    ArrayList<Integer> goal;
    ArrayList<Integer> temp = new ArrayList<>();
    ArrayList<Integer> origional=new ArrayList<>();
   public static  List<ArrayList<Integer>> permit = null;
   public HashMap<ArrayList<Integer>,Integer> heuristic=new HashMap<>();
   public List<ArrayList<Integer>> keys=new ArrayList<>();
   public LinkedHashMap<ArrayList<Integer>, Integer> sortedByOrigional=new LinkedHashMap<ArrayList<Integer>, Integer>();
   public ArrayList<Integer> indexes=new ArrayList<>();
   public ArrayList<Integer> values=new ArrayList<>();
   public int num_trans=0;


    public Permutation(Integer[] init, Integer[] endState)
    {
        initial=new ArrayList<>();
        goal=new ArrayList<>();
        permit=new ArrayList<>();
        Arrays.sort(endState);
        for(Integer k:init)
       {
           initial.add(k);
          // temp.add(k);
           origional.add(k);
       }
        for(Integer k:endState)
        {
            goal.add(k);
        }
        start();
    }
    public void lcs()
    {
        permit.removeAll(permit);
        indexes.removeAll(indexes);
        sortedByOrigional.clear();
        values.removeAll(values);
        keys.removeAll(keys);
        initial.removeAll(initial);
        initial.addAll(origional);
        while(true)
        {
            temp=new ArrayList<>();

            ArrayList<Integer> subString = new ArrayList<>();
            int[][] LCSuff = new int[initial.size() + 1][goal.size() + 1];
            int len = 0;
            // To store the index of the cell which contains the
            // maximum value. This cell's index helps in building
            // up the longest common substring from right to left.
            int row = 0, col = 0;

            /* Following steps build LCSuff[m+1][n+1] in bottom
            up fashion. */
            for (int i = 0; i <= initial.size(); i++) {
                for (int j = 0; j <= goal.size(); j++) {
                    if (i == 0 || j == 0)
                        LCSuff[i][j] = 0;

                    else if (initial.get(i - 1).equals(goal.get(j - 1))) {
                        LCSuff[i][j] = LCSuff[i - 1][j - 1] + 1;
                        if (len < LCSuff[i][j]) {
                            len = LCSuff[i][j];
                            row = i;
                            col = j;
                        }
                    } else
                        LCSuff[i][j] = 0;
                }
            }
            if (len == 0) {
                break;
            }

            while (LCSuff[row][col] != 0) {
                subString.add(initial.get(row - 1));
                initial.set(row-1,0);
                --len;
                row--;
                col--;
            }

            for (int i = subString.size() - 1; i >= 0; i--) {
                temp.add(subString.get(i));
            }
           // initial.removeAll(temp);
            permit.add(temp);
               // System.out.println(temp);



         }

        get_heuristic();
        give_heuristic_origional();
        removeEmpty();
        get_value();
        get_key();


      //  System.out.println(initial);
    }
    void removeEmpty()
    {

       sortedByOrigional.remove(new ArrayList<>());

    }
    void display()
    {
        for(int i=0;i<permit.size();i++)
        {
            System.out.println(permit.get(i));
        }
    }
    void get_heuristic()
    {
        int offset=goal.get(goal.size()-1)+1;
        for(int i=0;i<permit.size();i++)
        {
            int a=permit.get(i).get(0);
            heuristic.put(permit.get(i),offset-a);
        }
      //  System.out.println(heuristic);
       // System.out.println(disjointSet);
      //  System.out.println(origional);

    }

    void do_transposition(int i, int j, int k)
    {

        ArrayList<Integer> temp=new ArrayList<>();
        ArrayList<Integer> temp1=new ArrayList<>();
        ArrayList<Integer> temp2=new ArrayList<>();
        temp1=keys.get(i);
        temp2=keys.get(j);



        if(permit.contains(temp1) && permit.contains(temp2))
        {
            keys.set(i,temp2);
            keys.set(j,temp1);
            origional.removeAll(origional);
            for(int l=0;l<keys.size();l++)
            {
                origional.addAll(keys.get(l));
            }

            temp.addAll(temp2);
            temp.addAll(temp1);

            int m=0;
            for(int l=0;l<=temp.size()-2;l++)
            {
                if(temp.get(l)<temp.get(l+1) )
                {
                    m++;
                }
            }

            if(m==(temp.size()-1))
            {
                num_trans++;
            }
        }
    }
   public void give_heuristic_origional()
   {
       ArrayList<Integer> temp;
       for(int i=0;i<permit.size();i++)
       {
           temp=new ArrayList<>();
           temp=permit.get(i);
           int a=origional.indexOf(temp.get(0));
           indexes.add(a);
           Collections.sort(indexes);
       }
       for(int i=0;i<indexes.size();i++)
       {
           temp=new ArrayList<>();
           if(i!=indexes.size()-1)
           {
               int a=indexes.get(i);
               int b=indexes.get(i+1);
               for(int k=a;k<b;k++)
               {
                   temp.add(origional.get(k));
               }
               sortedByOrigional.put(temp,heuristic.get(temp));

           }
           else
           {
               int a=indexes.get(i);
               for(int k=a;k<origional.size();k++)
               {
                   temp.add(origional.get(k));
               }
               sortedByOrigional.put(temp,heuristic.get(temp));

           }

       }
   }
   public void get_value()
   {
       for(Integer value:sortedByOrigional.values())
       {
           values.add(value);
       }

   }
   public void get_key()
   {
       for(ArrayList<Integer> key:sortedByOrigional.keySet())
       {
           keys.add(key);
       }
   }
   public void swap_values(int i,int j)
   {
       Integer temp=values.get(i);
       values.set(i,values.get(j));
       values.set(j,temp);

   }
   public void start()
   {
       lcs();
       int m=sortedByOrigional.size();
       m=m/3;
       m=m+1;

     //  System.out.println(num_trans);


       int max=goal.size();
       int g=0;
       lcs();
    //  System.out.println(origional);
     //  System.out.println(values);
   /*    System.out.println(indexes);
       System.out.println(keys);
   //    System.out.println(sortedByOrigional);
    //   System.out.println(values);
       System.out.println("1-------------------------------------------------1");*/
       if(values.get(0).equals(max))
       {
           g=max;
       }
     //  System.out.println(sortedByOrigional);

       while(true) {
           if(origional.equals(goal))
           {
               break;
           }
           for (int i = 0; i < values.size() - 1; i++) {
               lcs();
               if (origional.equals(goal)) {
                   break;
               }
               if (values.get(i) + 1 == values.get(i + 1)) {

                   if(i!=values.size()-2 && values.get(i+1)+1==values.get(i+2))
                   {
                       if(values.get(i+2)+1==values.get(i+3)){
                           do_transposition(indexes.indexOf(indexes.get(i)), indexes.indexOf(indexes.get(i + 3)), indexes.indexOf(indexes.get(i)));

                       }
                       else
                       {
                           do_transposition(indexes.indexOf(indexes.get(i)), indexes.indexOf(indexes.get(i + 2)), indexes.indexOf(indexes.get(i)));

                       }
                   }
                   else {
                       do_transposition(indexes.indexOf(indexes.get(i)), indexes.indexOf(indexes.get(i + 1)), indexes.indexOf(indexes.get(i)));
                   }
                 //  swap_values(i, i + 1);
                  // num_trans++;
                   lcs();

                   //  System.out.println(origional);
                   // System.out.println(num_trans);

                //   System.out.println("---------------------------------------------------------");
                   break;
               }




           }
         //  System.out.println(sortedByOrigional);

           // System.out.println(origional);

           //    System.out.println(values);
        //   System.out.println(indexes);
         //  System.out.println(keys);
           //  System.out.println(sortedByOrigional);
           //   System.out.println(values);
         //  System.out.println("-------------------------------");

           for (int i = 0; i < values.size() - 1; i++) {
               lcs();
               if (origional.equals(goal)) {
                   break;
               }
               if (values.get(i) < values.get(i + 1)) {

                   if(i!=values.size()-2 && values.get(i+1)<values.get(i+2))
                   {
                       if(i!=values.size()-3 &&values.get(i+2)<values.get(i+3))
                       {
                           if(i!=values.size()-4&&values.get(i+3)<values.get(i+4))
                           {
                               if(values.get(i+4)<values.get(i+5)){
                                   do_transposition(indexes.indexOf(indexes.get(i)), indexes.indexOf(indexes.get(i + 5)), indexes.indexOf(indexes.get(i)));

                               }
                               else
                               {
                                   do_transposition(indexes.indexOf(indexes.get(i)), indexes.indexOf(indexes.get(i + 4)), indexes.indexOf(indexes.get(i)));

                               }
                           }
                           else
                           {
                               do_transposition(indexes.indexOf(indexes.get(i)), indexes.indexOf(indexes.get(i + 3)), indexes.indexOf(indexes.get(i)));
                           }

                       }
                       else
                       {
                           do_transposition(indexes.indexOf(indexes.get(i)), indexes.indexOf(indexes.get(i + 2)), indexes.indexOf(indexes.get(i)));
                       }

                   }
                    else
                   {
                       do_transposition(indexes.indexOf(indexes.get(i)), indexes.indexOf(indexes.get(i + 1)), indexes.indexOf(indexes.get(i)));

                   }

                   lcs();
                 //  System.out.println(origional);
                 //  System.out.println(num_trans);

                //   System.out.println("---------------------------------------------------------");
                   break;
               }

           }
         //  System.out.println(sortedByOrigional);

         //  System.out.println(origional);//for visulization uncomment this
        //  System.out.println(values);
        //   System.out.println(indexes);
           //  System.out.println(values);
         //  System.out.println("-------------------------------");
       }
       if(m<num_trans && m>0)
       {

           System.out.println(m);
       }
       else
       {

           System.out.println(num_trans);
       }








   }

}
