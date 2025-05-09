package data.geocoding.mapper

import data.model.LocationData
import logic.entity.Location

fun LocationData.toLocation() = Location(lat,lon)