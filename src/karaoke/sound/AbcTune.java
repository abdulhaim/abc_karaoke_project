package karaoke.sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AbcTune Class representing the contents of a music file


 * 
 * Specification Author: Marwa Abdulhai
 * Implementation Author: Marwa Abdulhai
 *
 */

/*
 *      AF (title, composer, meter, tempo, noteLength, indexNumber, accidental, music, keySignature, voices) = 
 *          represents the contents of a music file with a title (@param title) and a composer (@param composer)
 *          with a specified meter (@param meter), tempo (@param tempo), note length (@param noteLength), and with a track number on its album if applicable (@param indexNumber).
 *          The music file may also contain an accidental (@param accidental) different voices represented by Voices object (@param music), a key signature (@param keySignature)
 *          and a list of the voices as specified in the header which is used to creates the variable music (@param voices)
 *      RI: true
 *          Some fields have default settings if not specified in the header
 *          noteLength has a default value of 1/8 or 1/16 depending on meter
 *          meter has a default value of 4/4
 *          tempo has a default value of 100 beats/minute
 *          composer has a default value of Unknown Composer
 *      
 *
 */

public class AbcTune {
    private String title;
    private String composer;
    private String meter;
    private String tempo;
    private String noteLength;
    private int indexNumber;
    private String accidental;
    private Voices music;
    private List<String> keySignature;
    private List<String> voices;
    
    
    /**
     * Creates AbcTune object
     */
    public AbcTune() {
        this.keySignature = new ArrayList<String>();
        this.title = "";
        this.composer = "";
        this.meter = "";
        this.noteLength = "";
        this.tempo = "";
        this.indexNumber = 0;
        this.accidental = "";
        this.voices = new ArrayList<String>();
        
    }
    
    private void checkRep() {
        assert true;
    }
    /**
     * Returns Title of a Song
     * @return title of the song
     */
    public String getTitle() {
        return title;
    }

    
    /**
     * Set title of the song
     * @param title of the song
     */
    public void setTitle(String title) {
        this.title = title;
    }

    
    /**
     * Gets composer of the Song
     * @return string rep of composer of song. Returns default "Unknown Composer" is composer not avaliable
     */
    public String getComposer() {
        if(this.composer.length()==0) {
            return "Unknown Composer";
        }
        return composer;
    }

    
    /**
     * Sets composer of the song
     * @param composer of the song
     */
    public void setComposer(String composer) {
        this.composer = composer;
    }

    
    /**
     * Gets the meter or sum of the durations of all notes within a bar
     * @return meter of the song. Default of 4/4 if not available
     */
    public String getMeter() {
        if(this.meter.length()==0) {
            return "4/4";
        }
        return meter.replaceAll("\\s","");
    }

    /**
     * Sets the meter of the song
     * @param meter of the song
     */
    public void setMeter(String meter) {
        this.meter = meter;
    }

    
    /**
     * Gets the tempo of the song. Default of 100 if not avaliable
     * @return tempo of song
     */
    public String getTempo() {
        
        if(tempo.length()==0) {
            return "100";
        }
        return tempo;
    }

    
    /**
     * Sets the tempo of the song
     * @param tempo of the song
     */
    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    
    /**
     * Gets the note length of the song
     * @return note length
     */
    public String getNoteLength() {
        if(noteLength.length()==0) {
            double getMeter = convertToDouble(this.getMeter());
            if(getMeter<0.75) {
                return "1/16";
            }
            else {
                return "1/8";
            }        
        }
        return noteLength;
    }

    /**
     * Set the note length of the music
     * @param noteLength of the music
     */
    public void setNoteLength(String noteLength) {
        this.noteLength = noteLength;
    }


    /**
     * Get the unique index number of the music/
     * @return index number
     */
    public int getIndexNumber() {
        return indexNumber;
    }
    
    /**
     * Set the unique index number of the music
     * @param indexNumber to set 
     */
    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }
    
    /**
     * Gets the Key Accidental in the music
     * @return key accidental
     */
    public String getAccidental() {
        return accidental;
    }

    /**
     * Set the accidental for the music piece
     * @param accidental in music
     */
    public void setAccidental(String accidental) {
        this.accidental = accidental;
    }

    /**
     * Get Key Signature of the piece
     * @return key signatures
     */
    public List<String> getKeySignature() {
        return keySignature;
    }
    
    /**
     * Set the key signature of the piece
     * @param keySignature of music
     */
    public void setKeySignature(List<String> keySignature) {
        this.keySignature = keySignature;
    }
    
    /**
     * Get list of voices to be played in the music
     * @return voices
     */
    public List<String> getVoices() {
        return voices;
    }
    
    /**
     * Set the list of voices to be played in the music
     * @param voices to set
     */
    public void setVoices(List<String> voices) {
        this.voices = voices;
    }
    
    /**
     * Set the final music with multiple voices
     * @param music to set
     */
    public void setMusic(Voices music) {
        this.music = music;
        
    }
    /**
     * Get the final music
     * @return the music
     */
    public Voices getMusic() {
        return this.music;
    }


    
    /**
     * Converts a string fraction to a double fraction
     * @param ratio
     * @return double representation of string fraction
     */
    private static double convertToDouble(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(ratio);
        }
    }

}
