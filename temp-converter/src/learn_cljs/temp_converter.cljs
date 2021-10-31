(ns learn-cljs.temp-converter
  (:require [goog.dom :as gdom]
            [goog.events :as gevents]))

(defn f->c [deg-f]
  (/ (- deg-f 32) 1.8))

(defn c->f [deg-c]
  (+ (* deg-c 1.8) 32))

(defonce input-target (gdom/getElement "selectInputDegreeType"))
(defonce temp-input (gdom/getElement "inputDegree"))
(defonce output-target (gdom/getElement "convertedDegree"))
(defonce output-unit-target (gdom/getElement "selectConversionType"))
(defonce display-unit-target (gdom/getElement "convertedUnit"))
(defonce button (gdom/getElement "convertBtn"))

(defn get-input-temp []
  (js/parseInt (.-value temp-input)))

(defn set-output-temp [temp]
  (gdom/setTextContent output-target
                       (.toFixed temp 2)))

(defn get-input-target []
  (.-value input-target))

(defn get-output-target []
 (.-value output-unit-target))

(defn convert-from-celcius []
  (cond
    (= \F (get-output-target))
       (do (set-output-temp (c->f (get-input-temp)))
          (gdom/setTextContent display-unit-target "F"))))

(defn convert-from-fahrenheit[]
  (cond
    (= \C (get-output-target))
     (do (set-output-temp (f->c (get-input-temp)))
          (gdom/setTextContent display-unit-target "C"))))

(defn update-output [_]
  (cond 
    (= (get-input-target) (get-output-target))
     (do (set-output-temp (get-input-temp))
          (gdom/setTextContent display-unit-target(get-input-target)))
     (= \C (get-input-target))
      (convert-from-celcius)
     (= \F (get-input-target))
      (convert-from-fahrenheit)))

(gevents/listen output-unit-target "change" update-output)
(gevents/listen input-target "change" update-output)
(gevents/listen temp-input "keyup" update-output)
(gevents/listen button "click" update-output)
