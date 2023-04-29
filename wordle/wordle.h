#ifndef __WORDLE_H__
#define __WORDLE_H__

/* Default game configurations */
#define DEFAULTDICT "dict/common-words"
#define WORDLEN 5
#define WORDBUFSZ (WORDLEN + 1)
#define NATTEMPTS 6

/* Command line argument restrictions */
#define MINWORDLEN 3
#define MAXWORDLEN 10
#define MINATTEMPTS 1
#define MAXATTEMPTS 16

/* Utility functions */
void keygen(char *dict, int keylen, char *buf);
void print_title(int wordlen, int nattempts);
void get_input(char *buf, int wordlen);
void print_success_message(int nattempts);
void print_failure_message(char *answer);

/* Wordle function declaration for part 2 (dynamic allocation) */
int wordle(const char *key, int nattempts, char **attempts);

#endif //__WORDLE_H__
