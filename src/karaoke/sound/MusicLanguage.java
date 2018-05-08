package karaoke.sound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.mit.eecs.parserlib.ParseTree;
import edu.mit.eecs.parserlib.Parser;
import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.sound.Music;

/**
 * Parses a file in ABC format 
 * Specification Author: Myra Ahmad & Marwa Abdulhai
 * Implementation Author: Marwa Abdulhai
 *
 */
public class MusicLanguage {
    private final static AbcTune tune = new AbcTune();

    /**
     * Main method. Parses and then reprints an example 
     * @param args command line arguments
     * @throws UnableToParseException
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
        final Music music = MusicLanguage.parse(paddy);
        System.out.println(music);

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
        
        // make an AST from the parse tree
        makeAbstractSyntaxTree(parseTree);
        return tune.getMusic();

    }

    
    /**
     * Convert a parse tree into an abstract syntax tree.
     * 
     * @param parseTree constructed according to the grammar in Abc.g
     * @return abstract syntax tree corresponding to parseTree
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
                String digitString = children.get(0).toString();
                int digit = Integer.parseInt(children.get(0).text());
                tune.setIndexNumber(digit);    
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
                tune.setTitle(title);    
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
                tune.setComposer(composer);
                return;
            }
            
            case FIELDDEFAULTLENGTH:
            {
                String numerator = children.get(0).text();
                String denominator = children.get(1).text();
                tune.setNoteLength(numerator + "/" + denominator);
                return;
            
            }
            case FIELDMETER:
            {                
                makeAbstractSyntaxTree(children.get(0));
                return;
                
                
            }
            case FIELDTEMPO:
            {
                
                makeAbstractSyntaxTree(children.get(0));
                return;
            }
            case FIELDVOICE:
            {
                System.out.println("HEUY");
            }
            case FIELDKEY:
            {
                makeAbstractSyntaxTree(children.get(0));
                return;
            }
            case KEY:
            {
                makeAbstractSyntaxTree(children.get(0)); //keynote
                if(children.size()>1) {
                   tune.setMinor(true);
                }
                return;
            }
            case KEYNOTE:
            {
                String accidental = children.get(0).text();
                tune.setAccidental(accidental);
                return;
            }
            case METER:
            {
                if(children.toString().indexOf("METERFRACTION")!=-1) {
                    makeAbstractSyntaxTree(children.get(0));
                }
                else {
                    if(children.toString().indexOf("C|")!=-1) {
                        tune.setMeter("2/2");
                    }
                    else if(children.toString().indexOf("C")!=-1) {
                        tune.setMeter("4/4");
                    }
                }
                return;
            }
            case METERFRACTION:
            {
                String numerator = children.get(0).text();
                String denominator = children.get(1).text();
                tune.setMeter(numerator + "/" + denominator);
                return;
            }
            case ABCBODY:
            {
                Music music = makeAbstractSyntaxTreeForMusic(parseTree);
                tune.setMusic(music);
                return;
            }
                
            default:
                throw new AssertionError("should never get here");
            }
    
    }
    
    private static Music makeAbstractSyntaxTreeForMusic(final ParseTree<MusicGrammar> parseTree) {
        final java.util.List<ParseTree<MusicGrammar>> children = parseTree.children();
        switch (parseTree.name()) {
            case ABCBODY: {
                List<Music> music = new ArrayList<Music>();
                for(int i = 0;i<children.size();i++) {
                    System.out.println(children.get(i));
                    music.add(makeAbstractSyntaxTreeForMusic(children.get(i)));
                }
                return new Concat(music);    
                
            }
            case ABCLINE:
            {
                List<Music> concat = new ArrayList<Music>();
                List<Music> barNotes = new ArrayList<Music>();
                for(int i = 0; i< children.size(); i++) {
                    if(children.get(i).name().equals(MusicGrammar.BARLINE)) {
                        concat.add(new Bar(barNotes));
                        barNotes = new ArrayList<Music>();
                    }
                    else if(children.get(i).name().equals(MusicGrammar.SPACEORTAB)) {
                        continue;
                    }
                    else if(children.get(i).name().equals(MusicGrammar.ENDOFLINE)) {
                        concat.add(new Bar(barNotes));
                        barNotes = new ArrayList<Music>();
                    }
                    else {
                        barNotes.add(makeAbstractSyntaxTreeForMusic(children.get(i)));

                    }
                    
                }
                return new Concat(concat);

            }
            case NOTEELEMENT:
            {
                Music music = makeAbstractSyntaxTreeForMusic(children.get(0)); //Note or Chord
                return music;
            }
            case NOTE: 
            {
                Character pitchString = children.get(0).text().charAt(0);
                Pitch pitch = new Pitch(Character.toUpperCase(pitchString));

                if(Character.isLowerCase(pitchString)) {
                    pitch.transpose(Pitch.OCTAVE);
                }
                String noteLength = children.get(1).text();
                double duration;
                Note note;
                if(noteLength.length()==0) {
                    duration = 1;
                }
                else if(noteLength.length()==2){
                    duration = convertToDouble("1" + noteLength);
                }
                else {
                    duration = convertToDouble(noteLength);

                }
                note = new Note(pitch,duration);
                return note;
                
            }
            case NOTELENGTHSTRICT:
            {
                
            }
            case ACCIDENTAL:
            {
                
            }
            case BASENOTE:
            {
                
            }
            case RESTELEMENT:
            {
                
            }
            case TUPLETELEMENT: 
            {
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

                List<Note> notes = new ArrayList<>();
                for(int i =1; i<children.size(); i++) {
                    Note music = (Note) makeAbstractSyntaxTreeForMusic(children.get(i));
                    notes.add(new Note(music.getPitch(),music.getDuration()*duration));
                }
                return new Tuplet(notes,duration);
            }
            case CHORD:
                
            {
                
            }
            case NTHREPEAT:
            {
                
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
        return null;
        
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
