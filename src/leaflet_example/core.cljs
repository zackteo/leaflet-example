(ns leaflet-example.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   ["leaflet" :as leaflet]
   ["react-leaflet" :as react-leaflet]))

;; -------------------------
;; Views

(def copy-osm "&copy; <a href=\"http://osm.org/copyright\">OpenStreetMap</a> contributors")
(def osm-url (str js/window.location.protocol "//{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"))

(def MapContainer (r/adapt-react-class react-leaflet/MapContainer))
(def TileLayer (r/adapt-react-class react-leaflet/TileLayer))
(def default-center [1.352 103.851])

(def state (r/atom 0))

(defn leaflet-map []
  (fn []
    [MapContainer
     {:center default-center :zoom 12 ;; :scrollWheelZoom false
      :style {:width "1000px" :height "1000px"}}

     [TileLayer
      {:url osm-url
       :attribution copy-osm}]]))

(defn button []
  (fn []
    [:button.green
     {:on-click #(swap! state inc)}
     (str @state)]))

;; -------------------------
;; Initialize app


(defn mount-root []
  (d/render [:div
             [button]
             [leaflet-map]]
            (.getElementById js/document "app")))

;; (defn mount-root []
;;   (d/render
;;     [:div
;;      ;; {:style {:height "100%"}}
;;      [home-page]] (.getElementById js/document "app")))


(defn ^:export init! []
  (mount-root))
