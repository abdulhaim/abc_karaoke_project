package karaoke.sound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Voices implements Music{
    
    //fields
    private final Map<String, List<Concat>> voiceToMusic;
    
    
    /**
     * Constructor of Voices with unspecified voice.
     */
    public Voices() {
        String singer = "V";
        Map<String, List<Concat>> map = new HashMap<String, List<Concat>>();
        map.put(singer, Collections.synchronizedList(
                        Collections.unmodifiableList(new ArrayList<Concat>())));
        this.voiceToMusic = Collections.synchronizedMap(
                            Collections.unmodifiableMap(new HashMap<String, List<Concat>>(map)));
    }
    
    /**
     * Constructor of Voices
     * @param singers different voices that are present
     */
    public Voices(List<String> singers) {
        Map<String, List<Concat>> map = new HashMap<String, List<Concat>>();
        for (String singer : singers) {
            map.put(singer, Collections.synchronizedList(
                            Collections.unmodifiableList(new ArrayList<Concat>())));
        }
        
        this.voiceToMusic = Collections.synchronizedMap(
                            Collections.unmodifiableMap(new HashMap<String, List<Concat>>(map)));
    }
    
    /**
     * Second Constructor for Voices
     * @param singers different voices that are present
     * @param listOfMusics music corresponding to each player
     */
    public Voices(List<String> singers, List<List<Concat>> listOfMusics) {
        assert singers.size() == listOfMusics.size();
        Map<String, List<Concat>> dummyMap = new HashMap<>();
        for (int i = 0; i < singers.size(); i++) {
            dummyMap.put(singers.get(i), Collections.synchronizedList(
                                         Collections.unmodifiableList(listOfMusics.get(i))));
        }
        this.voiceToMusic = Collections.synchronizedMap(
                            Collections.unmodifiableMap(dummyMap));
    }
    
    
    /**
     * Add a piece of music to already existing music from the voice of {param singer}
     * @param singer a distinct voice
     * @param concMusic music to be added
     * @return new Voice object incorporating that change.
     */
    public Voices addMusic(String singer, Concat concMusic) {
        System.out.println("singer "+ singer);
        System.out.println("map "+this.voiceToMusic);
        List<Concat> modifiedList = new ArrayList<>(this.voiceToMusic.get(singer));
        modifiedList.add(concMusic);
        //modifiedList.addAll(this.voiceToMusic.get(singer));
        List<Concat> newMusicForSinger = Collections.synchronizedList(
                                         Collections.unmodifiableList(new ArrayList<Concat>(modifiedList)));
        List<String> listOfSingers = new ArrayList<>(voiceToMusic.keySet());
        List<List<Concat>> concats = new ArrayList<>();
        for (String s : listOfSingers) {
            if (s.equals(singer)) {
                concats.add(newMusicForSinger);
            }
            else {
                concats.add(this.voiceToMusic.get(s));
            }
        }
        return new Voices(listOfSingers, concats);
    }
    
    /**
     * OverLoad method for addMusic without a singer
     * @param concMusic music to be added
     * @return new Voice object incorporating that change.
     */
    public Voices addMusic(Concat concMusic) {
        String singer = "V";
        return addMusic(singer, concMusic);
    }

    
    @Override
    public double getDuration() { //duration of first singer
        double duration = 0;
        String key = (new ArrayList<String>(this.voiceToMusic.keySet())).get(0);
        for (Concat concMusic : this.voiceToMusic.get(key)) {
            duration += concMusic.getDuration();
        }
        return duration;
    }

    @Override
    public void play(SequencePlayer player, double atBeat) {
       for (String singer : this.voiceToMusic.keySet()) {
           double offsetDuration = 0;
           for (Concat concMusic : this.voiceToMusic.get(singer)) {
               concMusic.play(player, atBeat+offsetDuration);
               offsetDuration += concMusic.getDuration();
           }
       }
    }
    @Override
    public String toString() {
        String voices = "";
        for(String singer: this.voiceToMusic.keySet()) {
            voices += singer + ": " + this.voiceToMusic.get(singer).toString() + "\n";
        }
        return voices;
    }
        
}
