package karaoke.sound;

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
        //TODO used for debugging, prints the input and the parsed expression 
    }
    /**
     * Compile the grammar into a parser 
     * Tries to read the file and catches exceptions (IO and UnableToParse)
     * @return parser for the grammar
     */
    private static Parser<MusicGrammar> makeParser() {
        return null; //TODO Implement this
    }
    
    // need an enum for the different variants we have, can be edited later I just made it so that the method above 
    // wouldn't have an error
    private static enum MusicGrammar {
        ACCIDENTAL, BAR, CHORD, CONCAT, NOTE, PITCH, REST, REPEAT, TUPLET
    }
    
    /**
     * Parse a string into Music.
     * @param string string to parse
     * @return Music parsed from the string
     * @throws UnableToParseException if the string doesn't match the Music grammar
     */
    public static Music parse(final String string) throws UnableToParseException {
        return null; //TODO Implement this 
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
