package karaoke.sound;

import java.util.concurrent.BlockingQueue;

/**
 * Rest represents a pause in a piece of music.
 */
public class Rest implements Music {

    // fields
    private final double duration;

    /*
     * AF(duration): Music that represents a rest of duration {@param duration}
     * 
     * Rep Invariant:
     * - duration >= 0
     * 
     * Safety Argument: the only field is duration (a primitive type) which is made a final. So this type is immutable
     * 
     * Thread-Safety Argument: This is an immutable type with only primitive fields. So different threads can only observe
     *                         features of this ADT and not mutate it. Thus, this ADT is thread-safe.
     */

    /**
     * Make a Rest that lasts for duration beats.
     * @param duration duration in beats, must be >= 0
     */
    public Rest(double duration) {
        this.duration = duration;
        checkRep();
    }

    private void checkRep() {
        assert duration >= 0;
    }
    
    /**
     * @return duration of this rest
     */
    @Override
    public double getDuration() {
        return duration;
    }

    /**
     * Play this rest.
     */
    @Override
    public void play(SequencePlayer player, double atBeat,  BlockingQueue<String> queue) {
        return;
    }

    @Override
    public int hashCode() {
        long durationBits = Double.doubleToLongBits(duration);
        return (int) (durationBits ^ (durationBits >>> Integer.SIZE));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Rest other = (Rest) obj;
        return duration == other.duration;
    }

    @Override
    public String toString() {
        return "." + duration;
    }

}
