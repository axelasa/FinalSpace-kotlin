package com.asa.finalspace.viewmodel

sealed class ModelSate {
}

data object LoadingModelState:ModelSate()

data object  LoadedModelState:ModelSate()