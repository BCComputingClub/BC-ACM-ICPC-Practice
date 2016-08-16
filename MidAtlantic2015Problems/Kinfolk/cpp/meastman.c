#include <stdio.h>

char *nth[] = {"1st", "2nd", "3rd"};
char *removed[] = {"once", "twice", "thrice"};
#define MF(m, f) sex == 'M' ? m : f

void handle_case(int id_a, int id_b, char sex) {
    int dist_a = 0, dist_b = 0;
    while (id_a != id_b) {
        if (id_a > id_b) dist_a++, id_a = (id_a - 1) / 2;
        else             dist_b++, id_b = (id_b - 1) / 2;
    }
    int smaller = dist_a < dist_b ? dist_a : dist_b;
    int delta = dist_a - smaller + dist_b - smaller;

    if (dist_a == 0 && dist_b == 0) {
        puts("self");
    } else if (dist_a == 1 && dist_b == 1) {
        puts("sibling");
    } else if (smaller <= 1 && delta <= 4) {
        switch (delta) {
            case 4: fputs("great-", stdout);
            case 3: fputs("great-", stdout);
            case 2: fputs("grand",  stdout);
        }
        if      (dist_a == 0) puts("child");
        else if (dist_b == 0) puts("parent");
        else if (dist_a == 1) puts(MF("nephew", "niece"));
        else                  puts(MF("uncle",  "aunt"));
    } else if (smaller >= 2 && smaller <= 4 && delta <= 3) {
        if (delta == 0) printf("%s cousin\n", nth[smaller - 2]);
        else printf("%s cousin %s removed\n", nth[smaller - 2], removed[delta - 1]);
    } else {
        puts("kin");
    }
}

int main(int argc, char** argv) {
    int id_a, id_b;
    char sex;
    while (scanf("%d %d %c", &id_a, &id_b, &sex) && id_a >= 0) {
        handle_case(id_a, id_b, sex);
    }
    return 0;
}
