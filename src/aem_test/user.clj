(ns aem-test.user
    (:require [appengine-magic.core :as ae]
              [appengine-magic.services.user :as aeu]
              [clojure.pprint :as pp]
              [clojure.tools.logging :as log :only [debug info]]))

; #(if (aeu/user-logged-in?)
;   (str (aeu/get-user-id (aeu/current-user)))
;   "user ID unavailable (not logged in)")

(defn gen-user-table []
             [{:call "Is user logged in?",
                :res  (str (aeu/user-logged-in?))  },

              {:call  "Login URL", 
               :res  (aeu/login-url)  }
              
              {:call  "Login URL", 
               :res  (aeu/login-url)  }
              
              ])
