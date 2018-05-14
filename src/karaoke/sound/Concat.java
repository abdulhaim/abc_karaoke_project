package karaoke.sound;

import java.util.List;

/**
 * @author Bibek Kumar Pandit
 *
 * Concat represents an immutable datatype that concatenates two pieces of Music to return concatenated Music. 
 */
public class Concat implements Music{

    
    //fields
    private final List<Music> music;
    
    /**
     * Constructor of Concat.
     * @param this.music list of Music
     */
    public Concat(List<Music> music) {
        this.music = music;
    }
   
    
    @Override
    public double getDuration() {
        double duration = 0;
        for(Music m: music) {
            duration+=m.getDuration();
        }
        return duration;
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        
    }
    @Override
    public String toString() {
        String concat = "";
        for(Music m: music) {
            concat += m.toString();
        }
        return concat;
        
    }


    public List<Music> getMusic() {
        return this.music;
    }
    
}
