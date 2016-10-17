/*
* Erstellt eine neue Datei mit dem vom Benutzer gegebenen Namen
*
* Talal & Michel # 13.10.2016
*
*/

#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <stdbool.h>

const char version[] = {"HAW Shell 1.0\n"};
const char *options[3] = {"help", "version", "quit"}; // Built-in Befehle
bool isBuiltIn = false; // um festzustellen, dass ein Built-in Befehl gefunden wurde.
char input[30];
int inputSize;

void printVersion() {

 printf("%s", version);

}

// Help Ausgabe
void menu() {

 const char menu[] = {"Optionen:\n"
			"'quit'		Beenden der HAW-Shell\n"
			"'version' 	Anzeige des Autors und der Versionsnummer der HAW-Shell\n"
			"'/[Pfadname]' 	Wechsel des aktuellen Arbeitsverzeichnisses (analog zu cd)." 
			"Es muss immer ein kompletter Pfadname eingegeben werden.\n"
			"'help' 		Anzeige der möglichen Built-In-Befehle mit Kurzbeschreibung\n" };
 printf("%s", menu);

}

// Check welches Built-in Befehl
void checkForBuiltIn() {
    int i = 0;
	if ((input[0] != '/')) {
		 while(options[i]) {
			if (strcmp(options[i], input) == 0) { 
				switch(i) {
		   			case 0: menu(); break;
		   			case 1: printVersion(); break;
		   			default: printf("Tschüss!\n"); exit(0); break;
				}
			isBuiltIn = true; 		
			break;
			}
		i++; 
   		}
	}
	else {
		chdir(input);	
	}
}


void prompt() {

 char buf[200];
 int PIDstatus;
 int status;

    
 while(true) {
    
    printf("(%s)%s $ ", getenv("USER"), getcwd(buf, sizeof(buf)));
    
    while(inputSize == 0 || inputSize > 30){
     fgets(input,30,stdin);
     inputSize = strlen(input);
    }
    inputSize = 0; // resetSize, sont infinity loop

    // link http://stackoverflow.com/questions/2693776/removing-trailing-newline-character-from-fgets-input
    strtok(input, "\n");

    checkForBuiltIn();

    if (!isBuiltIn) {
	// Befehl preparieren fuer execvp (Pfad und Pointer mit einem Befehl)
	char *name[] = {
       		"/bin/bash",        
        	"-c",
       		input,
       		NULL
    	};	
		
	PIDstatus = fork();
	if (PIDstatus < 0) {
		printf("Unable to fork");
		continue;		
	}
		
	// FÜr kein Build-in Befehl, im Hintegrund laufen, ohne warten
	if (input[strlen(input)-1] == '&') {
		input[strlen(input)-1] = 0;
		if (PIDstatus == 0) {
			execvp(name[0], name);
    		}					
	}

	// Sofort ausfuehren, Standard Fall
	else {
		if (PIDstatus > 0) {
        		waitpid(PIDstatus, &status, 0);
    		}
    		else {
			execvp(name[0], name);
    		}		
	}
  }
 }
}

int main () {
   prompt();
}

