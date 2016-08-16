// Sample solution for Talking About Numbers
// Author: Bryce Sandlund

#include <iostream>
#include <string>

using namespace std;

typedef long long ll;

string low[] = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven",
    "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};

string tens[] = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

bool is_zero(string n) {
    return stoll(n) == 0LL;
}

void print_tens(string n) {
    if (stoll(n) <= 19LL) {
        cout << low[stoll(n)];
    }
    else {
        ll tens_d = stoll(n.substr(0, 1));
        cout << tens[tens_d];
        if (n[1] != '0') {
            cout << "-" << low[stoll(n.substr(1))];
        }        
    }
}

void print_hundreds(string n) {
    if (n[0] == '0') {
        print_tens(n.substr(1));
    }
    else {
        ll hundreds_d = stoll(n.substr(0, 1));
        cout << low[hundreds_d] << " hundred";
        if (!is_zero(n.substr(1))) {
            cout << " and ";
            print_tens(n.substr(1));
        }
    }
}

void print_num(string n) {
    string millions = n.substr(0, 3);
    string thousands = n.substr(3, 3);
    string hundreds = n.substr(6);
    
    if (!is_zero(millions)) {
        print_hundreds(millions);
        cout << " million";

        if (!is_zero(thousands)) {
            cout << ", ";
        }
        else if (!is_zero(hundreds)) {
            if (hundreds[0] == '0') {
                cout << " and ";
            }
            else {
                cout << ", ";
            }
        }
    }

    if (!is_zero(thousands)) {
        print_hundreds(thousands);
        cout << " thousand";

        if (!is_zero(hundreds)) {
            if (hundreds[0] == '0') {
                cout << " and ";
            }
            else {
                cout << ", ";
            }
        }
    }

    if (!is_zero(hundreds) || (is_zero(millions) && is_zero(thousands))) {
        print_hundreds(hundreds);
    }
}

int main() {
    string n;
    for (ll cs = 1; cin >> n && stoll(n) >= 0; ++cs) {
        string zeros = "000000000";
        for (ll i = 0; i < n.size(); ++i) {
            zeros[zeros.size()-n.size()+i] = n[i];
        }
        print_num(zeros);
        cout << endl;
    }
    return 0;
}
