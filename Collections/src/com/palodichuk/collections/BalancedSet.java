/**
 * File: BalancedSet.java
 */

package com.palodichuk.collections;

import java.util.Comparator;

/**
 * A BalancedSet&lt;T&gt; is a set of T objects stored in a
 * B-tree. Because it is a set, duplicates are not allowed.
 * <p>
 * <b>Rules of the Balanced Set:</b>
 * <ol>
 * <li>The root can have as few as one item (or even no items if it has no
 * children); every other node has at least <code>getMinNumberOfItems()</code>.
 * </li>
 * <li>The maximum number of items in a node is twice the value of
 * getMinNumberOfItems() + 1, which is equal to getMaxNumberOfItems().</li>
 * <li>The items of each B-tree node are stored in a partially filled array,
 * sorted from the smallest item (at index 0) to the largest item (at the final
 * used position of the array).</li>
 * <li>The number of children below a non-leaf node is always one more than the
 * number of items in the node.</li>
 * <li>For any non-leaf node:
 * <ul>
 * <li>Because duplicates are not allowed, an item at index <i>i</i> is
 * greater than all the items in child number <i>i</i> of the node.</li>
 * <li>Because duplicates are not allowed, an item at index <i>i</i> is
 * less than all the items in child number
 * <i>i</i> + 1 of the node.</li>
 * </ul>
 * </li>
 * <li>Every leaf in a B-tree has the same depth.</li>
 * </ol>
 * 
 * @author Vincent J Palodichuk <a HREF="mailto:hu0011wy@metrostate.edu">
 *         (e-mail me) </a>
 *
 * @version 08/15/2017
 *
 */
public class BalancedSet<T extends Comparable<T>> extends BalancedTree<T> {

	/**
	 * Initialize an empty balanced set with the default minimum number of items
	 * per node.
	 * <p>
	 * <b>Postcondition:</b>
	 * <ul>
	 * <li>The set is empty.</li>
	 * </ul>
	 * 
	 * @throws OutOfMemoryError
	 *             Indicates insufficient memory for creating the set.
	 */
	public BalancedSet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialize an empty balanced set with the specified minimum number of items
	 * per node.
	 * <p>
	 * <b>Precondition:</b>
	 * <ul>
	 * <li><code>minimumNumberOfItems</code> must be &gt;= 1.</li>
	 * </ul>
	 * <p>
	 * <b>Postcondition:</b>
	 * <ul>
	 * <li>The set is empty.</li>
	 * <li>The minimum number of items per node is set to the specified
	 * value.</li>
	 * </ul>
	 * 
	 * @param minimumNumberOfItems
	 *            The minimum number of items in each node.
	 * @throws OutOfMemoryError
	 *             Indicates insufficient memory for creating the set.
	 */
	public BalancedSet(int minimumNumberOfItems) {
		super(minimumNumberOfItems);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialize an empty balanced set with the specified minimum number of items
	 * per node and using the specified Comparator&lt;T&gt; to compare items.
	 * <p>
	 * <b>Precondition:</b>
	 * <ul>
	 * <li><code>minimumNumberOfItems</code> must be &gt;= 1.</li>
	 * </ul>
	 * <p>
	 * <b>Postcondition:</b>
	 * <ul>
	 * <li>The set is empty.</li>
	 * <li>The minimum number of items per node is set to the specified
	 * value.</li>
	 * <li>The specified Comparator&lt;T&gt; will be used for comparing items.</li>
	 * </ul>
	 * 
	 * @param minimumNumberOfItems
	 *            The minimum number of items in each node.
	 * @param comparator
	 *            The Comparator&lt;T&gt; to use for comparing items.
	 * @throws OutOfMemoryError
	 *             Indicates insufficient memory for creating the set.
	 */
	public BalancedSet(int minimumNumberOfItems, Comparator<T> comparator) {
		super(minimumNumberOfItems, comparator);
		// TODO Auto-generated constructor stub
	}

	// We override this protected method to prevents
	// duplicate items from being added to this set.
	@Override
	protected boolean looseAdd(int targetIndex, int compareResults, T entry) {
		boolean answer = false; 
		
		if (compareResults != 0) {
			answer = super.looseAdd(targetIndex, compareResults, entry);
		}
		
		return answer;
	}
	
	// We override this protected method to ensure that a
	// BalancedSet is created instead of a BalancedTree
	// Otherwise the code is identical.
	@Override
	protected void rebalanceRoot() {
		BalancedSet<T> child = new BalancedSet<>(minNumberOfItems, comparator);

		child.items = items;
		child.numberOfItems = numberOfItems;
		child.children = children;
		child.numberOfChildren = numberOfChildren;
		child.size = size;

		items = new Object[maxNumberOfItems + 1];
		numberOfItems = 0;
		children = new Object[maxNumberOfItems + 2];
		numberOfChildren = 0;
		size = 0;

		insertChild(0, child);

		fixExcess(0);
	}

	// We override this protected method to ensure that a
	// BalancedSet is created instead of a BalancedTree
	// Otherwise the code is identical.
	@SuppressWarnings("unchecked")
	@Override
	protected BalancedTree<T> splitChild(BalancedTree<T> child, int index) {
		BalancedSet<T> right = new BalancedSet<>(minNumberOfItems, comparator);

		int itemMidpoint = child.numberOfItems / 2;
		T midpoint = (T) child.items[itemMidpoint];
		child.items[itemMidpoint] = null;
		
		// move the items to the node.
		moveRightHalfOfChild(child, right, itemMidpoint + 1, child.numberOfItems - (itemMidpoint + 1));

		// Pass the middle child up to the root in the correct spot.
		insertItem(index, midpoint);

		return right;
	}
}
