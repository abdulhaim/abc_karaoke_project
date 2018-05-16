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
        final double offset = 0.125;
        final int ticksPerBeat = 12;
        final int beatsPerMinute = 120;
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);

        musicPiece.play(player, offset,queue);
        player.play();

    }
    
}
