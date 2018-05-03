package karaoke.sound;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

public class SequencePlayerTest {

    /**
     * Warm Up Partitions:
     *  addNote()
     *      pitch contains triplets,  chords, accidentals, and rests. 
     *      numBeats takes on any positive double value
     *      startBeat takes on any positive double value
     *      
     */     
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /**
     * Play Piece 1
     * @category no_didit
     * @throws MidiUnavailableException
     * @throws InvalidMidiDataException
     */
    
    @Test
    public void testPlayPiece1() throws MidiUnavailableException, InvalidMidiDataException {
        Instrument piano = Instrument.PIANO;

        // create a new player
        final int beatsPerMinute = 140; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 64; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        
        // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
        // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.
        
        int startBeat = 0;
        //C C C3/4 D/4 E | E3/4 D/4 E3/4 F/4 G2 | (3DDD (3BBB (3EEE (3CCC | G3/4 F1/4 E3/4 1/4D C2 
        player.addNote(piano, new Pitch('C'), startBeat, 1.0);
        player.addNote(piano, new Pitch('C'), startBeat +=1.0, 1.0);
        player.addNote(piano, new Pitch('C'), startBeat +=1.0, 3.0/4);
        player.addNote(piano, new Pitch('D'), startBeat +=3.0/4, 1.0/4);
        player.addNote(piano, new Pitch('E'), startBeat +=1.0/4, 1.0);
        player.addNote(piano, new Pitch('E'), startBeat +=1.0, 3.0/4);
        player.addNote(piano, new Pitch('D'), startBeat+=3.0/4, 1.0/4);
        player.addNote(piano, new Pitch('E'), startBeat+=1.0/4, 3.0/4);
        player.addNote(piano, new Pitch('F'), startBeat+=3.0/4, 1.0/4);
        player.addNote(piano, new Pitch('G'), startBeat+=1.0/4, 2.0);
        
        player.addNote(piano, new Pitch('D'), startBeat +=2.0, 1.0/3);
        player.addNote(piano, new Pitch('D'), startBeat +=1.0/3, 1.0/3);
        player.addNote(piano, new Pitch('D'), startBeat +=1.0/3, 1.0/3);
        
        player.addNote(piano, new Pitch('B'), startBeat +=1.0/3, 1.0/3);
        player.addNote(piano, new Pitch('B'), startBeat +=1.0/3, 1.0/3);
        player.addNote(piano, new Pitch('B'), startBeat +=1.0/3, 1.0/3);
        
        player.addNote(piano, new Pitch('E'), startBeat +=1.0/3, 1.0/3);
        player.addNote(piano, new Pitch('E'), startBeat +=1.0/3, 1.0/3);
        player.addNote(piano, new Pitch('E'), startBeat +=1.0/3, 1.0/3);
        
        player.addNote(piano, new Pitch('C'), startBeat +=1.0/3, 1.0/3);
        player.addNote(piano, new Pitch('C'), startBeat +=1.0/3, 1.0/3);
        player.addNote(piano, new Pitch('C'), startBeat +=1.0/3, 1.0/3);
        
        player.addNote(piano, new Pitch('G'), startBeat +=1.0/3, 3.0/4);
        player.addNote(piano, new Pitch('F'), startBeat +=3.0/4, 1.0/4);
        player.addNote(piano, new Pitch('E'), startBeat +=1.0/4, 3.0/4);
        player.addNote(piano, new Pitch('D'), startBeat +=3.0/4, 1.0/4);
        player.addNote(piano, new Pitch('C'), startBeat +=1.0/4, 2.0);

        
        
        // add a listener at the end of the piece to tell main thread when it's done
        Object lock = new Object();
        player.addEvent(startBeat, (Double beat) -> {
            synchronized (lock) {
                lock.notify();
            }
        });
        
        // print the configured player
        System.out.println(player);

        // play!
        player.play();
        

        // wait until player is done
        // (not strictly needed here, but useful for JUnit tests)
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("done playing");
    }
    
    /**
     * Play Piece 2
     * @category no_didit
     * @throws MidiUnavailableException
     * @throws InvalidMidiDataException
     */
    @Test
    public void testPlayPiece2() throws MidiUnavailableException, InvalidMidiDataException {
        Instrument piano = Instrument.PIANO;

        // create a new player
        final int beatsPerMinute = 200; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 96; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        
        // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
        // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.
        
        int startBeat = 0;
        //[e/2 ^F/2] [e/2 F/2] z/2 [e/2 ^F/2] z/2 [c/2 F/2] [e F] |[g B G] z G z | c3/2 G/2 z E | E/2 A B  _B/2 A | (3GeG a f/2 g/2 |z/2 e c/2 d/2 B3/4        

        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, 1.0/2);
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, 1.0/2);
        
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat+=1.0/2, 1.0/2);
        player.addNote(piano, new Pitch('F'), startBeat, 1.0/2);
        
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat+=(1.0/2 +1.0/2), 1.0/2); //added rest 
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, 1.0/2);
        
        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat+=(1.0/2 + 1.0/2), 1.0/2); //added rest
        player.addNote(piano, new Pitch('F'), startBeat, 1.0/2);
        
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat+=(1.0/2), 1);
        player.addNote(piano, new Pitch('F'), startBeat, 1.0);
        
        player.addNote(piano, new Pitch('G').transpose(Pitch.OCTAVE), startBeat+=1.0, 1);
        player.addNote(piano, new Pitch('B'), startBeat, 1);
        player.addNote(piano, new Pitch('G'), startBeat, 1);
        
        player.addNote(piano, new Pitch('G'), startBeat+=(1.0 + 1.0), 1); //added rest
        
        player.addNote(piano, new Pitch('C'), startBeat+=(1.0 + 1.0), 3.0/2); //added rest
        player.addNote(piano, new Pitch('G'), startBeat+=3.0/2, 1.0/2); 
        player.addNote(piano, new Pitch('E'), startBeat+=(1.0+1.0/2), 1.0); //added rest

        player.addNote(piano, new Pitch('E'), startBeat+=1.0, 1.0/2); 
        player.addNote(piano, new Pitch('A'), startBeat+=1.0/2, 1.0); 
        player.addNote(piano, new Pitch('B'), startBeat+=1.0, 1.0); 
        
        player.addNote(piano, new Pitch('B').transpose(-1), startBeat+=1.0, 1.0/2); // a B flat
        player.addNote(piano, new Pitch('A'), startBeat+=1.0/2, 1.0);
        player.addNote(piano, new Pitch('G'), startBeat +=1.0, 1.0/3);
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat +=1.0/3, 1.0/3);
        player.addNote(piano, new Pitch('G'), startBeat +=1.0/3, 1.0/3);
        player.addNote(piano, new Pitch('A').transpose(Pitch.OCTAVE), startBeat+=1.0/3, 1.0);
        player.addNote(piano, new Pitch('F').transpose(Pitch.OCTAVE), startBeat+=1.0, 1.0/2);
        player.addNote(piano, new Pitch('G').transpose(Pitch.OCTAVE), startBeat+=1.0/2, 1.0/2);

        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat+=(1.0/2 + 1.0/2), 1.0); //added a rest

        player.addNote(piano, new Pitch('C').transpose(Pitch.OCTAVE), startBeat+=1.0, 1.0/2);
        player.addNote(piano, new Pitch('D').transpose(Pitch.OCTAVE), startBeat+=1.0/2, 1.0/2);
        player.addNote(piano, new Pitch('B'), startBeat+=1.0/2, 3.0/4);


       
        // add a listener at the end of the piece to tell main thread when it's done
        Object lock = new Object();
        player.addEvent(startBeat, (Double beat) -> {
            synchronized (lock) {
                lock.notify();
            }
        });
        
        // print the configured player
        System.out.println(player);

        // play!
        player.play();
        
        // wait until player is done
        // (not strictly needed here, but useful for JUnit tests)
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("done playing");
    }
    /**
     * Play Piece 3
     * @category no_didit
     * @throws MidiUnavailableException
     * @throws InvalidMidiDataException
     */
    @Test
    public void testPlayPiece3() throws MidiUnavailableException, InvalidMidiDataException {
        Instrument piano = Instrument.PIANO;
        List<String> words = Arrays.asList("A -", " ma - zing_ "," grace! How "," sweet the "," sound That" ," saved a "," wretch like ","me.");
        // create a new player
        final int beatsPerMinute = 100; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 512; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        
        // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
        // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.
        //z4 D2 | G4 B G | B4 A2 | G4 E2 | D4 D2 | G4 B G | B4 A2 | d6
        //w: A - | ma - zing_ | grace! How | sweet the | sound ThSat | saved a_ | wretch like | me.
        int startBeat = 0;
        
        player.addEvent(startBeat, (Double beat) -> { System.out.print("A - "); });
        player.addNote(piano, new Pitch('D'), startBeat+=4, 2);

        player.addEvent(startBeat, (Double beat) -> { System.out.print("ma - zing "); });
        player.addNote(piano, new Pitch('G'), startBeat +=2, 4);
        player.addNote(piano, new Pitch('B'), startBeat +=4, 1);
        player.addNote(piano, new Pitch('G'), startBeat +=1, 1);

        player.addEvent(startBeat, (Double beat) -> { System.out.print("grace! How "); });
        player.addNote(piano, new Pitch('B'), startBeat +=1, 4);
        player.addNote(piano, new Pitch('A'), startBeat+=4, 2);

        
        player.addEvent(startBeat, (Double beat) -> { System.out.print("sweet the "); });
        player.addNote(piano, new Pitch('G'), startBeat +=2, 4);
        player.addNote(piano, new Pitch('E'), startBeat +=4, 2);

        player.addEvent(startBeat, (Double beat) -> { System.out.print("sound That "); });
        player.addNote(piano, new Pitch('D'), startBeat +=2, 4);
        player.addNote(piano, new Pitch('D'), startBeat +=4, 2);

        player.addEvent(startBeat, (Double beat) -> { System.out.print("saved a "); });
        player.addNote(piano, new Pitch('G'), startBeat +=2, 4);
        player.addNote(piano, new Pitch('B'), startBeat +=4, 1);
        player.addNote(piano, new Pitch('G'), startBeat +=1, 1);

        player.addEvent(startBeat, (Double beat) -> { System.out.print("wretch like "); });
        player.addNote(piano, new Pitch('B'), startBeat +=1, 4);
        player.addNote(piano, new Pitch('A'), startBeat +=4, 2);

        player.addEvent(startBeat, (Double beat) -> { System.out.print("me."); });
        player.addNote(piano, new Pitch('D').transpose(Pitch.OCTAVE), startBeat+=2, 6);

    
        // add a listener at the end of the piece to tell main thread when it's done
        Object lock = new Object();
        int count = 0;
        player.addEvent(startBeat, (Double beat) -> {
            synchronized (lock) {
                lock.notify();

            }
        });
        
        
        // print the configured player

        // play!
        player.play();
        
        // wait until player is done
        // (not strictly needed here, but useful for JUnit tests)
        synchronized (lock) {
            try {
                lock.wait();
                count+=1;
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("done playing");
    }

    
    
}
