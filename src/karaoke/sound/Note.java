package karaoke.sound;

import java.util.concurrent.BlockingQueue;

/**
 * Immutable data type Note represents a note played by an instrument.
 * @author Marwa Abdulhai
 * 
 */
public class Note implements Music {

    /* AF(duration, pitch, instrument, lyrics ): Represents a note of pitch {@param pitch} played on instrument {@param instrument}
     *                                  for duration (in number of beats) {@param duration}  with corresponding syllable (@param lyrics)
     *  
     * Rep Invariant:
     * - duration >= 0
     * - lyrics is the corresponding syllable or "no lyrics" if no lyrics 
     * 
     * Safety From Rep Exposure: All fields are private, final and immutable. Note itself is immutable too.
     * 
     * ThreadSafety Argument: Note is an immutable data-type with no beneficient mutation. duration is a primitive
     *                        datatype and thus threadsafe. pitch is both immutable and threadsafe. And instrument is an enum.
     *                        Thus, Note is threadsafe.
     */
    
    private final double duration;
    private final Pitch pitch;
    private final Instrument instrument;
    private final String lyrics;
    
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
        this.lyrics = "-1";
        checkRep();
    }
    
    /**
     * Make a Note played by instrument for duration beats.
     * @param duration duration in beats, must be >= 0
     * @param pitch pitch to play
     * @param lyrics the lyrics associated with the note.
     */
    public Note(Pitch pitch,double duration, String lyrics) {
        this.duration = duration;
        this.pitch = pitch;
        this.instrument = Instrument.PIANO;
        this.lyrics = lyrics;
        checkRep();
    }

    /**
     * Producer of Note.
     * @param lyric lyric to be given to a note
     * @return new Note with same fields as earlier but lyrics too.
     */
    public Note noteWithLyrics(String lyric) {
        assert this.lyrics.equals("-1");
        return new Note(this.pitch, this.duration, lyric);
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
//        player.addEvent(atBeat, (Double beat) -> {try {
//            queue.put(lyrics);
//        } catch (InterruptedException e) {
//            throw new AssertionError("Something went wrong!");
//        } });
        player.addEvent(atBeat, (Double beat) -> { if(!lyrics.equals("-1")) { System.out.println(lyrics); } }); //fix this
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
        return pitch.toString() + duration;
    }

}
