(ns wordcount.core
  (:require [wordcount.pages :refer :all]
            [wordcount.words :refer :all]
            [clojure.core.reducers :as r]
            [foldable-seq.core :refer :all]))

(defn frequencies-parallel [words]
  (r/fold (partial merge-with +)
          (fn [counts x] (assoc counts x (inc (get counts x 0))))
          words))

(defn -main [& args]
  (time 
    (frequencies-parallel
      (r/flatten
        (r/map get-words 
          (foldable-seq 10 (get-pages 10000 "enwiki.xml"))))))
  nil)
