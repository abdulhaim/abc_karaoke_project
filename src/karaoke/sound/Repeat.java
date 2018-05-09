package karaoke.sound;

import java.util.List;

/**
 * An immutable data type that represents a section of music that is to be repeated once
 * @author Myra
 *
 */
public class Repeat implements Music {
    List<Music> music;
    boolean repeatType;
    /*
     * AF(music, ending1, ending2) = Repeat music is the main music to be repeated and ending1 and ending2 are the first
     * and second endings  
     * RI: true
     * Safety from Rep: music, ending1, ending2 and duration are private and final and the data type is immutable
     */

    /**
     * Initialized a Repeat 
     * @param music the main music
     * @param ending1 the first ending
     * @param ending2 the second ending
     */
    public Repeat(List<Music> music, boolean repeatType) {
        this.music= music;
        this.repeatType = repeatType;
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
        if(music.size() ==1) {  
            return "|:" +  music.get(0).toString() + ":|";

        }
        else {
            return "|:" + music.get(0).toString() + "|[1" + music.get(1).toString() +  ":|[2" + music.get(2).toString();

        }
    }

}
