package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.List;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {


    private enum Month{
        JANUARY(31), 
        FEBRUARY(28), 
        MARCH(31), 
        APRIL(30), 
        MAY(31), 
        JUNE(30), 
        JULY(31), 
        AUGUST(31), 
        SEPTEMBER(30), 
        OCTOBER(31), 
        NOVEMBER(30), 
        DECEMBER(31);

        private final int days;

        Month(final int d){
            this.days=d;
        }   

        public static Month fromString(final String name){
            ArrayList<Month> lista=new ArrayList<>();
            for(final var m: Month.values()){
                if(m.name().contains(name.toUpperCase())) lista.add(m);
            }

            if(lista.size()==1) return lista.get(0);
            else {
                throw new IllegalArgumentException("error for Month "+name);
            
                }         
        }

        public int getDays(){return this.days;}
        
    }




    //order by the number of days
    public static final class SortByDate implements Comparator<String>{
        @Override
        public int compare(final String s1, final String s2){
            if(s1!=null && s2!=null){

                final int d1=Month.fromString(s1).getDays();
                final int d2=Month.fromString(s2).getDays();

                return d1-d2;
            }else {
                throw new IllegalArgumentException("error, Months are null");}
        }
    }

    //order by the number of days
    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }


    //order by the calendar
   public static final class SortByOrder implements Comparator<String>{
        @Override
        public int compare(final String s1, final String s2){
           if(s1!=null && s2!=null){

                Month m1=Month.fromString(s1);
                Month m2=Month.fromString(s2);

                if(m1!=null && m2!=null){
                    return m1.ordinal()-m2.ordinal();
                }else throw new IllegalArgumentException();
           }else throw new IllegalArgumentException();
          
        }
   }

   
   //order by the calendar
    @Override
    public Comparator<String> sortByOrder() {
        return new SortByOrder();
    }

}
