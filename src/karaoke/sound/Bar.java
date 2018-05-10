package karaoke.sound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Bibek Kumar Pandit
 *
 * Bar represents a bar in Music.
 */
public class Bar implements Music {

    /* AF(listOfSubMusic, totalDuration): Represents a bar of music. The notes and rests in listOfMusic form the music
     *                                    sequence of the bar and {@param totalDuration} gives the total duration of the bar.
     * 
     * Rep Invariant:
     * - totalDuration >= 0
     * 
     * Safety From Rep Exposure: All fields are private and final. listOfSubMusic is the only mutable field which we
     *                           have rendered immutable by wrapping into unmodifiable wrapper. So Bar is immutable.
     * 
     * ThreadSafety Argument: Bar is an immutable data-type with no beneficient mutation. totalDuration is a primitive
     *                        datatype and thus threadsafe. listOfMusic is both unmodifiable and rendered threadsafe by
     *                        wrapping it around Collections.synchronizedList. Also parameter of listOfMusic, Music, itself
     *                        is thread-safe.
     */
    
    //fields:
    private final List<Music> listOfSubMusic;
    private final double totalDuration;
    
    /**
     * Constructor of Bar.
     * @param subMusic list of Music types to be merged
     */
    public Bar(List<Music> subMusic) {

        //Might not need synchronizedList
        this.listOfSubMusic = Collections.synchronizedList(Collections.unmodifiableList(new ArrayList<Music>(subMusic)));
        checkRep();
        this.totalDuration = this.getDuration();
    }
    
    private void checkRep() {
        assert this.listOfSubMusic != null;
        assert this.totalDuration >= 0;
    }
    
    @Override
    public double getDuration() {
        double duration = 0;
        for (Music music : this.listOfSubMusic) {
            duration += music.getDuration();
        }
        checkRep();
        return duration;
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        double subDuration = 0;
        for (Music music : listOfSubMusic) {
            music.play(player, atBeat + subDuration);
            subDuration += music.getDuration();
        }
    }
    
    @Override
    public int hashCode() {
        long durationBits = Double.doubleToLongBits(totalDuration);
        return (int) (durationBits ^ (durationBits >>> Integer.SIZE))
                + listOfSubMusic.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Bar other = (Bar) obj;
        return totalDuration == other.totalDuration
                && listOfSubMusic.equals(other.listOfSubMusic);
    }
    
    @Override
    public String toString() {
        String bar = "";
        for(int i = 0; i<this.listOfSubMusic.size();i++) {
            bar+= listOfSubMusic.get(i) + " ";
        }
        return bar + "|";
        
    }
}
