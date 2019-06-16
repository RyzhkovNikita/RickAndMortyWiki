package com.aura.project.rickandmortywiki.data.request

class SuccessfulRequest<T>(val body: T): RepoRequest<T>()