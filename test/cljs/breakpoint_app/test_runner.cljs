(ns breakpoint-app.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [breakpoint-app.core-test]
   [breakpoint-app.common-test]))

(enable-console-print!)

(doo-tests 'breakpoint-app.core-test
           'breakpoint-app.common-test)
