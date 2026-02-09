(ns api-demo.http
  (:require
   [api-demo.store.person :as store]
   [cheshire.core :as json]
   [clojure.tools.logging :as log]
   [reitit.openapi :as openapi]
   [reitit.ring :as ring]
   [reitit.swagger-ui :as swagger-ui]))

(defn json-response [status body]
  {:status status
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string body)})

(defn read-json-body [req]
  (when-let [body (:body req)]
    (json/parse-string (slurp body) true)))


(defn list-persons [_]
  (json-response 200 {:persons ((store/list-persons))}))


(defn get-person [{{:keys [id]} :path-params}]
  (if-let [p (store/get-person id)]
    (json-response 200 p)
    (json-response 404 {:error "Person not found"})))

(defn create-person [req]
  (let [person (read-json-body req)]
    (if-let [id (:id person)]
      (do
        (store/add-person! person)
        (log/info "Created person" id)
        (json-response 201 person))
      (json-response 400 {:error "Missing :id"}))))

(defn delete-person [{{:keys [id]} :path-params}]
  (if (store/delete-person! id)
    (do
      (log/info "Deleted person" id)
      (json-response 200 {:status "deleted"}))
    (json-response 404 {:error "Person not found"})))



(def app
  (ring/ring-handler
   (ring/router
    [["/openapi.json"
      {:get {:no-doc true
             :handler (openapi/create-openapi-handler)}}]

     ["/swagger-ui/*"
      {:get {:no-doc true
             :handler (swagger-ui/create-swagger-ui-handler
                       {:url "/openapi.json"})}}]

     ["/persons"
      {:get list-persons
       :post create-person}]

     ["/persons/:id"
      {:get get-person
       :delete delete-person}]]
    {:data {:openapi {:info {:title "Person API"
                             :version "1.0.0"}}}})
   (ring/create-default-handler)))