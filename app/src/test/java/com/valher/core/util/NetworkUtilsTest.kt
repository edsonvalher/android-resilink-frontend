package com.valher.core.util

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class NetworkUtilsTest {
    @Test
    fun `isSuccessfulResponse should return true for successful codes`() {
        assertTrue(NetworkUtils.isSuccessfulResponse(200))
        assertTrue(NetworkUtils.isSuccessfulResponse(201))
        assertTrue(NetworkUtils.isSuccessfulResponse(204))
    }
    @Test
    fun `isSuccessfulResponse should return false for unsuccessful codes`() {
        assertFalse(NetworkUtils.isSuccessfulResponse(400))
        assertFalse(NetworkUtils.isSuccessfulResponse(500))
    }
}