import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author Shane Rivera (srr0044@auburn.edu)
 *
 */
public class LinkedSet<T extends Comparable<T>> implements Set<T> {

   //////////////////////////////////////////////////////////
   // Do not change the following three fields in any way. //
   //////////////////////////////////////////////////////////

   /** References to the first and last node of the list. */
   Node front;
   Node rear;

   /** The number of nodes in the list. */
   int size;

   /////////////////////////////////////////////////////////
   // Do not change the following constructor in any way. //
   /////////////////////////////////////////////////////////

   /**
    * Instantiates an empty LinkedSet.
    */
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


   //////////////////////////////////////////////////
   // Public interface and class-specific methods. //
   //////////////////////////////////////////////////

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this LinkedSet.
    *
    * @return a string representation of this LinkedSet
    */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }


   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements, false otherwise.
    */
   public boolean isEmpty() {
      return (size == 0);
   }


   /**
    * Ensures the collection contains the specified element. Neither duplicate
    * nor null values are allowed. This method ensures that the elements in the
    * linked list are maintained in ascending natural order.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   public boolean add(T element) {
   
      if (element == null || contains(element)) {
         return false;
      }
      
      Node current = front;
      Node n = new Node(element);
   
      if (size == 0) {
         front = n;
         rear = n;
         rear.next = null;
         size++;
         return true;
      }
      else if (front.element.compareTo(element) > 0) {
         n.next = front;
         front.prev = n;
         front = n;
         size++;
         return true;
      }
      else if (rear.element.compareTo(element) < 0) {
         rear.next = n;
         n.prev = rear;
         rear = n;
         rear.next = null;
         size++;
         return true;
      }
      else {
         while (current.next != null && element.compareTo(current.element) > 0) {
            current = current.next;
         }
         n.next = current;
         n.prev = current.prev;
         current.prev.next = n;
         current.prev = n;
         size++;
         return true;
      }
   }

   /**
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection. This method, consistent with add, ensures
    * that the elements in the linked lists are maintained in ascending
    * natural order.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
   public boolean remove(T element) {
   
      if (contains(element) == false) {
         return false;
      }
      
      if (size == 1) {
         front = null;
         rear = null;
         size--;
         return true;
      }
      
      Node n = new Node(element);
      Node current = front;
      
      while (!current.element.equals(element) && current.next != null) { 
         current = current.next;
      }
      
      if (current == front) {
         current.next.prev = null;
         front = current.next;
         size--;
         return true;
      }
      else if (current == rear) {
         rear = current.prev;
         rear.next = null;
         rear.prev = current.prev.prev;
         size--;
         return true;
      }
      
      else {
         current.element = null;
         current.prev.next = current.next;
         current.next.prev = current.prev;
         size--;
         return true;
      }
      
   }


   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection is to be tested.
    * @return  true if this collection contains the specified element, false otherwise.
    */
   public boolean contains(T element) {
      Node x = front;
      while (x != null) {
         if (x.element.equals(element)) {
            return true;
         }
         x = x.next;
      }
      return false;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(Set<T> s) {
      Node x = front;
      while (x != null) {
         if (s.add(x.element) == true) {
            return false;
         }
         x = x.next;
      }
      Iterator<T> i = s.iterator();
      while (i.hasNext()) {
         if(this.add(i.next()) == true) {
            return false;
         }
      }
      return true;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(LinkedSet<T> s) {
      if (complement(s).size() == 0) {
         return true;
      }
      return false;
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(Set<T> s){
      
      if (s == null) {
         return this;
      }
      if (this == null) {
         return s;
      }
      
      LinkedSet<T> out = new LinkedSet<T>();
      Node current = front;
      while (current != null) {
         out.add(current.element);
         current = current.next;
      }
      Iterator<T> i = s.iterator();
      while (i.hasNext()) {
         out.add(i.next());
      }
      
      return out;
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(LinkedSet<T> s){
      LinkedSet<T> out = new LinkedSet<T>();
      Node x = front;
      Node n = s.front;
      if (s.isEmpty()) {
         out = this;
         return out;
      }
      if (this.isEmpty()) {
         out = s;
         return out;
      }
      
      while (x != null) {
         out.add(x.element);
         x = x.next;
      }
      while (n != null) {
         out.add(n.element);
         n = n.next;
      }
      return out;
   }


   /**
    * Returns a set that is the intersection of this set and the parameter set.
    *
    * @return  a set that contains elements that are in both this set and the parameter set
    */
   public Set<T> intersection(Set<T> s) {
      LinkedSet<T> out = new LinkedSet<T>();
      if (this == null || s == null) {
         return out;
      }
      
      Node current = front;
      while (current != null && current.element != null) {
         if (s.contains(current.element)) {
            out.add(current.element);
         }
         current = current.next;
      }
      return out;
   }

   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(LinkedSet<T> s) {
      LinkedSet<T> out = new LinkedSet<T>();
      Node x = front;
      Node n = s.front;
      if (this == null || s == null) {
         return out;
      }
      while (x != null && n != null) {
         if (x.element.compareTo(n.element) > 0) {
            n = n.next;
         }
         else if (x.element.compareTo(n.element) < 0) {
            x = x.next;
         }
         else if (x.element.compareTo(n.element) == 0) { 
            out.add(x.element);
            x = x.next;
            n = n.next;
         }
      }
      return out;
   }


   /**
    * Returns a set that is the complement of this set and the parameter set.
    *
    * @return  a set that contains elements that are in this set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
      LinkedSet<T> out = new LinkedSet<T>();
      if (this == null) {
         return s;
      }
      if (s == null) {
         return this;
      }
      
      Node current = front;
      while (current != null) {
         if (!s.contains(current.element)) {
            out.add(current.element);
         }
         current = current.next;
      }
      return out;
   }


   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(LinkedSet<T> s) {
      LinkedSet<T> out = new LinkedSet<T>();
      Node x = front;
      Node n = s.front;
      if (s.isEmpty()) {
         while (x != null) {
            out.add(x.element);
            x = x.next;
         }
         return out;
      }
      if (this.isEmpty()) {
         return out;
      }
      
      while (x != null && n != null) {
         if (x.element.compareTo(n.element) > 0) {
            out.add(x.element);
            n = n.next;
         }
         else if (x.element.compareTo(n.element) < 0) {
            out.add(x.element);
            x = x.next;
         }
         else if (x.element.compareTo(n.element) == 0) { 
            x = x.next;
            n = n.next;
         }
      }
      return out;
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in ascending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> iterator() {
      return new LinkedIterator();
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in descending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> descendingIterator() {
      return new DescendingLinkedIterator(rear);
   }


   /**
    * Returns an iterator over the members of the power set
    * of this LinkedSet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      return new LinkedSetPowerSetIterator();
   }



   //////////////////////////////
   // Private utility methods. //
   //////////////////////////////

   // Feel free to add as many private methods as you need.

   ////////////////////
   // Nested classes //
   ////////////////////

   //////////////////////////////////////////////
   // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
   //////////////////////////////////////////////

   /**
    * Defines a node class for a doubly-linked list.
    */
   class Node {
      /** the value stored in this node. */
      T element;
      /** a reference to the node after this node. */
      Node next;
      /** a reference to the node before this node. */
      Node prev;
   
      /**
       * Instantiate an empty node.
       */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }
   
      /**
       * Instantiate a node that containts element
       * and with no node before or after it.
       */
      public Node(T e) {
         element = e;
         next = null;
         prev = null;
      }
   }
   
   private class LinkedIterator implements Iterator<T> {
      private Node current = front;
   
      public boolean hasNext() {
         return current != null;
      }
      
      public T next() {         
         T out = current.element;
         current = current.next;
         return out;
      }
   }
   
   private class DescendingLinkedIterator implements Iterator<T> {
      Node current;
      
      public DescendingLinkedIterator(Node rear) {
         current = rear;
      }
   
      public boolean hasNext() {
         return current != null && current.element != null;
      }
      
      public T next() {
         if (current != null) {
            T out = current.element;
            current = current.prev;
            return out;
         }
         return null;
      }
   }
   
   private class LinkedSetPowerSetIterator implements Iterator<Set<T>> {
    // cardinality of this set
      int N;
   
    // cardinality of the power set (2^N)
      int M;
   
    // the integer identifier of the current subset, i.e. the current element
    // of the power set. Legal values range from 0..M-1 inclusive.
      int current;
      
      Node x;
   
      public LinkedSetPowerSetIterator() {
      
         N = size;
         M = (int)Math.pow(2, size);
         current = 0;
         x = front;
      }
   
      public boolean hasNext() {
         return current < M;
      }
   
      public Set<T> next() {
         Set<T> s = new LinkedSet<T>();
         char[] bitstring = Integer.toBinaryString(current).toCharArray();
      
        // iterate from right to left over bitstring and the internal
        // linked list to ensure that the call to add will insert a new
        // first node (constant time)
         for(int i = bitstring.length - 1; i > 0; i--) {
            if(bitstring[i] == 1) {
               s.add(x.element);
            }
            if(x.next != null) {
               x = x.next;
            }
         }
         if (s.isEmpty()) {
            current = current + 1;
            return s;   
         }
          
         x = front;
         current = current + 1;
         return s;
      }
      public void remove() {
      
      }
   } 
}
