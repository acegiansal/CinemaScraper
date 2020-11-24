/*
 * Queue data structure
 * Queue.java
 * ICS4U
 * May 9, 2019
 * PPP (Gian S, Morgan S, Ben V.)
 */
package movieScraper;

import java.util.ArrayList;
import java.util.List;


public class Queue<E> {
    private List<E> data;

    /**
     * constructor pre: none post: An empty queue that can hold up to maxItems has
     * been created.
     */

    public Queue() {
        //Assigns the data
        data = new ArrayList<E>();
    }

    /**
     * Returns the front item without removing it from the queue. pre: Queue
     * contains at least one item. post: The front item has been returned while
     * leaving it in the queue.
     */

    public E front() {
        //Returns the front data
        return data.get(0);
    }

    /**
     * Removes the front item from the queue and returns it. pre: Queue contains at
     * least one item. post: The front item of the queue has been removed and
     * returned.
     */

    public E dequeue() {
        //Takes the front data
        E val = data.get(0);
        
        //Removes the front data
        data.remove(0);
        
        //Returns the front data 
        return (val);
    }
    /**
     * Adds an item to the queue if there is room. pre: none post: A new item has
     * been added to the queue.
     */
    public void enqueue(E val) {
        //Adds item to the queue
        data.add(val);
    }

    /**
     * Determines if there are items on the queue. pre: none post: true returned if
     * there are items on the queue, false returned otherwise.
     */
    public boolean isEmpty() {
        //Checks whether it is empty
        return data.isEmpty();
    }

    /**
     * Returns the number of items in the queue. pre: none post: The number of items
     * in the queue is returned.
     */
    public int size() {
        //Returns the number of items in the queue
        return data.size();
    }

    /**
     * Empties the queue. pre: none post: There are no items in the queue.
     */
    public void makeEmpty() {
        //Removes every item in the queue
        while(!isEmpty()) {
            dequeue();
        }
    }
}