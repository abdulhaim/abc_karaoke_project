package karaoke.sound;

/**
 * @author Bibek Kumar Pandit
 *
 * Concat represents an immutable datatype that concatenates two pieces of Music to return concatenated Music. 
 */
public class Concat implements Music{

    
    //fields
    private final Music music1;
    private final Music music2;
    
    /**
     * Constructor of Concat.
     * @param music1 first part of concatenated music
     * @param music2 second part of concatenated music
     */
    public Concat(Music music1, Music music2) {
        this.music1 = music1;
        this.music2 = music2;
    }
    
    /**
     * @return first piece in this concatenation
     */
    public Music first() {
        return music1;
    }

    /**
     * @return second piece in this concatenation
     */
    public Music second() {
        return music2;
    }

    
    @Override
    public double getDuration() {
        return music1.getDuration() + music2.getDuration();
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        music1.play(player, atBeat);
        music2.play(player, atBeat+music1.getDuration());
    }
    
}
