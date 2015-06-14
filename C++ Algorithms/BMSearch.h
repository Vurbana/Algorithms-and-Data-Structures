/* 
 * File:   BMSearch.h
 * Author: vankata
 *
 * Created on June 11, 2015, 3:15 PM
 */

#ifndef BMSEARCH_H
#define	BMSEARCH_H
#include<string>
#include<vector>
#include<cstdlib> 
#include<algorithm>
using std::string;
using std::vector;

/***
 * Boyer-Moore string matching algorithm
 */
class BMSearch {
public:
    BMSearch()
    :pattern(""), border(nullptr), shiftDistance(nullptr){
        process();
    }
    BMSearch(const string& str)
    : pattern(str), border(nullptr), shiftDistance(nullptr) {
        process();
    }

    BMSearch(const char *str)
    : pattern(str) {
        process();
    }

    void set_pattern(const string& p) {
        pattern = p;
        clear();
        process();
    }
    string get_pattern(){
        return pattern;
    }
    void set_pattern(const char * p) {
        pattern = p;
        clear();
        process();
    }

    ~BMSearch() {
        clear();
    }

    /**
     * Search for the pattern in the text.
     * @param text The text that is going to be searched.
     * @return Returns the starting index of the first match in the text or -1 
     * if there isn't any match.
     */
    int search(const string& text) {
        if(text.size() < pattern.size()){
            return -1;
        }
        int i = 0, j;  
        while (i <= (text.size() - pattern.size())) {
            j = pattern.size() - 1;
            while (j >= 0 && pattern[j] == text[i + j]) j--;
            if (j < 0) {    
                return i;
            } else{
                i += std::max(shiftDistance[j + 1], (j - occurrences[text[i + j]]));
            }
                
        }
        return -1;
    }

    /**
     * Search for all the occurrences of the pattern in the text.
     * @param text The text that is going to be searched.
     * @return Returns all the starting indices of the pattern in the text.
     */
    vector<int> searchAll(const string& text) {
        vector<int> vec;
        if(text.size() < pattern.size()){
            return vec;
        }
        int i = 0, j;
        while (i <= (text.size() - pattern.size())) {
            j = pattern.size() - 1;
            while (j >= 0 && pattern[j] == text[i + j]){
                j--;
            }
            if (j < 0) {
                vec.push_back(i);
                i += shiftDistance[0];
            } else{
                 i += std::max(shiftDistance[j + 1], j - occurrences[text[i + j]]);
            }
               
        }
        return vec;
    }
private:
    string pattern;
    int occurrences[256];
    int *border;
    int *shiftDistance;

    void process() {
        border = new int[pattern.size() + 1];
        shiftDistance = new int[pattern.size() + 1];
        for(int i=0; i < 256; i++){
            occurrences[i] = -1;
        }
        for(int i=0; i< pattern.size()+ 1; i++){
            border[i] = 0;
            shiftDistance[i] = 0;
        }
        bad_character();
        good_suffix_case_one();
        good_suffix_case_two();
    }

    void good_suffix_case_one() {
        int i = pattern.size(), j = pattern.size() + 1;
        border[i] = j;
        while (i > 0) {
            while (j <= pattern.size() && (pattern[i - 1] != pattern[j - 1])) {
                if (shiftDistance[j] == 0) shiftDistance[j] = j - i;
                
                j = border[j];
            }
            i--;
            j--;
            border[i] = j;
        }
    }

    void good_suffix_case_two() {
        int j;
        j = border[0];
        for (int i = 0; i <= pattern.size(); i++) {
            if (shiftDistance[i] == 0) {
                shiftDistance[i] = j;
            }
            if (i == j) {
                j = border[j];
            }
        }
    }

    void bad_character() {
        for (int i = 0; i < pattern.size(); i++) {
            occurrences[ pattern[i]] = i;
        }
    }

    void clear() {
        if (border != nullptr) {
            delete [] border;
            border = nullptr;
        }
        if (shiftDistance != nullptr) {
            delete [] shiftDistance;
            shiftDistance = nullptr;
        }
    }

};





#endif	/* BMSEARCH_H */

