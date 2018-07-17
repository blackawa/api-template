(ns {{name}}.handler.health
  (:require [integrant.core :as ig]))

(defmethod ig/init-key ::index [_ _]
  (fn [_] {:status 200 :body {:message "Hello :)"}}))
