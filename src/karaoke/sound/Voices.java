package karaoke.sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Voices {
    
    //fields
    private final Map<String, List<Concat>> voiceToMusic;
    
    public Voices(List<String> singers) {
        Map<String, List<Concat>> map = new HashMap<String, List<Concat>>();
        for (String singer : singers) {
            map.put(singer, Collections.synchronizedList(
                            Collections.unmodifiableList(new ArrayList<Concat>())));
        }
        
        this.voiceToMusic = Collections.synchronizedMap(
                               Collections.unmodifiableMap(new HashMap<String, List<Concat>>(map)));
    }
    
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
    
    public Voices addMusic(String singer, Concat concMusic) {
        List<Concat> modifiedList = Arrays.asList(concMusic);
        modifiedList.addAll(voiceToMusic.get(singer));
        List<Concat> newMusicForSinger = Collections.synchronizedList(
                                         Collections.unmodifiableList(new ArrayList<Concat>(modifiedList)));
        List<String> listOfSingers = new ArrayList<>(voiceToMusic.keySet());
        List<List<Concat>> concats = new ArrayList<>();
        for (String s : listOfSingers) {
            if (s.equals(singer)) {
                concats.add(newMusicForSinger);
            }
            else {
                concats.add(this.voiceToMusic.get(singer));
            }
        }
        return new Voices(listOfSingers, concats);
    }
    
    
    
    
}
