
#ifndef SUFFIXARRAY_H
#define	SUFFIXARRAY_H
#include<vector>
#include<cstring>
#include<algorithm>
#include<string>
using std::vector;
using std::string;
/**
 * Functor used to sort the suffix array.
 */
struct comparator {

    comparator(string* text)
    : data(text) {
    }

    bool operator()(const int firstIndex, const int secondIndex) {
        int result = std::strcmp(&(*data)[firstIndex], &(*data)[secondIndex]);
        if (result <= 0) {
            return true;
        }
        return false;
    }

private:
    string *data;
};

class SuffixArray {
public:

    SuffixArray(const char* text)
    : data(text) {
        build_suffix_array();
    }

    SuffixArray(const string& text)
    : data(text) {
        build_suffix_array();
    }

    /**
     * Returns starting indices of all occurrences of the string pattern.
     * |pattern| = m
     * |data| = N
     * Time complexity O(m*log(N)) 
     */
    vector<int> search(const char* pattern) {
        vector<int> indices;
        std::size_t size = std::strlen(pattern);
        int lower = lower_bound(size, pattern);
        if (lower == -1) {
            return indices;
        }
        int upper = upper_bound(lower, size, pattern);
        for (int i = lower; i < upper; i++) {
            indices.push_back(suffix_array[i]);
        }
        return indices;
    }
private:

    /**
     * Builds the suffix array.
     * |text| = N
     * Time complexity O(N^2log(N))
     */
    void build_suffix_array() {

        for (int i = 0; i < data.size(); i++) {
            suffix_array.push_back(i);
        }
        std::sort(suffix_array.begin(), suffix_array.end(), comparator(&data));
    }
    /**
     * The lower bound for the searched pattern
     * @param size number of characters to be compared
     * @param pattern the search pattern
     * @return  lower bound of the searched pattern or -1
     *  if the pattern is not in the suffix array
     */
    int lower_bound(std::size_t size, const char* pattern) {
        int left = 0;
        int right = suffix_array.size() - 1;
        int mid, result;
        while (left <= right) {
            mid = left + (right - left) / 2;
            result = std::strncmp(&data[suffix_array[mid]], pattern, size);
            if (result < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        int bound = right + 1;
        if (bound == suffix_array.size()) {
            return -1;
        }
        result = std::strncmp(&data[suffix_array[bound]], pattern, std::strlen(pattern));
        if (result == 0) {
            return bound;
        } else {
            return -1;
        }
    }
    /**
     * Upper bound for the searched pattern
     * @param left starting index
     * @param size number of characters to be compared
     * @param pattern the searched pattern
     * @return the upper bound for the searched pattern in the suffix array
     */
    int upper_bound(int left, std::size_t size, const char* pattern) {
        int right = suffix_array.size() - 1;
        int mid, result;
        while (left <= right) {
            mid = left + (right - left) / 2;
            result = std::strncmp(&data[suffix_array[mid]], pattern, size);
            if (result <= 0) {
                left = mid + 1;
                ;
            } else {

                right = mid - 1;
            }
        }
        return left;
    }

    string data;
    vector<int> suffix_array;
};

#endif	/* SUFFIXARRAY_H */

