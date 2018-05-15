package karaoke.sound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Main method. Parses and then reprints an example 
     * @param args command line arguments
     * @throws UnableToParseException if cannot parse grammar file
     * @throws InvalidMidiDataException 
     * @throws MidiUnavailableException 
     */
    public static void main(final String[] args) throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        final String piece1 = "X:1 %Comment Testing \n" +
                "T:First Music!" + "\n" + 
                "M:4/4  %Comment Testing\n" + 
                "L:1/4  %Comment Testing\n" + "C: W. Mozart\n" + 
                "Q:1/4=140\n" + 
                "K:C\n" + "C C C3/4 D/4 E | E3/4 D/4 E3/4 F/4 G2 | (3c/2c/2c/2 (3G/2G/2G/2 (3E/2E/2E/2 (3C/2C/2C/2 | G3/4 F/4 E3/4 D/4 C2";

        final List<Concat> musicPiece1 = MusicLanguage.parse(piece1);
        final int beatsPerMinute = 140; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 12; // allows up to 1/64-beat notes to be played with fidelity

        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        for(Concat c: musicPiece1) {
            c.play(player, 0.0);
        }
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
        ABCTUNE, ABCHEADER, FIELDNUMBER, FIELDTITLE, OTHERFIELDS, FIELDCOMPOSER, KEYACCIDENTAL,TEXT,WHITESPACE, MIDDLEOFBODYFIELD,LYRICTEXT,COMMENTTEXT,FIELDDEFAULTLENGTH, FIELDMETER, FIELDTEMPO, FIELDVOICE, FIELDKEY, KEY, KEYNOTE,MODEMINOR,METER, METERFRACTION,ABCBODY, ABCLINE, NOTEELEMENT, NOTE,PITCH,OCTAVE, NOTELENGTH, NOTELENGTHSTRICT, ACCIDENTAL,BASENOTE,RESTELEMENT, TUPLETELEMENT, TUPLETSPEC,CHORD, BARLINE, NTHREPEAT,LYRIC, LYRICALELEMENT, BACKSLASHHYPHEN,COMMENT, ENDOFLINE, DIGIT,NEWLINE,SPACEORTAB
    }
    
    
    private static Parser<MusicGrammar> parser = makeParser();

    /**
     * Parse a string into Music.
     * @param string string to parse
     * @return Music parsed from the string
     * @throws UnableToParseException if the string doesn't match the Music grammar
     */
    public static List<Concat> parse(final String string) throws UnableToParseException {
        final ParseTree<MusicGrammar> parseTree = parser.parse(string);
        makeAbstractSyntaxTree(parseTree);
        return TUNE.getMusicLine();

    }

    
    /**
     * Convert a parse tree into an abstract syntax tree.
     * 
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
                System.out.println("FILED VOICE");
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
                for(int i = 0;i<children.size();i++) {
                    
                    makeAbstractSyntaxTreeMusic(children.get(i));
                    Concat music = new Concat(builder.getMusicLine(),builder.getHashMap(),builder.getLyrics());
                    TUNE.addMusicLine(music);
                }
                return;
                
            }
            case ABCLINE: //abcLine ::= (noteElement | restElement | tupletElement | barline | nthRepeat | spaceOrTab)+ endOfLine (lyric endOfLine)?  | middleOfBodyField | comment;
            {
                builder.setStatus("Bar");
                for(int i = 0; i< children.size(); i++) {
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
                    else if(children.get(i).equals("[1") || children.get(i).equals("[2")) {
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
                        makeAbstractSyntaxTreeMusic(children.get(i));

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
                else if(noteLength.length()==2){
                    duration = convertToDouble("1" + noteLength);
                }
                else {
                    duration = convertToDouble(noteLength);

                }
                String meter = TUNE.getMeter();
                
                if(TUNE.getNoteLength() != null) {
                    duration = duration*convertToDouble(TUNE.getNoteLength())*Double.parseDouble(meter.substring(meter.indexOf("/")+1));

                }
                else {
                    duration = duration*Double.parseDouble(meter.substring(meter.indexOf("/")+1)); // multiply by 1/4 since that's implicit

                }
                if(builder.getStatus().equals("Tuplet")) {
                    duration*=builder.getTupletDuration();
                }
                Note note = new Note(pitch,duration);
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
                else if(durationString.length()==2){
                    duration = convertToDouble("1" + durationString);
                }
                else {
                    duration = convertToDouble(durationString);

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
               List<Concat> musicLines = TUNE.getMusicLine();
               List<String> lyrics = new ArrayList<String>();
               boolean atEnding = false;
               String word = "";
               for(int i =0; i<children.size();i++) {
                   String text = children.get(i).text();
                   if(text.equals(" ")|| text.equals("-")) {
                     if(i==0) {
                         continue;
                     }
                     else if(atEnding == false) {
                         lyrics.add(" ");
                     }
                   }
                   else if(text.equals("_")) {
                       lyrics.add("_");
                   }
                   else if(text.equals("*")) {
                       lyrics.add(" ");
                   }
                   else if(text.equals("~")) {
                       continue;
                   }
                   else if(text.equals("|")) {
                       
                   }
                   else {
                       if(i+1<children.size()-1 && children.get(i+1).text().equals("~")) {
                           word += text;
                       }
                       else if(word.length()>0) {
                           lyrics.add(word);
                       }
                       else {
                           lyrics.add(text);
                       }
                   }
               }
               builder.setLyrics(lyrics);
               
            }
            case BACKSLASHHYPHEN:
            {
                System.out.println("Voices");
                
            }
            case MIDDLEOFBODYFIELD: 
            {
                
            }
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
