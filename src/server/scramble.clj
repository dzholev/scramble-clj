(ns server.scramble
  (:require [shared.validation :refer [valid-input?]]))

(use '[clojure.string :only (replace-first)])

;
; The chars-map contains each character in the first string and how many times it has been found there.
; For "cedewaraaossoqqyt" this will build the following map: {\a 3, \c 1, \d 1, \e 2, \o 2, \q 2, \r 1, \s 2, \t 1, \w 1, \y 1}
;
(defn- can-built?
  "Check if str2 can be built using the given chars."
  [chars-map [ch & t]]
  (cond
    (nil? ch) true                                          ; we have reached the end of the second string, so it can be built using the provided map with chars
    (not (contains? chars-map ch)) false                    ; the current char is not present in the map
    (> (get chars-map ch) 0) (recur (update-in chars-map [ch] dec) t) ; we decrement the number for the current character and try for the rest of the second string
    :else false))

;
; This should work in O(m+n) time, where m and n are the lengths of the two input strings.
;
(defn scramble?
  "Predicate that returns true if a portion of str1 characters can be rearranged to match str2, otherwise returns false."
  [str1 str2]
  {:pre [(and (valid-input? str1) (valid-input? str2))]}
  (let [count-chars (fn [[k v]] (vector k (count v)))
        chars-map (->> str1
                       (group-by identity)
                       (map count-chars)
                       (into {}))]
    (can-built? chars-map str2)))
