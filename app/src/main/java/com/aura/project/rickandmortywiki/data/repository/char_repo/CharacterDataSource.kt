package com.aura.project.rickandmortywiki.data.repository.char_repo

interface CharacterDataSource :
    CharacterDataSourceInternal {
    var strategy: Strategy
}