package com.vetclinic.iadi.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class HTTPNotFoundException(s:String) : Exception(s)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class HTTPBadRequestException(s:String) : Exception(s)