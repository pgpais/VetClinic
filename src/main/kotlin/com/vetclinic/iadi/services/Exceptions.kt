package com.vetclinic.iadi.services

class NotFoundException(s:String): RuntimeException(s)

class PreconditionFailedException(s:String): RuntimeException(s)