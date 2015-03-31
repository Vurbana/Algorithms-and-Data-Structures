/*
 * sort.h
 *
 *  Created on: Mar 14, 2015
 *      Author: vankata
 */

#ifndef C___ALGORITHMS_SORT_H_
#define C___ALGORITHMS_SORT_H_
#include <algorithm>

template<typename Object>
void selection_sort(Object *arr, int size) {
	for (int i = 0; i < size - 1; i++) {
		int current = i;
		for (int j = i + 1; j < size; j++) {
			if (arr[current] > arr[j]) {
				current = j;
			}
		}
		std::swap(arr[i], arr[current]);
	}
}
template<typename Object>
void insertion_sort(Object *arr, int size) {
	for (int i = 0; i < size; i++) {
		int j = i;
		Object current = arr[i];
		while (j > 0 && current < arr[j - 1]) {
			arr[j] = arr[j - 1];
			j--;
		}
		arr[j] = current;

	}
}
template<typename Object>
void merger(Object* arr, int left, int mid, int right) {
	Object temp[right + 1];
	for (int i = left; i <= right; i++) {
		temp[i] = arr[i];
	}
	int k = left;
	int l = left;
	int h = mid + 1;
	while (l <= mid && h <= right) {
		if (temp[l] > temp[h]) {
			arr[k] = temp[h];
			h++;
		} else {
			arr[k] = temp[l];
			l++;
		}
		k++;
	}
	while (l <= mid) {
		arr[k] = temp[l];
		l++;
		k++;
	}
}
template<typename Object>
void merge_sort(Object* arr, int left, int right) {
	if (left < right) {
		int mid = left + (right - left) / 2;
		merge_sort(arr, left, mid);
		merge_sort(arr, mid + 1, right);
		merger(arr, left, mid, right);

	}
}
template<typename Object>
int partition(Object* arr, int lo, int hi) {
	int pivot_index = lo + ((hi - lo) / 2);
	Object pivot = arr[pivot_index];
	std::swap(arr[hi], arr[pivot_index]);
	int storeIndex = lo;
	for (int i = lo; i <= hi - 1; i++) {
		if (arr[i] < pivot) {
			std::swap(arr[i], arr[storeIndex]);
			storeIndex++;
		}
	}
	std::swap(arr[hi], arr[storeIndex]);
	return storeIndex;
}

template<typename Object>
void quicksort(Object* arr, int lo, int hi) {
	if (lo < hi) {
		int pivot = partition(arr, lo, hi);
		quicksort(arr, lo, pivot - 1);
		quicksort(arr, pivot + 1, hi);
	}
}

#endif /* C___ALGORITHMS_SORT_H_ */
