# code-syntax-parser
This started as a school project in comparative programming languages, I'm still expanding on it. 
A program is read through File IO and the LexicalAnalyzer breaks it down character by character and assigns each lexeme a token corresponding to the kind of lexeme that it is. 
Next, each lexeme is fed through the SyntaxParser in order, and the program is parsed in terms of the Backus-Naur grammar of the (made up) language to evaluate if the program
syntax is valid.
