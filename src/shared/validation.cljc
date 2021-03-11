(ns shared.validation)

(defn valid-input?
  "Validates that string contains only lower-case letters and no digits or special characters."
  [s]
  (if (empty? s)
    true
    (= s (re-matches #"[a-z]*" s))))
