(ns api-demo.core
  (:gen-class)
  (:require
   [clj-http.client :as client]
   [clojure.tools.logging :as log]))

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


;; Main prints (IO happens here)
(defn -main
  [& args]
  (let [url  (or (first args) "https://catfact.ninja/fact")
        fact (fetch-data url)]
    (log/info "Cat fact:" fact)))
