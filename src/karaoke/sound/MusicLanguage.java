package karaoke.sound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     */
    public static void main(final String[] args) throws UnableToParseException {
        final String piece1 = "X:1 %Comment Testing \n" +
                "T:First" + "\n" + 
                "M:4/4\n" + 
                "L:1/4\n" + "C: W. Mozart\n" + 
                "Q:1/4=140\n" + 
                "K:Cm\n" + 
                "C C C3/4 D/4 E | E3/4 D/4 E3/4 F/4 G2 | (3ccc (3GGG (3EEE (3CCC | G3/4 F/4 E3/4 D/4 C2\n";
        final String paddy = "X:1\r\n" + 
                "T:Paddy O'Rafferty\r\n" + 
                "C:Trad.\r\n" + 
                "M:6/8\r\n" + 
                "Q:1/8=200\r\n" + 
                "K:D\r\n" + 
                "dff cee|def gfe|dff cee|dfe dBA|\r\n" + 
                "dff cee|def gfe|faf gfe|[1 dfe dBA:|[2 dfe dcB|]\r\n" + 
                "A3 B3|gfe fdB|AFA B2c|dfe dcB|\r\n" + 
                "A3 B3|efe efg|faf gfe|[1 dfe dcB:|[2 dfe dBA|]\r\n" + 
                "fAA eAA| def gfe|fAA eAA|dfe dBA|\r\n" + 
                "fAA eAA| def gfe|faf gfe|dfe dBA:|\r\n";
        final String piece2 = "X:1\r\n" + 
                "T:Little Night Music Mvt. 1\r\n" + 
                "C:Wolfgang Amadeus Mozart\r\n" + 
                "Q:1/4=140\r\n" + 
                "M:4/4\r\n" + 
                "L:1/8\r\n" + 
                "K:G\r\n" + 
                "[D2B2g2]z d g2z d | g d g b d'2 z2 | c'2z a c'2z a | c' a f a d2 z2 |\r\n" + 
                "[DBg]z g3 b a g | g f f3 a c' f | a g g3 b a g | g f f3 a c' f |\r\n" + 
                "g g f e1/2f/2 g g a g/a/ | b b c' b/c'/ d'2 z2 | d4 e4 | c2 c2 B2 B2 |\r\n" + 
                "A2 A2 G F E F | G z A z B z z2  | d4 e4 | dccc cBBB | BAAA GFEF | \r\n" + 
                "[G4G,4] [GG,] G1/3F1/3G1/3 AF | B4 B B/3A/3B/3 c A | d4 e2 f2 |\r\n" + 
                "g2 a2 b2 ^c'2 | d'3 a ^c'3/2 a/ c'3/2 a/ | d'3 a ^c'3/2 a/ c'3/2 a/ | \r\n" + 
                "d' [d'2f2] [d'2f2] [d'2f2] [d'f] | d' [d'2e2] [d'2e2] [d'2e2] [d'e] | \r\n" + 
                "[^c'e] a d' a c' a d' a | ^c' A A A A2 z2 | \r\n" + 
                "a3 g/3f/3e/3 d z b z | g z e z a z z2 | f3 e/3d/3^c/3 B z g z | f4 e2 z2 |\r\n" + 
                "z aaa aaaa | aaaa aab^c' | ^c'd' z b b a z ^c | d2 z a d'^c'ba | \r\n" + 
                "b a z a a a a a | b a z a d' ^c' b a |\r\n" + 
                "b a z a a a a a | b a z2 [b3B3] a/3g/3f/3 | g2 z2 [a3A3] g/3f/3e/3 |\r\n" + 
                "f2 z2 b ^c'/d'/ c' b | b a f a a g f e | d2 z a d' ^c' b a | b a z a a a a a |\r\n" + 
                "b a z a d' ^c' b a | b a z a a a a a | b a z2 [b3B3] a/3g/3f/3 |\r\n" + 
                "g2 z2 [a3A3] g/3f/3e/3 | f2 z2 b ^c'/d'/ c' b | b a f a a g f e |\r\n" + 
                "d A B ^c d d e d/e/ | \r\n" + 
                "f ^c d e f f g f/g/ | a a ^a ^g/a/ b2 z2 | B3 e d ^c B A | d z f z d z z2 |\r\n";
        
        final String easyRepeat = "X:1 %Comment Testing \n" +
                "T:First" + "\n" + 
                "M:4/4\n" + 
                "K:Cm\n" + "|: C D E F | G A B c :|";
//        final Music musicPiece1 = MusicLanguage.parse(piece1);
//        final Music musicPiece2 = MusicLanguage.parse(piece2);
//        final Music musicPaddy = MusicLanguage.parse(paddy);
        final Music musicEasyRepeat = MusicLanguage.parse(easyRepeat);

//        System.out.println(musicPiece1);
//        System.out.println(musicPiece2);
//        System.out.println(musicPaddy);
        System.out.println(musicEasyRepeat);

        
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
    public static Music parse(final String string) throws UnableToParseException {
        final ParseTree<MusicGrammar> parseTree = parser.parse(string);
        System.out.println(parseTree);
        // make an AST from the parse tree
        makeAbstractSyntaxTree(parseTree);
        return new Concat(builder.getTotalMusic());

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
            case FIELDTITLE: // fieldTitle ::= "T:" text endOfLine;
            {   
                String title = children.get(0).text();
                TUNE.setTitle(title);    
                return;


            } 
            case OTHERFIELDS: //otherFields ::= fieldComposer | fieldDefaultLength | fieldMeter | fieldTempo | fieldVoice | comment;
            {
                for(int i = 0; i<children.size();i++) {
                    makeAbstractSyntaxTree(children.get(i));
                }
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
                String numerator = children.get(0).text();
                String denominator = children.get(1).text();
                TUNE.setNoteLength(numerator + "/" + denominator);
                return;
            
            }
            case FIELDMETER: // fieldMeter ::= "M:" meter endOfLine;
            {                
                makeAbstractSyntaxTree(children.get(0));
                return;
                
                
            }
            case FIELDTEMPO: // fieldTempo ::= "Q:" meterFraction "=" digit+ endOfLine;
            {
                makeAbstractSyntaxTree(children.get(0));
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
            case KEY: //    key ::= keynote modeMinor?;
            {
                makeAbstractSyntaxTree(children.get(0)); //keynote
                if(children.size()>1) {
                   TUNE.setMinor(true);
                }
                return;
            }
            case KEYNOTE: //    keynote ::= basenote keyAccidental?;
            {
                String accidental = children.get(0).text();
                TUNE.setAccidental(accidental);
                return;
            }
            case METER:
            {
                if(children.toString().indexOf("METERFRACTION")!=-1) {
                    makeAbstractSyntaxTree(children.get(0));
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
            case METERFRACTION: //  meter ::= "C" | "C|" | meterFraction;
                                //   meterFraction ::= digit+ "/" digit+;
            {
                String numerator = children.get(0).text();
                String denominator = children.get(1).text();
                TUNE.setMeter(numerator + "/" + denominator);
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
            case ABCBODY: {
                for(int i = 0;i<children.size();i++) {
                    makeAbstractSyntaxTreeMusic(children.get(i));
                   
                }
                return;
                
            }
            case ABCLINE:
            {
                builder.setStatus("Bar");
                for(int i = 0; i< children.size(); i++) {
                    System.out.println(children.get(i).text());
                    if(children.get(i).name().equals(MusicGrammar.SPACEORTAB)) {
                        continue;
                    }
                    
                    else if(i+1<children.size() && children.get(i+1).text().equals("[1")) {
                        builder.setRepeatStatus(1);
                        builder.resetBar();
                        builder.setRepeatStatus(2);
                    }
                    else if(i+1<children.size() && children.get(i+1).text().equals("[2")) {
                        builder.resetBar();
                        builder.setRepeatStatus(3);
                    }
                    else if(children.get(i).equals("[1") || children.get(i).equals("[2")) {
                        continue;

                    }
                    else if(children.get(i).text().equals("|:")) {
                        builder.setRepeatStatus(1);
                        builder.resetBar();
                        builder.setRepeatStatus(2);
                    }
                    else if(children.get(i).name().equals(MusicGrammar.BARLINE)) {
                        builder.resetBar();
                    }
                    else {
                        makeAbstractSyntaxTreeMusic(children.get(i));

                    }
                    
                }
                return;

            }
            case NOTEELEMENT:
            {
                makeAbstractSyntaxTreeMusic(children.get(0));
                return;
            }
            case NOTE: 
            {
                String pitchString = children.get(0).text();
                Character pitchChar = 'A';
                boolean isOctave = false;
                if(pitchString.length()==1) {
                    pitchChar = pitchString.charAt(0);
                }
                else if(pitchString.contains("'")) {
                    pitchChar = pitchString.charAt(pitchString.length()-2);
                    isOctave = true;
                    
                }
                if(pitchString.contains("^")) {
                    pitchChar = pitchString.charAt(1);
                    builder.addAccidental(Character.toString(pitchChar).toUpperCase());

                    
                }
                Pitch pitch = new Pitch(Character.toUpperCase(pitchChar));
                if(Character.isLowerCase(pitchChar)) {
                    pitch.transpose(Pitch.OCTAVE);
                }

                String noteLength = children.get(1).text();
                double duration;
                Note note;
                if(noteLength.length()==0) {
                    duration = 1;
                }
                else if(noteLength.equals("/")) {
                    duration = 1.0/2; //need to change
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
                    duration = duration*Double.parseDouble(meter.substring(meter.indexOf("/")+1));

                }
                note = new Note(pitch,duration);
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

            case RESTELEMENT:
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
            case TUPLETELEMENT: 
            {
                String prevStatus = builder.getStatus();
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

                for(int i =1; i<children.size(); i++) {
                    makeAbstractSyntaxTreeMusic(children.get(i));
                }
                List<Music> tupletNotes = builder.getTupletNotes();
                List<Music> modifiedDuration = new ArrayList<Music>();
                for(Music note: tupletNotes) {
                    if(note instanceof Note ) {
                        Note n = (Note) note;
                        modifiedDuration.add(new Note(n.getPitch(),n.getDuration()*duration));

                    }
                    if(note instanceof Chord) {
                        Chord c = (Chord) note;
                        List<Note> chordNotes = new ArrayList<Note>();

                        for(Note n: c.getNotes()) {
                            chordNotes.add(new Note(n.getPitch(),n.getDuration()*duration));

                        }
                        modifiedDuration.add(new Chord(chordNotes));

                    }
                   
                }
                Tuplet tuplet = new Tuplet(modifiedDuration,Double.parseDouble(durationString));
                builder.setStatus(prevStatus);
                if(builder.getStatus().equals("Bar")) {
                    builder.addToBar(tuplet);
                    builder.resetTuplet();
                    
                }

                return;
            }
            case CHORD:
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
            case LYRIC:
            {
                
            }
            case LYRICALELEMENT:
            {
                
            }
            case BACKSLASHHYPHEN:
            {
                
            }
            case MIDDLEOFBODYFIELD: 
            {
                
            }
            case LYRICTEXT:
            {
            }

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
