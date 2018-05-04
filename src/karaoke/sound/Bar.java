package karaoke.sound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Bibek Kumar Pandit
 *
 * 
 */
public class Bar implements Music{

    //fields:
    private final List<Music> listOfSubMusic;
    private final double totalDuration = this.getDuration();
    
    /**
     * Constructor of Bar.
     * @param subMusic list of Music types to be merged
     */
    public Bar(List<Music> subMusic) {
        listOfSubMusic = Collections.unmodifiableList(new ArrayList<Music>(subMusic));
    }
    
    private void checkRep() {
        assert totalDuration > 0;
    }
    
    @Override
    public double getDuration() {
        double duration = 0;
        for (Music music : listOfSubMusic) {
            duration += music.getDuration();
        }
        return duration;
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        throw new AssertionError();
    }
}
