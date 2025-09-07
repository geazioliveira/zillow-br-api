package com.zillowbrapi.auth.profile.errors

object ProfileErrorMessage {
    const val USER_ID_REQUIRED = "User ID is required."
    const val USER_ID_NOT_BLANK = "User ID cannot be blank."

    const val TYPE_REQUIRED = "Profile Type is required."

    const val AVATAR_URL_INVALID_FORMAT = "Avatar URL must be a valid URL."
    const val AVATAR_URL_INVALID_LENGTH = "Avatar URL must not exceed 500 characters."

    const val COVER_URL_INVALID_FORMAT = "Cover URL must be a valid URL."
    const val COVER_URL_INVALID_LENGTH = "Cover URL must not exceed 500 characters."

    const val BIO_SIZE_INVALID = "Bio must not exceed 1000 characters."

    const val PRIMARY_CITY_SIZE_INVALID = "Primary City must not exceed 50 characters."
    const val PRIMARY_CITY_INVALID = "Primary City must contain only letters and spaces."

    const val PRIMARY_STATE_SIZE_INVALID = "Primary State must not exceed 50 characters."
    const val PRIMARY_STATE_INVALID = "Primary State must contain only letters and spaces."

    const val VISIBILITY_NOT_NULL = "Visibility is required."
}