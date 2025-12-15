package implementations;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import utilities.Iterator;

public class BSTIterator<E extends Comparable<? super E>> implements Iterator<E> 
{
	private ArrayList<E> data;
	private int size;
	private int current = 0;
	
	public BSTIterator(ArrayList<E> data, int size)
	{
		this.data = data;
		this.size = size;
	}
	
	@Override
	public boolean hasNext()
	{
		return current < size;
	}
	@Override
	public E next() throws NoSuchElementException
	{
		if (!hasNext())
		{
			throw new NoSuchElementException();
		}
		return data.get(current++);
	}

}
