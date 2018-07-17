(ns {{name}}.boundary.dog
  (:refer-clojure :exclude [update])
  (:require [clojure.java.jdbc :as jdbc]
            [duct.database.sql]
            [honeysql.core :as sql]
            [integrant.core :as ig]))

(defprotocol Dog
  (create [this dog])
  (all [this])
  (find [this id])
  (update [this dog])
  (destroy [this id]))

(extend-protocol Dog
  duct.database.sql.Boundary
  (create [{:keys [spec]} dog]
    (jdbc/with-db-transaction [tran spec]
      (first (jdbc/insert! tran :dogs dog))))
  (all [{:keys [spec]}]
    (jdbc/query spec (sql/format {:select [:*] :from :dogs})))
  (find [{:keys [spec]} id]
    (first (jdbc/query spec (sql/format {:select [:*] :from :dogs :where [:= :id id]}))))
  (update [{:keys [spec] :as this} dog]
    (jdbc/with-db-transaction [tran spec]
      (jdbc/execute! tran (sql/format {:update :dogs :sset dog}))
      (find this (:id dog))))
  (destroy [{:keys [spec] :as this} id]
    (jdbc/with-db-transaction [tran spec]
      (let [deleted (find this id)]
        (jdbc/delete! tran :dogs [:= :id id])
        deleted))))
