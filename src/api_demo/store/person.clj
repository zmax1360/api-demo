(ns api-demo.store.person)
(defonce persons (atom {}))

(defn add-person!
  "Upserts a person by :id. Returns the stored person."
  [{:keys [id] :as person}]
  (when-not id
    (throw (ex-info "Person must include :id" {:person person})))
  (swap! persons assoc id person)
  (get @persons id))

(defn get-person
  "Returns a person by id, or nil if not found."
  [id]
  (get @persons id))

(defn delete-person!
  "Deletes a person by id. Returns true if deleted, false if not found."
  [id]
  (let [existed? (contains? @persons id)]
    (swap! persons dissoc id)
    existed?))
(defn list-persons
  "Returns all persons."
  []
  (vals @persons))