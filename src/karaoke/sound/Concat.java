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
     * @param music1 first part of concatenated music
     * @param music2 second part of concatenated music
     */
    public Concat(List<Music> music) {
        this.music = music;
    }
   
    
    @Override
    public double getDuration() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public String toString() {
        String concat = "";
        for(Music m: music) {
            concat += m.toString();
        }
        return concat;
        
    }
    
}
