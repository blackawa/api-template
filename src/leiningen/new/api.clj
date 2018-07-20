(ns leiningen.new.api
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "api"))

(defn api
  "API project template which uses
   - integrant
   - aero"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' api project.")
    (->files data
             [".gitignore" (render ".gitignore" data)]
             [".circleci/config.yml" (render ".circleci/config.yml" data)]
             ["README.md" (render "README.md" data)]
             ["dev/src/user.clj" (render "dev/user.clj" data)]
             ["docker-compose.yml" (render "docker-compose.yml" data)]
             ["project.clj" (render "project.clj" data)]
             ["resources/config.edn" (render "config.edn" data)]
             ["resources/migrations/create_dogs.up.sql" (render "create_dogs.up.sql" data)]
             ["resources/migrations/create_dogs.down.sql" (render "create_dogs.down.sql" data)]
             ["src/{{sanitized}}/app.clj" (render "app.clj" data)]
             ["src/{{sanitized}}/handler/health.clj" (render "handler/health.clj" data)]
             ["src/{{sanitized}}/handler/not_found.clj" (render "handler/not_found.clj" data)]
             ["src/{{sanitized}}/handler/dog.clj" (render "handler/dog.clj" data)]
             ["src/{{sanitized}}/boundary/dog.clj" (render "boundary/dog.clj" data)]
             ["src/{{sanitized}}/util/response.clj" (render "util/response.clj" data)]
             ["src/{{sanitized}}/validator/dog.clj" (render "validator/dog.clj" data)]
             ["src/{{sanitized}}/component.clj" (render "component.clj" data)]
             ["swagger.yaml" (render "swagger.yaml" data)])))
