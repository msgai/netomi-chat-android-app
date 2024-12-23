package com.netomi.sampleapplication.data.network

import com.netomi.chat.model.theme.NCWThemeResponse
import com.netomi.sampleapplication.model.BotListingResponse
import com.netomi.sampleapplication.utils.HostRoutes.ROUTE_BOT_LISTING
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API interface for defining network endpoints in the NCW SDK.
 *
 * This interface provides methods to interact with the NCW server,
 * including fetching chat history and sending new messages.
 * Each method is annotated with the appropriate **HTTP method** (GET, POST)
 * and defines the corresponding API endpoint.
 */

interface AppApiInterface {

    @GET(ROUTE_BOT_LISTING)
    suspend fun getBotListing(): Response<BotListingResponse>

}