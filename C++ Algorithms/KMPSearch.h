

#ifndef KMPSEARCH_H
#define	KMPSEARCH_H
#include<string>
#include<vector>

using std::string;
using std::vector;
/***
 * Knuth–Morris–Pratt string searching algorithm
 */
class KMPSearch{
public:
    
    KMPSearch(const string& str)
    :pattern(str), border(nullptr){
        border = new int[pattern.size()+1];
        process();
    }
    KMPSearch(const char* str)
    :pattern(str), border(nullptr){
        border = new int[pattern.size()+1];
        process();
    }
    void setPattern(const string& pattern) {
        this->pattern = pattern;
        if(border != 0){
            delete [] border;
            border = nullptr;
        }
        border = new int[pattern.size()+1];
        process();
    }
    

    string getPattern() const {
        return pattern;
    }
    
    void printBorder(){
        for(int i=0; i < pattern.size() + 1; i++){
            std::printf("%d ", border[i]);
        }
        std::printf("\n");
    }
    /**
     * Search for the pattern in the text.
     * @param text The text that is going to be searched.
     * @return Returns the starting index of the first match in the text or -1 
     * if there isn't any match.
     */
    int search(const string& text){
        int i = 0, j = 0;
        while(i < text.size()){
            while(j >= 0 && text[i] != pattern[j]){
                j = border[j];
            }
            i++;
            j++;
            if( j == pattern.size()){
                return i - j;
            }
        }
        return -1;
    }
    /**
     * Search for all the occurrences of the pattern in the text.
     * @param text The text that is going to be searched.
     * @return Returns all the starting indices of the pattern in the text.
     */
    vector<int> searchAll(const string& text){
        int i = 0, j = 0;
        vector<int> indices;
        while( i < text.size()){
            while( j >= 0 && text[i] != pattern[j]){
                j = border[j];
            }
            i++;
            j++;
            if( j == pattern.size()){
                indices.push_back(i - j);
                j  = border[j];
            }
        }
        return indices;
    }
    ~KMPSearch(){
        if(border != 0){
            delete [] border;
            border = nullptr;
        }
    }
private:
    string pattern;
    int* border;
    void process(){
        int i = 0, j = -1;
        border[i] = j;
        while ( i < pattern.size()){
            while( j >= 0 && pattern[i] != pattern[j]){
                j = border[j];
            }
            i++;
            j++;
            border[i] = j;
        }
    }
    
};


#endif	/* KMPSEARCH_H */

