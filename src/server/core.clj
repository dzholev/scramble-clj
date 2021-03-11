(ns server.core
  (:require [ring.adapter.jetty :refer :all]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [server.handlers :as h])
  (:gen-class))

(def app
  (-> h/app-routes
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-defaults api-defaults)))
