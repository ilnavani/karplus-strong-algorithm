import java.lang.Math;
/** An implementation of a 37 string guitar. Implements the Guitar interface
 *  to specify methods that can process and play the given guitar keys. 
 * 
 * Time spent: 1.5 hours
 * 
 * @author Ilina Navani
 * @author Kate Hynes
 */

public class Guitar37 implements Guitar {

    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static final int keyboard_length = KEYBOARD.length();
    public static final double CONCERT = 440.0;

    //initializing arrays
    char[] myString = new char[keyboard_length];
    double[] myFrequencies = new double[keyboard_length];
    GuitarString[] myObjects = new GuitarString[keyboard_length];

    /**
     * Constructs the three arrays that store guitar keys, frequency of the keys, 
       and the guitar string objects respectively. The order of elements in each
       array correspond with the others, and this is the order of the characters  
       that imitate a piano keyboard.
     */
    public Guitar37() {
        for(int i=0; i<keyboard_length; i++) {
            myString[i] = KEYBOARD.charAt(i); 
        }
        
        for(int i=0; i<myFrequencies.length; i++) {
            myFrequencies[i] = CONCERT * Math.pow(2, ((i-24)/12.0));
        }
        
        for(int i=0; i<myObjects.length; i++) {
            myObjects[i] = new GuitarStringArrayList(myFrequencies[i]);
        }
    }


    /** The method determines whether there is a guitar string that corresponds to 
      * the entered key. 
      *
      * @param string the key on the keyboard to look up
      * @return true if the entered key maps to one of the 37 strings in this instrument
      * @return false if the entered key is not one of the 37 strings
    */
    public boolean hasString(char string) {
        for(int i=0; i<myString.length; i++) {
            if (myString[i] == string) {
                return true;
            }
        }
        return false;
    }
    
    /** The method plucks the string corresponding to the key pressed by the user.
      * 
      * @param string the key on the keyboard to be plucked
      * @throws IllegalArgumentException if the key pressed is not one of the 37 strings
    */
    public void pluck(char string) {
        if (hasString(string)) {
            for(int i=0; i<myString.length; i++) {
                //finding the pressed key within the array of strings and plucking it
                if (string == myString[i]) {
                    myObjects[i].pluck();
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /** The method finds the sum of all the guitar strings to play the current sound.
    */
    public void play() {
        double sample = 0;
        for(int i=0; i<myObjects.length; i++) {
            sample += myObjects[i].sample();
        }
        StdAudio.play(sample);
    }

    /** The method advances the string simulation by having each string tic forward
    */
    public void tic() {
        for(int i=0; i<myObjects.length; i++) {
            myObjects[i].tic();
        }
    }
}