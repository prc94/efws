package com.github.prc94.efws.exception

class EntityNotFoundException(entityDescription: String): Exception("Entity $entityDescription was not found")