package singlylinkedlist;

import static sbcc.Core.*;

import java.io.*;
import java.util.*;

public class BasicSinglyLinkedList implements SinglyLinkedList {

	public Node top;
	public Node tail;
	public int count;

	public BasicSinglyLinkedList() {
		top = null;
		tail = null;
		count = 0;
	}


	@Override
	public Node getFirst() {
		if (top != null) {
			return top;
		}
		return null;
	}


	@Override
	public Node getLast() {
		// if (top != null) {// If list is not empty, iterate through until end
		// Node cursor = top;
		// while (cursor.next != null) {
		// cursor = cursor.next;
		// }
		// return cursor;
		// }
		// return null;
		return tail;
	}


	@Override
	public int size() {
		return count;
	}


	@Override
	public boolean isEmpty() {
		if (count == 0) {
			return true;
		}
		return false;
	}


	@Override
	public void clear() {
		top.next = null;
		top = null;
		tail = null;
	}


	@Override
	public Node insert(Node cursor, String value) {
		// Create new node for insert
		Node newNode = new Node(value);
		// FIRST INSTANCE: Not top
		if (cursor != null) {
			newNode.next = cursor.next;
			cursor.next = newNode;
			if (newNode.next == null)// TAIL CASE
				tail = newNode;
			// SECOND INSTANCE: Inserting at top
		} else {
			newNode.next = top;
			top = newNode;
		}

		count++;
		return newNode;
	}


	@Override
	public Node append(String value) {
		Node newNode = new Node(value);
		if (count > 0) {// Not empty
			getLast().next = newNode;
		} else {// Empty
			top = newNode;
		}
		count++;
		tail = newNode;
		return newNode;
	}


	@Override
	public void remove(Node cursor) {// Might create function to find prev.
		// FIRST INSTANCE: CURSOR AT TOP
		if (cursor != null) {
			if (cursor == top) {
				if (cursor.next != null) {
					top = cursor.next;
					cursor.next = null;
				} else {// Last node
					top = null;
					tail = null;
				}
			} else {
				// SECOND INSTANCE: CURSOR NOT AT TOP
				Node prev = findPrev(cursor);// Uses method to find node prior to cursor
				if (prev != null && cursor.next == null) {// If cursor is last
					prev.next = null;
					cursor = null;
					tail = prev;
				} else {// Cursor is in middle
					prev.next = cursor.next;
					cursor.next = null;
				}
			}
			count--;
		}
	}


	@Override
	public Node find(Node start, String key) {
		Node cursor = start;
		if (cursor != null) {
			while (cursor.next != null) {
				if (cursor.value == key) {// Might need to use .equals instead
					return cursor;
				}
				cursor = cursor.next;
			}
		}

		return null;
	}


	// Returns node previous to key. If not found returns null
	public Node findPrev(Node key) {
		Node prev = top;
		boolean flag = false;

		while (prev.next != null && flag == false) {// Iterate through to find node prior to cursor
			if (prev.next == key) {
				flag = true;
				continue;
			} else
				prev = prev.next;// Next node
		}
		if (flag)
			return prev;
		return null;
	}


	@Override
	public String toCsvString() {
		if (count > 0) {// If list is not empty
			Node cursor = top;
			// Create string/stringbuilder
			StringBuilder str = new StringBuilder();
			// Iterate through each node
			while (cursor.next != null) {
				// Add [node.text + ","] to sb
				str.append(cursor.value + ",");
				cursor = cursor.next;
			}
			str.append(cursor.value);// Adds last node value to string
			return str.toString();
		}
		return null;
	}


	@Override
	public ArrayList<String> toList() {
		if (count > 0) {
			ArrayList<String> list = new ArrayList();
			Node cursor = top;
			// Iterate through each node
			while (cursor.next != null) {
				// add string to arraylist
				list.add(cursor.value);
				cursor = cursor.next;
			}
			list.add(cursor.value);
			return list;
		}
		return null;
	}


	@Override
	public void loadFile(String filename) throws IOException {
		var lines = readFileAsLines(filename);
		// Maybe use insert instead of append|| insert(node, value)
		for (var line : lines) {
			append(line);
		}
	}


	@Override
	public void saveFile(String filename) throws IOException {
		writeFileAsLines(filename, toList());
	}

}
