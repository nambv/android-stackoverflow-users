package com.nambv.android_stackoverflow.data.remote

import com.google.gson.annotations.Expose

data class ErrorBody(@Expose var error: String = "")
