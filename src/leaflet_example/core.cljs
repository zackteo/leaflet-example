(ns leaflet-example.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [reagent.ratom]
   ["leaflet" :as leaflet]
   ["react-leaflet" :as react-leaflet]))

;; -------------------------
;; Views

(def copy-osm "&copy; <a href=\"http://osm.org/copyright\">OpenStreetMap</a> contributors")
(def osm-url (str js/window.location.protocol "//{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"))

(def MapContainer (r/adapt-react-class react-leaflet/MapContainer))
(def TileLayer (r/adapt-react-class react-leaflet/TileLayer))
(def GeoJSON (r/adapt-react-class react-leaflet/GeoJSON))

(def default-center [1.352 103.851])

(def state (r/atom nil
                   ;; (clj->js
                   ;;   {:type "MultiPolygon"
                   ;;    :crs {:type "name"
                   ;;          :properties {:name "EPSG:3857"}}
                   ;;    :coordinates [[[[103.72881064,1.381712426],[103.728247365,1.380988051]
                   ;;                    [103.727701114,1.38028557],[103.726068963,1.381602655]
                   ;;                    [103.726049687,1.38161821],[103.727219458,1.383022459]
                   ;;                    [103.727259673,1.382986286],[103.72881064,1.381712426]]]]})
                   ))

(def other-geojson (clj->js {:type "MultiPolygon"
                             :coordinates [[[[104.015476006,1.400841267]
                                             [104.073266125,1.404366425]
                                             [104.075302033,1.367916928]
                                             [104.080940674,1.337752011]
                                             [104.015476006,1.400841267]]]]}))

(defn leaflet-map []
  (fn []
    [:div
     [:div.delete-me (pr-str @state)]
     [MapContainer
      {:center default-center :zoom 12 ;; :scrollWheelZoom false
       :style {:width "1000px" :height "1000px"}}
      [TileLayer
       {:url osm-url
        :attribution copy-osm}]
      [GeoJSON
       {:data @state}]]]))

(defn button []
  (fn []
    [:button.green
     {:on-click #(reset! state other-geojson)}
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
