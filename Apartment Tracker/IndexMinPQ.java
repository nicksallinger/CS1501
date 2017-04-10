/******************************************************************************
 *  Compilation:  javac IndexMinPQ.java
 *  Execution:    java IndexMinPQ
 *  Dependencies: StdOut.java
 *
 *  Minimum-oriented indexed PQ implementation using a binary heap.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The {@code IndexMinPQ} class represents an indexed priority queue of generic apts.
 *  It supports the usual <em>insert</em> and <em>delete-the-minimum</em>
 *  operations, along with <em>delete</em> and <em>change-the-apt</em> 
 *  methods. In order to let the client refer to apts on the priority queue,
 *  an integer between {@code 0} and {@code maxN - 1}
 *  is associated with each apt—the client uses this integer to specify
 *  which apt to delete or change.
 *  It also supports methods for peeking at the minimum apt,
 *  testing if the priority queue is empty, and iterating through
 *  the apts.
 *  <p>
 *  This implementation uses a binary heap along with an array to associate
 *  apts with integers in the given range.
 *  The <em>insert</em>, <em>delete-the-minimum</em>, <em>delete</em>,
 *  <em>change-apt</em>, <em>decrease-apt</em>, and <em>increase-apt</em>
 *  operations take logarithmic time.
 *  The <em>is-empty</em>, <em>size</em>, <em>min-index</em>, <em>min-apt</em>,
 *  and <em>apt-of</em> operations take constant time.
 *  Construction takes time proportional to the specified capacity.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @param <Apt> the generic type of apt on this priority queue
 */
public class IndexMinPQ implements Iterable<Integer> {
    private int maxN;        // maximum number of elements on PQ
    private int n;           // number of elements on PQ
    private int[] pq;        // binary heap using 1-based indexing
    private int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private Apt[] apts;      // apts[i] = priority of i

    /**
     * Initializes an empty indexed priority queue with indices between {@code 0}
     * and {@code maxN - 1}.
     * @param  maxN the apts on this priority queue are index from {@code 0}
     *         {@code maxN - 1}
     * @throws IllegalArgumentException if {@code maxN < 0}
     */
    public IndexMinPQ(){
    	this(25);
    }
    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        n = 0;
        apts = new Apt[maxN + 1];    // make this of length maxN??
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];                   // make this of length maxN??
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    /**
     * Returns true if this priority queue is empty.
     *
     * @return {@code true} if this priority queue is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Is {@code i} an index on this priority queue?
     *
     * @param  i an index
     * @return {@code true} if {@code i} is an index on this priority queue;
     *         {@code false} otherwise
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     */
    public boolean contains(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }

    /**
     * Returns the number of apts on this priority queue.
     *
     * @return the number of apts on this priority queue
     */
    public int size() {
        return n;
    }

    /**
     * Associates apt with index {@code i}.
     *
     * @param  i an index
     * @param  apt the apt to associate with index {@code i}
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     * @throws IllegalArgumentException if there already is an item associated
     *         with index {@code i}
     */
    public void insert(int i, Apt apt) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        n++;
        qp[i] = n;
        pq[n] = i;
        apts[i] = apt;
        swim(n);
    }

    /**
     * Returns an index associated with a minimum apt.
     *
     * @return an index associated with a minimum apt
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int minIndex() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    /**
     * Returns a minimum apt.
     *
     * @return a minimum apt
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Apt minApt() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return apts[pq[1]];
    }
    
    /*
     * similar to minApt, but does not simply return value of highest priority. Iterates
     * through sorted PQ and returns first (sorted by lowest priced) apartment
     * that has a matching city
     * 
     * try/catch added so as to not get a null pointer, and still increment all
        	 * the way through the pq
     */
    public Apt minAptCity(String city) {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        for(int i = 0;i <= maxN; i++){
        	try{
        	//System.out.println(apts[pq[i]].getCity() + "comapred to " + city);
        	if(apts[pq[i]].getCity().equals(city)){
        		return apts[pq[i]];
        	}
        	}
        	catch(Exception e){
        		
        	}
        }
        return null;
    }

    /**
     * Removes a minimum apt and returns its associated index.
     * @return an index associated with a minimum apt
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int delMin() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exch(1, n--);
        sink(1);
        assert min == pq[n+1];
        qp[min] = -1;        // delete
        apts[min] = null;    // to help with garbage collection
        pq[n+1] = -1;        // not needed
        return min;
    }

    /**
     * Returns the apt associated with index {@code i}.
     *
     * @param  i the index of the apt to return
     * @return the apt associated with index {@code i}
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     * @throws NoSuchElementException no apt is associated with index {@code i}
     */
    public Apt aptOf(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return apts[i];
    }

    /**
     * Change the apt associated with index {@code i} to the specified value.
     *
     * @param  i the index of the apt to change
     * @param  apt change the apt associated with index {@code i} to this apt
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     * @throws NoSuchElementException no apt is associated with index {@code i}
     */
    public void changeAptPrice(int i,int newPrice) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        apts[i].setPrice(newPrice);
        swim(qp[i]);
        sink(qp[i]);
    }
    public void changeAptFootage(int i,int newFootage) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        apts[i].setFootage(newFootage);
        swim(qp[i]);
        sink(qp[i]);
    }

    /**
     * Change the apt associated with index {@code i} to the specified value.
     *
     * @param  i the index of the apt to change
     * @param  apt change the apt associated with index {@code i} to this apt
     * @throws IndexOutOfBoundsException unless {@code 0 <= i < maxN}
     * @deprecated Replaced by {@code changeApt(int, Apt)}.
     */
    @Deprecated
    public void change(int i, Apt apt) {
       // changeApt(i, apt);
    }

    public void delete(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        exch(index, n--);
        swim(index);
        sink(index);
        apts[i] = null;
        qp[i] = -1;
    }


   /***************************************************************************
    * General helper functions.
    ***************************************************************************/
    private boolean greaterPrice(int i, int j) {
        return apts[pq[i]].getPrice() > (apts[pq[j]]).getPrice();
    }
    

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }


   /***************************************************************************
    * Heap helper functions.
    ***************************************************************************/
    private void swim(int k) {
        while (k > 1 && greaterPrice(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && greaterPrice(j, j+1)) j++;
            if (!greaterPrice(k, j)) break;
            exch(k, j);
            k = j;
        }
    }


   /***************************************************************************
    * Iterators.
    ***************************************************************************/

    /**
     * Returns an iterator that iterates over the apts on the
     * priority queue in ascending order.
     * The iterator doesn't implement {@code remove()} since it's optional.
     *
     * @return an iterator that iterates over the apts in ascending order
     */
    public Iterator<Integer> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Integer> {
        // create a new pq
        private IndexMinPQ copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no apts move
        public HeapIterator() {
            copy = new IndexMinPQ(pq.length - 1);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i], apts[pq[i]]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
}
    
