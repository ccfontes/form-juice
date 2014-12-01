(ns form.juice
  (:require [clojure.string :as str]
            [goog.dom :as gdom]
            [goog.string :as gstring]
            [goog.dom.forms :as gforms]))

;; form-decode from ring.util.codec
(defn- assoc-conj
  "Associate a key with a value in a map. If the key already exists in the map,
  a vector of values is associated with the key."
  [map key val]
  (assoc map key
         (if-let [cur (get map key)]
           (if (vector? cur)
             (conj cur val)
             [cur val])
           val)))

(defn- form-decode-str
  "Decode the supplied www-form-urlencoded string."
  [^String encoded]
  (gstring/urlDecode encoded))

(defn- form-decode
  "Decode the supplied www-form-urlencoded string.
  If the encoded value is a string, a string is returned.
  If the encoded value is a map of parameters, a map is returned."
  [^String encoded]
  (if-not (gstring/contains encoded "=")
    (form-decode-str encoded)
    (reduce
     (fn [m param]
       (if-let [[k v] (str/split param #"=" 2)]
         (assoc-conj m (form-decode-str k) (form-decode-str v))
         m))
     {}
     (str/split encoded #"&"))))

(defn- form-data [form]
  (form-decode (gforms/getFormDataString form)))

(defn- parent-form [btn]
  (gdom/getAncestorByTagNameAndClass btn "form"))

(defn squeeze [event]
  (-> event .-currentTarget parent-form form-data js->clj))
