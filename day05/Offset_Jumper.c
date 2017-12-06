/*
 ============================================================================
 Name        : Offset_Jumper.c
 Author      : Oliver Scherf
 Description : Advent of Code Day #5
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

FILE* open_file(char* file_name) {
	FILE* fh = fopen(file_name, "r");
	if (fh == NULL){
	    printf("The file does not exists %s!\n", file_name);
	    return NULL;
	}
	return fh;
}

void part_a() {
	FILE* file = open_file("input.txt");
	if (file == NULL) {
		exit(EXIT_FAILURE);
	}

	unsigned int charsRead = 0;
	size_t len = 100;
	char *line = malloc(sizeof(char) * len);

	unsigned int lines = 0;
    while ((charsRead = getline(&line, &len, file)) != -1) {
    	lines++;
    }

    rewind(file);
    int* jump_list = malloc(sizeof(int) * lines);
    for (int i = 0; i < lines; ++i) {
    	getline(&line, &len, file);
    	jump_list[i] = atoi(line);
    }

    int jumps = 0;
    int i = 0;
    do {
    	i = i + jump_list[i]++;
    	jumps++;
    } while (i < lines && i >= 0);

    printf("Jumps part A: %d\n", jumps);

    free(jump_list);
    free(line);
    fclose(file);
}

void part_b() {
	FILE* file = open_file("input.txt");
	if (file == NULL) {
		exit(EXIT_FAILURE);
	}

	unsigned int charsRead = 0;
	size_t len = 100;
	char *line = malloc(sizeof(char) * len);

	unsigned int lines = 0;
    while ((charsRead = getline(&line, &len, file)) != -1) {
    	lines++;
    }

    rewind(file);
    int* jump_list = malloc(sizeof(int) * lines);
    for (int i = 0; i < lines; ++i) {
    	getline(&line, &len, file);
    	jump_list[i] = atoi(line);
    }

    int jumps = 0;
    int i = 0;
    do {
    	if (jump_list[i] >= 3) {
			i = i + jump_list[i]--;
    	} else {
    		i = i + jump_list[i]++;
    	}
    	jumps++;
    } while (i < lines && i >= 0);

    printf("Jumps part B: %d\n", jumps);

    free(jump_list);
    free(line);
    fclose(file);
}

int main(void) {
	part_a();
	part_b();
	return EXIT_SUCCESS;
}
