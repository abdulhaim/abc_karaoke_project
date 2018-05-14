package karaoke.sound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * enum to represent different types of tuplets.
 * 
 * @author Bibek Kumar Pandit
 */
enum TupletType{
    
    DUPLET (2, 3.0),
    TRIPLET (3, 2.0),
    QUADRUPLET (4, 3.0);
    
    private final int numberOfSubMusic;
    private final double totalDuration;
    
    /**
     * Constructor of enum TupletType
     * @param numberOflistOfSubMusic number of listOfSubMusic associated witht Tuplet
     * @param totalDuration duration for which the tuplet plays.
     */
    TupletType(int numberOfSubMusic, double totalDuration){
        this.numberOfSubMusic = numberOfSubMusic;
        this.totalDuration = totalDuration;
    }
    
    public int getNumOfSubMusic() {return numberOfSubMusic;}
    public double totalDuration() {return totalDuration;}
}


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
    //private final TupletType tupletType;
    private final double tupletSize;
    private final double durationPerMusic;
    
    /*
     * AF(listOfSubMusic, tupletType, duration): 
     *  -  Tuplet where listOfSubMusic is the list of listOfSubMusic that forms the tuplet, tupletType tells the type of tuplet and 
     *     duration is the enlarged/shrunken duration for the entire tuplet
     * 
     * specific type of tuplet (triplet, duplet, quadruplet)
     * Rep Invariant:
     * - listOfSubMusic.size() == 2 or 3 or 4
     * - duration == tupletType.getTotalDuration()
     * 
     * Safety from Rep: All fields are private and final. tupletType is enum and duration is primitive type, so
     *                  they are immutable. listOfSubMusic is made immutable by wrapping it around the immutable wrapper
     *                  Collections.unmodifiable(). This ADT cannot be mutated, not even beneficient mutation.
     *                             
     */

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
    public void play(SequencePlayer player, double atBeat) {

        double currentBeat = atBeat;
        for (Music music : this.listOfSubMusic) {
            music.play(player, currentBeat);
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
