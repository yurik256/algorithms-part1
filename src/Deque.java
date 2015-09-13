public class Deque<Item> implements Iterable<Item> {
    
    private class Node {
        private Item item; // node's item content
        private Node next; // the next node
        private Node prev; // the previous node
    }
    
    private Node first;
    private Node last;
    private int size;
    
   public Deque() {
       first = null;
       last = null;
       size = 0;
   }
  
   public boolean isEmpty() {
       return size == 0;
   }        
   
   public int size() {
       return size;
   }       
   
   public void addFirst(Item item) {
       if (item == null)
           throw new NullPointerException("null value can not be added");
       
       Node node = new Node();
       node.item = item;       
              
       if (first == null) {
           first = node;
           
           if (last == null) {
               last = first;
           }
       } else {
           node.next = first;
           node.prev = null;
           first.prev = node;
       
           if (size == 1) {
               last = first;
           }

           first = node;
       }

       size++;
   }
      
   public void addLast(Item item) {
       if (item == null)
           throw new NullPointerException("null value can not be added");
       
       Node node = new Node();
       node.item = item;
       
       if (last == null) {
           last = node;
           
           if (first == null) {
               first = last;
           }
       } else {
           last.next = node;
           node.prev = last;
           node.next = null;
                      
           last = node;
       }
       size++;
   }
  
   public Item removeFirst() {
       if (size == 0)
           throw new java.util.NoSuchElementException("There is no first element");
              
       Item item = first.item;
       
       if (first.next != null)
           first.next.prev = null;
       
       first = first.next;
       size--;
       
       if (size == 0)
           last = null;
       
       return item;
   }        
   
   public Item removeLast() {
       if (size == 0) 
           throw new java.util.NoSuchElementException("There is no last element");
       
       Item item = last.item;
       
       if (last.prev != null)
           last.prev.next = null;
       
       last = last.prev;
       size--;       
       
       if (size == 0)
           first = null;
       
       return item;
   }    
   
   public java.util.Iterator<Item> iterator() {
       return new DequeIterator();
   }   
   
    private class DequeIterator implements java.util.Iterator<Item> {
        private Node current = first;
        
        public Item next() {
            if (current == null) 
                throw new java.util.NoSuchElementException();
            
            Item item = current.item;
            current = current.next;
            
            return item;
        }
        
        public void remove() { 
            throw new java.lang.UnsupportedOperationException();
        }
        
        public boolean hasNext() {
            return current != null;
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(5);
        System.out.println(!deque.isEmpty() && deque.size() == 1);
        
        deque.addLast(4);
        deque.addLast(3);
        System.out.println(!deque.isEmpty() && deque.size() == 3);
        
        deque.removeFirst();
        System.out.println(deque.size() == 2);
        
        deque.removeFirst();
        System.out.println(deque.size() == 1);

        deque.removeLast();
        System.out.println(deque.size() == 0);
        
        deque.addFirst(1);
        deque.addLast(6);
        for (int aDeque : deque) {
            System.out.println(aDeque);
        }
    }
}