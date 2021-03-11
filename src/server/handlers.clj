(ns server.handlers
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [schema.core :as s]
            [server.scramble :refer [scramble?]]
            [shared.validation :refer [valid-input?]]))

; Schema for validating the input params
(s/defschema Input
  {:str1 s/Str
   :str2 s/Str})

; POST check for scrambled strings
(defn check-scramble? [req]
  (let [body (:body req)
        check-result (s/check Input body)]
    (cond
      check-result {:status 400
                    :body   check-result}
      (not (valid-input? (:str1 body))) {:status 400
                                              :body   "str1 is not lower case characters only!"}
      (not (valid-input? (:str2 body))) {:status 400
                                              :body   "str2 is not lower case characters only!"}
      :else {:status 200
             :body   (.toString (scramble? (:str1 body) (:str2 body)))})))

; definition of routes handlers
(defroutes app-routes
           (POST "/check-scramble" [] check-scramble?)
           (route/not-found "Resource not found!"))