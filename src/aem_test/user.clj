(ns aem-test.user
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

(defroutes user-routes
  (GET "/user/:arg" [arg]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (format "This is the <i>GET user</i> service of the <i><b>aem-test.user</b></i> servlet.   Now serving <i>%s</i>." arg)})

  (GET "/_ah/login_required" []
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (format "This is the <i>GET login</i> service of the <i><b>aem-test.user</b></i> servlet. ")})


;;  (route/files "/" {:root "/public/"})

  (route/not-found "Sorry, aem-test.user page not found\n"))

(def user-handler
  (-> #'user-routes
      wrap-params
      wrap-file-info
      ;; handle-dump
      ))

(println "prepping aem-test-user servlet")
(ae/def-appengine-app aem-test-user #'user-handler)

(defn -service [this request response]
  ((make-servlet-service-method aem-test-user) this request response))
