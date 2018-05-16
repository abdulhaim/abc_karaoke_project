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
    public static void play(AbcTune musicPiece, BlockingQueue queue) throws MidiUnavailableException, InvalidMidiDataException {
        final double offset = 0.125;
        final int beatsPerMinute = Integer.parseInt(musicPiece.getTempo()); 
        final int ticksPerBeat = 12;
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        Voices voice = musicPiece.getMusic();
        voice.play(player, offset,queue);
        player.play();

    }
    
}
