(ns leaflet-example.core
  (:require [reagent.core :as r]
            [reagent.dom :as d]
            [leaflet-example.move :as move]
            ["react-leaflet" :refer [MapContainer TileLayer GeoJSON]]))

(def copy-osm "&copy; <a href=\"http://osm.org/copyright\">OpenStreetMap</a> contributors")
(def osm-url (str js/window.location.protocol "//{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"))

(def default-center [1.352 103.851])

(def geojson (clj->js
               {:type "MultiPolygon"
                :crs {:type "name"
                      :properties {:name "EPSG:3857"}}
                :coordinates [[[[103.72881064,1.381712426],[103.728247365,1.380988051]
                                [103.727701114,1.38028557],[103.726068963,1.381602655]
                                [103.726049687,1.38161821],[103.727219458,1.383022459]
                                [103.727259673,1.382986286],[103.72881064,1.381712426]]]]}))

(def other-geojson (clj->js {:type "MultiPolygon"
                             :coordinates [[[[104.015476006,1.400841267]
                                             [104.073266125,1.404366425]
                                             [104.075302033,1.367916928]
                                             [104.080940674,1.337752011]
                                             [104.015476006,1.400841267]]]]}))

(defn leaflet-map [state]
  [:div
   ;; [:div.delete-me (pr-str state)]
   [:> MapContainer
    {:center default-center :zoom 11 ;; :scrollWheelZoom false
     :style {:width "1000px" :height "1000px"}}
    [:> TileLayer
     {:url osm-url
      :attribution copy-osm}]
    ^{:key state}
    [:> GeoJSON
     {:data state}]]])

(defn app []
  (r/with-let [state (r/atom nil)
               add-area-1! #(reset! state geojson)
               add-area-2! #(reset! state other-geojson)
               remove-area! #(reset! state nil)]

    [:div
     [:button {:on-click add-area-1!}
      "GeoJSON 1"]
     [:button {:on-click add-area-2!}
      "GeoJSON 2"]
     [:button {:on-click remove-area!}
      "Remove"]
     [leaflet-map @state]]))

(defn mount-root []
  (d/render [app] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
