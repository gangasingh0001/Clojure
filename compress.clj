(ns compress 
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn list-files []
  (let [files_list (file-seq (io/file "./"))]
    (doseq [file files_list]
      (when (.isFile file)(println (.getName file)))))
  (println))

(defn parse-integer [item]
  (try
    (Integer/parseInt item)
    (catch NumberFormatException _ item)))

(defn convert-to-int [lst]
  (reduce (fn [acc item] 
            (conj acc (parse-integer item))) 
          [] lst))

(defn display-file-contents [file]
  (try (let [compressed-text (slurp file)]
         (println compressed-text)) (catch java.io.FileNotFoundException e
                                      (println "File not found: " (.getMessage e)))))

(defn add-spaces-around-special-chars [text]
  (str/replace text #"[^a-zA-Z0-9\s]" #(str " " % " ")))

(defn replace-numbers [text]
  (str/replace text #"\b(\d+)\b" "@$1@"))

(defn check-symbol-around-word [word]
  (if (and (> (count word) 2) (str/starts-with? word "@") (str/ends-with? word "@"))
    (subs word 1 (- (count word) 1))
    word))

(defn frequency-words-map-decompress []
  (let [frequency-text (slurp "frequency.txt")
        frequency-words (distinct (str/split frequency-text #"\s+"))
        frequency-words (convert-to-int frequency-words)]
    frequency-words))

(defn decompress-file-text-list [file]
  (let [compressed-text (slurp file)
        compressed-list (str/split compressed-text #"\s+")
        converted-list (convert-to-int compressed-list)]
    converted-list))

(defn add-grammar-rules [decompressed-pre-final-text]
  (let [modified-text (-> decompressed-pre-final-text
                        (str/replace #"\(\s" "(")
                        (str/replace #"\s\)" ")")
                        (str/replace #"\[\s" "[")
                        (str/replace #"\s\]" "]")
                        (str/replace #"\s\." ".")
                        (str/replace #"\s\," ",")
                        (str/replace #"\s\?" "?")
                        (str/replace #"\s\!" "!")
                        (str/replace #"\@\s" "@")
                        (str/replace #"\$\s" "\\$")
                          )]
    modified-text))

(defn capitalise-first-letter [modified-text] 
  (let [result (str/replace modified-text #"(^|[.!?]\s+)(\p{javaLowerCase})"
                            (fn [[_ prevChar currentChar]]
                              (str prevChar (str/upper-case currentChar))))]
    result))

(defn decompress-text [file]
  (try
    (let [frequency-words (frequency-words-map-decompress)
          file-to-decompress (decompress-file-text-list file)
          output (str/join " " (map #(if (integer? %) (get frequency-words %) (check-symbol-around-word %)) file-to-decompress))
          final-output (add-grammar-rules output)
          final-output (capitalise-first-letter final-output)]
      (println final-output))
    (catch java.io.FileNotFoundException e
      (println "File not found: " (.getMessage e))))) 
    
(defn read-file-to-compress [file-name]
  (let [file-text (slurp file-name)
        file-text (add-spaces-around-special-chars file-text)
        file-content (replace-numbers file-text)]
    file-content))

;; (defn compress-word [word frequency-list]
;;   )

(defn compress-file [file-name]
  (try
    (let [file-content (read-file-to-compress file-name)
          words (str/split file-content #"\s+")
          frequency-table (slurp "frequency.txt")
          frequency-words (distinct (str/split frequency-table #"\s+"))
          compressed-content (map (fn [word]
                                    (let [lowercase-word (clojure.string/lower-case word)
                                          index (.indexOf (map clojure.string/lower-case frequency-words) lowercase-word)]
                                      (if (>= index 0)
                                        index
                                        word))
                                    ) words)
          compressed-file-name (str file-name ".ct")]
          
      (spit compressed-file-name (str/join " " compressed-content))
      (println "File compressed."))
    (catch java.io.FileNotFoundException e
      (println "File not found: " (.getMessage e)))))
  

