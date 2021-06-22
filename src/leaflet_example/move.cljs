(ns leaflet-example.move
  (:require [reagent.core :as r]
            [reagent.dom :as d]
            ["react-leaflet" :refer [MapContainer TileLayer GeoJSON]]))

(def point [-104.99404 39.75621])

(def geojson {:type     "Feature"
              :geometry {:type        "Point"
                         :coordinates point}})

(defn leaflet-map [state]
  [:div
   [:> MapContainer
    {:center (reverse point) :zoom 11
     :style {:width "1000px" :height "1000px"}}
    [:> TileLayer {:url "//{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"}]
    ^{:key state}
    [:> GeoJSON
     {:data (clj->js state)}]]])

(defn app []
  (r/with-let [state (r/atom geojson)
               move-left (fn [state]
                           (update-in state [:geometry :coordinates 0]
                                      (fn [x]
                                        (- x 0.01))))
               move-left! #(swap! state move-left)]
    [:div
     [:button {:on-click move-left!}
      "Move point left"]
     [leaflet-map @state]]))
