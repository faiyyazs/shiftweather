package com.shiftweather.core.presentation

/**
 * Different states of a resource that is provided to the presentation layer.
 */
sealed class ResourceState {
    object LOADING : ResourceState()
    object SUCCESS : ResourceState()
    object ERROR : ResourceState()
}