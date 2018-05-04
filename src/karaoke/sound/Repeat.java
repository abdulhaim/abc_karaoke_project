package karaoke.sound;
/**
 * An immutable data type that represents a section of music that is to be repeated once
 * @author Myra
 *
 */
public class Repeat implements Music {
    private final Music music;
    private final Music ending1;
    private final Music ending2;
    private final double duration;
    
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
    public Repeat(Music music, Music ending1, Music ending2, double duration) {
        this.music= music;
        this.ending1 =ending1;
        this.ending2 = ending2;
        this.duration = duration;
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
        String ans = "|:";
        ans += music.toString();
        ans += ending1.toString();
        ans += music.toString();
        ans += ending2.toString();
        
        ans += ":|";
        return ans;
        
    }

}
