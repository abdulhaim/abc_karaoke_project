package karaoke.sound;

import java.util.List;

/**
 * An immutable data type that represents a section of music that is to be repeated once
 * @author Myra
 *
 */
public class Repeat implements Music {
    List<List<Music>> music;
    boolean repeatType;
    /*
     * AF(music, ending1, ending2) = Repeat music is the main music to be repeated and ending1 and ending2 are the first
     * and second endings  
     * RI: true
     * Safety from Rep: music are private and final and the data type is immutable
     */

    /**
     * Initialized a Repeat 
     * @param music the main music
     * @param repeatType the type of repeat
     */
    public Repeat(List<List<Music>> music,boolean repeatType) {
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
            String beginRepeat = "";
            String firstRepeat = "";
            String secondRepeat = "";
            for(Music m: music.get(0)) {
                beginRepeat+= m.toString();
            }
            for(Music m: music.get(1)) {
                firstRepeat+= m.toString();
            }
            for(Music m: music.get(2)) {
                secondRepeat+= m.toString();
            }

            return "|:" + beginRepeat + "|[1" + firstRepeat +  ":|[2" + secondRepeat;

        }
    }

}
