(ns menu
  (:require
   [compress :refer [compress-file decompress-text
                     display-file-contents list-files]]))

(defn menu []
  (loop []
    (println "Menu:")
    (println "1. Display list of files")
    (println "2. Display file contents")
    (println "3. Compress a file")
    (println "4. Uncompress a file")
    (println "5. Exit")
    (println "Enter your option: ")
    (let [choice (read-line)]
      (cond
        (= choice "1") (do
                         (println "File List:")
                         (list-files))
        (= choice "2") (do
                         (println "Enter file name: ")
                         (let [file (read-line)]
                           (display-file-contents file)))
        (= choice "3") (do
                         (println "Enter file name: ")
                         (let [file (read-line)]
                           (compress-file file)))
        (= choice "4") (do
                         (println "Enter file name: ")
                         (let [file (read-line)]
                           (decompress-text file)))
        (= choice "5") (println "Exiting...")
        :else (println "Invalid choice. Try again."))
      (when (not= choice "5")
        (recur)))))

(menu)