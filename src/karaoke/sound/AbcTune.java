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


public class AbcTune {
    
    private String title;
    private String composer;
    private String meter;
    private String tempo;
    private String noteLength;
    private int indexNumber;
    private String accidental;
//    private List<Concat> musicLines;
    private Voices music;
    private List<String> keySignature;
    private List<String> voices;
    
    
    public AbcTune() {
        this.keySignature = new ArrayList<String>();
        
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
        if(meter.length()!=0) {
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

    
//    public List<Concat> getMusicLine() {
//        return this.musicLines;
//    }

    
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
    
    private static double convertToDouble(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(ratio);
        }
    }






}
