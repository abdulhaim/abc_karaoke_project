package karaoke.sound;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * MusicPlayer can play a Music expression on the MIDI synthesizer.
 */
public class SoundPlayback {

    /**
     * Play music.
     * @param AbcTune object with music to play and music piece's attributes
     * @throws MidiUnavailableException if MIDI device unavailable
     * @throws InvalidMidiDataException if MIDI play fails
     */
    public static void play(Voices musicPiece, BlockingQueue<String> queue) throws MidiUnavailableException, InvalidMidiDataException {
        System.out.println("beginning");
        final double offset = 0.125;
        final int ticksPerBeat = 12;
        final int beatsPerMinute = 120;
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        System.out.println("10");

        System.out.println("inside sound");
        musicPiece.play(player, offset,queue);
        System.out.println("after voice play");
        player.play();

    }
    
}
