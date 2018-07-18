(ns {{name}}.handler.dog
  (:require [integrant.core :as ig]
            [{{name}}.boundary.dog :as dog]
            [{{name}}.util.response :as response]
            [{{name}}.validator.dog :as validator]))

(defmethod ig/init-key ::create [_ {:keys [db]}]
  (fn [req]
    (let [dog (:body-params req)
          [errors _] (validator/validate-create dog)]
      (if errors
        (response/bad-request errors)
        (response/created (dog/create db dog))))))

(defmethod ig/init-key ::index [_ {:keys [db]}]
  (fn [req]
    (response/ok (dog/all db))))

(defmethod ig/init-key ::show [_ {:keys [db]}]
  (fn [req]
    (response/ok (dog/find db (-> req :params :id)))))

(defmethod ig/init-key ::update [_ {:keys [db]}]
  (fn [req]
    (response/updated (dog/update db (:params req)))))

(defmethod ig/init-key ::destroy [_ {:keys [db]}]
  (fn [req]
    (response/destroyed (dog/destroy db (-> req :params :id)))))
