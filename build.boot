(set-env!
 :dependencies '[[org.clojure/clojure "1.7.0"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]
                 [metosin/boot-alt-test "0.1.2" :scope "test"]]

 :resource-paths #{"src" "test-resources"}) ;; TODO stop test-resources from ending up in jar file (only include for test-task)

(def version "0.1.0-SNAPSHOT")

(require '[metosin.boot-alt-test :refer [alt-test]]
         '[adzerk.bootlaces :refer :all]
         '[schranz.boot-resource-filter :refer [filter-resources]])

(bootlaces! version)

(task-options!
  pom {:project     'schranz/boot-resource-filter
       :version     version
       :description "Boot task to replace tokens in resource files."
       :url         "https://github.com/danielschranz/boot-resource-filter"
       :scm         {:url "https://github.com/danielschranz/boot-resource-filter"}
       :license     {"LGPL" "https://www.gnu.org/licenses/lgpl-3.0.en.html#content"}}
  jar {:file "boot-resource-filter.jar"})

(deftask ;^:private
  add-test-paths
  "Profile setup for running tests."
  []
  (set-env! :source-paths #(conj % "test"))
  identity)
