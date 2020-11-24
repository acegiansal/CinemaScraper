/*
 * LinkedList.java
 * Creates a linked list of objects
 * PPP (Gian S, Morgan S, Ben V.)
 * ICS4U
 * May 20, 2019
 */
package movieScraper;

public class LinkedList {
	private Node head;

	/**
	 * constructor pre: none post: A linked list with a null item has been created.
	 */
	public LinkedList() {
		head = null;
	}

	/**
	 * Adds a node to the linked list. pre: none post: The linked list has a new
	 * node at the head.
	 */
	public void addAtFront(String str) {
		Node newNode = new Node(str);
		newNode.setNext(head);
		head = newNode;
	}

	/**
	 * Adds a node to the end of the linked list. pre: String parameter post: The
	 * linked list has a new node at the end.
	 */
	public void addAtEnd(String s) {
		Node current = head;
		Node newNode = new Node(s);
		if (head == null) {
			head = newNode;
			head.setNext(null);
		} else {
			// iterate to the last node
			while (current.getNext() != null) {
				current = current.getNext();
			}
			// Append the new node to the end
			current.setNext(newNode);
		}
	}

	public void makeEmpty() {
		head = null;
	}

	/**
	 * Deletes a node in the linked list. pre: none post: The first node containing
	 * str has been deleted.
	 */
	public void remove(String str) {
		Node current = head;
		Node previous = head;
		if (current.getData().equals(str)) {
			head = current.getNext();
		} else {
			while (current.getNext() != null) {
				previous = current;
				current = current.getNext();
				if (current.getData().equals(str)) {
					previous.setNext(current.getNext());
				}
			}
		}
	}

	/**
	 * pre: none post: size is returned
	 * 
	 * @return The size of the linked list (number of nodes)
	 */
	public int getSize() {
		int size = 0;

		while (head != null) {
			size++;
			head = head.getNext();
		}
		return size;
	}

	/**
	 * Creates a string that lists the nodes of the linked list. pre: none post: The
	 * linked list has been written to a string.
	 */
	public String toString() {
		Node current = head;
		String listString;
		if (current != null) {
			listString = current.getData() + "\n";
			while (current.getNext() != null) {
				current = current.getNext();
				listString += current.getData() + "\n";
			}
			return (listString);
		} else {
			return ("There are no items in list.");
		}
	}

	///////////////////////////// NESTED CLASS////////////////////////////////

	private class Node {
		private String data;
		private Node next;

		/**
		 * constructor pre: none post: A node has been created.
		 */

		public Node(String newData) {
			data = newData;
			next = null;
		}

		/**
		 * The node pointed to by next is returned pre: none post: A node has been
		 * returned.
		 */
		public Node getNext() {
			return (next);
		}

		/**
		 * The node pointed to by next is changed to newNode pre: none post: next points
		 * to newNode.
		 */

		public void setNext(Node newNode) {
			next = newNode;
		}

		/**
		 * The node pointed to by next is returned pre: none post: A node has been
		 * returned.
		 */
		public String getData() {
			return (data);
		}
	}
}
