;; Copyright 2016 Daniel Schranz. Distributed under the LGPL v3.

(ns schranz.boot-resource-filter
  {:boot/export-tasks true}
  (:require [boot
             [core :as core]
             [util :as util]
             [pod :as pod]]
            [clojure.java.io :as io]))

(defn ^:private check-params [{:keys [files replacements no-properties]}]
  (cond
    (not files) (util/fail "Parameter -f/--files is required!\n")
    (and no-properties (not replacements)) (util/fail "Please specify replacements using one or more -r/--replacements or remove the -n/--no-properties parameter!\n")
    :else true))

(defn ^:private replace-token [content token value]
  "Replaces one token (potentially multiple times in a string. If the token can't be found in env it returns the original string."
  (if value
    (clojure.string/replace content (re-pattern (str "\\{\\{" token "\\}\\}")) value)
    content))

(defn ^:private replace-tokens [replacements content]
  (let [tokens (->> content
                    (re-seq #"\{\{([a-z-]+)\}\}")
                    (map last)
                    distinct)]
    (reduce #(replace-token % %2 (get replacements (keyword %2))) content tokens)))

(defn ^:private filter-file! [replacements out-path in-file]
  (let [file (core/tmp-file in-file)
        path (core/tmp-path in-file)
        out-file (io/file out-path path)]
    (util/info "Processing: %s\n" file)
    (io/make-parents out-file)
    (->> file
         slurp
         (replace-tokens replacements)
         (spit out-file))))

(defn get-environment []
  (let [my-pod (pod/make-pod
                (update-in (core/get-env) [:dependencies]
                           conj '[environ "1.1.0"]))]
    (pod/with-eval-in my-pod
      (require '[environ.core :refer [env]])
      env)))

(core/deftask filter-resources
  "Filter resource files and replace tokens with system properties or strings."
  [f files REGEX #{regex} "The set of regular expressions to select files for filtering."
   n no-properties bool "Do not use system properties to replace tokens."
   r replacements MATCH=RESULT #{[kw str]} "The replacements map. You can overwrite system properties by adding entries here."]

  (fn [handler]
    (fn [fileset]
      (if (check-params *opts*)
        (let [tmp (core/tmp-dir!)
          replacements-map (merge (if-not no-properties (get-environment) {})
                                  (into {} replacements))]
          (do
            (core/empty-dir! tmp)
            (dorun (->> fileset
                        core/input-files
                        (core/by-re files)
                        (map #(filter-file! replacements-map tmp %))))
            (handler (-> fileset
                         (core/add-resource tmp :include files)
                         core/commit!))))
        (*usage*)))))
