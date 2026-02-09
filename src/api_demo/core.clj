(ns api-demo.core
  (:gen-class)
  (:require
   [api-demo.http :as http]
   [clj-http.client :as client]
   [clojure.tools.logging :as log]
   [ring.adapter.jetty :as jetty]))

(defn fetch-data
  "Fetches a cat fact from the API and returns the fact string."
  [url]
  (try
    (let [response (client/get url {:as :json :throw-exceptions false})
          status   (:status response)
          body     (:body response)]
      (if (= status 200)
        (:fact body)
        (do
          (log/warn "HTTP error. URL:" url "Status:" status)
          (str "API returned non-200 status: " status))))
    (catch Exception e
      (log/error "Network/Request error. URL:" url "Message:" (.getMessage e))
      "Could not fetch cat fact right now.")))

(defonce server (atom nil))

(defn start!
  []
  (when-not @server
    (reset! server (jetty/run-jetty http/app {:port 3000 :join? false}))
    (log/info "Server started on port 3000")))

(defn stop!
  []
  (when-let [s @server]
    (.stop s)
    (reset! server nil)
    (log/info "Server stopped")))

;; Main prints (IO happens here)
(defn -main
  [& args]
  (let [url  (or (first args) "https://catfact.ninja/fact")
        fact (fetch-data url)]
    (log/info "Cat fact:" fact)))
