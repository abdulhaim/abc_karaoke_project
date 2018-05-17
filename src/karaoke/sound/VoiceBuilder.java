package karaoke.sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoiceBuilder {
    private final List<Bar> majorSection;
    private final String singer;
    private RepeatStatus repeatStatus;
    private final List<Bar> listOfRepeats;
    private final List<Bar> firstRepeat;
    private final List<Bar> secondRepeat;
    private final Map<Integer, List<Integer>> mapForRepeats;
    private boolean simpleRepeat;
    
    /**
     * Constructor of VoiceBuilder
     * @param singer voice associated with object
     */
    public VoiceBuilder(String singer) {
        this.singer = singer;
        this.repeatStatus = RepeatStatus.NO_REPEAT;
        this.majorSection = new ArrayList<>();
        this.listOfRepeats = new ArrayList<>();
        this.mapForRepeats = new HashMap<>();
        this.firstRepeat = new ArrayList<>();
        this.secondRepeat = new ArrayList<>();
        this.simpleRepeat = true;
    }
    
    public boolean getSimpleRepeat() {
        return this.simpleRepeat;
    }
    public void setSimpleRepeat(boolean val) {
        this.simpleRepeat = val;
    }
    /**
     * Handle the case of simple repeat
     */
    public void stageSimpleRepeat() {
        assert this.simpleRepeat;
        assert this.firstRepeat.size() == 0;
        int key = this.majorSection.size();
        List<Integer> value = Arrays.asList(this.majorSection.size() - this.listOfRepeats.size(), 
                                            this.majorSection.size());
        this.mapForRepeats.put(key, value);
        this.listOfRepeats.clear();
        this.setRepeatStatus(RepeatStatus.NO_REPEAT);
        //this.simpleRepeat = true;
    }
    
    /**
     * Handle the case of regular repeat
     */
    public void stageRegularRepeat() {
        assert this.firstRepeat.size() != 0;
        assert this.listOfRepeats.size() != 0;

        int key = this.majorSection.size();
        List<Integer> value = Arrays.asList(this.majorSection.size() - this.firstRepeat.size() - this.listOfRepeats.size(), 
                                            this.majorSection.size() - this.firstRepeat.size());
       
        this.mapForRepeats.put(key, value);
        
        this.listOfRepeats.clear();
        this.firstRepeat.clear();
        this.setRepeatStatus(RepeatStatus.NO_REPEAT);
        this.simpleRepeat = true;
    }
    
    
    /**
     * Change repeat status.
     * @param status status to change to
     */
    public void setRepeatStatus(RepeatStatus status) {
        this.repeatStatus = status;
    }
    
    /**
     * @return the current repeat status.
     */
    public RepeatStatus getRepeatStatus() {
        return this.repeatStatus;
    }
    
    /**
     * Puts the entire major section in the listOfRepeats.
     */
    public void setRepeatsFromMajorSec() {
        listOfRepeats.addAll(new ArrayList<Bar>(majorSection));
    }
    
    /**
     * Add bar to voice's majorSection
     * @param bar bar to add
     */
    public void addBar(Bar bar) {
        if (singer.equals("OneVoice")) {
            System.out.println("Bar " +bar);
        }
        this.majorSection.add(bar);
        if (repeatStatus.equals(RepeatStatus.BEGIN_REPEAT)) {
            this.listOfRepeats.add(bar);
        }
        else if(repeatStatus.equals(RepeatStatus.FIRST_REPEAT)) {
            this.firstRepeat.add(bar);
        }
    }
    
    /**
     * @return singer (or voice) associated with object
     */
    public String getSinger() {
        return this.singer;
    }
    
    /**
     * Makes a concat of the bars in major section and resets majorSection
     * @return concat of the bars in major section
     */
    public Concat endMajorSection() {
        //listOfRepeats = 
       
        Concat result = new Concat(majorSection, mapForRepeats, Arrays.asList(""));
      
        majorSection.clear();
        return result;
    }
    
}
