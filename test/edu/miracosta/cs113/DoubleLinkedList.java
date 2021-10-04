package edu.miracosta.cs113;

import java.util.*;

public class DoubleLinkedList<E> extends AbstractSequentialList<E> {  // Data fields
    private Node<E> head = null;   // points to the head of the list
    private Node<E> tail = null;   //points to the tail of the list
    private int size = 0;    // the number of items in the list

    public void add(int index, E obj) {
        listIterator(index).add(obj);
    }

    public void addFirst(E obj) {
        add(0, obj);
    }

    public void addLast(E obj) {
        add(size, obj);
    }

    public E get(int index) {
        ListIterator<E> iter = listIterator(index);
        return iter.next();
    }

    public E getFirst() {
        return head.data;
    }

    public E getLast() {
        return tail.data;
    }

    public int size() {
        return size;
    }

    public E remove(int index) {
        E returnValue = null;
        ListIterator<E> iter = listIterator(index);
        if (iter.hasNext()) {
            returnValue = iter.next();
            iter.remove();
        } else {
            throw new IndexOutOfBoundsException();
        }
        return returnValue;
    }

    public Iterator iterator() {
        return new ListIter(0);
    }

    public ListIterator listIterator() {
        return new ListIter(0);
    }

    public ListIterator listIterator(int index) {
        return new ListIter(index);
    }

    public ListIterator listIterator(ListIterator iter) {
        return new ListIter((ListIter) iter);
    }

    // Inner Classes
    private static class Node<E> {
        private E data;
        private Node<E> next = null;
        private Node<E> prev = null;

        private Node(E dataItem)  //constructor
        {
            data = dataItem;
        }
    }  // end class Node

    public class ListIter implements ListIterator<E> {
        private Node<E> nextItem;      // the current node
        private Node<E> lastItemReturned;   // the previous node
        private int index = 0;   // 

        public ListIter(int i)  // constructor for ListIter class
        {
            if (i < 0 || i > size) {
                throw new IndexOutOfBoundsException("Invalid index " + i);
            }
            lastItemReturned = null;

            if (i == size)     // Special case of last item
            {
                index = size;
                nextItem = null;
            } else          // start at the beginning
            {
                nextItem = head;
                for (index = 0; index < i; index++){
                    nextItem = nextItem.next;
                }
            }// end else
        }  // end constructor

        public ListIter(ListIter other) {
            nextItem = other.nextItem;
            index = other.index;
        }

        public boolean hasNext() {
            return nextItem != null;
        }

        public boolean hasPrevious() {
            return head!= null;
        }

        public int previousIndex() {
            return index - 1;
        }

        public int nextIndex() {
            return index;
        }

        public void set(E o) {
            if (lastItemReturned != null){
                lastItemReturned.data = o;
            } else {
                System.err.println("Cannot set");
            }
        }

        public void remove() {
            if (lastItemReturned != null){
                if(lastItemReturned.next != null){
                    lastItemReturned.next.prev = lastItemReturned.prev;
                } else {
                    tail = lastItemReturned.prev;
                    if (tail != null){
                        tail.next = null;
                    } else {
                        tail = null;
                    }
                }
                if(lastItemReturned.prev != null) {
                    lastItemReturned.prev.next = lastItemReturned.next;
                } else {
                    head = lastItemReturned.next;
                    if (head != null){
                        head.prev = null;
                    } else {
                        tail = null;
                    }
                }
            } else {
                System.err.println("Cannot remove");
            }
        }

        public E next() {
            if(hasNext()){
                lastItemReturned = nextItem;
                nextItem = nextItem.next;
                index++;
            }
            else{
                System.err.println("No next item");
            }
            return lastItemReturned.data;
        }

        public E previous() {
            if(hasPrevious()){
                if(nextItem != null){
                    nextItem = nextItem.prev;
                }
                else{
                    nextItem = tail;
                }
            }
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.data;
        }

        public void add(E obj) {
            Node<E> node = new Node<>(obj);
            if(head == null){
                head = new Node<>(obj);
                tail = head;
            } else if (nextItem == null){
                tail.next = node;
                node.prev = tail;
                tail = node;
            } else if (nextItem == head){
                node.next = nextItem;
                nextItem.prev = node;
                head = node;
            } else {
                node.prev = nextItem.prev;
                nextItem.prev.next = node;
                node.next = nextItem;
                nextItem.prev = node;
            }
            index++;
            size++;
            lastItemReturned = null;
        }
    }// end of inner class ListIter
}// end of class DoubleLinkedList
