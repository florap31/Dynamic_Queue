

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

class DynamicNode{
    private Object info;
    private DynamicNode next;

    public DynamicNode(Object x, DynamicNode n){
        info=x;
        next=n;
    }

    public Object getInfo(){
        return info;
    }

    public DynamicNode getNext(){
        return next;
    }

    public void setInfo(Object x){
        info=x;
    }

    public void setNext(DynamicNode n){
        next=n;
    }

    public String toString(){
        return info.toString();
    }
}

public class DynamicQueue {
    private DynamicNode front, rear;
    public int queueSize = 4;
    public DynamicQueue() {
        front = rear = null;
    }

    public boolean empty() {
        return (front == null);
    }

    // insert to list rear (insert last element)
    public void insert(Object x){
        DynamicNode p = new DynamicNode(x,null);
        System.out.print("Inserting "+x+" in rear.");
        if(empty())
            front = rear = p;
        else
            rear.setNext(p);

        rear=p;
    }

    // remove from the front of the list (delete first element)
    public Object remove(){
        if(empty()){
            System.out.println("Queue underflow");
            return -1;
        }
        DynamicNode p = front;
        Object temp = p.getInfo();
        front = p.getNext();

        if(front == null)
            rear = null;
        return temp;
    }

    public void pringQ(int num) {
        System.out.print(" Q"+num+": ");
        if(empty())
            System.out.print("Empty");
        DynamicNode p = front;
        String str = "";
        //traverse through list to print
        while(p!=null) {
            str+=p.getInfo();
                if(p.getNext()!=null)
                    str+="->";
            p = p.getNext();
        }
        System.out.println(str);

    }

    public boolean contains(Object x) {
        if(empty())
            return false;
        DynamicNode p = front;
        while(p!=null) {
            // check if theres a match within the queue
            if(p.getInfo().equals(x)) {
                return true;
            }
            p = p.getNext();
        }
        //if not return false
        return false;
    }

    public boolean isFull() {
        if(empty())
            return false;
        DynamicNode q = front;
        int count = 0;
        while(q!=null) {
            count++;
            //check if count ever reaches queue max size
            if(count==queueSize){
              return true;
            }
            q = q.getNext();
        }
        //if count never reaches size, return false
        return false;
    }
    public void moveToRear(Object x) {

            //if char is already at rear return
            if(rear.getInfo().equals(x)){
                System.out.print(x+" is already at rear.");
                return;
            }

             //if char is at front, get the next of front and set
             //it to front. Then move it to rear
            if(x.equals(front.getInfo())) {
                System.out.print("Moving "+x+" to rear.");
                DynamicNode temp = front;
                //get the next of temp
                front = temp.getNext();
                temp.setNext(null);
                rear.setNext(temp);
                rear = temp;
                return;
            }
            DynamicNode curr = front;
            DynamicNode temp;

            //if char isn't front or rear, find the node that equals x
            //set it's previous's next to its next
            //Then move it to rear
            while(curr.getNext()!=null) {
                if(x.equals(curr.getNext().getInfo())) {
                    System.out.print("Moving "+x+" to rear.");
                    temp = curr.getNext();
                    curr.setNext(temp.getNext());
                    temp.setNext(null);
                    rear.setNext(temp);
                    rear = temp;
                    return;
                }
                curr = curr.getNext();
            }

    }


    public static void main(String args[]){

        int N =4;
        //create array of queues
        DynamicQueue[] queues = new DynamicQueue[N];
        //instantiate queues
        for(int i =0;i<N;i++) {
            queues[i] = new DynamicQueue();
        }
        //find file
        File file = new File("src/data.txt");
        Scanner sc = null;
        try {
            //instantiate scanner
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //While file has a next, loop through to read text
        while(sc.hasNext()) {
            String chr = sc.next();
            int num = sc.nextInt();
            System.out.print("Read key "+chr+" for queue "+num+". ");
            //find queue within array containing number, num, from file
            DynamicQueue queue = queues[num];
            //if queue doesnt contain the element
          if(!queue.contains(chr)) {
              //and if queue isn't full
                if(!queue.isFull()) {
                    //insert queue to rear
                    queue.insert(chr);
                    queue.pringQ(num);
                }
                //else if queue is full
                else {
                    System.out.print("Q is full, removing front. ");
                    //remove front element from queue
                    queue.remove();
                    //and insert new node
                    queue.insert(chr);
                    queue.pringQ(num);
                }
          }
          //else if queue contains the element
          else {
                //move character to rear
                queue.moveToRear(chr);
                queue.pringQ(num);
          }

        }
        System.out.println("..Final Queues..");
        //print all queues with loop
        for(int i = 0; i<N;i++) {
            queues[i].pringQ(i);
        }

        sc.close();
    }
}
