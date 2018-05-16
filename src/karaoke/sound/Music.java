
package karaoke.sound;

import java.util.concurrent.BlockingQueue;

/**
 * Immutable Music Interface representing a piece of music played by multiple instruments.
 * @author Marwa Abdulhai

 */
public interface Music {

    /*
     * 
     * Music = Note(duration : double, pitch : Pitch, instrument : Instrument)
     *        + Rest(duration : double)
     *        + Accidental(duration: double, pitchChange: Pitch, bar: Bar)
     *        + Chord()
     *        + Tuplets()
     *        + My Signature()
     *        + Repeat()
     *        + Concat(music1:Music, music2:Music)
     */
    
    /**
     * @return total duration of this piece in beats
     */
    public double getDuration();

    /**
     * Play this piece.
     * @param player player to play on
     * @param atBeat when to play
     * @param queue blocking queue to put lyrics on
     */
    public void play(SequencePlayer player, double atBeat);

    
}
