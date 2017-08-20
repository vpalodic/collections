/**
 * File: BalancedTreeIterator.java
 */
package com.palodichuk.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * An inorder {@code Iterator<T>} for {@code BalancedTree<T>} and derived classes.
 * Provides a fail-fast iterator that throws a ConcurrentModificationException
 * if it detects that the underlying source has been modified since this iterator
 * was created. This iterator iterates in least to greatest order.
 * 
 * @author Vincent J Palodichuk <a href="mailto:hu0011wy@metrostate.edu">
 *         (e-mail me) </a>
 *
 * @version 08-19-2017
 *
 */
public class BalancedTreeIterator<T extends Comparable<T>> implements Iterator<T> {
	private final BalancedTree<T> root;
	private final int size;
	private int index;
	private Queue<T> queue;
	
	/**
	 * Initializes a new BalancedTreeIterator bound to the passed in tree.
	 * The passed in tree cannot be null. The Iterator does its best to fail
	 * fast if it detects a modification to the underlying tree.
	 * 
	 * @param tree
	 *  The tree to iterate over.
	 * @throws NullPointerException
	 *  Indicates that tree is null.
	 */
	public BalancedTreeIterator(BalancedTree<T> tree) {
		if (tree == null) {
			throw new NullPointerException("The tree cannot be null.");
		}
				
		this.root = tree;
		size = root.size();
		index = 0;
		queue = new LinkedList<T>();
	}
	
	/**
	 * Returns true if the underlying source still has data to read and
	 * next() can be called to retrieve it; otherwise false is returned.
	 * @return
	 *  Returns true if there is more data and next() can be called.
	 * @see java.util.Iterator#hasNext()
	 * @throws ConcurrentModificationException
	 *  Indicates that the underlying data source was modified.
	 */
	@Override
	public boolean hasNext() {
		if (root.modified || size != root.size()) {
			throw new ConcurrentModificationException("The underlying data source has been modified since this iterator was created.");
		}
		
		if (queue.isEmpty()) {
			fillQueue();
		}
		
		return !queue.isEmpty();
	}

	// Helper method to fill the iterator's queue of T items.
	// The instance variable index is used to determine which
	// items should be queued up. If there are no more items,
	// then nothing will be added to the queue.
	@SuppressWarnings("unchecked")
	private void fillQueue() {
		if (index < root.numberOfItems) {
			if (index < root.numberOfChildren) {
				// Queue the subtrees
				((BalancedTree<T>) root.children[index]).inorderEnqueue(queue);
			}
			
			queue.add((T) root.items[index]);
			index++;
		} else if (index < root.numberOfChildren) {
			((BalancedTree<T>) root.children[index]).inorderEnqueue(queue);
			index++;
		} //else {
//			System.out.println(String.format("\nBalancedTreeIterator.fillQueue() - Filled up %d times.", index));
//		}
	}

	/**
	 * Retrieve the next element, in order from least to greatest,
	 * from the underlying source. 
	 * @throws NoSuchElementException
	 *  Indicates that there are no more items to iterate over.
	 * @see java.util.Iterator#next()
	 */
	@Override
	public T next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException("All has been iterated over.");
		}
		
		return queue.poll();
	}
	
	/**
	 * The {@code remove()} method is not supported by this iterator.
	 * @throws UnsupportedOperationException
	 *  This is a read-only iterator and {@code remove()} should not be called.
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException("This iterator is read-only");
	}

}
