/**
 * 
 */
package com.flatironschool.javacs;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author downey
 * @param <E>: Type of the elements in the List.
 *
 */
public class MyArrayList<E> implements List<E> {
	int size;                    // keeps track of the number of elements
	private E[] array;           // stores the elements
	
	/**
	 * 
	 */
	public MyArrayList() {
		// You can't instantiate an array of T[], but you can instantiate an
		// array of Object and then typecast it.  Details at
		// http://www.ibm.com/developerworks/java/library/j-jtp01255/index.html
		array = (E[]) new Object[10];
		size = 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// run a few simple tests
		MyArrayList<Integer> mal = new MyArrayList<Integer>();
		mal.add(1);
		mal.add(2);
		mal.add(3);
		System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);
		
		mal.remove(new Integer(2));
		System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);
	}

	@Override
	public boolean add(E element) {
		if (size >= array.length) {
			// make a bigger array and copy over the elements
			E[] bigger = (E[]) new Object[array.length * 2];
			System.arraycopy(array, 0, bigger, 0, array.length);
			array = bigger;
		} 
		array[size] = element;
		size++;
		return true;
	}

	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (size >= array.length) {
			E[] bigger = (E[]) new Object[array.length * 2]; //make bigger array
			System.arraycopy(array, 0, bigger, 0, index-1); //copy elements up to index
			bigger[index] = element; //insert element at index
			System.arraycopy(array, index, bigger, index+1, array.length); //copy the rest of the elements to their index+1
			array = bigger; //array now points to new bigger array
		}
		else {
			for (int i = size-1; i >= index ; i--) {
				array[i+1] = array[i]; //shift elements behind index by 1
			}
			array[index] = element;//insert element
		}
		//alternate solution: use add(E element) to get the resizing;
		size++;
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean flag = true;
		for (E element: collection) {
			flag &= add(element);
		}
		return flag;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		// note: this version does not actually null out the references
		// in the array, so it might delay garbage collection.
		size = 0;
	}

	@Override
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object element: collection) {
			if (!contains(element)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return array[index];
	}

	@Override
	public int indexOf(Object target) {
		if (target == null) { 
			for (int i = 0; i < size; i++) {
				if (array[i] == null) {
					return i;
				}
			}
		}
		else { //if target isn't null
			for (int i = 0; i < size; i++) {
				if (array[i].equals(target)) {
					return i;
				}
			}
		}
		return -1; //not found
	}

	/** Checks whether an element of the array is the target.
	 * 
	 * Handles the special case that the target is null.
	 * 
	 * @param target
	 * @param object
	 */
	private boolean equals(Object target, Object element) {
		if (target == null) {
			return element == null;
		} else {
			return target.equals(element);
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		// make a copy of the array
		E[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).iterator();
	}

	@Override
	public int lastIndexOf(Object target) {
		// see notes on indexOf
		for (int i = size-1; i>=0; i--) {
			if (equals(target, array[i])) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		// make a copy of the array
		E[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		// make a copy of the array
		E[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).listIterator(index);
	}

	@Override
	public boolean remove(Object obj) {
		int index = indexOf(obj);
		if (index == -1) {
			return false;
		}
		remove(index);
		return true;
	}

	@Override
	public E remove(int index) {
		E removed = get(index);
		for (int i = index; i < size-1; i++) {
			array[i] = array[i+1];
		}
		size--;
		return removed;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean flag = true;
		for (Object obj: collection) {
			flag &= remove(obj);
		}
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E element) {
		E prevElement = get(index);
		array[index] = element;
		return prevElement;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		E[] copy = Arrays.copyOfRange(array, fromIndex, toIndex);
		return Arrays.asList(copy);
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(array, size);
	}

	@Override
	public <T> T[] toArray(T[] array) {
		throw new UnsupportedOperationException();		
	}
}
