package karaoke.sound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Bibek Kumar Pandit
 * 
 */
public class Bar implements Music {

    private final List<Music> listOfSubMusic;
    private final double totalDuration;
    
    /**
     * Constructor of Bar.
     * @param subMusic list of Music types to be merged
     */
    public Bar(List<Music> subMusic) {
        this.listOfSubMusic = subMusic;
        this.totalDuration = this.getDuration();
    }
    
    private void checkRep() {
        assert totalDuration > 0;
    }
    
    @Override
    public double getDuration() {
        double duration = 0;
        for (Music music : this.listOfSubMusic) {
            duration += music.getDuration();
        }
        return duration;
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        throw new AssertionError();
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
