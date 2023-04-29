#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "wordle.h"

#define MAXCANDIDATES 3000

void get_input(char *buf, int wordlen)
{
    char *line = NULL;
    size_t len = 0;
    int bytes_read = getline(&line, &len, stdin);
    if (bytes_read == -1){
	    free(line);
	    return;
    }
    line[strlen(line) - 1] = '\0';
    strncpy(buf, line, (size_t)wordlen);
    free(line);
    for (int i = 0; i < strlen(buf); i++) {
        buf[i] = tolower(buf[i]);
    }
}

void print_title(int wordlen, int nattempts)
{
    printf("Wordle (Difficulty: easy)\n");
    printf("You have %d attempts to guess a %d-letter word.\n", nattempts, wordlen);
    printf("(Enter lowercase letters only.)\n");
}

void keygen(char *dict, int keylen, char *buf)
{
    FILE *fp;
    char *line = NULL;
    size_t len = 0;
    ssize_t nread;

    char candidates[MAXCANDIDATES][MAXWORDLEN + 1];
    int ncandidates = 0;

    if (!(fp = fopen(dict, "r"))) {
        perror("fopen");
        exit(EXIT_FAILURE);
    }

    // Gather candidates with given length
    while (ncandidates < MAXCANDIDATES && (nread = getline(&line, &len, fp)) != -1) {
        line[strlen(line) - 1] = '\0'; // strip trailing newline
        if (strlen(line) != keylen)
            continue;

        strcpy(candidates[ncandidates++], line);
    }
    free(line);
    fclose(fp);

    // Randomly choose a candidate word as the answer key
    srand(time(NULL));
    int keyidx = rand() % ncandidates;
    strcpy(buf, candidates[keyidx]);
}

void print_success_message(int nattempts)
{
    printf("Success! You guessed the word in %d attempt(s)!\n", nattempts);
}

void print_failure_message(char *answer)
{
    printf("Better luck next time! The word was %s.\n", answer);
}
