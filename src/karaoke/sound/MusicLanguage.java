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
                "K:Cm\n" + "C C C3/4 D/4 E | E3/4 D/4 E3/4 F/4 G2 | (3c/2c/2c/2 (3G/2G/2G/2 (3E/2E/2E/2 (3C/2C/2C/2 | G3/4 F/4 E3/4 D/4 C2";

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
        
        final String mario = "X: 2\r\n" + 
                "T:Piece No.2\r\n" + 
                "M:4/4\r\n" + 
                "L:1/4\r\n" + 
                "Q:1/4=200\r\n" + 
                "K:C\r\n" + 
                "[e/2^F/2] [e/2F/2] z/2 [e/2^F/2] z/2 [c/2F/2] [eF] |[gBG] z G z | c3/2 G/2 z E | E/2 A B  _B/2 A | (3GeG a f/2 g/2 |z/2 e c/2 d/2 B3/4\r\n";
        final String piece2 = "X:1\r\n" + 
                "T:Little Night Music Mvt. 1\r\n" + 
                "C:Wolfgang Amadeus Mozart\r\n" + 
                "Q:1/4=140\r\n" + 
                "M:4/4\r\n" + 
                "L:1/8\r\n" + 
                "K:G\r\n" + 
                "[D2B2g2]z d g2z d | g d g b d'2 z2 | c'2z a c'2z a | c' a f a d2 z2 |\r\n";
        
//        + 
//                "[DBg]z g3 b a g | g f f3 a c' f | a g g3 b a g | g f f3 a c' f |\r\n" + 
//                "g g f e1/2f/2 g g a g/a/ | b b c' b/c'/ d'2 z2 | d4 e4 | c2 c2 B2 B2 |\r\n" + 
//                "A2 A2 G F E F | G z A z B z z2  | d4 e4 | dccc cBBB | BAAA GFEF | \r\n" + 
//                "[G4G,4] [GG,] G1/3F1/3G1/3 AF | B4 B B/3A/3B/3 c A | d4 e2 f2 |\r\n" + 
//                "g2 a2 b2 ^c'2 | d'3 a ^c'3/2 a/ c'3/2 a/ | d'3 a ^c'3/2 a/ c'3/2 a/ | \r\n" + 
//                "d' [d'2f2] [d'2f2] [d'2f2] [d'f] | d' [d'2e2] [d'2e2] [d'2e2] [d'e] | \r\n" + 
//                "[^c'e] a d' a c' a d' a | ^c' A A A A2 z2 | \r\n" + 
//                "a3 g/3f/3e/3 d z b z | g z e z a z z2 | f3 e/3d/3^c/3 B z g z | f4 e2 z2 |\r\n" + 
//                "z aaa aaaa | aaaa aab^c' | ^c'd' z b b a z ^c | d2 z a d'^c'ba | \r\n" + 
//                "b a z a a a a a | b a z a d' ^c' b a |\r\n" + 
//                "b a z a a a a a | b a z2 [b3B3] a/3g/3f/3 | g2 z2 [a3A3] g/3f/3e/3 |\r\n" + 
//                "f2 z2 b ^c'/d'/ c' b | b a f a a g f e | d2 z a d' ^c' b a | b a z a a a a a |\r\n" + 
//                "b a z a d' ^c' b a | b a z a a a a a | b a z2 [b3B3] a/3g/3f/3 |\r\n" + 
//                "g2 z2 [a3A3] g/3f/3e/3 | f2 z2 b ^c'/d'/ c' b | b a f a a g f e |\r\n" + 
//                "d A B ^c d d e d/e/ | \r\n" + 
//                "f ^c d e f f g f/g/ | a a ^a ^g/a/ b2 z2 | B3 e d ^c B A | d z f z d z z2 |\r\n";
//        
        final String easyRepeat = "X:1 %Comment Testing \n" +
                "T:First" + "\n" + 
                "M:4/4\n" + 
                "K:Cm\n" + "|: C D E F | G A B c :|";
        final String differentVoices = "X:1868\r\n" + 
                "T:Invention no. 1\r\n" + 
                "C:Johann Sebastian Bach\r\n" + 
                "V:1\r\n" + 
                "V:2\r\n" + 
                "M:C\r\n" + 
                "L:1/8\r\n" + 
                "Q:1/4=70\r\n" + 
                "K:C\r\n" + 
                "V:1\r\n" + 
                "z/C/D/E/ F/D/E/C/ GcBc|d/G/A/B/ c/A/B/G/ dgfg|\r\n" + 
                "V:2\r\n" + 
                "z4 z/C,/D,/E,/ F,/D,/E,/C,/|G,G,, z2 z/G,/A,/B,/ C/A,/B,/G,/|\r\n" + 
                "%\r\n" + 
                "V:1\r\n" + 
                "e/a/g/f/ e/g/f/a/ g/f/e/d/ c/e/d/f/|e/d/c/B/ A/c/B/d/ c/B/A/G/ ^F/A/G/B/|\r\n" + 
                "V:2\r\n" + 
                "CB,CD EG,A,B,|CE,^F,G, A,B,C2|\r\n" + 
                "%\r\n" + 
                "V:1\r\n" + 
                "AD c3/d/ B/A/G/^F/ E/G/F/A/|G/B/A/c/ B/d/c/e/ d/B/4c/4d/g/ BA/G/|\r\n" + 
                "V:2\r\n" + 
                "C/D,/E,/^F,/ G,/E,/F,/D,/ G,B,,C,D,|E,^F,G,E, B,,C, D,D,,|\r\n" + 
                "%\r\n" + 
                "V:1\r\n" + 
                "Gz z2 z/G/A/B/ c/A/B/G/|^Fz3 z/A/B/c/ d/B/c/A/|\r\n" + 
                "V:2\r\n" + 
                "z/G,,/A,,/B,,/ C,/A,,/B,,/G,,/ D,G,^F,G,|A,/D,/E,/^F,/ G,/E,/F,/D,/ A,DCD|\r\n" + 
                "%\r\n" + 
                "V:1\r\n" + 
                "Bz z2 z/d/c/B/ A/c/B/d/|cz z2 z/e/d/c/ B/d/^c/e/|\r\n" + 
                "V:2\r\n" + 
                "G,/G/=F/E/ D/F/E/G/ FEFD|E/A/G/F/ E/G/F/A/ GFGE|\r\n" + 
                "%\r\n" + 
                "V:1\r\n" + 
                "d^cde fA=Bc|d^F^GA Bcd2|\r\n" + 
                "V:2\r\n" + 
                "F/_B/A/G/ F/A/G/B/ A/G/F/E/ D/F/E/G/|F/E/D/C/ B,/D/C/E/ D/C/B,/A,/ ^G,/B,/A,/C/|\r\n" + 
                "%\r\n" + 
                "V:1\r\n" + 
                "d/E/^F/^G/ A/F/G/E/ e/d/c/e/ d/c/B/d/|c/a/^g/b/ a/e/f/d/ ^G/f/e/d/ cB/A/|\r\n" + 
                "V:2\r\n" + 
                "B,E, D3/E/ C/B,/A,/=G,/ ^F,/A,/^G,/B,/|A,/C/B,/D/ C/E/D/F/ EA,EE,|\r\n" + 
                "%\r\n" + 
                "V:1\r\n" + 
                "A/a/g/f/ e/g/f/a/ g4|g/e/f/g/ a/f/g/e/ f4|\r\n" + 
                "V:2\r\n" + 
                "A,A,, z2 z/E/D/C/ B,/D/^C/E/|D4 D/A,/B,/=C/ D/B,/C/A,/|\r\n" + 
                "%\r\n" + 
                "V:1\r\n" + 
                "f/g/f/e/ d/f/e/g/ f4|f/d/e/f/ g/e/f/d/ e4|\r\n" + 
                "V:2\r\n" + 
                "B,4 B,/D/C/B,/ A,/C/B,/D/|C4 C/G,/A,/_B,/ C/A,/^A,/G,/|\r\n" + 
                "%\r\n" + 
                "V:1\r\n" + 
                "e/c/d/e/ f/d/e/c/ d/e/f/g/ a/f/g/e/|f/g/a/b/ c'/a/b/g/ c'g ed/c/|\r\n" + 
                "V:2\r\n" + 
                "A,_B,A,G, F,DCB,|A,FED E/D,/E,/F,/ G,/E,/F,/D,/|\r\n" + 
                "%\r\n" + 
                "V:1\r\n" + 
                "c/_B/A/G/ F/A/G/_B/ A/=B/c/E/ D/c/F/B/|[c8G8E8]|]\r\n" + 
                "V:2\r\n" + 
                "E,C,D,E, F,/D,/E,/F,/ G,G,,|[C,8C,,8]|]\r\n";
        final String withLyrics = "X:2167\r\n" + 
                "T:Waxie's Dargle\r\n" + 
                "M:4/4\r\n" + 
                "L:1/8\r\n" + 
                "Q:1/4=180\r\n" + 
                "K:Gb\r\n" + 
                "gf|e2dc B2A2|B2G2 E2D2|G2G2 GABc|d4 B2gf|\r\n" + 
                "w: Sa-ys my au-l' wan to your aul' wan Will~ye come to the Wa-x-ies dar-gle? Sa-ys\r\n" + 
                "e2dc B2A2|B2G2 E2G2|F2A2 D2EF|G2z2 G4|\r\n" + 
                "w: your aul'_ wan to my aul' wan, Sure I ha-ven't got a far-thing.\r\n" + 
                "B2d2 e2f2|g2d2 BAG2|Bcd2 e2f2|g4 f2gf|\r\n" + 
                "w: I'll go down to Mon-to to-w-n To see un-cle Mc-Ar-dle A-nd\r\n" + 
                "e2dc B2A2|B2G2 E2G2|F2A2 D2EF|G2z2 G4|\r\n" + 
                "w: ask him for a half a crown For~to go to the Wa-x-ies dar-gle\r\n";

        final List<Concat> musicPiece1 = MusicLanguage.parse(withLyrics);
        final int beatsPerMinute = 140; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 12; // allows up to 1/64-beat notes to be played with fidelity
        System.out.println(musicPiece1);

        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        for(Concat c: musicPiece1) {
            System.out.println(c);
            System.out.println(c.getLyrics());
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
//        System.out.println(parseTree);
        // make an AST from the parse tree
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
                String baseNote = children.get(1).text();

                TUNE.setAccidental(accidental);
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
                    
                    else if(i+1<children.size() && children.get(i+1).text().equals("[1")) { //if at first repeat ending
                        builder.setRepeatStatus(1);
                        builder.resetBar();
                        builder.setRepeatStatus(2);
                    }
                    else if(i+1<children.size() && children.get(i+1).text().equals("[2")) { //if at second repeat ending
                        builder.resetBar();
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
                    //add coment

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
            case NOTEELEMENT:
            {
                makeAbstractSyntaxTreeMusic(children.get(0));
                return;
            }
            case NOTE:  //note ::= pitch noteLength?;
            {
                //calculating pitch
                //pitch ::= accidental? basenote octave?;
                List<ParseTree<MusicGrammar>> pitchList = children.get(0).children();
//                System.out.println("pitchList" + pitchList);
                Character pitchChar = null;
                Pitch pitch = null;

                if(pitchList.size()==1) {
                    pitchChar = pitchList.get(0).text().charAt(0);
                    
                    pitch = builder.applyAccidental(Character.toUpperCase(pitchChar));
                }
                else if(pitchList.size()==2) {
                    //found accidental
                    if(pitchList.get(0).name().equals(MusicGrammar.ACCIDENTAL)) {
                        pitchChar = pitchList.get(1).text().charAt(0); 
                        String accidentalType = pitchList.get(0).text();
                        builder.addAccidental(Character.toUpperCase(pitchChar),accidentalType);
                        
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
                    }
                    //found octave
                    else {
                        pitchChar = pitchList.get(0).text().charAt(0);
                        String octaveType = pitchList.get(1).text();
                        
                        pitch = builder.applyAccidental(Character.toUpperCase(pitchChar));

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

                String noteLength = children.get(1).text();
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
                    duration = duration*Double.parseDouble(meter.substring(meter.indexOf("/")+1));

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
