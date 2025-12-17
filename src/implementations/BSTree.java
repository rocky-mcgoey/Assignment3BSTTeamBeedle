package implementations;

import java.util.ArrayList;

import utilities.BSTreeADT;
import utilities.Iterator;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>
{
	private BSTreeNode<E> root;
	private int size;

	public BSTree() {
		this.root = null;
		this.size = 0;
	}

	public BSTree(E elem) {
		BSTreeNode<E> newRoot = new BSTreeNode<E>(elem);
		this.root = newRoot;
		this.size = 1;
	}

	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException {
		if (root != null) return root;
		else throw new NullPointerException();
	}

	@Override
	public int getHeight() {
		if (this.size == 0) return 0;
		else if (this.size == 1) return 1;
		else if (this.size == 2) return 2;
		else return getHeightHelper(root);
	}
	
	private int getHeightHelper(BSTreeNode<E> node) {
		if (node.isLeaf()) return 1;
		else if (node.getNumberNodes() == 2) {
			int right = getHeightHelper(node.getRightChild());
			int left = getHeightHelper(node.getLeftChild());
			
			if (right > left) return right + 1;
			else return left + 1;
		}
		else if (node.hasLeftChild()) return getHeightHelper(node.getLeftChild()) + 1;
		else return getHeightHelper(node.getRightChild()) + 1;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		if (this.size == 0) return true;
		return false;
	}

	@Override
	public void clear() {
		this.root = null;
		this.size = 0;
	}

	@Override
	public boolean contains(E entry) throws NullPointerException {
		if (entry == null) throw new NullPointerException();
		if (root.getElement() == entry) return true;
		
		BSTreeNode<E> currentNode = root;
		while (!currentNode.isLeaf()) {
			if (currentNode.getElement() == entry) return true;
			
			if (entry.compareTo(currentNode.getElement()) > 0) {
				if (currentNode.hasRightChild()) {
					currentNode = currentNode.getRightChild();
				}
			}

			else if (entry.compareTo(currentNode.getElement()) < 0) {
				if (currentNode.hasLeftChild()) {
					currentNode = currentNode.getLeftChild();
				}
			}
		}

		if (currentNode.getElement().compareTo(entry) == 0) return true;
		return false;
	}

	@Override
	public BSTreeNode<E> search(E entry) throws NullPointerException {
		if (entry == null) throw new NullPointerException();
		BSTreeNode<E> newNode = new BSTreeNode<E>(entry);
		BSTreeNode<E> currentNode = root;

		if (root.getElement() == entry) return root;
		
		while (!currentNode.isLeaf()) {
			if (currentNode.getElement().compareTo(entry) == 0) return currentNode;
			
			if (entry.compareTo(currentNode.getElement()) > 0) {
				if (currentNode.hasRightChild()) {
					currentNode = currentNode.getRightChild();
				}
			}

			else if (entry.compareTo(currentNode.getElement()) < 0) {
				if (currentNode.hasLeftChild()) {
					currentNode = currentNode.getLeftChild();
				}
			}
		}
		
		if (currentNode.getElement().compareTo(entry) == 0) return currentNode;
		return null;
	}

	@Override
	public boolean add(E newEntry) throws NullPointerException {
		if (newEntry == null) throw new NullPointerException();
		
		BSTreeNode<E> newNode = new BSTreeNode<E>(newEntry);
		
		if (this.size == 0) {
			root = newNode;
			this.size += 1;
			return true;
		}
	
		BSTreeNode<E> currentNode = root;
		while (true) {
			if (newNode.getElement().compareTo(currentNode.getElement()) > 0) {
				if (!currentNode.hasRightChild()) {
					currentNode.setRightChild(newNode);
					this.size += 1;
					return true;
				}
				else currentNode = currentNode.getRightChild();
			}

			else if (newNode.getElement().compareTo(currentNode.getElement()) <= 0) {
				if (!currentNode.hasLeftChild()) {
					currentNode.setLeftChild(newNode);
					this.size += 1;
					return true;
				}
				else currentNode = currentNode.getLeftChild();
			}
		}
	}

	@Override
	public BSTreeNode<E> removeMin() {
		if (this.size == 0) return null;
		BSTreeNode<E> returnNode = new BSTreeNode<E>(null);
		
		if (!root.hasLeftChild()) {
			returnNode = root;
			root = root.getRightChild();
		}
		
		else if (root.getLeftChild().isLeaf()) {
			returnNode = root.getLeftChild();
			root.setLeftChild(null);
		}
		
		else {
			BSTreeNode<E> currentNode = root;
			BSTreeNode<E> nextNode = currentNode.getLeftChild();

			while (true) {
				if (nextNode.isLeaf()) {
					returnNode = nextNode;
					currentNode.setLeftChild(null);
					break;
				}
				else if(!nextNode.hasLeftChild() && nextNode.hasRightChild()) {
					returnNode = nextNode;
					currentNode.setLeftChild(nextNode.getRightChild());
					break;
				}
				else {
					currentNode = nextNode;
					nextNode = nextNode.getLeftChild();
				}
			}
		}
		
		this.size -= 1;
		return returnNode;
	}

	@Override
	public BSTreeNode<E> removeMax() {
		if (this.size == 0) return null;
		BSTreeNode<E> returnNode = new BSTreeNode<E>(null);
		
		if (!root.hasRightChild()) {
			returnNode = root;
			root = root.getLeftChild();
		}
		
		else if (root.getRightChild().isLeaf()){
			returnNode = root.getRightChild();
			root.setRightChild(null);
		}
		
		else {
			BSTreeNode<E> currentNode = root;
			BSTreeNode<E> nextNode = currentNode.getRightChild();
			
			while (true) {
				if (nextNode.isLeaf()) {
					returnNode = nextNode;
					currentNode.setRightChild(null);
					break;
				}
				else if(!nextNode.hasRightChild() && nextNode.hasLeftChild()) {
					returnNode = nextNode;
					currentNode.setRightChild(nextNode.getLeftChild());
					break;
				}
				else {
					currentNode = nextNode;
					nextNode = nextNode.getRightChild();
				}
			}
		}
		
		this.size -= 1;
		return returnNode;
	}

	@Override
	public Iterator<E> inorderIterator() {
	   ArrayList<E> list = new ArrayList<E>();
	   ArrayList<BSTreeNode<E>> stack = new ArrayList<BSTreeNode<E>>();
	
	   BSTreeNode<E> current = root;
	
	   while (current != null || !stack.isEmpty()) {
	
	       while (current != null) {
	           stack.add(current);
	           current = current.getLeftChild();
	       }
	
	       current = stack.remove(stack.size() - 1);
	       list.add(current.getElement());
	
	       current = current.getRightChild();
	    }
	    return new BSTIterator<E>(list, list.size());
	}

	private ArrayList<E> inorderArrayMaker(BSTreeNode<E> node) {
		ArrayList<E> list = new ArrayList<E>();
		if (node != null) {
		list.addAll(inorderArrayMaker(node.getLeftChild()));
		list.add(node.getElement());
		list.addAll(inorderArrayMaker(node.getRightChild()));
		}
		return list;
	}

	@Override
	public Iterator<E> preorderIterator() {
		ArrayList<E> list = preorderArrayMaker(root);
		return new BSTIterator<E>(list, this.size);
	}

	private ArrayList<E> preorderArrayMaker(BSTreeNode<E> node) {
		ArrayList<E> list = new ArrayList<E>();
		if (node != null) {
		list.add(node.getElement());
		list.addAll(preorderArrayMaker(node.getLeftChild()));
		list.addAll(preorderArrayMaker(node.getRightChild()));
		}
		return list;
	}

	@Override
	public Iterator<E> postorderIterator() {
		ArrayList<E> list = postorderArrayMaker(root);
		return new BSTIterator<E>(list, this.size);
	}

	private ArrayList<E> postorderArrayMaker(BSTreeNode<E> node) {
		ArrayList<E> list = new ArrayList<E>();
		if (node != null) {
		list.addAll(postorderArrayMaker(node.getLeftChild()));
		list.addAll(postorderArrayMaker(node.getRightChild()));
		list.add(node.getElement());
		}
		return list;
	}

}
