package karaoke.sound;


import java.util.Map;
import java.util.concurrent.BlockingQueue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * MusicPlayer can play a Music expression on the MIDI synthesizer.
 */
public class SoundPlayback {

    /**
     * Play music.
     * @param beatsPerMinute to play music
     * @param musicPiece object on Type Music to play
     * @param queue to store the lyrics
     * @throws MidiUnavailableException if MIDI device unavailable
     * @throws InvalidMidiDataException if MIDI play fails
     */
    public static void play(Voices musicPiece, Map<String,BlockingQueue<String>> queue, int beatsPerMinute) throws MidiUnavailableException, InvalidMidiDataException {
        final double offset = 0.125;
        final int ticksPerBeat = 12;
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        musicPiece.play(player, offset,queue);
        player.play();

    }
    
}
