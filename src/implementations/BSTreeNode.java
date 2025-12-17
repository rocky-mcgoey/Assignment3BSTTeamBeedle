package implementations;

public class BSTreeNode<E>
{
	private E element;
	private BSTreeNode<E> rightChild;
	private BSTreeNode<E> leftChild;
	
	public BSTreeNode() {
		this.element = null;
		this.rightChild = null;
		this.leftChild = null;
	}

	public BSTreeNode(E elem) {
		this.element = elem;
		this.rightChild = null;
		this.leftChild = null;
	}

	public BSTreeNode(E elem, BSTreeNode<E> right, BSTreeNode<E> left) {
		this.element = elem;
		this.rightChild = right;
		this.leftChild = left;
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public BSTreeNode<E> getRightChild() {
		return rightChild;
	}

	public void setRightChild(BSTreeNode<E> rightChild) {
		this.rightChild = rightChild;
	}

	public BSTreeNode<E> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(BSTreeNode<E> leftChild) {
		this.leftChild = leftChild;
	}

	public int getHeight() {
		return 0;
	}

	
	public boolean hasLeftChild() {
		if (this.leftChild == null) return false;
		else return true;
	}

	public boolean hasRightChild() {
		if (this.rightChild == null) return false;
		else return true;
	}

	public boolean isLeaf() {
		if (!hasLeftChild() && !hasRightChild()) return true;
		else return false;
	}
	
	public int getNumberNodes() {
		if (isLeaf()) return 0;
		else if (hasLeftChild() && hasRightChild()) return 2;
		else return 1;
	}
}
