package com.aura.project.rickandmortywiki.data.repository

interface CharacterDataSource : CharacterDataSourceInternal {
    var strategy: Strategy
}