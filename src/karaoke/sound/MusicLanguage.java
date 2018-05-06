package karaoke.sound;

import java.io.File;
import java.io.IOException;

import edu.mit.eecs.parserlib.ParseTree;
import edu.mit.eecs.parserlib.Parser;
import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.sound.Music;
// should import the grammar file but for some reason it was failing to compile when I had it -Myra
/**
 * Parses a file in abc format 
 * @author Myra
 *
 */
public class MusicLanguage {
    /**
     * Main method. Parses and then reprints an example 
     * @param args command line arguments
     * @throws UnableToParseException
     */
    public static void main(final String[] args) throws UnableToParseException {
        final String input = "X:1 \n" + 
                "T:Piece No.1\n" + 
                "M:4/4\r\n" + 
                "L:1/4\r\n" + 
                "Q:1/4=140\r\n" + 
                "K:C\r\n" + 
                "C C C3/4 D/4 E | E3/4 D/4 E3/4 F/4 G2 | (3ccc (3GGG (3EEE (3CCC | G3/4 F/4 E3/4 D/4 C2 \r\n";
        final Music expression = MusicLanguage.parse(input);
        System.out.println(expression);

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
        ABCTUNE, ABCHEADER, FIELDNUMBER, FIELDTITLE, OTHERFIELDS, FIELDCOMPOSER, KEYACCIDENTAL,TEXT,WHITESPACE, MIDDLEOFBODYFIELD,LYRICTEXT,COMMENTTEXT,FIELDDEFAULTLENGTH, FIELDMETER, FIELDTEMPO, FIELDVOICE, FIELDKEY, KEY, KEYNOTE,MODEMINOR,METER, METERFRACTION,TEMPO,ABCBODY, ABCLINE,ELEMENT, NOTEELEMENT, NOTE,PITCH,OCTAVE, NOTELENGTH, NOTELENGTHSTRICT, ACCIDENTAL,BASENOTE,RESTELEMENT, TUPLETELEMENT, TUPLETSPEC,CHORD, BARLINE, NTHREPEAT,LYRIC, LYRICALELEMENT, BACKSLASHHYPHEN,COMMENT, ENDOFLINE, DIGIT,NEWLINE,SPACEORTAB
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
        return null;

        // display the parse tree in various ways, for debugging only
        //System.out.println("parse tree " + parseTree);
        //Visualizer.showInBrowser(parseTree);

        // make an AST from the parse tree
//        final Expression expression = makeAbstractSyntaxTree(parseTree);
        //System.out.println("AST " + expression);
        
//        return expression;
    }
    
    /**
     * Convert a parse tree into an abstract syntax tree.
     * 
     * @param parseTree constructed according to the grammar in Abc.g
     * @return abstract syntax tree corresponding to parseTree
     */
    private static Music makeAbstractSyntaxTree(final ParseTree<MusicGrammar> parseTree) {
        // TODO implement this (switch statement etc)
        return null;
    }
    
    
    
    
    
    
    
}
