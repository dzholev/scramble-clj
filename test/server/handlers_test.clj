(ns server.handlers-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [server.core :refer :all]))

(deftest wrong-body1-test
  (let [resp (app (-> (mock/request :post "/check-scramble")
                      (mock/json-body {:foo "bar"})))]
    (is (= 400 (:status resp)))))

(deftest wrong-body2-test
  (let [resp (app (-> (mock/request :post "/check-scramble")))]
    (is (= 400 (:status resp)))))

(deftest wrong-first-param-test
  (let [resp (app (-> (mock/request :post "/check-scramble")
                      (mock/json-body {:str1 "bar1"
                                       :str2 "foo"})))]
    (is (= 400 (:status resp)))
    (is (= "str1 is not lower case characters only!" (:body resp)))))

(deftest wrong-second-param-test
  (let [resp (app (-> (mock/request :post "/check-scramble")
                      (mock/json-body {:str1 "bar"
                                       :str2 "foO"})))]
    (is (= 400 (:status resp)))
    (is (= "str2 is not lower case characters only!" (:body resp)))))

(deftest wrong-all-param-test
  (let [resp (app (-> (mock/request :post "/check-scramble")
                      (mock/json-body {:str1 "bar#"
                                       :str2 "foO"})))]
    (is (= 400 (:status resp)))
    (is (= "str1 is not lower case characters only!" (:body resp)))))

(deftest false-simple-test
  (let [resp (app (-> (mock/request :post "/check-scramble")
                      (mock/json-body {:str1 "bar"
                                       :str2 "foo"})))]
    (is (= 200 (:status resp)))
    (is (= "false" (:body resp)))))

(deftest true-simple-test
  (let [resp (app (-> (mock/request :post "/check-scramble")
                      (mock/json-body {:str1 "bar"
                                       :str2 "ra"})))]
    (is (= 200 (:status resp)))
    (is (= "true" (:body resp)))))
