package karaoke.sound;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import edu.mit.eecs.parserlib.ParseTree;
import edu.mit.eecs.parserlib.Parser;
import edu.mit.eecs.parserlib.UnableToParseException;

/**
 * Parses a file in ABC format 
 * Specification Author: Myra Ahmad & Marwa Abdulhai
 * Implementation Author: Marwa Abdulhai
 *
 */

public class MusicLanguage {
    private static final AbcTune TUNE = new AbcTune();
    private static AbcBuilder builder = new AbcBuilder();
    private static List<String> voices = new ArrayList<String>();

    /**
     * Main method. Parses and then reprints an example 
     * @param args command line arguments
     * @throws UnableToParseException if cannot parse grammar file
     * @throws InvalidMidiDataException 
     * @throws MidiUnavailableException 
     */
    public static void main(final String[] args) throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {

        

        final AbcTune musicPiece = MusicLanguage.parse("sample-abc/piece1.abc");

        final int beatsPerMinute = 120; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 12; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Voices voice = musicPiece.getMusic();
        voice.play(player, 0.0, queue);
        player.play();
        
        
    }
    /**
     * Compile the grammar into a parser 
     * Tries to read the file and catches exceptions (IO and UnableToParse)
     * @return parser for the grammar
     */
    private static Parser<MusicGrammar> makeParser() {
        try {
            final File grammarFile = new File("src/karaoke/parser/Abc.g");
            return Parser.compile(grammarFile, MusicGrammar.ABCTUNE);
            
        } catch (IOException e) {
            throw new RuntimeException("can't read the grammar file", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException("the grammar has a syntax error", e);
        }

    }
    
    // need an enum for the different variants we have, can be edited later I just made it so that the method above 
    // wouldn't have an error
    private static enum MusicGrammar {
        ABCTUNE, ABCHEADER, FIELDNUMBER, FIELDTITLE, OTHERFIELDS, FIELDCOMPOSER, KEYACCIDENTAL,
        TEXT,WHITESPACE, MIDDLEOFBODYFIELD,LYRICTEXT,COMMENTTEXT,FIELDDEFAULTLENGTH, FIELDMETER, 
        FIELDTEMPO, FIELDVOICE, FIELDKEY, KEY, KEYNOTE,MODEMINOR,METER, METERFRACTION,ABCBODY, 
        ABCLINE, NOTEELEMENT, NOTE,PITCH,OCTAVE, NOTELENGTH, NOTELENGTHSTRICT, ACCIDENTAL,BASENOTE,
        RESTELEMENT, TUPLETELEMENT, TUPLETSPEC,CHORD, BARLINE, NTHREPEAT,LYRIC, LYRICALELEMENT, 
        BACKSLASHHYPHEN,COMMENT, ENDOFLINE, DIGIT,NEWLINE,SPACEORTAB
    }
    
    private static String readFile(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
     
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("File either not readable or does not exist.");
        }
        return contentBuilder.toString();
    }
    
    private static Parser<MusicGrammar> parser = makeParser();

    /**
     * Parse a string into Music.
     * @param path file to parse
     * @return Music parsed from the string
     * @throws UnableToParseException if the string doesn't match the Music grammar
     */
    public static AbcTune parse(final String path) throws UnableToParseException {
        final String musicFile = readFile(path);
        final ParseTree<MusicGrammar> parseTree = parser.parse(musicFile);
        makeAbstractSyntaxTree(parseTree);
        return TUNE;

    }

    
    /**
     * Convert a parse tree into an abstract syntax tree.
     * @param parseTree constructed according to the grammar in Abc.g
     * 
     */
    private static void makeAbstractSyntaxTree(final ParseTree<MusicGrammar> parseTree) {
        final java.util.List<ParseTree<MusicGrammar>> children = parseTree.children();
        switch (parseTree.name()) { //abcTune ::= abcHeader abcBody;
            case ABCTUNE:
            {
                makeAbstractSyntaxTree(children.get(0)); //AbcHeader
                makeAbstractSyntaxTree(children.get(1)); //AbcBody
                return;

            }
            case FIELDNUMBER:  // "X:" digit+ endOfLine;
            {
                int digit = Integer.parseInt(children.get(0).text());
                TUNE.setIndexNumber(digit);    
                return;
                
            }
            case ABCHEADER: //abcHeader ::= fieldNumber comment* fieldTitle otherFields* fieldKey;
            {            
                for(int i = 0; i<children.size();i++) {
                     makeAbstractSyntaxTree(children.get(i));
                }
                return;

            }
            case COMMENTTEXT:
                
                return;
                
            case FIELDTITLE: // fieldTitle ::= "T:" text endOfLine;
            {   
                String title = children.get(0).text();
                TUNE.setTitle(title);    
                return;


            } 
            case OTHERFIELDS: //otherFields ::= fieldComposer | fieldDefaultLength | fieldMeter | fieldTempo | fieldVoice | comment;
            {
                makeAbstractSyntaxTree(children.get(0));
                return;


            }
            case FIELDCOMPOSER: //fieldComposer ::= "C:" text endOfLine;
            {
                String composer = children.get(0).text();
                TUNE.setComposer(composer);
                return;
            }
            
            case FIELDDEFAULTLENGTH: //fieldDefaultLength ::= "L:" noteLengthStrict endOfLine;
                                     //noteLengthStrict ::= digit+ "/" digit+;
            {
                String number = children.get(0).text();
                TUNE.setNoteLength(number);
                return;
            
            }
            case FIELDMETER: // fieldMeter ::= "M:" meter endOfLine;
            {                
                makeAbstractSyntaxTree(children.get(0));
                return;
                
                
            }
            case FIELDTEMPO: // fieldTempo ::= "Q:" meterFraction "=" digit+ endOfLine;
            {
                String tempo = "";
                for(int i = 1;i<children.size()-1;i++) {
                    tempo+=children.get(i);
                }
                TUNE.setTempo(tempo);
                return;
            }
            case FIELDVOICE: // fieldVoice ::= "V:" text endOfLine;
            {
               String name = parseTree.text();
               String singer = name.substring(name.indexOf(":")+1).replaceAll("\\s","");
               voices.add(singer);
               return;
            }
            case FIELDKEY: //   fieldKey ::= "K:" key endOfLine;
            {
                makeAbstractSyntaxTree(children.get(0));
                return;
            }
            case KEY:               
            {
              TUNE.setAccidental(parseTree.text().replaceAll("\\s",""));
              return;
            }

            case METER:
            {
                if(children.toString().indexOf("METERFRACTION")!=-1) {
                    TUNE.setMeter(children.get(0).text());
                }
                else {
                    if(children.toString().indexOf("C|")!=-1) {
                        TUNE.setMeter("2/2");
                    }
                    else if(children.toString().indexOf("C")!=-1) {
                        TUNE.setMeter("4/4");
                    }
                }
                return;
            }
            case ABCBODY: //abcBody ::= abcLine+;
            {
                makeAbstractSyntaxTreeMusic(parseTree);
                return;
            }
                
            default:
                throw new AssertionError("should never get here");
            }
    
    }

    private static void makeAbstractSyntaxTreeMusic(final ParseTree<MusicGrammar> parseTree) {
        final java.util.List<ParseTree<MusicGrammar>> children = parseTree.children();
        switch (parseTree.name()) {
            case ABCBODY: { //abcBody ::= abcLine+;
                Voices voice;
                if(voices.size()==0) {
                    voice = new Voices();
                }
                else {
                    voice = new Voices(voices);
                }
                for(int i = 0;i<children.size();i++) {
                    makeAbstractSyntaxTreeMusic(children.get(i));
                    if(builder.inMusic()) {
                        Concat music = new Concat(builder.getMusicLine(),builder.getHashMap(),builder.getLyrics());
                        if(voices.size()==0) {
                            voice = voice.addMusic(music);
                        }
                        else {
                            voice = voice.addMusic(builder.getSinger(), music);

                        }
                        builder.setInMusic(false);

                        
                    }
                }
                TUNE.setMusic(voice);
                return;
                
            }
            case ABCLINE: //abcLine ::= (noteElement | restElement | tupletElement | barline | nthRepeat | spaceOrTab)+ 
                          //endOfLine (lyric endOfLine)?  | middleOfBodyField | comment;
            {
                builder.setStatus("Bar");
                // if lyrics exist, parse it first. Might want to make code more readable.
                if(children.size() > 2 && children.get(children.size()-2).name().equals(MusicGrammar.LYRIC)) {
                    makeAbstractSyntaxTreeMusic(children.get(children.size()-2));
                }
                else if(children.size()==1 && (children.get(0).name().equals(MusicGrammar.MIDDLEOFBODYFIELD) || children.get(0).name().equals(MusicGrammar.COMMENT))) {
                    makeAbstractSyntaxTreeMusic(children.get(0));
                    return;

                }
                builder.setInMusic(true);

                for(int i = 0; i< children.size(); i++) {
                    //if (children.toString().contains(s))
                    if(children.get(i).name().equals(MusicGrammar.SPACEORTAB)) {
                        continue;
                    }
                    // also how do we parse the last note in the bar if we do this
                    else if(i+1<children.size() && children.get(i+1).text().equals("[1")) { //if at first repeat ending
                        builder.setRepeatStatus(1);
                        builder.resetBar();
                        builder.setRepeatStatus(2);
                    }
                    
                    else if(i+1<children.size() && children.get(i+1).text().equals("[2")) { //if at second repeat ending
                        builder.resetBar(); // maybe assert repeatStatus(2)
                        builder.setRepeatStatus(3);
                    }
                    else if(children.get(i).text().equals("[1") || children.get(i).text().equals("[2")) {
                        continue;

                    }
                    else if((i+1<children.size() && children.get(i).text().equals("|:"))) {
                        if(builder.getBarNotesSize()!=0) {
                            builder.resetBar();
                        }
                        builder.setRepeatStatus(2);
                    }

                    else if(children.get(i).text().equals(":|")) {
                        builder.flagSimpleRepeat(true);
                        builder.setRepeatStatus(3);
                        builder.resetBar();
                        
                        
                    }

                    else if(children.get(i).name().equals(MusicGrammar.BARLINE)) {
                        builder.resetBar();
                    }
                    else if(children.get(i).name().equals(MusicGrammar.ENDOFLINE)) {
                        builder.resetBar();
                    }
                    else if(children.get(i).name().equals(MusicGrammar.LYRIC)){
                        return;
                    }
                    else {
                        makeAbstractSyntaxTreeMusic(children.get(i));

                    }
                    
                }
                return;

            }
            case NOTEELEMENT: //noteElement ::= note | chord;
            {
                makeAbstractSyntaxTreeMusic(children.get(0));
                return;
            }
            case NOTE:  //note ::= pitch noteLength?;
            {
                //calculating pitch
                //pitch ::= accidental? basenote octave?;
                List<ParseTree<MusicGrammar>> pitchList = children.get(0).children();
                Character pitchChar = null; // do we really need null here.
                Pitch pitch = null;

                if(pitchList.size()==1) {
                    pitchChar = pitchList.get(0).text().charAt(0); // assert length of text() == 1
                    

                    pitch = builder.applyKeyAccidental(Character.toUpperCase(pitchChar),TUNE.getAccidental()); // why uppercase

                }
                else if(pitchList.size()==2) {
                    //found accidental
                    if(pitchList.get(0).name().equals(MusicGrammar.ACCIDENTAL)) {
                        pitchChar = pitchList.get(1).text().charAt(0); 
                        String accidentalType = pitchList.get(0).text();
                        builder.addAccidental(Character.toUpperCase(pitchChar),accidentalType);
                        
                       pitch = new Pitch(Character.toUpperCase(pitchChar));
                       
                       // what if accidental of type "^^", "__", "="
                        if(accidentalType.indexOf("^")!=-1) {
                            for(int i = 0; i<accidentalType.length();i++) {
                                pitch = pitch.transpose(1);
                            }
                        }
                        else if(accidentalType.indexOf("_")!=-1) {
                            for(int i = 0; i<accidentalType.length();i++) {
                                pitch = pitch.transpose(-1);
                            }
                        }
                    }
                    //found octave
                    else {
                        pitchChar = pitchList.get(0).text().charAt(0);
                        String octaveType = pitchList.get(1).text();
                        
                        pitch = builder.applyKeyAccidental(Character.toUpperCase(pitchChar),TUNE.getAccidental());
                        // what if multiple ' or ,
                        if(octaveType.indexOf("'")!=-1) {
                            for(int i = 0; i<octaveType.length();i++) {
                                pitch = pitch.transpose(Pitch.OCTAVE);
                            }
                        }
                        else if(octaveType.indexOf(",")!=-1) {
                            for(int i = 0; i<octaveType.length();i++) {
                                pitch = pitch.transpose(-Pitch.OCTAVE);
                            }
                        }                        
                    }
                }
                
                // can use an else statement here and an assert in the beginning to assert
                // pitchList.size() is either 1, 2 or 3
                else if(pitchList.size() == 3) {
                    String accidentalType = pitchList.get(0).text();
                    pitchChar = pitchList.get(1).text().charAt(0); 
                    String octaveType = pitchList.get(2).text();
                    
                    pitch = new Pitch(Character.toUpperCase(pitchChar));

                    if(accidentalType.indexOf("^")!=-1) {
                        for(int i = 0; i<accidentalType.length();i++) {
                            pitch = pitch.transpose(1);
                        }
                    }
                    else if(accidentalType.indexOf("_")!=-1) {
                        for(int i = 0; i<accidentalType.length();i++) {
                            pitch = pitch.transpose(-1);
                        }
                    }

                    if(octaveType.indexOf("'")!=-1) {
                        for(int i = 0; i<octaveType.length();i++) {
                            pitch = pitch.transpose(Pitch.OCTAVE);
                        }
                    }
                    else if(octaveType.indexOf(",")!=-1) {
                        for(int i = 0; i<octaveType.length();i++) {
                            pitch = pitch.transpose(-Pitch.OCTAVE);
                        }
                    }                        

                }
                if(Character.isLowerCase(pitchChar)) {

                    pitch = pitch.transpose(Pitch.OCTAVE);

                }
                // maybe start a new method here
                String noteLength = children.get(1).text(); // notelength might not be present
                double duration;
                if(noteLength.length()==0) {
                    duration = 1;
                }
                else if(noteLength.equals("/")) {
                    duration = 1.0/2;
                }
                else if(noteLength.length()==2) {
                    if(noteLength.substring(1).equals("/")) {
                        duration = convertToDouble(noteLength + "1");
                    } 
                    else {
                        duration = convertToDouble("1" + noteLength);
                    }
                }
                else {
                    duration = convertToDouble(noteLength);
                    //System.out.println(children.get(0).text() + " " + duration);

                }
               
                String meter = TUNE.getMeter();
                
                if(!TUNE.getNoteLength().isEmpty()) {
                    duration = duration*convertToDouble(TUNE.getNoteLength())*Double.parseDouble(meter.substring(meter.indexOf("/")+1));

                }
                else {
                    duration = duration*Double.parseDouble(meter.substring(meter.indexOf("/")+1)); // multiply by 1/4 since that's implicit

                }
                System.out.println("Durations "+ children.get(0).text() + " " + duration);
                if(builder.getStatus().equals("Tuplet")) {
                    duration*=builder.getTupletDuration();
                }
                
                Note note = new Note(pitch,duration,builder.getLyricOnCount()); //made change here
                if(builder.getStatus().equals("Bar")) {
                    builder.addToBar(note);
                    
                }
                if(builder.getStatus().equals("Chord")) {
                    builder.addToChord(note);

                }
                if(builder.getStatus().equals("Tuplet")) {
                    builder.addToTuplet(note);

                }
                return;
            }

            case RESTELEMENT: // similar calculation like above for rest might be necessary here too
            {
                String durationString = children.get(0).text();
                double duration;
                if(durationString.length()==0) {
                    duration = 1;
                }
                else if(durationString.equals("/")) {
                    duration = 1.0/2;
                }
                else if(durationString.length()==2) {
                    if(durationString.substring(1).equals("/")) {
                        duration = convertToDouble(durationString + "1");
                    } 
                    else {
                        duration = convertToDouble("1" + durationString);
                    }
                }
                else {
                    duration = convertToDouble(durationString);

                }
                
                String meter = TUNE.getMeter();
                
                if(!TUNE.getNoteLength().isEmpty()) {
                    duration = duration*convertToDouble(TUNE.getNoteLength())*Double.parseDouble(meter.substring(meter.indexOf("/")+1));

                }
                else {
                    duration = duration*Double.parseDouble(meter.substring(meter.indexOf("/")+1)); // multiply by 1/4 since that's implicit

                }

                
                builder.addToBar(new Rest(duration));
                return;
                
            }
            case TUPLETELEMENT:  // tupletElement ::= tupletSpec noteElement+;
                                 // tupletSpec ::= "(" digit;
            {
                String prevStatus = builder.getStatus(); // why all this
                builder.setStatus("Tuplet");
                String durationString = children.get(0).text().substring(1);
                double duration = 0;
                if(durationString.equals("3")) {
                    duration = 2.0/3;
                }
                else if(durationString.equals("2")) {
                    duration = 3.0/2;

                }
                else if(durationString.equals("4")) {
                    duration = 3.0/4;
                }
                duration = (double) Math.round(duration * 100) / 100;
                builder.setTupletDuration(duration);
                for(int i =1; i<children.size(); i++) {
                    makeAbstractSyntaxTreeMusic(children.get(i));
                }
                List<Music> tupletNotes = builder.getTupletNotes();

                Tuplet tuplet = new Tuplet(tupletNotes,Double.parseDouble(durationString));

                builder.setStatus(prevStatus);
                if(builder.getStatus().equals("Bar")) {
                    builder.addToBar(tuplet);
                    builder.resetTuplet();
                    
                }

                return;
            }
            case CHORD: //chord ::= "[" note+ "]"
            {
                String prevStatus = builder.getStatus();
                builder.setStatus("Chord");
                for(int i =0; i<children.size();i++) {
                    makeAbstractSyntaxTreeMusic(children.get(i));
                }
                List<Note> chordNotes = builder.getChordNotes();
                Chord chord = new Chord(chordNotes);
                builder.setStatus(prevStatus);
                if(builder.getStatus().equals("Bar")) {
                    builder.addToBar(chord);
                    builder.resetChord();

                    
                }
                if(builder.getStatus().equals("Tuplet")) {
                    builder.addToTuplet(chord);
                    builder.resetChord();


                }
                return;
                
            }
            case LYRIC: //lyricalElement ::= " "+ | "-" | "_" | "*" | "~" | backslashHyphen | "|" | lyricText;
            {
               //List<Concat> musicLines = TUNE.getMusicLine();
               List<String> lyrics = new ArrayList<String>();
               //boolean atEnding = false;
               boolean waitForNext = false;
               String word = "";
               
               for(int i =0; i<children.size();i++) {
                   String text = children.get(i).text();
                   
                   if (text.startsWith(" ")) { // issue with multiple spaces
                       if (i ==0) {continue;}
                       else {lyrics.add(" ");}
                   }
                   else if (text.equals("-")) {
                       continue;
                   }
                   else if (text.equals("_")) {
                       lyrics.add("_");
                   }
                   else if(text.equals("*")) {
                       lyrics.add(""); // -1 represents a blank syllable
                   }
                   else if(text.equals("~")) {
                       continue;
                  }
                   else if(text.equals("|")) {
                       // pass
                   }
                   else {
                       if(i+1<children.size()-1 && children.get(i+1).text().equals("~")) {
                           word += text + " ";
                           waitForNext = true;
                       }
                       else if(waitForNext && (i+1>=children.size()-1 || !children.get(i+1).text().equals("~")) ) {
                           word += text;
                           lyrics.add(word);
                           word = "";
                           waitForNext = false;
                       }
                       else {
                           lyrics.add(text);
                       }
                   }
               }
                   
               
               List<String> lyrics2 = new ArrayList<String>();
               for (int i = 0; i< lyrics.size(); i++) {
                   if (lyrics.get(i).equals(" ")) {
                       if (i - 1 >=0 && lyrics.get(i-1).equals(" ")) {
                           //skip
                       }
                       else {
                           lyrics2.add(lyrics.get(i));
                       }
                   }
                   else {
                       lyrics2.add(lyrics.get(i));
                   }
               }
//               System.out.println("The Lyrics are here " + lyrics2);
               builder.setLyrics(lyrics2);
               return;
               
            }

            case MIDDLEOFBODYFIELD: 
                
            {

                String name = parseTree.text();
                String singer = name.substring(name.indexOf(":")+1).replaceAll("\\s","");
                builder.setSinger(singer);
                return;
            }
            case COMMENT:
                return;
        default:
            break;

        }
        
    }

    private static double convertToDouble(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(ratio);
        }
    }
    
}
