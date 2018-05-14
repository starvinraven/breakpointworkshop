(ns excercises.assignments-test
  (:require [clojure.test :refer :all]
            [excercises.assignments :as a]))
            ;[excercises.solutions :as a]))

(deftest test-inc-and-divide
  (is (= (a/inc-and-divide 6)
         3.5)))

(deftest test-circle-area
  (is (= (a/circle-area 10)
         314.1592653589793)))

(deftest test-calculate-areas
  (is (= (a/calculate-areas a/radius-vec)
         '(314.1592653589793 28.274333882308138 58.08804816487527 12.566370614359172))))

(deftest test-avg-of-xs
  (is (= (a/avg-of-xs (a/calculate-areas a/radius-vec))
         103.27200450513048)))

(deftest test-capitalize-short-enough
  (is (= (a/capitalize-short-enough a/vec-of-strings)
         ["Pizza" "Beer" "Juicy"])))

(deftest test-second-element
  (is (= (:name (a/second-element a/data))
         "Hello World")))

(deftest test-third-element
  (is (= (:name (a/third-element a/data))
         "Super Gueuze")))

(deftest test-euro-price-of-second-element
  (is (= (a/euro-price-of-second-element a/data)
         5.3)))

(deftest test-beer?
  (is (not (a/beer? (first a/data))))
  (is (a/beer? (second a/data))))

(deftest test-all-beers
  (is (= (->> a/data a/all-beers (map :name))
         '("Hello World" "Super Gueuze"))))

(deftest test-calculate-all-prices
  (is (= (a/calculate-all-prices a/data)
         '({:euro 10.5 :usd 12.6}
           {:euro 5.3 :usd 6.359999999999999}
           {:euro 9.3 :usd 11.16}
           {:usd 90312 :euro 75260.0}))))
