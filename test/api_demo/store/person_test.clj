(ns api-demo.store.person-test
  (:require
   [api-demo.store.person :as p]
   [clojure.test :refer :all]))
(deftest add-and-get-person-test
  ;; reset atom before test
  (reset! p/persons {})
  ;; add person
  (let [person {:id 1 :name "Alice"}
        stored (p/add-person! person)]
    (is (= person stored))
    ;; assert get returns same person
    (is (= person (p/get-person 1)))))
(deftest delete-person-test
  (reset! p/persons {})
  ;; delete non-existing person
  (is (false? (p/delete-person! 999)))

  ;; add and then delete person
  (let [person {:id "2" :name "Bob"}]
    (p/add-person! person)
    (is (true? (p/delete-person! "2")))
    (is (nil? (p/get-person "2")))))
(deftest list-persons-test
  (reset! p/persons {})
  (let [persons [{:id 1 :name "Alice"}
                 {:id 2 :name "Bob"}
                 {:id 3 :name "Charlie"}]]
    (doseq [person persons]
      (p/add-person! person))
    (let [listed (p/list-persons)]
      (is (= (set persons) (set listed)))
      (is (= 3 (count listed))))))