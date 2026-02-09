(defproject api-demo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clj-http "3.12.3"]
                 [cheshire "5.11.0"]
                 [org.clojure/tools.logging "1.3.0"]
                 [ch.qos.logback/logback-classic "1.5.6"]
                 [ring/ring-core "1.12.2"]
                 [ring/ring-jetty-adapter "1.12.2"]
                 [metosin/reitit-ring "0.7.2"]
                 ;; Reitit (routing)
                 [metosin/reitit-ring "0.10.0"]

                 ;; OpenAPI spec generator
                 [fi.metosin/reitit-openapi "0.10.0"]

                 ;; Swagger UI
                 [metosin/reitit-swagger-ui "0.10.0"]
                 [com.fasterxml.jackson.core/jackson-annotations "2.17.2"]
                 [com.fasterxml.jackson.core/jackson-core "2.17.2"]
                 [com.fasterxml.jackson.core/jackson-databind "2.17.2"]
                 [com.fasterxml.jackson.datatype/jackson-datatype-jsr310 "2.17.2"]
                 [com.fasterxml.jackson.dataformat/jackson-dataformat-smile "2.17.2"]
                 [com.fasterxml.jackson.dataformat/jackson-dataformat-cbor "2.17.2"]]
  :main ^:skip-aot api-demo.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
