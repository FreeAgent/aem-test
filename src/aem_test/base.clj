(ns aem-test.base
    (:use [net.cgrand.enlive-html :as html])
    (:use [aem-test.utils :only [parse-int maybe-content maybe-substitute]])
    (:require [appengine-magic.core :as ae]
              ;[appengine-magic.services.user :as aeu]
              [clojure.pprint :as pp]
              [clojure.tools.logging :as log :only [debug info]]))


(def base-page (html/html-resource (ae/open-resource-stream "html/base-page.html")))


(html/deftemplate base-view base-page
               [active-id table-rows]
               [(id= active-id)]   (add-class "active")
               ;; [:tbody :tr] (clone-for [i (range (count table-rows))] 
               [:tbody :tr] (clone-for [row (map-indexed vector table-rows)] 
                                         [:td#num]  (html/content (str (first row)))
                                         [:td#call]
                                           (html/content (:call (second row)))
                                         [:td#res]
                                           (html/content (:res (second row)))))


;; placeholder values to be used until pages are ready...
(def under-con [{ :call "under construction",
               :res  "under construction" },

              {:call "under construction",
               :res  "under construction" },

              {:call "under construction",
               :res  "under construction" } ])
