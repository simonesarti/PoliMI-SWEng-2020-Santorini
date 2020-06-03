package it.polimi.ingsw.observe;

public interface Observer<T> {

    /**
     * does something based on the message received
     * @param message is the object received through notify of the observed object
     */
    public void update(T message);

}