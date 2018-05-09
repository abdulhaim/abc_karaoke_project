package karaoke.sound;

/**
 * Immutable data type Note represents a note played by an instrument.
 * @author Marwa Abdulhai
 * 
 */
public class Note implements Music {

    /* AF(duration, pitch, instrument): Represents a note of pitch {@param pitch} played on instrument {@param instrument}
     *                                  for duration (in number of beats) {@param duration}  
     *  
     * Rep Invariant:
     * - duration >= 0
     * 
     * Safety From Rep Exposure: All fields are private, final and immutable. Note itself is immutable too.
     * 
     * ThreadSafety Argument: Note is an immutable data-type with no beneficient mutation. duration is a primitive
     *                        datatype and thus threadsafe. pitch is both immutable and threadsafe. And instrument is an enum.
     *                        Thus, Note is threadsafe.
     */
    
    //fields
    private final double duration;
    private final Pitch pitch;
    private final Instrument instrument;
    private void checkRep() {
        assert pitch != null;
        assert instrument != null;
        assert duration >= 0;
    }

    /**
     * Make a Note played by instrument for duration beats.
     * @param duration duration in beats, must be >= 0
     * @param pitch pitch to play
     */
    public Note(Pitch pitch,double duration) {
        this.duration = duration;
        this.pitch = pitch;
        this.instrument = Instrument.PIANO;
        checkRep();
    }

    /**
     * @return pitch of this note
     */
    public Pitch getPitch() {
        return pitch;
    }

    /**
     * @return duration of this note
     */
    @Override
    public double getDuration() {
        return duration;
    }
    

    /**
     * Play this note.
     * @param player player taht plays the note
     * @param atBeat beat position of when the notwe will be played.
     */
    @Override
    public void play(SequencePlayer player, double atBeat) {
        player.addNote(instrument, pitch, atBeat, duration);
    }

    @Override
    public int hashCode() {
        long durationBits = Double.doubleToLongBits(duration);
        return (int) (durationBits ^ (durationBits >>> Integer.SIZE))
                + instrument.hashCode()
                + pitch.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Note other = (Note) obj;
        return duration == other.duration
                && instrument.equals(other.instrument)
                && pitch.equals(other.pitch);
    }

    @Override
    public String toString() {
        return pitch.toString();
    }

}
