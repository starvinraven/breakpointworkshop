(ns excercises.assignments)

;; clojure API: https://clojure.org/api/cheatsheet
;; you can run the tests from command line: lein auto test
;; you can also use the sandboxed environment at https://repl.it/@EskoLahti/reaktor-breakpoint-2018-clojure

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; using the threading macro (->) write a function that
;; - increments a value
;; - then divides it by 2, producing a floating-point value

(defn inc-and-divide [x]
  )

;; should return 3.5
(inc-and-divide 6)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; write a function that calculates the area of a circle
;; - you can get pi by using (Math/PI)
;; - use (Math/pow) for the exponent calculation

(defn circle-area [rad]
  )

;; should return 314.1592653589793
(circle-area 10)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; calculate circle area for all radiuses listed in the vector
(def radius-vec [10 3 4.3 2])

(defn calculate-areas [radius-vec]
  )

;; should return (314.1592653589793 28.274333882308138 58.08804816487527 12.566370614359172)
(def area-vec (calculate-areas radius-vec))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; calculate average of the radiuses
(defn avg-of-xs [xs]
  )

;; should return 103.27200450513048
(avg-of-xs area-vec)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; using the threading macro (->>)
;; - list all strings with length of 5 or less,
;; - capitalize first letter of each string.
(def vec-of-strings ["pizza"
                     "beer"
                     "juicy"
                     "long one"])

(defn capitalize-short-enough [strings]
  )

;; should return ["Pizza" "Beer" "Juicy"]
(capitalize-short-enough vec-of-strings)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def data [{:category     :pizza
            :price        {:euro 10.5}
            :name         "Margherita"
            :manufacturer "Luigi's Pizza"}

           {:category     :beer
            :price        {:euro 5.3}
            :name         "Hello World"
            :manufacturer "Reaktor"}

           {:category    :beer
            :price       {:euro 9.3}
            :name        "Super Gueuze"
            :manufacurer "Belgian Brewers"}

           {:category     :car
            :price        {:usd 90312}
            :name         "911"
            :manufacturer "Porsche"}])

;; get the second and third elements of the data
(defn second-element [data]
  )

(second-element data)

(defn third-element [data]
  )

(third-element data)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; get the euro price of the second element

(defn euro-price-of-second-element [data]
  )

(euro-price-of-second-element data)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; define a function that tells if given data belongs to the beer category
;; try not to use defn or fn

(def beer? identity) ;; replace the identity fn call here

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; get all elements in the beer category

(defn all-beers [data]
  )

(all-beers data)

;; to pretty print the results, use
;; (clojure.pprint/pprint beers)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; calculate prices of all products in both usd and euro
;; - as a result each item should have prices in both usd and euros
;; - only include the price objects in the result list
;; 1 euro = 1.2 usd

(def eur->usd-ratio 1.2)
(defn calculate-all-prices [data]
  )

;; should return ({:euro 10.5, :usd 12.6}
;;                {:euro 5.3, :usd 6.359999999999999}
;;                {:euro 9.3, :usd 11.16}
;;                {:usd 90312, :euro 75260.0})

(calculate-all-prices data)
