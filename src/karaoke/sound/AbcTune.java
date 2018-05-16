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
/*AF (title, composer, meter, tempo, noteLength, indexNumber, accidental, music, keySignature, voices) = 
 *      represents the contents of a music file with a title (@param title) and a composer (@param composer)
 *      with a specified meter (@param meter), tempo (@param tempo), note length (@param noteLength), and with a track number on its album if applicable (@param indexNumber).
 *      The music file may also contain an accidental (@param accidental) different voices represented by Voices object (@param music), a key signature (@param keySignature)
 *      and a list of the voices as specified in the header which is used to creates the variable music (@param voices)
 *RI: true
 * Some fields have default settings if not specified in the header
 *      noteLength has a default value of 1/8 or 1/16 depending on meter
 *      meter has a default value of 4/4
 *      tempo has a default value of 100 beats/minute
 *      composer has a default value of Unknown Composer
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
    public String getTitle() {
        return title;
    }

    
    public void setTitle(String title) {
        this.title = title;
    }

    
    public String getComposer() {
        if(this.composer.length()==0) {
            return "Unknown Composer";
        }
        return composer;
    }

    
    public void setComposer(String composer) {
        this.composer = composer;
    }

    
    public String getMeter() {
        if(this.meter.length()==0) {
            return "4/4";
        }
        return meter;
    }

    
    public void setMeter(String meter) {
        this.meter = meter;
    }

    
    public String getTempo() {
        
        if(tempo.length()==0) {
            return "100";
        }
        return tempo;
    }

    
    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    
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

    
    public void setNoteLength(String noteLength) {
        this.noteLength = noteLength;
    }

       
    public int getIndexNumber() {
        return indexNumber;
    }
    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }
    public String getAccidental() {
        return accidental;
    }

    public void setAccidental(String accidental) {
        this.accidental = accidental;
    }

    public List<String> getKeySignature() {
        return keySignature;
    }
    
    public void setKeySignature(List<String> keySignature) {
        this.keySignature = keySignature;
    }
    
    public List<String> getVoices() {
        return voices;
    }
    
    public void setVoices(List<String> voices) {
        this.voices = voices;
    }
    public void setMusic(Voices music) {
        this.music = music;
        
    }
    public Voices getMusic() {
        return this.music;
    }
    
    public Music getCompleteMusic() {
        return this.music;
    }

    
    private static double convertToDouble(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(ratio);
        }
    }

}
