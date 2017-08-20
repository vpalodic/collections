package com.palodichuk.collections.tests;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.Test;

import com.palodichuk.collections.AlphanumComparator;
import com.palodichuk.collections.BalancedTree;

public class BalancedTreeTest {
	@Test
	public void testPrintTrees() {
		final long SEED = 20170815;
		final int MINIMUM = 2;
		final int NUM_NUMBERS = 1024;

		long start = System.nanoTime();

		System.out.println();
		System.out.println("Begin BalancedTree Printing Test...");
		System.out.println();
		System.out.println(String.format("Using Seed: %,d\tMinimum: %,d\tSize: %,d", SEED, MINIMUM, NUM_NUMBERS));
		System.out.println();
		System.out.println("Testing Printing String Trees Using An AlphanumComparator()");
		System.out.println();

		testPrintComparatorStringTree(SEED, MINIMUM, NUM_NUMBERS);

		System.out.println();
		System.out.println("Testing Printing String Trees Using Natural Ordering");
		System.out.println();

		testPrintStringTree(SEED, MINIMUM, NUM_NUMBERS);

		System.out.println();
		System.out.println("Testing Printing Integer Trees Using A Comparator<Integer>");
		System.out.println();

		testPrintComparatorIntegerTree(SEED, MINIMUM, NUM_NUMBERS);

		System.out.println();
		System.out.println("Testing Printing Integer Trees Using Natural Ordering");
		System.out.println();

		testPrintIntegerTree(SEED, MINIMUM, NUM_NUMBERS);

		System.out.println();
		System.out.println("Testing Printing Integer Trees Using Natural Ordering and Native Iterator");
		System.out.println();

		testPrintIntegerIteratorTree(SEED, MINIMUM, NUM_NUMBERS);

		System.out.println();


		long elapsed = System.nanoTime() - start;

		System.out.println(
				String.format("It took %,f seconds to perform the BalancedTree Printing Test!", (elapsed / 1E9)));
		System.out.println();
	}

	@Test
	public void testRemoveTrees() {
		final long SEED = 20170815;
		final int MINIMUM = 10;
		final int NUM_NUMBERS = 1024;

		long start = System.nanoTime();
		System.out.println();
		System.out.println("Begin BalancedTree Removing Test...");
		System.out.println();
		System.out.println(String.format("Using Seed: %,d\tMinimum: %,d\tSize: %,d", SEED, MINIMUM, NUM_NUMBERS));
		System.out.println();
		System.out.println("Testing Removing String Trees Using A Comparator<String>");
		System.out.println();
		testRemoveComparatorStringTree(SEED, MINIMUM, NUM_NUMBERS);
		System.out.println();
		System.out.println("Testing Removing String Trees Using Natural Ordering");
		System.out.println();
		testRemoveStringTree(SEED, MINIMUM, NUM_NUMBERS);
		System.out.println();
		System.out.println("Testing Removing Integer Trees Using A Comparator<Integer>");
		System.out.println();
		testRemoveComparatorIntegerTree(SEED, MINIMUM, NUM_NUMBERS);
		System.out.println();
		System.out.println("Testing Removing Integer Trees Using Natural Ordering");
		System.out.println();
		testRemoveIntegerTree(SEED, MINIMUM, NUM_NUMBERS);
		System.out.println();
		long elapsed = System.nanoTime() - start;
		System.out.println(
				String.format("It took %,f seconds to perform the BalancedTree Removing Test!", (elapsed / 1E9)));
		System.out.println();
	}

	@Test
	public void testSearchTrees() {
		final long SEED = 20170815;
		final int MINIMUM = 10;
		final int NUM_NUMBERS = 1024;

		long start = System.nanoTime();
		System.out.println();
		System.out.println("Begin BalancedTree Searching Test...");
		System.out.println();
		System.out.println(String.format("Using Seed: %,d\tMinimum: %,d\tSize: %,d", SEED, MINIMUM, NUM_NUMBERS));
		System.out.println();
		System.out.println("Testing Searching String Trees Using A Comparator<String>");
		System.out.println();
		testSearchComparatorStringTree(SEED, MINIMUM, NUM_NUMBERS);
		System.out.println();
		System.out.println("Testing Searching String Trees Using Natural Ordering");
		System.out.println();
		testSearchStringTree(SEED, MINIMUM, NUM_NUMBERS);
		System.out.println();
		System.out.println("Testing Searching Integer Trees Using A Comparator<Integer>");
		System.out.println();
		testSearchComparatorIntegerTree(SEED, MINIMUM, NUM_NUMBERS);
		System.out.println();
		System.out.println("Testing Searching Integer Trees Using Natural Ordering");
		System.out.println();
		testSearchIntegerTree(SEED, MINIMUM, NUM_NUMBERS);
		System.out.println();
		System.out.println("Testing Searching Integer Trees Sequential Using Natural Ordering");
		System.out.println();
		testSearchIntegerSequentialTree(MINIMUM, NUM_NUMBERS);
		System.out.println();
		System.out.println("Testing Searching Integer Trees Java's SortedTree Using Natural Ordering");
		System.out.println();
		testSearchIntegerSortedSet(NUM_NUMBERS);
		System.out.println();

		long elapsed = System.nanoTime() - start;
		System.out.println(
				String.format("It took %,f seconds to perform the BalancedTree Searching Test!", (elapsed / 1E9)));
		System.out.println();
	}

	private void testPrintComparatorStringTree(long seed, int min, int num) {
		final long SEED = seed;
		final Random random = new Random(SEED);

		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		BalancedTree<String> intTree = new BalancedTree<String>(MINIMUM, new AlphanumComparator());

		long start = 0;
		long elapsed = 0;

		assertNotNull(intTree);

		int i = 0;
		int j = 0;

		System.out.println(String.format(
				"Building a BalancedTree with an AlphanumComparator() of %,d Strings using a random number generator...",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			j++;
			if (intTree.add(String.format("%d", nextNumber))) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random number generator calls in reverse to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		if (intTree.add(null)) {
			assertEquals(++i, intTree.size());
		}
		
		assertTrue((int)(Math.log(intTree.size()) / Math.log(2)) > intTree.height());

		start = System.nanoTime();
		assertTrue(intTree.contains(null));
		elapsed = System.nanoTime() - start;

		System.out.println(String.format("It took %,f seconds to perform a search for null in a sea of %,d numbers.",
				(elapsed / 1E9), NUM_NUMBERS));

		start = System.nanoTime();

		for (i = NUM_NUMBERS, j = 0; i > 0; i--) {
			if (intTree.contains(String.format("%d", i))) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));

		System.out.println();
		System.out.println("The wonderful B-tree inorder traversal:");
		System.out.println();
		intTree.inorderPrint();
		System.out.println();

		System.out.println();
		System.out.println("The wonderful B-tree preorder traversal:");
		System.out.println();
		intTree.print(0);
	}

	private void testPrintStringTree(long seed, int min, int num) {
		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		BalancedTree<String> intTree = new BalancedTree<String>(MINIMUM);

		long start = 0;
		long elapsed = 0;

		assertNotNull(intTree);

		int i = 0;
		int j = 0;

		System.out
				.println(String.format("Building a BalancedTree of %,d Strings using sequential calls.", NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = i;
			j++;
			if (intTree.add(String.format("%d", nextNumber))) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		System.out.println(
				String.format("It took %,f seconds to make %,03d sequential calls to create the tree of %,d numbers.",
						elapsed / 1E9, j, NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(String.format("%d", i))) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));

		System.out.println();
		System.out.println("The wonderful B-tree inorder traversal:");
		System.out.println();
		intTree.inorderPrint();
		System.out.println();

		System.out.println();
		System.out.println("The wonderful B-tree preorder traversal:");
		System.out.println();
		intTree.print(0);
	}

	private void testPrintComparatorIntegerTree(long seed, int min, int num) {
		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		BalancedTree<Integer> intTree = new BalancedTree<Integer>(MINIMUM, new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				int answer = -1;

				if (a == null && b == null) {
					answer = 0;
				} else if ((a == null) ^ (b == null)) {
					answer = (a == null) ? -1 : 1;
				} else {
					answer = a.compareTo(b);
				}
				return answer;
			}
		});

		long start = 0;
		long elapsed = 0;

		assertNotNull(intTree);

		int i = 0;
		int j = 0;

		System.out.println(String.format(
				"Building a BalancedTree with a Comparator<Integer> of %,d Integers using sequential calls.",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = i;
			j++;
			if (intTree.add(nextNumber)) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		System.out.println(
				String.format("It took %,f seconds to make %,03d sequential calls to create the tree of %,d numbers.",
						elapsed / 1E9, j, NUM_NUMBERS));

		if (intTree.add(null)) {
			assertEquals(++i, intTree.size());
		}

		start = System.nanoTime();
		assertTrue(intTree.contains(null));
		elapsed = System.nanoTime() - start;

		System.out.println(String.format("It took %,f seconds to perform a search for null in a sea of %,d numbers.",
				(elapsed / 1E9), NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(i)) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));

		System.out.println();
		System.out.println("The wonderful B-tree inorder traversal:");
		System.out.println();
		intTree.inorderPrint();
		System.out.println();

		System.out.println();
		System.out.println("The wonderful B-tree preorder traversal:");
		System.out.println();
		intTree.print(0);
	}

	private void testPrintIntegerTree(long seed, int min, int num) {
		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		long start = 0;
		long elapsed = 0;

		BalancedTree<Integer> intTree = new BalancedTree<Integer>(MINIMUM);

		assertNotNull(intTree);

		int i = NUM_NUMBERS;
		int j = 0;

		System.out.println(String.format(
				"Building a BalancedTree of %,d Integers using sequential calls in reverse order.", NUM_NUMBERS));

		start = System.nanoTime();

		while (i > 0) {
			int nextNumber = i;
			j++;
			if (intTree.add(nextNumber)) {
				assertEquals(NUM_NUMBERS - --i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d sequential calls in reverse order to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		start = System.nanoTime();

		for (i = NUM_NUMBERS, j = 0; i > 0; i--) {
			if (intTree.contains(i)) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));

		System.out.println();
		System.out.println("The wonderful B-tree inorder traversal:");
		System.out.println();
		intTree.inorderPrint();
		System.out.println();

		System.out.println();
		System.out.println("The wonderful B-tree preorder traversal:");
		System.out.println();
		intTree.print(0);
	}

	private void testPrintIntegerIteratorTree(long seed, int min, int num) {
		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		long start = 0;
		long elapsed = 0;

		BalancedTree<Integer> intTree = new BalancedTree<Integer>(MINIMUM);

		assertNotNull(intTree);

		int i = NUM_NUMBERS;
		int j = 0;

		System.out.println(String.format(
				"Building a BalancedTree of %,d Integers using sequential calls in reverse order.", NUM_NUMBERS));

		start = System.nanoTime();

		while (i > 0) {
			int nextNumber = i;
			j++;
			if (intTree.add(nextNumber)) {
				assertEquals(NUM_NUMBERS - --i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d sequential calls in reverse order to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		start = System.nanoTime();

		for (i = NUM_NUMBERS, j = 0; i > 0; i--) {
			if (intTree.contains(i)) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));

		System.out.println();
		System.out.println("The wonderful B-tree inorder traversal print method:");
		System.out.println();
		intTree.inorderPrint();
		System.out.println();

		System.out.println();
		System.out.println("The wonderful B-tree inorder traversal iterator method:");
		System.out.println();
		
		i = 0;
		j = 0;
		
		for (Integer integer : intTree) {
			System.out.print(integer.toString() + " ");
			i++;
			if (i % 20 == 0) {
				System.out.println();
			}
		}
		System.out.println();
	}

	private void testRemoveComparatorIntegerTree(long seed, int min, int num) {
		final long SEED = seed;
		final Random random = new Random(SEED);

		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		BalancedTree<Integer> intTree = new BalancedTree<Integer>(MINIMUM, new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				int answer = -1;

				if (a == null && b == null) {
					answer = 0;
				} else if ((a == null) ^ (b == null)) {
					answer = (a == null) ? -1 : 1;
				} else {
					answer = a.compareTo(b);
				}
				return answer;
			}
		});

		assertNotNull(intTree);

		int i = 0;
		int j = 0;
		long start = 0;
		long elapsed = 0;

		System.out.println(String.format(
				"Building a BalancedTree with a Comparator<Integers> of %,d Integers using a random number generator...",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			j++;
			if (intTree.add(nextNumber)) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		j = 0;
		i = intTree.size();

		start = System.nanoTime();

		while (intTree.getNumberOfItems() > 0) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			if (intTree.remove(nextNumber)) {
				assertEquals(--i, intTree.size());
			}

			j++;
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to delete the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		i = 0;
		j = 0;

		System.out.println(String.format(
				"Building a BalancedTree with a Comparator<Integers> of %,d Integers using sequential calls.",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = i;

			if (intTree.add(nextNumber)) {
				assertEquals(++i, intTree.size());
			}

			j++;
		}

		elapsed = System.nanoTime() - start;

		if (intTree.add(null)) {
			assertEquals(++i, intTree.size());
		}

		System.out.println(
				String.format("It took %,f seconds to make %,03d sequential calls to create the tree of %,d numbers.",
						elapsed / 1E9, j, NUM_NUMBERS));

		start = System.nanoTime();
		assertTrue(intTree.contains(null));
		elapsed = System.nanoTime() - start;

		System.out.println(String.format("It took %,f seconds to perform a search for null in a sea of %,d numbers.",
				(elapsed / 1E9), NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(i)) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));

		j = 0;
		i = intTree.size();

		if (intTree.remove(null)) {
			assertEquals(--i, intTree.size());
		}

		start = System.nanoTime();

		while (intTree.getNumberOfItems() > 0) {
			int nextNumber = random.nextInt(NUM_NUMBERS);

			if (intTree.remove(nextNumber)) {
				assertEquals(--i, intTree.size());
			}

			j++;
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to delete the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));
	}

	private void testRemoveComparatorStringTree(long seed, int min, int num) {
		final long SEED = seed;
		final Random random = new Random(SEED);

		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		BalancedTree<String> intTree = new BalancedTree<String>(MINIMUM, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				int answer = -1;

				if (a == null && b == null) {
					answer = 0;
				} else if ((a == null) ^ (b == null)) {
					answer = (a == null) ? -1 : 1;
				} else {
					answer = a.compareTo(b);
				}
				return answer;
			}
		});

		assertNotNull(intTree);

		int i = 0;
		int j = 0;
		long start = 0;
		long elapsed = 0;

		System.out.println(String.format(
				"Building a BalancedTree with a Comparator<String> of %,d Strings using a random number generator...",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			j++;
			if (intTree.add(String.format("%010d", nextNumber))) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		j = 0;
		i = intTree.size();

		start = System.nanoTime();

		while (intTree.getNumberOfItems() > 0) {
			int nextNumber = random.nextInt(NUM_NUMBERS);

			if (intTree.remove(String.format("%010d", nextNumber))) {
				assertEquals(--i, intTree.size());
			}

			j++;
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to delete the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		i = 0;
		j = 0;

		System.out.println(String.format(
				"Building a BalancedTree with a Comparator<String> of %,d Strings using sequential calls...",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = i;

			if (intTree.add(String.format("%010d", nextNumber))) {
				assertEquals(++i, intTree.size());
			}
			j++;
		}

		elapsed = System.nanoTime() - start;

		if (intTree.add(null)) {
			assertEquals(++i, intTree.size());
		}

		System.out.println(
				String.format("It took %,f seconds to make %,03d sequential calls to create the tree of %,d numbers.",
						elapsed / 1E9, j, NUM_NUMBERS));

		start = System.nanoTime();
		assertTrue(intTree.contains(null));
		elapsed = System.nanoTime() - start;

		System.out.println(String.format("It took %,f seconds to perform a search for null in a sea of %,d numbers.",
				(elapsed / 1E9), NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(String.format("%010d", i))) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));

		j = 0;
		i = intTree.size();

		if (intTree.remove(null)) {
			assertEquals(--i, intTree.size());
		}

		start = System.nanoTime();

		while (intTree.getNumberOfItems() > 0) {
			int nextNumber = random.nextInt(NUM_NUMBERS);

			if (intTree.remove(String.format("%010d", nextNumber))) {
				assertEquals(--i, intTree.size());
			}

			j++;
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to delete the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));
	}

	private void testRemoveIntegerTree(long seed, int min, int num) {
		final long SEED = seed;
		final Random random = new Random(SEED);

		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		BalancedTree<Integer> intTree = new BalancedTree<Integer>(MINIMUM);

		assertNotNull(intTree);

		int i = 0;
		int j = 0;
		long start = 0;
		long elapsed = 0;

		System.out.println(String.format("Building a BalancedTree of %,d Integerss using a random number generator...",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			j++;
			if (intTree.add(nextNumber)) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		j = 0;
		i = intTree.size();

		start = System.nanoTime();

		while (intTree.getNumberOfItems() > 0) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			if (intTree.remove(nextNumber)) {
				assertEquals(--i, intTree.size());
			}

			j++;
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to delete the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		i = 0;
		j = 0;

		System.out.println(
				String.format("Building a BalancedTree of %,d Integers using sequential calls...", NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = i;

			if (intTree.add(nextNumber)) {
				assertEquals(++i, intTree.size());
			}
			j++;
		}

		elapsed = System.nanoTime() - start;

		System.out.println(
				String.format("It took %,f seconds to make %,03d sequential calls to create the tree of %,d numbers.",
						elapsed / 1E9, j, NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(i)) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));

		j = 0;
		i = intTree.size();

		start = System.nanoTime();

		while (intTree.getNumberOfItems() > 0) {
			int nextNumber = random.nextInt(NUM_NUMBERS);

			if (intTree.remove(nextNumber)) {
				assertEquals(--i, intTree.size());
			}

			j++;
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to delete the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));
	}

	private void testRemoveStringTree(long seed, int min, int num) {
		final long SEED = seed;
		final Random random = new Random(SEED);

		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		BalancedTree<String> intTree = new BalancedTree<String>(MINIMUM);

		assertNotNull(intTree);

		int i = 0;
		int j = 0;
		long start = 0;
		long elapsed = 0;

		System.out.println(String.format("Building a BalancedTree of %,d Strings using a random number generator...",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			j++;
			if (intTree.add(String.format("%,d", nextNumber))) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		j = 0;
		i = intTree.size();

		start = System.nanoTime();

		while (intTree.getNumberOfItems() > 0) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			if (intTree.remove(String.format("%,d", nextNumber))) {
				assertEquals(--i, intTree.size());
			}
			j++;
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to delete the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		i = 0;
		j = 0;

		System.out.println(
				String.format("Building a BalancedTree of %,d Strings using sequential calls...", NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = i;

			if (intTree.add(String.format("%,d", nextNumber))) {
				assertEquals(++i, intTree.size());
			}
			j++;
		}

		elapsed = System.nanoTime() - start;

		System.out.println(
				String.format("It took %,f seconds to make %,03d sequential calls to create the tree of %,d numbers.",
						elapsed / 1E9, j, NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(String.format("%,d", i))) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));

		j = 0;
		i = intTree.size();

		start = System.nanoTime();

		while (intTree.getNumberOfItems() > 0) {
			int nextNumber = random.nextInt(NUM_NUMBERS);

			if (intTree.remove(String.format("%,d", nextNumber))) {
				assertEquals(--i, intTree.size());
			}

			j++;
		}

		elapsed = System.nanoTime() - start;

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to delete the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));
	}

	private void testSearchComparatorStringTree(long seed, int min, int num) {
		final long SEED = seed;
		final Random random = new Random(SEED);

		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		BalancedTree<String> intTree = new BalancedTree<String>(MINIMUM, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				int answer = -1;

				if (a == null && b == null) {
					answer = 0;
				} else if ((a == null) ^ (b == null)) {
					answer = (a == null) ? -1 : 1;
				} else {
					answer = a.compareTo(b);
				}
				return answer;
			}
		});

		long start = 0;
		long elapsed = 0;

		assertNotNull(intTree);

		int i = 0;
		int j = 0;

		System.out.println(String.format(
				"Building a BalancedTree with a Comparator<String> of %,d Strings using a random number generator...",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			j++;
			if (intTree.add(String.format("%,d", nextNumber))) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		assertEquals(NUM_NUMBERS, intTree.size());

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		if (intTree.add(null)) {
			assertEquals(++i, intTree.size());
		}

		start = System.nanoTime();
		assertTrue(intTree.contains(null));
		elapsed = System.nanoTime() - start;

		System.out.println(String.format("It took %,f seconds to perform a search for null in a sea of %,d numbers.",
				(elapsed / 1E9), NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(String.format("%,d", i))) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));
	}

	private void testSearchStringTree(long seed, int min, int num) {
		final long SEED = seed;
		final Random random = new Random(SEED);

		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		BalancedTree<String> intTree = new BalancedTree<String>(MINIMUM);

		long start = 0;
		long elapsed = 0;

		assertNotNull(intTree);

		int i = 0;
		int j = 0;

		System.out.println(String.format("Building a BalancedTree of %,d Strings using a random number generator...",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			j++;
			if (intTree.add(String.format("%,d", nextNumber))) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		assertEquals(NUM_NUMBERS, intTree.size());

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(String.format("%,d", i))) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));
	}

	private void testSearchComparatorIntegerTree(long seed, int min, int num) {
		final long SEED = seed;
		final Random random = new Random(SEED);

		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		BalancedTree<Integer> intTree = new BalancedTree<Integer>(MINIMUM, new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				int answer = -1;

				if (a == null && b == null) {
					answer = 0;
				} else if ((a == null) ^ (b == null)) {
					answer = (a == null) ? -1 : 1;
				} else {
					answer = a.compareTo(b);
				}
				return answer;
			}
		});

		long start = 0;
		long elapsed = 0;

		assertNotNull(intTree);

		int i = 0;
		int j = 0;

		System.out.println(String.format(
				"Building a BalancedTree with a Comparator<Integer> of %,d Integers using a random number generator...",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			j++;
			if (intTree.add(nextNumber)) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		assertEquals(NUM_NUMBERS, intTree.size());

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		if (intTree.add(null)) {
			assertEquals(++i, intTree.size());
		}

		start = System.nanoTime();
		assertTrue(intTree.contains(null));
		elapsed = System.nanoTime() - start;

		System.out.println(String.format("It took %,f seconds to perform a search for null in a sea of %,d numbers.",
				(elapsed / 1E9), NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(i)) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));
	}

	private void testSearchIntegerTree(long seed, int min, int num) {
		final long SEED = seed;
		final Random random = new Random(SEED);

		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		long start = 0;
		long elapsed = 0;

		BalancedTree<Integer> intTree = new BalancedTree<Integer>(MINIMUM);

		assertNotNull(intTree);

		int i = 0;
		int j = 0;

		System.out.println(String.format("Building a BalancedTree of %,d Integers using a random number generator...",
				NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = random.nextInt(NUM_NUMBERS);
			j++;
			if (intTree.add(nextNumber)) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		assertEquals(NUM_NUMBERS, intTree.size());

		System.out.println(String.format(
				"It took %,f seconds to make %,03d random generator calls to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(i)) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));
	}

	private void testSearchIntegerSequentialTree(int min, int num) {
		final int MINIMUM = min;
		final int NUM_NUMBERS = num;

		long start = 0;
		long elapsed = 0;

		BalancedTree<Integer> intTree = new BalancedTree<Integer>(MINIMUM);

		assertNotNull(intTree);

		int i = 0;
		int j = 0;

		System.out
				.println(String.format("Building a BalancedTree of %,d Integers using sequential calls.", NUM_NUMBERS));

		start = System.nanoTime();

		try {
			while (i < NUM_NUMBERS) {
				int nextNumber = i;
				j++;
				if (intTree.add(nextNumber)) {
					assertEquals(++i, intTree.size());
				}
			}
		} catch (OutOfMemoryError exception) {
			System.out.println(
					String.format("Ran out of memory after adding %,d out of %,d numbers :-(", i, NUM_NUMBERS));
			fail(String.format("Ran out of memory after adding %,d out of %,d numbers :-(", i, NUM_NUMBERS));
		}

		elapsed = System.nanoTime() - start;

		assertEquals(NUM_NUMBERS, intTree.size());

		System.out.println(
				String.format("It took %,f seconds to make %,03d sequential calls to create the tree of %,d numbers.",
						elapsed / 1E9, j, NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(i)) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));
	}

	private void testSearchIntegerSortedSet(int num) {
		final int NUM_NUMBERS = num;

		long start = 0;
		long elapsed = 0;

		SortedSet<Integer> intTree = new TreeSet<Integer>();

		assertNotNull(intTree);

		int i = 0;
		int j = 0;

		System.out.println(
				String.format("Building a BalancedTree of %,d Integers using sequential calls in order.", NUM_NUMBERS));

		start = System.nanoTime();

		while (i < NUM_NUMBERS) {
			int nextNumber = i;
			j++;
			if (intTree.add(nextNumber)) {
				assertEquals(++i, intTree.size());
			}
		}

		elapsed = System.nanoTime() - start;

		assertEquals(NUM_NUMBERS, intTree.size());

		System.out.println(String.format(
				"It took %,f seconds to make %,03d sequential calls in order to create the tree of %,d numbers.",
				elapsed / 1E9, j, NUM_NUMBERS));

		start = System.nanoTime();

		for (i = 0, j = 0; i < NUM_NUMBERS; i++) {
			if (intTree.contains(i)) {
				j++;
			}
		}

		elapsed = System.nanoTime() - start;

		System.out
				.println(String.format("It took %,f seconds to perform %,d searches with %,d hits for all %,d numbers.",
						(elapsed / 1E9), NUM_NUMBERS, j, NUM_NUMBERS));
	}
}
