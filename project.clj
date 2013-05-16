(defproject aem-test "0.1.0-SNAPSHOT"
  :description "A web-app for use in testing Appengine-Magic (the Clojure library for App Engine)"
  :min-lein-version "2.0"
  :url "http://aem-test.appspot.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repl-options {:port 4005
                 :init (do
                         (require '[appengine-magic.core :as ae])
                         (load-file "src/aem-test/request.clj")
                         (defn request []
                           (do (load-file "src/aem-test/request.clj")
                               (ae/serve aem-test.request/aem-test-request)))
                         (load-file "src/aem-test/user.clj")
                         (defn user []
                           (do (load-file "src/aem-test/user.clj")
                               (ae/serve aem-test.user/aem-test-user)))
                         (user))}
  :gae-sdk "/usr/local/appengine-java-sdk"
  :gae-app {:id "aem-test"
            ;; using '-' prefix on version nbr forces user to customize
            :version  {:dev "0-1-0"
                       :test "0-1-0"
                       :prod "0-1-0"}
            :servlets [{:name "aem-test", :class "request",
                       :services [{:svcname "request" :url-pattern  "/request/*"}
                                  ]}
                       {:name "aem-test", :class "user",
                       :services [{:svcname "user" :url-pattern  "/user/*"}
                                  {:svcname "login" :url-pattern  "/_ah/login_required"}
                                  ]}
                       ]
            :war "war"
            :display-name "aem-test"
            :welcome "index.html"
            :threads true,
            :sessions true,
            :java-logging "logging.properties",
            ;; static-files: html, css, js, etc.
            :statics {:src "src/main/public"
                      :dest ""
                      :include {:pattern "public/**"
                                ;; :expire "5d"
                                }
                      ;; :exclude {:pattern "foo/**"}
                      }
            ;; resources: img, etc. - use lein default
            :resources {:src "src/main/resource"
                        :dest ""
                        :include {:pattern "public/**"
                                  ;; :expire "5d"
                                  }
                        ;; :exclude {:pattern "bar/**"}
                        }
            }
  :aot [aem-test.request aem-test.user  *]
  :compile-path "war/WEB-INF/classes"
  :target-path "war/WEB-INF/lib"
  :uberjar-exclusions [#"META-INF/DUMMY.SF"
                       #"appengine-tools-api-1.8.0.jar"
                       #"appengine-local.*"
                       #"appengine-api.*"]
  :keep-non-project-classes false
  :omit-source true ;; default
  :jar-exclusions [#"^WEB-INF/appengine-generated.*$"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [ring/ring-core "1.1.8"]
                 [ring/ring-devel "1.1.8"]
                 [enlive "1.1.1"]
                 [org.clojars.wjlroe/kerodon "0.0.7-SNAPSHOT"]
                 [org.clojure/tools.logging "0.2.3"]]
  :profiles {:dev {:dependencies [[appengine-magic "0.5.1-CANDIDATE-1"]]}}
  :plugins [[gaem "0.2.0-SNAPSHOT"]])
