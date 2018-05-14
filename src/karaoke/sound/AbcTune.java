package karaoke.sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbcTune {
    
    private String title;
    private String composer;
    private String meter;
    private String tempo;
    private String noteLength;
    private int indexNumber;
    private String accidental;
    private boolean minor;
    private List<Concat> musicLines;
    private List<String> keySignature;
    private List<String> voices;
    
    
    public AbcTune() {
        this.musicLines = new ArrayList<>();
        this.keySignature = new ArrayList<String>();
        
    }
    public String getTitle() {
        return title;
    }

    
    public void setTitle(String title) {
        this.title = title;
    }

    
    public String getComposer() {
        return composer;
    }

    
    public void setComposer(String composer) {
        this.composer = composer;
    }

    
    public String getMeter() {
        return meter;
    }

    
    public void setMeter(String meter) {
        this.meter = meter;
    }

    
    public String getTempo() {
        return tempo;
    }

    
    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    
    public String getNoteLength() {
        return noteLength;
    }

    
    public void setNoteLength(String noteLength) {
        this.noteLength = noteLength;
    }

    

    
    public List<Concat> getMusicLine() {
        return this.musicLines;
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

    public boolean isMinor() {
        return minor;
    }

    public void setMinor(boolean minor) {
        this.minor = minor;
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
    public void addMusicLine(Concat music) {
        this.musicLines.add(music);
        
    }





}
