package karaoke.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * MusicPlayer can play a Music expression on the MIDI synthesizer.
 */
public class SoundPlayback {

    /**
     * Play music.
     * @param music music to play
     * @throws MidiUnavailableException if MIDI device unavailable
     * @throws InvalidMidiDataException if MIDI play fails
     */
    public static void play(Music music) throws MidiUnavailableException, InvalidMidiDataException {
        final SequencePlayer player = new MidiSequencePlayer();
        
        // load the player with a sequence created from music (add a small delay at the beginning)
        final double warmup = 0.125;
        music.play(player, warmup);
        
        // start playing
        player.play();
    }
}
