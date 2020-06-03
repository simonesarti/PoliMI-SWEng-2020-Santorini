package it.polimi.ingsw.observe;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private List<Observer<T>> observers = new ArrayList<>();

    /**
     * adds an observer to the object on which the method is called
     * @param observer is the object which will observe the object on which the method is called
     */
    public void addObserver(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * removes the specified object from the list of observers
     * @param observer is the observer object to remove from the list of observers
     */
    public void removeObserver(Observer<T> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    /**
     * notifies the object's observer of a change
     * @param message in the object which is notified
     */
    public void notify(T message){
        synchronized (observers) {
            for (Observer<T> observer : observers) {
                observer.update(message);
            }
        }
    }

}
