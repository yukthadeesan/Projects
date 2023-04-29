/**
 * CSE 30: Computer Organization & Systems Programming
 * Winter Quarter, 2023
 * Programming Assignment III
 *
 * wordle.c
 * This program implements a command-line version of the popular wordle game.
 * (https://en.wikipedia.org/wiki/Wordle)
 * This second part handles taking in CLAs and allows the user to configure
 * the word length and number of attempts.
 *
 * Author: Jerry Yu & Arjun Sampath
 * January 2023
 */

#include <getopt.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "wordle.h"

/**
 * Parse command line arguments and update game configuration values.
 * Command line options:
 *      -d <dictionary>     Configure what dictionary file to use.
 *      -a <n>              Number of attempts allowed.
 *      -l <len>            Length of target word.
 */
int parse_getopt(int argc, char **argv, unsigned long *nattempts, unsigned long *wordlen,
                 char **dict)
{
    int opt;
    char *endptr;
    while ((opt = getopt(argc, argv, "d:a:l:")) != -1) {
        switch (opt) {
        case 'd':
            *dict = optarg;
            break;
        case 'a':
            if (((*nattempts = strtoul(optarg, &endptr, 10)) < MINATTEMPTS) ||
                (*nattempts > MAXATTEMPTS) || (*endptr != '\0')) {
                fprintf(stderr, "%s: -a value must be in between %d and %d\n", argv[0], MINATTEMPTS,
                        MAXATTEMPTS);
                return -1;
            }
            break;
        case 'l':
            if (((*wordlen = strtoul(optarg, &endptr, 10)) < MINWORDLEN) ||
                (*wordlen > MAXWORDLEN) || (*endptr != '\0')) {
                fprintf(stderr, "%s: -l value must be in between %d and %d\n", argv[0], MINWORDLEN,
                        MAXWORDLEN);
                return -1;
            }
            break;
        case '?':
            // fprintf(stderr, "%s: unknown option -%c\n", argv[0], optopt);
            return -1;
        default:
            break;
        }
    }
    return 0;
}

/**
 * Allocate a 2D array to hold game data.
 * There should be as many rows as the maximum # of attempts allowed.
 * Each row stores one attempt at guessing the word made by the player.
 *
 * nattempts    The number of attempts allowed.
 * wordbufsz    How large the string buffer should be to store a guess.
 * returns      The dynamically allocated 2D array.
 */
char **alloc_attempts(int nattempts, int wordbufsz)
{
    /* TODO: Implement this function */
    return NULL; // placeholder
}

/**
 * Free the 2D array which holds game data.
 *
 * attempts     The 2D array for game data
 * nattempts    The number of attempts allowed (# of rows in 2D array)
 */
void free_attempts(char **attempts, int nattempts)
{
    /* TODO: Implement this function */
}

/**
 * Allocate a char buffer to store the target word.
 *
 * wordbufsz    How large the buffer should be
 * returns      The dynamically allocated buffer.
 */
char *alloc_answer(unsigned long wordbufsz)
{
    /* TODO: Implement this function */
    return NULL; // placeholder
}

int main(int argc, char **argv)
{
    /* Game settings */
    unsigned long nattempts;
    unsigned long wordlen;
    unsigned long wordbufsz;
    char *dict;
    char *answer = NULL;
    char **attempts = NULL;

    /* Default values, can be changed by command line arguments. */
    nattempts = NATTEMPTS;
    wordlen = WORDLEN;
    dict = DEFAULTDICT;

    /* Parse command line arguments */
    if (parse_getopt(argc, argv, &nattempts, &wordlen, &dict) == -1) {
        return EXIT_FAILURE;
    }

    /* Size of buffer needed to hold the answer/a player guess. */
    wordbufsz = wordlen + 1;

    /* Allocate memory for the attempts 2D array */
    /* TODO: implement alloc_attempts and alloc_answer */
    if (!(attempts = alloc_attempts(nattempts, wordbufsz)) || !(answer = alloc_answer(wordbufsz))) {
        fprintf(stderr, "Error: failed to allocate memory.\n");
        free_attempts(attempts, nattempts);
        return EXIT_FAILURE;
    }

    print_title(wordlen, nattempts);

    /* 
     * TODO:
     * Generate the target word. 
     */

    /* wipe out the garbadge values in the game data. */
    for (int i = 0; i < nattempts; i++) {
        for (int j = 0; j < wordbufsz; j++) {
            attempts[i][j] = '\0';
        }
    }

    int n;
    if ((n = wordle(answer, nattempts, attempts))) {
        print_success_message(n);
    } else {
        print_failure_message(answer);
    }

    /* 
     * TODO:
     * Free all dynamically allocated memory 
     */

    return EXIT_SUCCESS;
}
