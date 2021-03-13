;; This test runner is intended to be run from the command line
(ns street-fighter-selection-cljs.test-runner
  (:require
    ;; require all the namespaces that you want to test
    [street-fighter-selection-cljs.core-test]
    [figwheel.main.testing :refer [run-tests-async]]))

(defn -main [& args]
  (run-tests-async 5000))
