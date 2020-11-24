/*
 * Stack.java
 * Creates a stack object
 * PPP (Gian S, Morgan S, Ben V.)
 * ICS4U
 * May 20, 2019
 */
package movieScraper;

import java.util.ArrayList;

public class Stack<E> {
	private ArrayList<Object> data;
	private int top;

	/**
	 * constructor pre: none post: An empty stack that can hold up to maxItems has
	 * been created.
	 */
	public Stack() {
		data = new ArrayList<Object>();
		top = -1; // no items in the array
	}

	/**
	 * Returns the top item without removing it from the stack. pre: Stack contains
	 * at least one item. post: The top item has been returned while leaving it on
	 * the stack.
	 */
	public Object top() {
		return (data.get(top));
	}

	/**
	 * Removes the top item from the stack and returns it. pre: Stack contains at
	 * least one item. post: The top item of the stack has been removed and
	 * returned.
	 */
	public Object pop() {
		top--;
		return (data.get(top + 1));
	}

	/**
	 * Adds an item to the top of the stack if there is room. pre: none post: A new
	 * item has been added to the top of the stack.
	 */
	public void push(Object object) {
		/*
		 * top += 1; data.set(top, object);
		 */
		top++;
		data.add(object);
	}

	/**
	 * Determines if there are items on the stack. pre: none post: true returned if
	 * there are items on the stack, false returned otherwise.
	 */
	public boolean isEmpty() {
		if (top == -1) {
			return (true);
		} else {
			return (false);
		}
	}

	/**
	 * Returns the number of items in the stack. pre: none post: The number of items
	 * in the stack is returned.
	 */
	public int size() {
		if (isEmpty()) {
			return (0);
		} else {
			return (top + 1);
		}
	}

	/**
	 * Empties the stack. pre: none post: There are no items in the stack.
	 */
	public void makeEmpty() {
		top = -1;
	}
}



