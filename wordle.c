#include "wordle.h"

/**
 * Play the wordle game.
 *
 * key          The answer word. Should be lowercase only.
 * nattempts    The total number of attempts allowed.
 * attempts     An array of string buffers to keep track of each attempt.
 * return       Number of guesses the player took to guess the right word,
 *              return 0 if player failed to guess the word.
 */
int wordle(const char *key, int nattempts, char **attempts)
{
    /* TODO: Implement this function
     * You should be able to copy-paste the body of your part 1 wordle 
     * function here without any modifications. Do NOT change the parameters
     * to this function when you do so. The type of `attempts` must be (char **) 
     * in part 2.
     */
     char guess[WORDBUFSZ];
    int correct = 0;
    int tries = 0;
    char display[6];
    display[5] = '\0';
    for (int i = 0; i < WORDLEN; i++) {
        display[i] = '_';
    }
    printf("%s\r",display);
    strcpy(attempts[0],display);
    while (correct < WORDLEN && tries < 6) {
        correct = 0;
        get_input(guess, WORDBUFSZ);
        tries++;
        for(int i = 0; i < WORDLEN; i++) {
            for(int j = 0; j < WORDLEN; j++){
                if (guess[i] == key[j] && display[j]== '_'){
                    display[j]=key[j];
                }
            }
        }
        for(int i = 0; i < WORDLEN; i++) {
            if (guess[i] == key[i]){
                    correct++;}
                    
            else if (tries == 6)
            {
                return 0;
            }
        }
        strcpy(attempts[tries],display);
         if (correct != 5){
         printf("%s\r",attempts[tries]);} 
    }
    return tries;
}
