(ns aem-test.request
    (:require [compojure.handler :as handler])
    (:use [aem-test.utils :only [render render-to-response]]
          [aem-test.base :as base]
          [aem-test.user :as user])
    (:gen-class :extends javax.servlet.http.HttpServlet)
    (:use [appengine-magic.servlet :only [make-servlet-service-method]]
          [ring.middleware.params :only [wrap-params]]
          [ring.middleware.file-info :only [wrap-file-info]]
          ;; [ring.handler.dump :only [handle-dump]]
          [compojure.core])
    (:require [ring.handler.dump]
              [compojure.route :as route]
              [appengine-magic.core :as ae]
              [appengine-magic.services.user :as aeu]
              [clojure.pprint :as pp]
              [clojure.tools.logging :as log :only [debug info]]))

(defroutes user-routes
  (GET "/user" [] (render-to-response (base/base-view "user" (user/gen-user-table))))
  (GET "/capable" [] (render-to-response (base/base-view "capable" base/under-con)))
  (GET "/namespaces" [] (render-to-response (base/base-view "namespaces" base/under-con)))
  (GET "/backends" [] (render-to-response (base/base-view "backends" base/under-con)))
  (GET "/taskqueues" [] (render-to-response (base/base-view "taskqueues" base/under-con)))

  (route/not-found "Sorry, page not found\n"))

(def request-handler
  (-> #'user-routes
      wrap-params
      wrap-file-info
      ;; handle-dump
      ))

(println "prepping request servlet")
(ae/def-appengine-app aem-test-request #'request-handler)

(defn -service [this request response]
  ((make-servlet-service-method aem-test-request) this request response))
