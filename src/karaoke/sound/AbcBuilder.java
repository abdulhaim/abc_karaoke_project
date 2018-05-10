package karaoke.sound;

import java.util.ArrayList;
import java.util.List;

public class AbcBuilder {
    List<Music> totalMusic = new ArrayList<Music>();

    List<Music> barNotes = new ArrayList<Music>();
    List<Music> tupletNotes = new ArrayList<Music>();
    List<Note> chordNotes = new ArrayList<Note>();
    List<Music> accidentals = new ArrayList<Music>();
    
    List<Music> beginRepeat = new ArrayList<Music>();
    List<Music> firstRepeat = new ArrayList<Music>();
    List<Music> secondRepeat = new ArrayList<Music>();

    int repeatStatus;
    String status;
    
    public AbcBuilder() {
        this.repeatStatus = 0;
        status = "";
    }


    public void addToBar(Music note) {
        barNotes.add(note);

    }
    public void addToChord(Note note) {
        chordNotes.add(note);
    }
    public void addToTuplet(Music m) {
        tupletNotes.add(m);
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public List<Music> getTupletNotes() {
        return tupletNotes;
    }


    public void addTuplet(Tuplet tuplet) {
        this.barNotes.add(tuplet);
        this.tupletNotes = new ArrayList<Music>();

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
        else if(repeatStatus == 1) {
            this.beginRepeat.add(bar);

        }
        else if(repeatStatus ==2) {
            this.firstRepeat.add(bar);
        }
        else if(repeatStatus == 3) {
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


    public void resetTuplet() {
        this.tupletNotes = new ArrayList<Music>();
        
    }


    public void resetChord() {
        this.chordNotes = new ArrayList<Note>();
        
    }


    public void setRepeatStatus(int status) {
        this.repeatStatus = status;
        
    }



}

