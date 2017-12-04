/*
 ============================================================================
 Name        : Checksum.c
 Author      : Oliver Scherf
 Description : Advent of Code Day #2
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

FILE* open_file(char* file_name) {
	FILE* fh = fopen(file_name, "r");
	if (fh == NULL){
	    printf("The file does not exists %s!\n", file_name);
	    return NULL;
	}
	return fh;
}

int compute_checksum(char* line, unsigned int length) {
	unsigned int min = UINT_MAX;
	unsigned int max = 0;

	char c = *line;
	unsigned int num_pos = 0;

	char *buf = malloc(sizeof(char) * 10);
	while (1) {
		while (c != '\t' && c != '\0') {
			buf[num_pos++] = c;
			c = *++line;
		}
		buf[num_pos++] = '\0';
		printf("buf: %s\n", buf);
		unsigned next_number = atoi(buf);
		printf("Number is %d\n", next_number);
		num_pos = 0;
		if (next_number < min) {
			min = next_number;
		}
		if (next_number > max) {
			max = next_number;
		}
		if (c == '\0') {
			break;
		}
		c = *++line;
	}
	return max - min;
}

int main(void) {
	FILE* file = open_file("input.txt");
	if (file == NULL) {
		return EXIT_FAILURE;
	}

	unsigned int charsRead = 0;
	size_t len = 100;
	char *line = malloc(sizeof(char) * len);

	unsigned int sum = 0;
    while ((charsRead = getline(&line, &len, file)) != -1) {
    	sum += compute_checksum(line, charsRead);
    }

    printf("Sum: %u", sum);
	return EXIT_SUCCESS;
}
