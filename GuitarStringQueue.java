import java.util.*;
import java.lang.Math;

/**
Implementation of a subclass of GuitarString that creates a ring buffer as a queue
and applies the Karplus-Strong method when a string is plucked.

Time spent: 45 minutes

@author Ilina Navani
@author Kate Hynes
 */

public class GuitarStringQueue extends GuitarString {
    
    //initializing ring buffer as a Queue
    Queue<Double> myBuffer = new LinkedList<Double>();

    /**
     * Constructs a ring buffer of a size equal to the sampling rate divided 
     * by the entered frequency, and intializes all values in it to zero.  
     * @param frequency frequency value of a guitar string
     * @throws IllegalArgumentException if the entered frequency value is 
     * lesser than or equal to zero, or if the buffer size is lesser than 2
     */
    public GuitarStringQueue(double frequency) {
        myNumTics=0;
        myBufferSize=0;

        if (frequency <= 0) {
            throw new IllegalArgumentException();
        }

        //determining the capacity of the ring buffer, that is, its size
        int n = (int)Math.round(StdAudio.SAMPLE_RATE/frequency);
        myBufferSize = n;

        if(myBufferSize<2) {
            throw new IllegalArgumentException();
        }
        //initializing all values in the buffer to zero 
        for(int i=0; i<myBufferSize; i++) {
            myBuffer.add(0.0);
        }
    }

    /**
     * Constructs a ring buffer containing frequency values from a given array
     * @param init an array containing frequency values
     * @throws IllegalArgumentException if the length of the array is lesser than 2
     */
    public GuitarStringQueue(double[]init) {
        if (init.length < 2) {
            throw new IllegalArgumentException();
        }
        //initializing all values of the buffer to their corresponding values from the array 
        for(int i=0; i<init.length; i++) {
            myBuffer.add(init[i]);
        }
    }
    /**
    The method sets the values in the ring buffer to equal a random value between -0.5 to 0.5.
    Random values are generated using the Java Math class. 
     */
    public void pluck() {
        for(int i=0; i<myBufferSize; i++) {
            myBuffer.remove();
            myBuffer.add((Math.random()*(0.5-(-0.5))+(-0.5)));
        }
    }

    /**
    The method applies the Karplus-Strong method once.
    
    The Karplus-Strong method takes the average of the first two values in the ring buffer,
    multiplies that number by an energy decay factor, 0.996, and adds it to the end of 
    the buffer. It also deletes the sample that is currently in the front of the buffer.
     */
    public void tic() {
        myNumTics++;
        double firstElement = myBuffer.peek();
        myBuffer.remove();
        double average = ((firstElement + myBuffer.peek())/2) * ENERGY_DECAY;
        myBuffer.add(average);
    }

    /**
    Returns the current sample, which is the value at the front of the ring buffer.
    @return a double representing the sample (first value of buffer)
     */
    public double sample() {
        return myBuffer.peek();
    }

    /**
    Returns the number of time that the tic method has been called.
    @return an integer representing the times the tic method has been called
     */
    public int time() {
        return myNumTics;
    }
}
