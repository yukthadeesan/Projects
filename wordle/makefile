# Which compiler to use
CC = gcc
# Compiler flags
CFLAGS = -m32 -Wall -I. -Werror=vla -g
# Final executable
TARGET = wordle
# Constituent object files
OBJ_PART1 = part1.o wordle.o utils.o
OBJ_PART2 = part2.o wordle.o utils.o

# Default Make recipe
default: part2

# part 1
part1: $(OBJ_PART1)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJ_PART1)

# Part 2
part2: $(OBJ_PART2)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJ_PART2)

# Clean recipe removes all build artifacts
clean:
	$(RM) $(TARGET) $(OBJ_PART1) $(OBJ_PART2)
