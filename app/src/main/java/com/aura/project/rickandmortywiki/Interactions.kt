package com.aura.project.rickandmortywiki

import com.aura.project.rickandmortywiki.data.Location
import com.aura.project.rickandmortywiki.data.Origin


interface OnLocationClickListener {
    fun onLocationClick(location: Location)
}

interface OnOriginClickListener {
    fun onOriginClick(origin: Origin)
}