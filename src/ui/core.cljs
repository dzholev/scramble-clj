(ns ui.core
  (:require
    [reagent.core :as reagent :refer [atom]]
    [reagent.dom :as rd]
    [ajax.core :refer [POST]]
    [clojure.string :as string]
    [shared.validation :refer [valid-input?]]))

(enable-console-print!)

;; app state
(defonce app-state (atom {:input  {:str1 ""
                                   :str2 ""}
                          :result ""
                          :errors nil}))

(defn update-state! [path value]
  (swap! app-state assoc-in path value))

(defn success-handler [response]
  (do
    (update-state! [:result] (str response))
    (update-state! [:errors] nil)))

(defn error-handler [response]
  (do
    (update-state! [:result] "")
    (update-state! [:errors :server-error] (str (:response response)))))

(defn submit-data! [data]
  (POST "/check-scramble"
        {:params        data
         :format        :json
         :handler       success-handler
         :error-handler error-handler}))

(defn errors-component [id]
  (when-let [error (id (:errors @app-state))]
    [:div.notification.is-danger error]))

(defn input [id label]
  [:div.field
   [:label {:for  id
            :type "text"} label ": "]
   [errors-component id]
   [:input
    {:id        id
     :value     (get-in @app-state [:input id])
     :on-change (fn [evt]
                  (let [value (-> evt .-target .-value)
                        valid? (valid-input? value)
                        _ (update-state! [:input id] value)
                        _ (update-state! [:result] "")]
                    (if valid?
                      (update-state! [:errors id] nil)
                      (update-state! [:errors id] "Must contain only lower-case characters!"))))
     }]])

(defn button []
  [:button
   {:on-click #(submit-data! (:input @app-state))}
   "Test"])

(defn main-component []
  [:div
   [:h1 "Scramble test"]
   [errors-component :server-error]
   [input :str1 "String 1"]
   [input :str2 "String 2"]
   [button]
   [:h3 (:result @app-state)]])

(rd/render [main-component]
           (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
