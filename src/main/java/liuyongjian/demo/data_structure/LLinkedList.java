package liuyongjian.demo.data_structure;

import java.util.NoSuchElementException;

public class LLinkedList<E> {
	private Node<E> first;

	private Node<E> last;

	private int size;

	// 添加第一个节点
	public void addFirst(E e) {
		Node<E> f = first;
		Node<E> newNode = new Node<>(null, e, f);
		first = newNode;
		if (f == null) {
			last = newNode;
		} else {
			f.prev =  newNode;
		}
		size++;
	}

	public void addLast(E e) {
		Node<E> l = last;
		Node<E> newNode = new Node<>(last, e, null);
		last = newNode;
		if (l == null) {
			first = newNode;
		} else {
			l.next = newNode;
		}
		size++;
	}
	
	public E getFirst() {
		Node<E> f = first;
		if (f == null) {
			throw new NoSuchElementException();
		}
		return first.item;
	}

	public E getLast() {
		Node<E> l = last;
		if (l == null) {
			throw new NoSuchElementException();
		}
		return last.item;
	}

	public void removeFirst() {
		if (first == null) {
			throw new NoSuchElementException();
		}
		Node<E> f = first.next;
		f.prev = null;
		first = f;
		size--;
	}
	
	public void removeLast() {
		if (last == null) {
			throw new NoSuchElementException();
		}
		Node<E> l = last.prev;
		l.next = null;
		last = l;
		size--;
	}
	
	@SuppressWarnings("hiding")
	class Node<E> {
		private E item;
		private Node<E> prev;
		private Node<E> next;

		public Node(Node<E> prev, E item, Node<E> next) {
			this.prev = prev;
			this.item = item;
			this.next = next;
		}
	}

	public int size() {
		return size;
	}

	public void clear() {
		for (Node<E> next = first; next != null;) {
			Node<E> node = next.next;
			next.prev = null;
			next.item = null;
			next.next = null;
			next = node;
		}
		first = last = null;
		size = 0;
	}

}
