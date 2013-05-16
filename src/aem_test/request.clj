(ns aem-test.request
    (:gen-class :extends javax.servlet.http.HttpServlet)
    (:use [appengine-magic.servlet :only [make-servlet-service-method]]
          [ring.middleware.params :only [wrap-params]]
          [ring.middleware.file-info :only [wrap-file-info]]
          [ring.handler.dump :only [handle-dump]]
          [compojure.core])
    (:require [ring.handler.dump]
              [compojure.route :as route]
              [appengine-magic.core :as ae]
              [appengine-magic.services.user :as aeu]
              [clojure.pprint :as pp]
              [clojure.tools.logging :as log :only [debug info]]))

(defroutes request-routes
  (GET "/request/:rqst" [rqst]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (format "This is the <i>GET request</i> service of the <i><b>aem-test.request</b></i> servlet.   Now serving <i>%s</i>." rqst)})


;;  (route/files "/" {:root "/public/"})

  (route/not-found "Sorry, aem-test.request page not found\n"))

(def request-handler
  (-> #'request-routes
      wrap-params
      wrap-file-info
      ;; handle-dump
      ))

(println "prepping aem-test-request servlet")
(ae/def-appengine-app aem-test-request #'request-handler)

(defn -service [this request response]
  ((make-servlet-service-method aem-test-request) this request response))
