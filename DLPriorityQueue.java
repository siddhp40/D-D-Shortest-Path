public class DLPriorityQueue<T> implements PriorityQueueADT<T> {
    private DLinkedNode<T> front;
    private DLinkedNode<T> rear;
    private int count;

    public DLPriorityQueue() {
        front = null;
        rear = null;
        count = 0;
    }

    public void add(T dataItem, double priority) {
        // Creates node to be inserted into priority queue
        DLinkedNode<T> temp = new DLinkedNode<T>(dataItem, priority);
        // If count is 0 then the Priority Queue is empty and that means both front and
        // rear should be temp
        // Increment count to indicate the new size and then return to avoid running the
        // code below
        if (count == 0) {
            front = temp;
            rear = temp;
            count++;
            return;
        }
        if (priority <= front.getPriority()) {
            // If priority is smaller then all priorties in the Priority Queue then we need
            // to insert the node at the beginning
            // No need to setPrev to temp as temp is the first node
            temp.setNext(front);
            front.setPrev(temp);
            front = front.getPrev();
            // Increment counter & return to avoid running the code below
            count++;
            return;
        }
        if (priority >= rear.getPriority()) {
            // If priority is larger than all priorties in the priorithy queue we need to
            // insert the node at the end
            rear.setNext(temp);
            temp.setPrev(rear);
            rear = rear.getNext();
            // Increment count & return to avoid runnign the code below
            count++;
            return;
        }
        // If the code has reached this point then the node to be inserted is somewhere
        // in the middle
        // For that we need to find the node when front.getPriority is greater than
        // priority
        // So loop through front until we find the newNode
        // Note we can't change front because front needs to point to the front so we
        // need to store front in another DLinkedList
        count++;
        DLinkedNode<T> hold = front;
        // No need to check if hold.getNext() is defined because we already checked if
        // newNode is the last Node.
        while (hold.getPriority() < priority) {
            hold = hold.getNext();
        }
        // Update Priority Queue to include newNode
        temp.setPrev(hold.getPrev());
        hold.getPrev().setNext(temp);
        hold.setPrev(temp);
        temp.setNext(hold);

        // The number of elements in the priority queue is incremented.
    }

    public T removeMin() throws EmptyPriorityQueueException {
        // If the count is 0 then it doesn't make sense to remove value so throw an
        // exception
        if (count == 0) {
            throw new EmptyPriorityQueueException("Priority Queue Size is 0");
        }
        T toReturn = front.getDataItem();
        // If count is 1 then set front and rear to null
        if (count == 1) {
            front = null;
            rear = null;
            count--;
            return toReturn;
        }
        // Decrement count
        count--;
        // Save value to return

        // Skip the value with the lowest priority and return the dataValue

        front = front.getNext();
        front.setPrev(null);
        return toReturn;
    }

    public void updatePriority(T dataItem, double newPriority) throws InvalidElementException {
        if (count == 1) {
            // If count is 1 then just change the priority as there is nothing else to
            // compare to.
            front.setPriority(newPriority);
            return;
        }

        // If not then I find the element that needs an the new priority and removed it
        // from the priority queue
        // I then invoed this.add to add the new priority as well as the data item
        // If data Item was never found then I throw invalid element

        DLinkedNode<T> hold = front;
        while (hold != null) {
            // If the element is found, change its priority to newPriority and stop
            // searching.
            if (hold.getDataItem().equals(dataItem)) {
                // If we found the right element then break out of the loop
                break;
            }
            // Otherwise, get check the next element
            hold = hold.getNext();
        }

        // Throw invalid element exception if hold is null because that means the
        // dataItem was not found in the Priority Queue
        if (hold == null) {
            throw new InvalidElementException("Invalid Element.");
        }
        // If the first element is the element that needs to be changed, update front
        // and then add the new element using .add
        if (hold.getPrev() == null) {
            front = front.getNext();
            front.setPrev(hold.getPrev());
            count--;
            this.add(dataItem, newPriority);
            return;
        }
        // If the last element is the element that needs to be updated, update rear and
        // then add the new element using .add
        if (hold.getNext() == null) {
            rear = rear.getPrev();
            rear.setNext(hold.getNext());
            count--;
            this.add(dataItem, newPriority);
            return;
        }
        hold.getPrev().setNext(hold.getNext());
        hold.getNext().setPrev(hold.getPrev());
        // Decrement count as .add will add one to count
        count--;
        this.add(dataItem, newPriority);
    }

    public boolean isEmpty() {
        // If count is 0 then Prioity Qeuue is empty so return true
        // Else return false
        if (count == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        // Count is the size of the Priority Queue
        return count;
    }

    public DLinkedNode<T> getRear() {
        // Getter for rear
        return rear;
    }

    public String toString() {
        // Combines all values into one string
        String combinedString = "";
        DLinkedNode<T> temp = front;
        while (temp.getNext() != null) {
            // Gets all data items in temp
            combinedString += temp.getDataItem();
            // Adds them to string and concanates them
            temp = temp.getNext();
        }
        combinedString += temp.getDataItem();
        // Ensure last element is added to String
        return combinedString;
        // Return String
    }
}