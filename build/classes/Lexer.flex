import compilerTools.Token;

%%
%class Lexer
%type Token
%line
%column
%{
    private Token token(String lexeme, String lexicalComp, int line, int column){
        return new Token(lexeme, lexicalComp, line+1, column+1);
    }
%}
/* Variables básicas de comentarios y espacios */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinDeLineaComentario = "$" {EntradaDeCaracter}* {TerminadorDeLinea}?
ContenidoComentario = ( [^*] | \*+ [^/*] )*
ComentarioDeDocumentacion = "/**" {ContenidoComentario} "*"+ "/"

/* Comentario */
Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

/* Identificador */
Letra = [A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü]
Digito = [0-9]
Identificador = {Letra}({Letra}|{Digito})*

/* Número */
Numero = 0 | [1-9][0-9]*
%%

/* Comentarios o espacios en blanco */
{Comentario}|{EspacioEnBlanco} { /*Ignorar*/ }

/* Agrupaciones */
"(" { return token(yytext(), "PARENTESIS ABIERTO", yyline, yycolumn);  }
")" { return token(yytext(), "PARENTESIS CERRADO", yyline, yycolumn);  }
"{" { return token(yytext(), "LLAVE ABIERTA", yyline, yycolumn);  }
"}" { return token(yytext(), "LLAVE CERRADA", yyline, yycolumn);  }

/* Declaracion de Variables */
^[a-z]+[\\ ]{0,1} \= [\\ ]{0,1} [0-9]+$ { return token(yytext(), "VARIABLE ENTERA", yyline, yycolumn); }
^[a-z]+[\\ ]{0,1} \= [\\ ]{0,1} [0-9]+[\.]{1}[0-9]+$ { return token(yytext(), "VARIABLE FLOTANTE", yyline, yycolumn); }
^[a-z]+[\\ ]{0,1} \= [\\ ]{0,1} \"([a-zA-Z] | [\\ ] | [0-9])*\"$ { return token(yytext(), "VARIABLE CADENA", yyline, yycolumn); }
^[a-z]+[\\ ]{0,1} \= [\\ ]{0,1}(True | False)$ { return token(yytext(), "VARIABLE BOOLEANA", yyline, yycolumn); }
^[a-z]+[\\ ]{0,1} \= [\\ ]{0,1} \"[a-zA-Z]\"$ { return token(yytext(), "VARIABLE CARACTER", yyline, yycolumn); }

/* Tipos de Datos */ 
int | str | float | char | bool { return token(yytext(), "TIPO DE DATO", yyline, yycolumn); }

/* Palabras reservadas */
read | write | if | else | elif | while | for |
break | continue | return | class | import | from |
True | False | None | lambda | try | function | 
is | pass | except | in { return token(yytext(), "PALABRA RESERVADA", yyline, yycolumn); }

/* Operadores */
and | not |  or | \=\= | <\= | >\= | < | > | \!\= { return token(yytext(), "OPERADOR LOGICO", yyline, yycolumn); }

\+ | \- | \* | \/ | \/\/ | % { return token(yytext(), "OPERADOR MATEMATICO", yyline, yycolumn); }

. { return token(yytext(), "ERROR", yyline, yycolumn); }