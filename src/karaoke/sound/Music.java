
package karaoke.sound;

/**
 * Immutable Music Interface represents a piece of music played by multiple instruments.
 */
public interface Music {

    /*
     * 
     * Music = Note(duration : double, pitch : Pitch, instrument : Instrument)
              + Rest(duration : double)
              + Accidents()
              + Chords()
              + Tuplets()
              + My Signature()
              + Repeat()
              + Concat(music1:Music, music2:Music)
     */
    
    /**
     * @return total duration of this piece in beats
     */
    public double getDuration();

    /**
     * Play this piece.
     * @param player player to play on
     * @param atBeat when to play
     */
    public void play(SequencePlayer player, double atBeat);

    
}
