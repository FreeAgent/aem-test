(ns aem-test.user-test
  (:use [clojure.test]
        [clj-webdriver.taxi]))

(deftest user-login-url-exists
  (set-driver! {:browser :firefox} "https://aem-test.appspot.com")
  (is (text "#login-url") "User Login")
)

