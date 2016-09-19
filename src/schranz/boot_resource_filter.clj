(ns schranz.boot-resource-filter
  {:boot/export-tasks true}
  (:require [boot
             [core :as core]
             [util :as util]]
            [clojure.java.io :as io]))

(defn ^:private check-params [options]
  (cond
    (not (contains? options :matching)) (util/fail "Parameter -m/--matching is required!\n")
    (not (contains? options :replacements)) (util/fail "Parameter -r/--replacements is required\n")
    :else true))

(defn ^:private filter-file! [out-path in-file]
  (let [file (core/tmp-file in-file)
        path (core/tmp-path in-file)
        out-file (io/file out-path path)]
    (util/info "Processing: %s\n" file)
    (io/make-parents out-file)
    (spit out-file (str (slurp file) "EDITED!!!!!"))))

(core/deftask filter-resources
  "Replace tokens in files"
  [m matching REGEX #{regex} "Set of regular expressions to select files for filtering."
   r replacements MATCH=RESULT #{[edn str]} "A map to specify which tokens to replace with which string. The files will be searched for tokens in the following formats: {{token}} . TODO allow for ${token} or @token@. TODO allow for regexps as generic string matchers."]

  (let [tmp (core/tmp-dir!)]
    (core/with-pre-wrap [fileset]
      (if (check-params *opts*)
        (do
          (core/empty-dir! tmp)
          (dorun (->> fileset
                      core/input-files
                      (core/by-re matching)
                      (map #(filter-file! tmp %) )))
          (-> fileset
              (core/add-resource tmp)
              core/commit!))
        (*usage*)))))
