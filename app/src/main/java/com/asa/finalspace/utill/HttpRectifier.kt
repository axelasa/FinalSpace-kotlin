package com.asa.finalspace.utill

fun ensureHttps(url: String?): String {
    return when {
        url == null -> ""
        url.startsWith("https://") -> url // Already HTTPS
        url.startsWith("http://") -> url.replaceFirst("http://", "https://") // Convert to HTTPS
        else -> "https://$url" // No protocol, add HTTPS
    }
}