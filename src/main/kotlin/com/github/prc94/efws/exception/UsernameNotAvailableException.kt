package com.github.prc94.efws.exception

class UsernameNotAvailableException(username: String) : Exception("Username $username is not available")