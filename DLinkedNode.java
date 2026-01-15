public class DLinkedNode<T>{
    // Doubly Linked Node Class


    // Declare variables
    private T dataItem;
    private double priority;
    private DLinkedNode<T> prev;
    private DLinkedNode<T> next;
    public DLinkedNode(){
        // Creates empty node of 0 priority
        prev = null;
        next = null;
       dataItem = null;
       priority = 0.0;
    }
    public DLinkedNode(T data, double prio){
        // Creates node with specified data and priority
        dataItem = data;
        priority = prio;
        prev = null;
        next = null;
    }
    public double getPriority(){
        // Returns priority
        return priority;
    }
    public T getDataItem(){
        // Returns dataItem
        return dataItem;
    }
    public DLinkedNode<T> getPrev(){
        // Returns prev
        return prev;
    }
    public DLinkedNode<T> getNext(){
        // Returns next
        return next;
    }
    public void setDataItem(T newDataItem){
        // Setter for DataItem
        dataItem = newDataItem;
    }
    public void setNext(DLinkedNode<T> newNext){
        // Setter for Next
        next = newNext;
    }
    public void setPrev(DLinkedNode<T> newPrev){
        // Setter for Prev
        prev = newPrev;
    }
    public void setPriority(double newPriority){
        // Setter for Priority
        priority = newPriority;
    }
}