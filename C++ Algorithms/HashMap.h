/*
 * HashMap implementation in C++
 * @author:
 * @keywords: Data Structures, Map, Hashing
 * @modified:
 *
 * Implement an unordered map with STL string keys.
 * Use load factor ((number of elements + 1) / number of buckets) of at least 0.25.
 *
 * (optional): Make it also work for integers (int) as keys.
 * (optional): Make it also work for arbitrary objects as keys.
 */

#ifndef __HASHMAP_H__
#define __HASHMAP_H__
#include<iostream>
#include <string>
#include <exception>

#include<vector>
#include<cmath>
#include<list>
#include<cstdio>
#include<algorithm>
template<typename V>
class HashMap
{
    public:

        /*
         * Constructs an empty HashMap with minimum minBuckets buckets and
         * maxium maxBuckets buckets. These lower and upper bounds on the
         * number of buckets should be maintained at all times!
         * Expected complexity: O(minBuckets)
         */
        HashMap(int minBuckets = 1, int maxBuckets = 1000000000)
                :min_buckets(minBuckets), max_buckets(maxBuckets), elements_count(0), hashList(minBuckets)
        {
                 if(maxBuckets >= MOD){
                      MOD = next_prime(maxBuckets);
                  }  
                    
                   
        }

        /*
         * Destroys all values in the HashMap and the HashMap itself.
         * Expected complexity: O(N + B), where B is the number of buckets.
         */
        ~HashMap()
        {
            
        }

        /*
         * Creates a new HashMap using the values of already existing instance.
         * Expected complexity: O(N' + B'), where N' and B' are the number of
         * elements and the number of buckets, respectively, in the existing
         * instance.
         * Note: optional
         */

        // HashMap(const HashMap<V>& other);

        /*
         * Destroys all current values and assigns new ones using the values
         * of another existing instance.
         * Expected complexity: O(N + N' + B + B'), where N' and B' are the
         * number of elements and the number of buckets, respectively, in the
         * existing instance.
         * Note: optional
         */

        // HashMap<V>& operator = (const HashMap<V>& other);

        

        /*
         * Removes all existing values from the HashMap and leaves it empty.
         * Expected complexity: O(N + B)
         */
        void clear()
        {
            for(auto &list:hashList){
                list.clear();
            }
            elements_count = 0;
        }

        /*
         * Returns the number of elements in the HashMap.
         * Expected complexity: O(1)
         */
        int size() const
        {
            return elements_count;
        }

        /*
         * Returns the number of allocated buckets in the HashMap.
         * Expected complexity: O(1)
         */
        int capacity() const
        {
            return hashList.capacity();
        }

        /*
         * Checks if a value is already associated with a certain key.
         * Expected complexity: O(H + 1), where O(H) is needed to hash the key.
         */
        bool contains(const std::string & key) const
        {
          const auto & list = hashList[my_hash(key)];
           for(auto  el: list){
               if(el.first == key){
                   return true;
               }
           }
           return false;
        }
        int get_collision_number(){
            int collisions = 0;
            for(int i=0; i < hashList.size(); i++){
                auto &list = hashList[i];
                if(!list.empty()){
                    collisions += (list.size() - 1);
                }
            }
            return collisions;
        }
        /*  
         * Inserts a new (key, value) pair or replaces the current value with
         * another one, if the key is already present.
         * Expected complexity: O(H + 1), where O(H) is needed to hash the key.
         */
        void insert(const std::string & key,  const V &           value)
        
       {
            auto &list = hashList[my_hash(key)];
            for(auto &el: list){
                if(el.first == key){
                    el.second = value;
                    return;
                }
            }
            list.push_back(std::make_pair(key, value));
            elements_count++;
            double factor = (double)(elements_count)/ (double)hashList.capacity();
            factor = int(factor*sd + (factor<0? -0.5 : 0.5))/sd;
            if(factor >= 0.75){
                if(hashList.capacity() == max_buckets){
                    return;
                }
                int new_size = elements_count*2;
                if(new_size >= max_buckets){
                    resize(max_buckets);
                    return;
                }
                resize(new_size);
            }
        }

        /*
         * Removes a key and the associated with it value from the HashMap.
         * Expected complexity: O(H + 1), where O(H) is needed to hash the key.
         */
        void erase(const std::string & key)
        {
            auto &list = hashList[my_hash(key)];
            auto listIterator = list.end();
            for(auto i = list.begin(); i != list.end(); i++){
                if((*i).first == key){
                    listIterator = i;
                }
            }
            if(listIterator != list.end()){
                list.erase(listIterator);
                elements_count--;
                double factor = (double)(elements_count)/ (double)hashList.capacity();
                factor = int(factor*sd + (factor<0? -0.5 : 0.5))/sd;
                if(factor <= 0.25){
                    resize(hashList.capacity()/2);
                }
                
            }
            
        }
        

        /*
         * Returns a reference to the value, associated with a certain key.
         * If the key is not present in the HashMap, throw an error or fail
         * by assertion.
         * Expected complexity: O(H + 1), where O(H) is needed to hash the key.
         */
        V & get(const std::string & key)
        {
            auto &list = hashList[my_hash(key)];
            for(auto &element: list){
                if(element.first == key){
                    return std::get<1>(element);
                }
            }
            throw std::runtime_error("No such element!");
        }

        /*
         * Same as get().
         */
        V & operator [](const std::string & key)
        {
            return get(key);
        }

    private:
        std::vector<std::list<std::pair<std::string, V> > > hashList;
        int MOD = 1000000007;
        const int BASE = 127;
        int elements_count;
        int max_buckets;
        int min_buckets;
        const double sd = 100;
        
        bool is_prime(int n)
        {
            if (n < 2)
            {
                return false;
            }

            for (int i = 2; true; i++)
            {
                int q = n / i;

                if (q < i)
                {
                    return true;
                }

                if (n == q * i)
                {
                    return false;
                }
            }

            return true;
        }

        int next_prime(int n)
        {
            n++;

            for (; !(is_prime(n)); n++);

            return n;
        }

        int my_hash(const std::string & str) const
        {
            int ret = 1;

            for (int i = 0; i < (int) str.size(); i++)
            {
                ret = ((long long) ret * BASE + str[i]) % MOD;
            }

            return ret%hashList.capacity();
        }
        /*
         * Resizes the HashMap so it has at least numBuckets slots (buckets).
         * Re-hashes the current values if needed.
         * Expected time complexity: O(N + numBuckets)
         */
        void resize(int numBuckets)
        {
            
            std::vector<std::list<std::pair<std::string, V> > > oldList = std::move(hashList);
            hashList.resize(numBuckets);
            clear();
            for(int i=0; i < oldList.size(); i++){
               std::list<std::pair<std::string, V> > list = oldList[i];
                if(!list.empty()){
                    for(auto element: list){
                        insert(element.first, element.second);
                    }
                }
            }
        }
};
#endif // #ifndef __HASHMAP_H__


//~ Formatted by Jindent --- http://www.jindent.com
