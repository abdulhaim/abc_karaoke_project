// Grammar for ABC music notation 

abcTune ::= abcHeader abcBody;

@skip whitespace { 

	abcHeader ::= fieldNumber comment* fieldTitle otherFields* fieldKey;
	
	fieldNumber ::= "X:" digit+ endOfLine;
	fieldTitle ::= "T:" text endOfLine;
	otherFields ::= fieldComposer | fieldDefaultLength | fieldMeter | fieldTempo | fieldVoice | comment;
	fieldComposer ::= "C:" text endOfLine;
	fieldDefaultLength ::= "L:" noteLengthStrict endOfLine;
	fieldMeter ::= "M:" meter endOfLine;
	fieldTempo ::= "Q:" tempo endOfLine;
	fieldVoice ::= "V:" text endOfLine;
	fieldKey ::= "K:" key endOfLine;
	
	key ::= keynote modeMinor?;
	keynote ::= basenote keyAccidental?;
	keyAccidental ::= "#" | "b";
	modeMinor ::= "m";
	
	meter ::= "C" | "C|" | meterFraction;
	meterFraction ::= digit+ "/" digit+;
	
	tempo ::= meterFraction "=" digit+;

}

abcBody ::= abcLine+;
abcLine ::= element+ endOfLine (lyric endOfLine)?  | middleOfBodyField | comment;
element ::= noteElement | restElement | tupletElement | barline | nthRepeat | spaceOrTab; 

noteElement ::= note | chord;

note ::= pitch noteLength?;
pitch ::= accidental? basenote octave?;
octave ::= "'"+ | ","+;
noteLength ::= (digit+)? ("/" (digit+)?)?;
noteLengthStrict ::= digit+ "/" digit+;

accidental ::= "^" | "^^" | "_" | "__" | "=";

basenote ::= "C" | "D" | "E" | "F" | "G" | "A" | "B" | "c" | "d" | "e" | "f" | "g" | "a" | "b";
restElement ::= "z" noteLength?;

tupletElement ::= tupletSpec noteElement+;
tupletSpec ::= "(" digit;

chord ::= "[" note+ "]";

barline ::= "|" | "||" | "[|" | "|]" | ":|" | "|:";
nthRepeat ::= "[1" | "[2";
middleOfBodyField ::= fieldVoice;

lyric ::= "w:" lyricalElement*;
lyricalElement ::= " "+ | "-" | "_" | "*" | "~" | backslashHyphen | "|" | lyricText;
lyricText ::= [A-Za-z]*;
backslashHyphen ::= "\\" "-";

comment ::= spaceOrTab* "%" commentText newline;
commentText ::= [A-Za-z]*;
endOfLine ::= newline | comment;

digit ::= [0-9];
newline ::= "\n" | "\r" "\n"?;
spaceOrTab ::= " " | "\t";
text ::= [A-Za-z]*;
whitespace ::= [ \t\r\n]+;


