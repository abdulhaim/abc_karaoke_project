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
    private static List<String> voices = new ArrayList<String>();

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
                "K:C\n" + "[DDD] [EEE] [FFF]";//"C C C3/4 D/4 E | E3/4 D/4 E3/4 F/4 G2 | (3c/2c/2c/2 (3G/2G/2G/2 (3E/2E/2E/2 (3C/2C/2C/2 | G3/4 F/4 E3/4 D/4 C2";
        final String mozart = "X:1\r\n" + 
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

        final String payphone = "X: 1\r\n" + 
                "T:Payphone\r\n" + 
                "C:Maroon 5\r\n" + 
                "V:Sing\r\n" + 
                "V:Melody\r\n" + 
                "M:4/4\r\n" + 
                "L:1/4\r\n" + 
                "Q:1/4=120\r\n" + 
                "K:G\r\n" + 
                "% abc lyrics transcription from eruleman,nickmm,vhorta\r\n" + 
                "V:Sing\r\n" + 
                "z2 B/2 c/2 d/2 a/2 | a g g/2 f/2 d/2 c/2 | c c c/2 B/2 G/2 c/2 | c B/2 A G/2 B/2 A/2 | A2 B/2 c/2 d/2 a/2 |\r\n" + 
                "w: I'm at a pay*phone try-ing to call_ home All of my change_ I spent_ on you._ Where have the times\r\n" + 
                "V:Melody\r\n" + 
                "z4 | C,,/2 G,/2 C/2 E/2 G/2 c/2 z | G,,/2 D,/2 G,/2 B,/2 D/2 G/2 z | E,,/2 B,,/2 E,/2 G,/2 B,/2 D/2 z | D,,/2 A,,/2 D,/2 F,/2 A,/2 D/2 z |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "a g g/2 f/2 d/2 c/2 | c c c/2 B/2 G/2 c/2 | c B/2 A G/2 B/2 A/2 | A2 z G | G3/4 G/4 B/4 d/2 d/2 B/2 A/4 B/2 A/2 |\r\n" + 
                "w: *gone? Ba-by, it's all_ wrong Where are the plans_ we made_ for two?_ Yeah, I, I know it's hard to re-mem-ber\r\n" + 
                "V:Melody\r\n" + 
                "C,,/2 G,/2 C/2 E/2 G/2 c/2 z | G,,/2 D,/2 G,/2 B,/2 D/2 G/2 z | E,,/2 B,,/2 E,/2 G,/2 B,/2 D/2 z | D,,/2 A,,/2 D,/2 F,/2 A,/2 D/2 z | C, [G,C] C, [G,C] |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "A3/4 G/4 B/4 d/2 d/2 B/2 A/4 B | B3/4 G/4 B/4 d/2 d/2 B/2 A/4 B/2 A/2 | A3/4 G/4 B/4 d/2 d/2 e/2 B/4 B | B3/4 G/4 B/4 d/2 d/2 B/2 A/4 B/2 A/2 | A3/4 G/4 B/4 d/2 d/2 B/2 A/4 B |\r\n" + 
                "w: *the peo-ple we used to be..._ It's e-ven hard_ to pic-ture_ that you're not here next to me._ You say it's to late to make it,_ But is it too late to try?_\r\n" + 
                "V:Melody\r\n" + 
                "G,, [D,G,] G,, [D,G,] | E,, [B,,E,] E,, [B,,E,] | D,, [F,,D,] D,, [F,,D,] | C, [G,C] C, [G,C] | G,, [D,G,] G,, [D,G,] |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "B3/4 G/4 B/4 d/2 d/2 B/2 A/4 B/2 A/2 | c3/4 B3/4 G/2 c3/4 B3/4 A/2 | B B B3/4 B3/4 A/2 | B B B3/4 B3/4 A/2 | B B B3/4 B3/4 A/2 |\r\n" + 
                "w:* And in our time that you was-ted All of our brid-ges burned down I've was-ted my nights, You turned out the lights Now I'm pa-ra\\-\r\n" + 
                "V:Melody\r\n" + 
                "E,, [B,,E,] E,, [B,,E,] | D,, [F,,D,] D,, [F,,D,] | C,/2 G,/2 C [C2E2G2] | G,,/2 D,/2 G, [G,2B,2D2] | E,,/2 B,,/2 E, [E,2G,2B,2] |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "A G G3/4 A3/4 d/2 | B B B3/4 B3/4 A/2 | B B B3/4 B3/4 A/2 | B B B3/4 B3/4 A/2 | A2 [B/2b/2] [c/2c'/2] [d/2d'/2] [a/2a'/2] |\r\n" + 
                "w:lized. Still stuck in that time When we called it love But e-ven the sun sets in pa-ra-dise. I'm at a pay\r\n" + 
                "V:Melody\r\n" + 
                "D,,/2 A,,/2 D, [D,2F,2A,2] | C,/2 G,/2 C [C2E2G2] | G,,/2 D,/2 G, [G,2B,2D2] | E,,/2 B,,/2 E, [E,2G,2B,2] | D,,/2 A,,/2 D, z2 | \r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "[aa'] [gg'] [g/2g'/2] [f/2f'/2] [d/2d'/2] [c/2c'/2] | [cc'] [cc'] [c/2c'/2] [B/2B'/2] [G/2g/2] [c/2c'/2] | [cc'] [B/2b/2] [Aa] [G/2g/2] [B/2b/2] [A/2a/2] | [A2a2] [B/2b/2] [c/2c'/2] [d/2d'/2] [a/2a'/2] |\r\n" + 
                "w:*phone try-ing to call_ home All of my change_ I spent_ on you._ Where have the times\r\n" + 
                "V:Melody\r\n" + 
                "C,,/2 G,/2 C/2 E/2 G/2 c/2 z | G,,/2 D,/2 G,/2 B,/2 D/2 G/2 z | E,,/2 B,,/2 E,/2 G,/2 B,/2 D/2 z | D,,/2 A,,/2 D,/2 F,/2 A,/2 D/2 z |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "[aa'] [gg'] [g/2g'/2] [f/2f'/2] [d/2d'/2] [c/2c'/2] | [cc'] [cc'] [c/2c'/2] [B/2B'/2] [G/2g/2] [c/2c'/2] | [cc'] [B/2b/2] [Aa] [G/2g/2] [B/2b/2] [A/2a/2] | [A3/2a3/2] [G/2g/2] [B/2b/2] [c/2c'/2] [B/2b/2] [A/2a/2] |\r\n" + 
                "w:*gone? Ba-by, it's all_ wrong Where are the plans_ we made_ for two?_ If Hap-py Ev-er\r\n" + 
                "V:Melody\r\n" + 
                "C,,/2 G,/2 C/2 E/2 G/2 c/2 z | G,,/2 D,/2 G,/2 B,/2 D/2 G/2 z | E,,/2 B,,/2 E,/2 G,/2 B,/2 D/2 z | D,,/2 A,,/2 D,/2 F,/2 A,/2 D/2 z |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "[A3/4a3/4] [G3/4g3/4] [Gg] [A/2a/2] [Bb] | [B2b2] [B/2b/2] [c/2c'/2] [B/2b/2] [A/2a/2] | [A3/4a3/4] [G3/4g3/4] [Gg] [d/2d'/2] [Aa] |  [A2a2] [B/2b/2] [c/2c'/2] [B/2b/2] [A/2a/2] |\r\n" + 
                "w:Af-ter did ex-ist,_ I would still be hol-ding you like this_ All these fai-ry\r\n" + 
                "V:Melody\r\n" + 
                "C,/2 G,/2 C/2 G,/2 C/2 G,/2 C/2 G,/2 | G,,/2 D,/2 G,/2 D,/2 G,/2 D,/2 G,/2 D,/2 | E,,/2 B,,/2 E,/2 B,,/2 E,/2 B,,/2 E,/2 B,,/2 | D,,/2 A,,/2 D,/2 A,,/2 D,/2 A,,/2 D,/2 A,,/2 |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "[A3/4a3/4] [G3/4g3/4] [Gg] [A/2a/2] [Bb] | [B2b2] [B/2b/2] [c/2c'/2] [B/2b/2] [A/2a/2] | [A3/4a3/4] [G3/4g3/4] [Gg] [d/2d'/2] [Aa] |  [A2a2] [B/2b/2] [c/2c'/2] [B/2b/2] [A/2a/2] | [A2a2] [Gg] G |\r\n" + 
                "w: tales are full of it._ One more stu-pid love song, I'll be sick._ I'm at a pay*phone\r\n" + 
                "V:Melody\r\n" + 
                "C,/2 G,/2 C/2 G,/2 C/2 G,/2 C/2 G,/2 | G,,/2 D,/2 G,/2 D,/2 G,/2 D,/2 G,/2 D,/2 | E,,/2 B,,/2 E,/2 B,,/2 E,/2 B,,/2 E,/2 B,,/2 | D,,/2 A,,/2 D,/2 A,,/2 D,/2 A,,/2 D,/2 A,,/2 | C,/2 G,/2 C/2 G,/2 C/2 G,/2 z |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "| G3/4 G/4 B/4 d/2 d/2 B/2 A/4 B/2 A/2 |\r\n" + 
                "w: Oh, You turned your back on to-mor-row\r\n" + 
                "V: Melody\r\n" + 
                "C, [G,C] C, [G,C] |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "A3/4 G/4 B/4 d/2 d/2 B/2 A/4 B | B3/4 G/4 B/4 d/2 d/2 B/2 A/4 B/2 A/2 | A3/4 G/4 B/4 d/2 d/2 e/2 B/4 B | B3/4 G/4 B/4 d/2 d/2 B/2 A/4 B/2 A/2 | A3/4 G/4 B/4 d/2 d/2 B/2 A/4 B |\r\n" + 
                "w: *'Cause you for-got yes-ter-day._ I gave you my love to bor-row,_ But you just gave it a-way._ You can't ex-pect me to be fine,_ I don't ex-pect you to care\r\n" + 
                "V:Melody\r\n" + 
                "G,, [D,G,] G,, [D,G,] | E,, [B,,E,] E,, [B,,E,] | D,, [F,,D,] D,, [F,,D,] | C, [G,C] C, [G,C] | G,, [D,G,] G,, [D,G,] |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "B3/4 G/4 B/4 d/2 d/2 B/2 A/4 B/2 A/2 | c3/4 B3/4 G/2 c3/4 B3/4 A/2 | B B B3/4 B3/4 A/2 | B B B3/4 B3/4 A/2 | B B B3/4 B3/4 A/2 |\r\n" + 
                "w:*I know I've said it be-fore but All of our brid-ges burned down I've was-ted my nights, You turned out the lights Now I'm pa-ra\\-\r\n" + 
                "V:Melody\r\n" + 
                "E,, [B,,E,] E,, [B,,E,] | D,, [F,,D,] D,, [F,,D,] | C,/2 G,/2 C [C2E2G2] | G,,/2 D,/2 G, [G,2B,2D2] | E,,/2 B,,/2 E, [E,2G,2B,2] |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "A G G3/4 A3/4 d/2 | B B B3/4 B3/4 A/2 | B B B3/4 B3/4 A/2 | B B B3/4 B3/4 A/2 | A2 [B/2b/2] [c/2c'/2] [d/2d'/2] [a/2a'/2] |\r\n" + 
                "w:lized. Still stuck in that time When we called it love But e-ven the sun sets in pa-ra-dise. I'm at a pay\r\n" + 
                "V:Melody\r\n" + 
                "D,,/2 A,,/2 D, [D,2F,2A,2] | C,/2 G,/2 C [C2E2G2] | G,,/2 D,/2 G, [G,2B,2D2] | E,,/2 B,,/2 E, [E,2G,2B,2] | D,,/2 A,,/2 D, z2 | \r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "[aa'] [gg'] [g/2g'/2] [f/2f'/2] [d/2d'/2] [c/2c'/2] | [cc'] [cc'] [c/2c'/2] [B/2B'/2] [G/2g/2] [c/2c'/2] | [cc'] [B/2b/2] [Aa] [G/2g/2] [B/2b/2] [A/2a/2] | [A2a2] [B/2b/2] [c/2c'/2] [d/2d'/2] [a/2a'/2] |\r\n" + 
                "w:*phone try-ing to call_ home All of my change_ I spent_ on you._ Where have the times\r\n" + 
                "V:Melody\r\n" + 
                "C,,/2 G,/2 C/2 E/2 G/2 c/2 z | G,,/2 D,/2 G,/2 B,/2 D/2 G/2 z | E,,/2 B,,/2 E,/2 G,/2 B,/2 D/2 z | D,,/2 A,,/2 D,/2 F,/2 A,/2 D/2 z |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "[aa'] [gg'] [g/2g'/2] [f/2f'/2] [d/2d'/2] [c/2c'/2] | [cc'] [cc'] [c/2c'/2] [B/2B'/2] [G/2g/2] [c/2c'/2] | [cc'] [B/2b/2] [Aa] [G/2g/2] [B/2b/2] [A/2a/2] | [A3/2a3/2] [G/2g/2] [B/2b/2] [c/2c'/2] [B/2b/2] [A/2a/2] |\r\n" + 
                "w:*gone? Ba-by, it's all_ wrong Where are the plans_ we made_ for two?_ If Hap-py Ev-er\r\n" + 
                "V:Melody\r\n" + 
                "C,,/2 G,/2 C/2 E/2 G/2 c/2 z | G,,/2 D,/2 G,/2 B,/2 D/2 G/2 z | E,,/2 B,,/2 E,/2 G,/2 B,/2 D/2 z | D,,/2 A,,/2 D,/2 F,/2 A,/2 D/2 z |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "[A3/4a3/4] [G3/4g3/4] [Gg] [A/2a/2] [Bb] | [B2b2] [B/2b/2] [c/2c'/2] [B/2b/2] [A/2a/2] | [A3/4a3/4] [G3/4g3/4] [Gg] [d/2d'/2] [Aa] |  [A2a2] [B/2b/2] [c/2c'/2] [B/2b/2] [A/2a/2] |\r\n" + 
                "w:Af-ter did ex-ist,_ I would still be hol-ding you like this_ All these fai-ry\r\n" + 
                "V:Melody\r\n" + 
                "C,/2 G,/2 C/2 G,/2 C/2 G,/2 C/2 G,/2 | G,,/2 D,/2 G,/2 D,/2 G,/2 D,/2 G,/2 D,/2 | E,,/2 B,,/2 E,/2 B,,/2 E,/2 B,,/2 E,/2 B,,/2 | D,,/2 A,,/2 D,/2 A,,/2 D,/2 A,,/2 D,/2 A,,/2 |\r\n" + 
                "%\r\n" + 
                "%\r\n" + 
                "V:Sing\r\n" + 
                "[A3/4a3/4] [G3/4g3/4] [Gg] [A/2a/2] [Bb] | [B2b2] [B/2b/2] [c/2c'/2] [B/2b/2] [A/2a/2] | [A3/4a3/4] [G3/4g3/4] [Gg] [d/2d'/2] [Aa] |  [A2a2] [B/2b/2] [c/2c'/2] [B/2b/2] [A/2a/2] | [A2a2] [G6g6] |\r\n" + 
                "w: tales are full of it._ One more stu-pid love song, I'll be sick._ I'm at a pay*phone\r\n" + 
                "V:Melody\r\n" + 
                "C,/2 G,/2 C/2 G,/2 C/2 G,/2 C/2 G,/2 | G,,/2 D,/2 G,/2 D,/2 G,/2 D,/2 G,/2 D,/2 | E,,/2 B,,/2 E,/2 B,,/2 E,/2 B,,/2 E,/2 B,,/2 | D,,/2 A,,/2 D,/2 A,,/2 D,/2 A,,/2 D,/2 A,,/2 | z4 | z4 |\r\n" + 
                "%\r\n" + 
                "%";
        final String invention = "X:1868\r\n" + 
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
        final AbcTune musicPiece = MusicLanguage.parse(payphone);

        final int beatsPerMinute = 120; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 12; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        Voices voice = musicPiece.getMusic();
        voice.play(player, 0.0);
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
    
    
    private static Parser<MusicGrammar> parser = makeParser();

    /**
     * Parse a string into Music.
     * @param string string to parse
     * @return Music parsed from the string
     * @throws UnableToParseException if the string doesn't match the Music grammar
     */
    public static AbcTune parse(final String string) throws UnableToParseException {
        System.out.println(string);
        final ParseTree<MusicGrammar> parseTree = parser.parse(string);
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
                Voices voice = new Voices(voices);
                for(int i = 0;i<children.size();i++) {
                    makeAbstractSyntaxTreeMusic(children.get(i));
                    if(builder.inMusic()) {
                        Concat music = new Concat(builder.getMusicLine(),builder.getHashMap(),builder.getLyrics());
                        voice = voice.addMusic(builder.getSinger(), music);
                        builder = new AbcBuilder();
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

                }
                String meter = TUNE.getMeter();
                
                if(!TUNE.getNoteLength().isEmpty()) {
                    duration = duration*convertToDouble(TUNE.getNoteLength())*Double.parseDouble(meter.substring(meter.indexOf("/")+1));

                }
                else {
                    duration = duration*Double.parseDouble(meter.substring(meter.indexOf("/")+1)); // multiply by 1/4 since that's implicit

                }
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
