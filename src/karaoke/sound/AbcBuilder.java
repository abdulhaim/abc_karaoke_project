package karaoke.sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A mutable AbcBuilder Class to build Abc Music Object as you parse through the AbcBody in MusicLanguageParser Class
 * 
 * Specification Author: Marwa Abdulhai
 * Implementation Author: Marwa Abdulhai
 *
 */
public class AbcBuilder {
    private final List<Bar> totalMusic;

    private List<Music> barNotes;
    private List<Music> tupletNotes;
    private List<Note> chordNotes;
    private Map<Character,String> accidentals;
    
    private List<Bar> beginRepeat;
    private List<Bar> firstRepeat;
    private List<Bar> secondRepeat;

    private int repeatStatus;
    private String status;
    private boolean repeatType;
    
    private final int beginRepeatStatus = 1;
    private final int firstRepeatEndingStatus = 2;
    private final int secondRepeatEndingStatus = 3;
    
    private static final Map<String, List<String>> keyAccidentals;
    
    // repeatMap provides information on when to repeat
    private final Map<Integer, List<Integer>> repeatMap = new HashMap<>();

    private double tupletDuration ;
    private List<String> lyrics;
    private int lyricsCounter;

    private String currentSinger;
    static
    {
        keyAccidentals = new HashMap<String, List<String>> ();
        keyAccidentals.put("C", Arrays.asList("C","D","E","F","G","A","B"));
        keyAccidentals.put("Am", Arrays.asList("C","D","E","F","G","A","B"));
        keyAccidentals.put("G", Arrays.asList("C","D","E","F#","G","A","B"));
        keyAccidentals.put("Em", Arrays.asList("C","D","E","F#","G","A","B"));
        keyAccidentals.put("D", Arrays.asList("C#","D","E","F#","G","A","B"));
        keyAccidentals.put("Bm", Arrays.asList("C#","D","E","F#","G","A","B"));
        keyAccidentals.put("A", Arrays.asList("C#","D","E","F#","G#","A","B"));
        keyAccidentals.put("F#m", Arrays.asList("C#","D","E","F#","G#","A","B"));
        keyAccidentals.put("E", Arrays.asList("C#","D#","E","F#","G#","A","B"));
        keyAccidentals.put("C#m", Arrays.asList("C#","D#","E","F#","G#","A","B"));
        keyAccidentals.put("B", Arrays.asList("C#","D#","E","F#","G#","A#","B"));
        keyAccidentals.put("G#m", Arrays.asList("C#","D#","E","F#","G#","A#","B"));
        keyAccidentals.put("F#", Arrays.asList("C#","D#","E#","F#","G#","A#","B"));
        keyAccidentals.put("D#m", Arrays.asList("C#","D#","E#","F#","G#","A#","B"));
        keyAccidentals.put("C#", Arrays.asList("C#","D#","E#","F#","G#","A#","B#"));
        keyAccidentals.put("A#m", Arrays.asList("C#","D#","E#","F#","G#","A#","B#"));

        keyAccidentals.put("F", Arrays.asList("C","D","E","F","G","A","Bb"));
        keyAccidentals.put("Dm", Arrays.asList("C","D","E","F","G","A","Bb"));
        keyAccidentals.put("Bb", Arrays.asList("C","D","Eb","F","G","A","Bb"));
        keyAccidentals.put("Gm", Arrays.asList("C","D","Eb","F","G","A","Bb"));
        keyAccidentals.put("Eb", Arrays.asList("C","D","Eb","F","G","Ab","Bb"));
        keyAccidentals.put("Cm", Arrays.asList("C","D","Eb","F","G","Ab","Bb"));
        keyAccidentals.put("Ab", Arrays.asList("C","Db","Eb","F","G","Ab","Bb"));
        keyAccidentals.put("Fm", Arrays.asList("C","Db","Eb","F","G","Ab","Bb"));
        keyAccidentals.put("Db", Arrays.asList("C","Db","Eb","F","Gb","Ab","Bb"));
        keyAccidentals.put("Bbm", Arrays.asList("C","Db","Eb","F","Gb","Ab","Bb"));
        keyAccidentals.put("Gb", Arrays.asList("Cb","Db","Eb","F","Gb","Ab","Bb"));
        keyAccidentals.put("Ebm", Arrays.asList("Cb","Db","Eb","F","Gb","Ab","Bb"));
        keyAccidentals.put("Cb", Arrays.asList("Cb","Db","Eb","Fb","Gb","Ab","Bb"));
        keyAccidentals.put("Abm", Arrays.asList("Cb","Db","Eb","Fb","Gb","Ab","Bb"));

    }
    
    /*
     * AF(totalMusic, barNotes, tupletNotes, chordNotes, =  Builds an Abc Body into a list of Music objects in totalMusic
     * Accidentals, beginRepeat, firstRepeat, secondRepeat) 
     */
    
    /**
     * Create AbcBuilder object to Build Music containing Notes, Chords, Tuplets, 
     * with Repeats inside Bar, Bars inside Repeats and Concats
     */
    public AbcBuilder() {
        this.totalMusic = new ArrayList<Bar>();

        this.barNotes  = new ArrayList<Music>();
        this.tupletNotes = new ArrayList<Music>();
        this.chordNotes = new ArrayList<Note>();
        this.accidentals = new HashMap<Character,String>();
        this.beginRepeat = new ArrayList<Bar>();
        this.firstRepeat = new ArrayList<Bar>();
        this.secondRepeat = new ArrayList<Bar>();
        this.repeatStatus = 0;
        this.lyricsCounter = 0;
        this.lyrics = new ArrayList<String>();
        this.status = "";
    }
    
    /** 
     * @return lyric at lyricsCounter if there are lyrics and increment counter.
     *         if no lyrics is present, returns "-1"
     */
    public String getLyricOnCount() {
        try {
            if(lyrics.isEmpty()) {
                return "-1";
            }
            if (lyrics.get(lyricsCounter).equals(" ")) { //won't work if multiple spaces in the lyrics
                lyricsCounter++;
            }
            if (lyrics.get(lyricsCounter).equals("_")) {
                int dummyCount = lyricsCounter - 1;
                lyricsCounter++;
                while (true) {
                    if (lyrics.get(dummyCount).equals("_")) {dummyCount = dummyCount -1;}
                    else {return lyrics.get(dummyCount);}
                }
            }
            return lyrics.get(lyricsCounter++);
        }
        catch(IndexOutOfBoundsException exp) {
            return "-1";
        }
        
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
    public List<Bar> getTotalMusic() {
        return this.totalMusic;
    }



    /**
     * Adds Tuplet to a Bar
     * @param tuplet to add to Bar
     */
    public void addTuplet(Tuplet tuplet) {
        this.barNotes.add(tuplet);
        //this.tupletNotes = new ArrayList<Music>(); // is the following necessary after this one

    }

    /**
     * Reset Tuplet after adding to Bar
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
     * @param c that accidental is applied on
     */
    public void addAccidental(char c,String type) {
        this.accidentals.put(c, type);
        
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
    public void setStatus(String status) {  //assert statement here
        this.status = status;
    }

    /**
     * Adds Music objects in a single Bar to the Concatenation of Entire Music
     * Applies Accidentals to Music Notes in the Bar
     */
    public void resetBar() {
        Bar bar = new Bar(this.barNotes);
        if(repeatStatus == 0) {
            this.totalMusic.add(bar);
        }
        else if(repeatStatus == beginRepeatStatus) {
            this.beginRepeat.add(bar);

        }
        else if(repeatStatus == firstRepeatEndingStatus) {
            this.firstRepeat.add(bar);
        }
        else if(this.repeatType && repeatStatus == secondRepeatEndingStatus) { //initial value of repeat type?
            this.firstRepeat.add(bar);
            // putting (key, value) into the repeat map. key holds the position of where repeat has to start
            // value specifies the range of sub-list of bars that has to be repeated at position key.
            // key = L + l_0
            // value = [L, L+ l_0]
            int key = this.totalMusic.size() + this.firstRepeat.size();
            List<Integer> value = Arrays.asList(this.totalMusic.size(),
                                                this.totalMusic.size()+this.firstRepeat.size());
            repeatMap.put(key,value);
            
            for(Bar b: this.firstRepeat) {
                totalMusic.add(b);
            }
            this.beginRepeat = new ArrayList<Bar>();
            this.firstRepeat = new ArrayList<Bar>();
            this.secondRepeat = new ArrayList<Bar>();
            this.repeatStatus = 0;
            this.repeatType = false;

        }
        else if(repeatStatus == secondRepeatEndingStatus) {
            this.secondRepeat.add(bar);
            // key = L + l_0 + l_1
            // value = [L, L+ l_0]
            int key = this.totalMusic.size()+this.beginRepeat.size()+this.firstRepeat.size();
            List<Integer> value = Arrays.asList(this.totalMusic.size(),
                                                this.totalMusic.size()+this.beginRepeat.size());
            repeatMap.put(key, value);
            totalMusic.addAll(this.beginRepeat);
            totalMusic.addAll(this.firstRepeat);
            totalMusic.addAll(this.secondRepeat);

            this.beginRepeat = new ArrayList<Bar>();
            this.firstRepeat = new ArrayList<Bar>();
            this.secondRepeat = new ArrayList<Bar>();
            this.repeatStatus = 0;
            
        }
        this.barNotes = new ArrayList<Music>();
        this.chordNotes = new ArrayList<Note>();
        this.tupletNotes = new ArrayList<Music>();
        this.accidentals = new HashMap<Character,String>();
        
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

    /**
     * 
     * @return
     */
    public List<Bar> getMusicLine() {
        return this.totalMusic;
    }

    public Pitch applyKeyAccidental(Character pitchChar, String keyAccidental) {
        Pitch pitch = new Pitch(pitchChar);
        List<String> accidentalList = this.keyAccidentals.get(keyAccidental);
        for(String accidental: accidentalList) {
            if(accidental.indexOf(pitchChar)!=-1) {
                if(accidental.contains("#")) {
                    pitch = pitch.transpose(1);
                    
                }
                else if(accidental.contains("b")) {
                    pitch = pitch.transpose(-1);
                }
            }
        }
        return pitch;
            
    }

    public void flagSimpleRepeat(boolean b) {
        this.repeatType = b;
    }

    public int getBarNotesSize() {
        return this.barNotes.size();
    }

    public Map<Integer, List<Integer>> getHashMap() {
        return this.repeatMap;
    }

    public void setTupletDuration(double duration) {
       this.tupletDuration = duration;
        
    }
    public double getTupletDuration() {
        return this.tupletDuration;         
     }

    public void setLyrics(List<String> lyrics) {
        this.lyrics = lyrics;
        
    }
    public List<String> getLyrics() {
        return this.lyrics;
        
    }

    public String getSinger() {
        return this.currentSinger;
    }

    public void setSinger(String singer) {
        this.currentSinger = singer;
    }

}

