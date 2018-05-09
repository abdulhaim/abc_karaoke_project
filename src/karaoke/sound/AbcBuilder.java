package karaoke.sound;

import java.util.ArrayList;
import java.util.List;

public class AbcBuilder {
    List<Music> barNotes = new ArrayList<Music>();
    List<Music> repeatNotes = new ArrayList<Music>();
    List<Note> tupletNotes = new ArrayList<Note>();
    List<Note> chordNotes = new ArrayList<Note>();
    List<Music> totalMusic = new ArrayList<Music>();
    List<Music> accidentals = new ArrayList<Music>();
    boolean inRepeat;
    String status;
    
    public AbcBuilder() {
        this.inRepeat = false;
        status = "";
    }


    public void addNote(Music note) {
        if(status.equals("Bar")) {
            barNotes.add(note);
            
        }
        if(status.equals("Tuplet")) {
            
        }
        if(status.equals("Chord")) {
            chordNotes.add((Note) note);
            
        }
        if(status.equals("Repeat2")) {
            repeatNotes.add(note);
            
        }
        
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public List<Note> getTupletNotes() {
        return tupletNotes;
    }


    public void addTuplet(Tuplet tuplet) {
        this.totalMusic.add(tuplet);
        this.tupletNotes = new ArrayList<Note>();

    }


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
        }
        
        Bar bar = new Bar(newBar);
        this.totalMusic.add(bar);
        this.barNotes = new ArrayList<Music>();
        
    }


    public void transferFromBar() {
        for(Music m: this.barNotes) {
            this.repeatNotes.add(m);
        }
        this.barNotes = new ArrayList<Music>();
        
    }


    public String getStatus() {
        return this.status;
    }
    public String toString() {
        String total = "";
        for(Music music: this.totalMusic) {
            total+=music.toString();
            
        }
        return total;
    }


    public List<Music> getTotalMusic() {
        return this.totalMusic;
    }


    public List<Note> getChordNotes() {
        return this.chordNotes;
    }


    public void addChord(Chord chord) {
        this.totalMusic.add(chord);
        this.chordNotes = new ArrayList<Note>();
        
    }


    public void addAccidental(Character pitchString) {
        // TODO Auto-generated method stub
        
    }


}

