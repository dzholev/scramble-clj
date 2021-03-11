(ns server.scramble-test
  (:require [clojure.test :refer :all]
            [server.scramble :refer :all]))

(deftest test-violate-precondition-first-string
  (testing "Violate precondition for the first string."
    (let [str1 "abCd"
          str2 "abcd"]
      (is (thrown? AssertionError (scramble? str1 str2))))))

(deftest test-violate-precondition-second-string
  (testing "Violate precondition for the second string."
    (let [str1 "abcd"
          str2 "abCd"]
      (is (thrown? AssertionError (scramble? str1 str2))))))

(deftest test-violate-precondition-all-strings
  (testing "Violate precondition for all strings."
    (let [str1 "abcD"
          str2 "abCd"]
      (is (thrown? AssertionError (scramble? str1 str2))))))

(deftest test-first-string-nil
  (testing "Check when the first string is nil."
    (let [str1 nil
          str2 "abcd"
          result (scramble? str1 str2)]
      (is (= result false)))))

(deftest test-first-string-empty
  (testing "Check when the first string is empty."
    (let [str1 ""
          str2 "abcd"
          result (scramble? str1 str2)]
      (is (= result false)))))

(deftest test-second-string-nil
  (testing "Check when the second string is nil."
    (let [str1 "abcd"
          str2 nil
          result (scramble? str1 str2)]
      (is (= result true)))))

(deftest test-second-string-empty
  (testing "Check when the second string is empty."
    (let [str1 "abcd"
          str2 ""
          result (scramble? str1 str2)]
      (is (= result true)))))

(deftest test-second-string-empty
  (testing "Check when the second string is empty."
    (let [str1 "abcd"
          str2 ""
          result (scramble? str1 str2)]
      (is (= result true)))))

(deftest test-positive-no-repeats
  (testing "Check simple positive case with no repeats."
    (let [str1 "abcdefgh"
          str2 "hda"
          result (scramble? str1 str2)]
      (is (= result true)))))

(deftest test-negative-no-repeats
  (testing "Check simple negative case with no repeats."
    (let [str1 "abcdefgh"
          str2 "hdas"
          result (scramble? str1 str2)]
      (is (= result false)))))

(deftest test-positive-repeats-first-string
  (testing "Check positive case with repeats in the first string."
    (let [str1 "abacdefbghd"
          str2 "hda"
          result (scramble? str1 str2)]
      (is (= result true)))))

(deftest test-negative-repeats-first-string
  (testing "Check negative case with repeats in the first string."
    (let [str1 "abacdefbghd"
          str2 "hdas"
          result (scramble? str1 str2)]
      (is (= result false)))))

(deftest test-negative-repeats-second-string
  (testing "Check negative case with repeats in the second string."
    (let [str1 "abcdefgh"
          str2 "hdad"
          result (scramble? str1 str2)]
      (is (= result false)))))

(deftest test-positive-repeats-all-strings
  (testing "Check positive case with repeats in all strings."
    (let [str1 "abacdefbghd"
          str2 "hdaba"
          result (scramble? str1 str2)]
      (is (= result true)))))

(deftest test-negative-repeats-all-strings
  (testing "Check negative case with repeats in all strings."
    (let [str1 "abacdefbghd"
          str2 "hdabada"
          result (scramble? str1 str2)]
      (is (= result false)))))
