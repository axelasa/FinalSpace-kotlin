package com.asa.finalspace.model.dto_model

sealed class TaskResults<T> {

}

class TaskSuccess<T>(val data:T ):TaskResults<T>(){

}

class TaskFail<T>(val message:String, val exception: Exception):TaskResults<T>(){}