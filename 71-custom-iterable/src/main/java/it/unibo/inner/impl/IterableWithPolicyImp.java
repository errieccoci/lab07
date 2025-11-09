package it.unibo.inner.api;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.lang.*;

public class IterableWithPolicyImp<T> implements IterableWithPolicy<T>{
    Predicate<T> filter_policy;
    private ArrayList<T> Elements;

    public IterableWithPolicyImp(final T[] elements, final Predicate<T> filter){
        setIterationPolicy(filter);
        this.Elements=new ArrayList<>();

        for(T el: elements){
            if(pol(el)) this.Elements.add(el);
        }

    }

    public IterableWithPolicyImp(final T[] elements){
        this(elements, new Predicate<>(){
            public boolean test( T elem){
                return true;
            }
        });
    }
    private boolean pol(T elem){
        return filter_policy.test(elem);
    }

    public ArrayList getElements(){return this.Elements;}


    private class privateIterator implements Iterator{
        private Iterator<T> it;

        private privateIterator(){
            this.it=Elements.iterator();
        }

        
        public boolean hasNext(){
            return this.it.hasNext();
        }

        
        public T next(){
            return this.it.next();

        }

        
        public void remove(){
            this.it.remove();
        }
    }


    public Iterator<T> iterator(){
        return new privateIterator();
    }

   
    public void setIterationPolicy(Predicate<T> filter){
        this.filter_policy=filter;
        this.Elements=new ArrayList<>();
    }
}
