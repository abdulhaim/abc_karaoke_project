package karaoke.sound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
/*
 * AF(listOfSubMusic, tupletSize, duration): 
 *  -  Tuplet where listOfSubMusic is the list of listOfSubMusic that forms the tuplet, tupletSize is the size of the tuplet and
 *     indicates its type as a triplet, duplet, or quadruplet, and 
 *     duration is the enlarged/shrunken duration for the entire tuplet
 * 
 * specific type of tuplet (triplet, duplet, quadruplet)
 * Rep Invariant:
 * - listOfSubMusic.size() == 2 or 3 or 4
 * - duration == tupletType.getTotalDuration()
 * 
 * Safety from Rep: All fields are private and final. tupletType is enum and duration is primitive type, so
 *                  they are immutable. listOfSubMusic is made immutable by wrapping it around the immutable wrapper
 *                  Collections.unmodifiable(). 
 *                  ThreadSafety:
 *                  This ADT cannot be mutated, not even beneficent mutation.
 *                             
 */
import java.util.concurrent.BlockingQueue;

/**
 * enum to represent different types of tuplets.
 * 
 * @author Bibek Kumar Pandit
 */



/**
 * An immutable data type representing a tuplet 
 * 
 * A Tuplet is a consecutive group of listOfSubMusic that are to be played for a duration
 * that is either greater or less than the sum of the individual listOfSubMusic within that group
 * 
 * @author Myra
 *
 */
public class Tuplet implements Music {
    
    
    private final List<Music> listOfSubMusic;
    private final double tupletSize;
    private final double durationPerMusic;
    
    

    /**
     * Creates a Tuplet consisting of listOfSubMusic fitted to the proper duration
     * @param modifiedDuration
     * @param duration
     */
    public Tuplet(List<Music> modifiedDuration, double tupletSize) {
        this.listOfSubMusic = Collections.synchronizedList(Collections.unmodifiableList(modifiedDuration));
        this.tupletSize = tupletSize;
        this.durationPerMusic = modifiedDuration.get(0).getDuration();
        checkRep();
    }   
    
    private void checkRep() {
        for (Music music: listOfSubMusic) {
            assert music.getDuration() == this.durationPerMusic;
        }
        assert listOfSubMusic.size() == (int) this.tupletSize;
    }
    
    
    @Override
    public double getDuration() {
        return this.tupletSize*this.durationPerMusic;
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat,Map<String,BlockingQueue<String>> queue) {

        double currentBeat = atBeat;
        for (Music music : this.listOfSubMusic) {
            music.play(player, currentBeat,queue);
            currentBeat += music.getDuration();
        } 
    }
    
    @Override 
    public String toString() {
        String ans = "(";
        ans += this.tupletSize;
        for (Music subMusic: listOfSubMusic) {
            ans += subMusic.toString();
        }
        return ans;   
    }
    
    /**
     * @return list of music that form tuplet
     */
    public List<Music> getMusic() {
        return new ArrayList<Music>(this.listOfSubMusic);
    }
    

}
