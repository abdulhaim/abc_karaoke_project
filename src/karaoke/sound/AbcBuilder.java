package karaoke.sound;

import java.util.ArrayList;
import java.util.List;

/**
 * AbcBuilder Class to build Abc Music Object as you parse through the AbcBody in MusicLanguageParser Class
 * 
 * Specification Author: Marwa Abdulhai
 * Implementation Author: Marwa Abdulhai
 *
 */
public class AbcBuilder {
    private final List<Music> totalMusic;

    private List<Music> barNotes;
    private List<Music> tupletNotes;
    private List<Note> chordNotes;
    private List<String> accidentals;
    
    private List<Music> beginRepeat;
    private List<Music> firstRepeat;
    private List<Music> secondRepeat;

    private int repeatStatus;
    private String status;
    
    private final int beginRepeatStatus = 1;
    private final int firstRepeatEndingStatus = 2;
    private final int secondRepeatEndingStatus = 2;

    
    /*
     * AF(totalMusic, barNotes, tupletNotes, chordNotes, =  Builds an Abc Body into a list of Music objects in totalMusic
     * Accidentals, beginRepeat, firstRepeat, secondRepeat) 
     */
    
    /**
     * Create AbcBuilder object to Build Music containing Notes, Chords, Tuplets, 
     * with Repeats inside Bar, Bars inside Repeats and Concats
     */
    public AbcBuilder() {
        this.totalMusic = new ArrayList<Music>();

        this.barNotes  = new ArrayList<Music>();
        this.tupletNotes = new ArrayList<Music>();
        this.chordNotes = new ArrayList<Note>();
        this.accidentals = new ArrayList<String>();
        
        this.beginRepeat = new ArrayList<Music>();
        this.firstRepeat = new ArrayList<Music>();
        this.secondRepeat = new ArrayList<Music>();
        this.repeatStatus = 0;
        status = "";
    }

    /**
     * Add Music object to Bar
     * @param music to add
     */
    public void addToBar(Music music) {
        barNotes.add(music);

    }
    
    /**
     * Add Note object to Chord
     * @param note to add 
     */
    public void addToChord(Note note) {
        chordNotes.add(note);
    }
    
    /**
     * Add Music object to Tuplet
     * @param music to add
     */
    public void addToTuplet(Music music) {
        tupletNotes.add(music);
    }


    /**
     * Return current accumulation of notes in a Chord within a specific Bar
     * @return list of notes in the chord
     */
    public List<Note> getChordNotes() {
        return this.chordNotes;
    }

    /**
     * Get list of Music objects inside a Tuplet
     * @return list of music 
     */
    public List<Music> getTupletNotes() {
        return tupletNotes;
    }

    /**
     * Return current accumulation of the music objects
     * @return list of music gathered so far
     */
    public List<Music> getTotalMusic() {
        return this.totalMusic;
    }



    /**
     * Adds Tuplet to a Bar
     * @param tuplet to add to Bar
     */
    public void addTuplet(Tuplet tuplet) {
        this.barNotes.add(tuplet);
        this.tupletNotes = new ArrayList<Music>();

    }

    /**
     * Reset Chord after adding to Bar
     */
    public void resetTuplet() {
        this.tupletNotes = new ArrayList<Music>();
        
    }

    /**
     * Reset Chord after adding to Bar
     */

    public void resetChord() {
        this.chordNotes = new ArrayList<Note>();
        
    }

    /**
     * Add Accidental found in the Bar
     * @param pitchString that accidental is applied on
     */
    public void addAccidental(String pitchString) {
        this.accidentals.add(pitchString);
        
    }


    /**
     * Set status of where you are in the Repeat 
     * @param status of repeat
     */
    public void setRepeatStatus(int status) {
        this.repeatStatus = status;
        
    }



    /**
     * Return status of where to add Music object to
     * @return string containing status of where to add Music object (Bar, Chord, or Tuplet)
     */
    public String getStatus() {
        return this.status;
    }
    
    /**
     * Setting status of where to add music objects to 
     * @param status string representing status of where to add music object
     *        Can take values of "Bar", "Chord" or "Tuplet"
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Adds Music objects in a single Bar to the Concatenation of Entire Music
     * Applies Accidentals to Music Notes in the Bar
     */
    public void resetBar() {
        ArrayList<Music> newBar = new ArrayList<Music>();
        for(Music m: this.barNotes) {
            if(m instanceof Note) {
                Note note = (Note) m;
                if(this.accidentals.contains(note.getPitch().toString())) {
                    newBar.add(new Note(note.getPitch().transpose(Pitch.OCTAVE),note.getDuration()));
                }
                else {
                    newBar.add(note);
                }
            }
            if (m instanceof Chord) {
                Chord chord = (Chord) m;
                List<Note> newChord = new ArrayList<Note>();
                for(Note note: chord.getNotes()) {
                    if(this.accidentals.contains(note.getPitch().toString())) {
                        newChord.add(new Note(note.getPitch().transpose(Pitch.OCTAVE),note.getDuration()));
                    }
                    else {
                        newChord.add(note);
                    }

                }
                newBar.add(new Chord(newChord));
            }
            if (m instanceof Tuplet) {
                Tuplet tuplet = (Tuplet) m;
                List<Music> newTuplet = new ArrayList<Music>();
                for(Music note: tuplet.getMusic()) {
                    if(note instanceof Note ) {
                        Note n = (Note) note;
                        if(this.accidentals.contains(n.getPitch().toString())) {
                            newTuplet.add(new Note(n.getPitch().transpose(Pitch.OCTAVE),note.getDuration()));
                        }
                        else {
                            newTuplet.add(n);
                        }

                    }
                    if(note instanceof Chord) {
                        Chord c = (Chord) note;
                        List<Note> newChord = new ArrayList<Note>();
                        for(Note n: c.getNotes()) {
                            if(this.accidentals.contains(n.getPitch().toString())) {
                                newChord.add(new Note(n.getPitch().transpose(Pitch.OCTAVE),note.getDuration()));
                            }
                            else {
                                newChord.add(n);
                            }

                        }
                        newTuplet.add(new Chord(newChord));
                    }

                }
                newBar.add(new Tuplet(newTuplet,tuplet.getDuration()));

            }
        }
        
        Bar bar = new Bar(newBar);
        if(repeatStatus == 0) {
            this.totalMusic.add(bar);
        }
        else if(repeatStatus == beginRepeatStatus) {
            this.beginRepeat.add(bar);

        }
        else if(repeatStatus == firstRepeatEndingStatus) {
            this.firstRepeat.add(bar);
        }
        else if(repeatStatus == secondRepeatEndingStatus) {
            this.secondRepeat.add(bar);
            List<List<Music>> music = new ArrayList<>();
            music.add(beginRepeat);
            music.add(firstRepeat);
            music.add(secondRepeat);
            Repeat repeat = new Repeat(music,true);
            totalMusic.add(repeat);
            this.beginRepeat = new ArrayList<Music>();
            this.firstRepeat = new ArrayList<Music>();
            this.secondRepeat = new ArrayList<Music>();
            this.repeatStatus = 0;
            
        }
        this.barNotes = new ArrayList<Music>();
        this.chordNotes = new ArrayList<Note>();
        this.tupletNotes = new ArrayList<Music>();
        this.accidentals = new ArrayList<String>();
        
    }

    
    /**
     * Returns to String representation of Music accumulated so far by the AbcBuilder
     * @return string of the AbcBuilder Music objects
     */
    @Override
    public String toString() {
        String total = "";
        for(Music music: this.totalMusic) {
            total+=music.toString();
            
        }
        return total;
    }


}

