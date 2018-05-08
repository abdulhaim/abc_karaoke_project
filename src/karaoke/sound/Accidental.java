package karaoke.sound;


public class Accidental implements Music {

    private final double duration;
    private final Pitch pitchChange;
    private final Bar bar; 
    //private final Instrument instr;

    /*
     *  Abstraction Function:
     *      AF(duration, pitchChange, bar) = Transform the pitch of a note to be played one semi-tone higher, one semi-tone lower, 
     *                                       or a natural
     * 
     * Rep Invariant
     *      duration > 0    
     *      
     * Safety From Exposure
     *      All fields are private and final
     *      
     */
    
    /**
     * 
     * @param duration
     * @param pitchChange
     * @param bar
     */
    public Accidental(double duration, Pitch pitchChange, Bar bar) {
        this.duration = duration;
        this.pitchChange = pitchChange;
        this.bar = bar;
        //this.instr = bar.getInstrument();
    
    }
    public double getDuration() {
        return duration;
    }

    public void play(SequencePlayer player, double atBeat) {
        //player.addNote(, pitchChange, atBeat, duration);
        
    }

}
