package de.bitb.spacerace.network

//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.ResponseBody
//import org.greenrobot.eventbus.EventBus
//import retrofit2.Response
//import java.net.HttpURLConnection
//​
//​
//private const val STATUS_SUCCESS = 200
//private const val STATUS_REDIRECT = 300
//private const val STATUS_CLIENT_ERROR = 400
//private const val STATUS_SERVER_ERROR = 500
//private const val STATUS_UNKNOWN = 600
//​
//object NetworkResponseEvaluator {
//    ​
//    fun <T> evaluate(response: Response<T>?): HTTPResponseEvaluation<T> =
//            response?.let {
//                when (response.code()) {
//                    in STATUS_SUCCESS until STATUS_REDIRECT -> processSuccess(response)
//                    in STATUS_CLIENT_ERROR until STATUS_SERVER_ERROR -> processClientError(response)
//                    in STATUS_SERVER_ERROR until STATUS_UNKNOWN -> processServerError(response)
//                    else -> null
//                } ?: HTTPResponseEvaluation.Error.UnknownError(response)
//            } ?: HTTPResponseEvaluation.Error.UnknownError(generateUnknownResult())
//    ​
//    ​
//    private fun <T> generateUnknownResult(): Response<T> = Response.error(9001, ResponseBody.create("application/json".toMediaTypeOrNull(), ""))
//    ​
//    private fun <T> processSuccess(response: Response<T>): Success<T>? =
//            when (response.code()) {
//                HttpURLConnection.HTTP_OK -> Success.Ok(response)
//                HttpURLConnection.HTTP_CREATED -> Success.Created(response)
//                HttpURLConnection.HTTP_ACCEPTED -> Success.Accepted(response)
//                else -> null
//            }
//    ​
//    @Throws(Exception::class)
//    private fun <T> processClientError(response: Response<T>): HTTPResponseEvaluation.Error.ClientError<T>? =
//            when (response.code()) {
//                HttpURLConnection.HTTP_BAD_REQUEST -> HTTPResponseEvaluation.Error.ClientError.BadRequest(response)
//                HttpURLConnection.HTTP_UNAUTHORIZED -> HTTPResponseEvaluation.Error.ClientError.Unauthorized(response).also { EventBus.getDefault().post(OnForbiddenEvent()) } // EventBus call is only required for this app
//                HttpURLConnection.HTTP_FORBIDDEN -> HTTPResponseEvaluation.Error.ClientError.Forbidden(response)
//                HttpURLConnection.HTTP_NOT_FOUND -> HTTPResponseEvaluation.Error.ClientError.NotFound(response)
//                else -> null
//            }
//    ​
//    ​
//    @Throws(Exception::class)
//    private fun <T> processServerError(response: Response<T>): ServerError<T>? =
//            when (response.code()) {
//                HttpURLConnection.HTTP_INTERNAL_ERROR -> ServerError.InternalError(response)
//                HttpURLConnection.HTTP_BAD_GATEWAY -> ServerError.BadGateway(response)
//                HttpURLConnection.HTTP_UNAVAILABLE -> ServerError.Unavailable(response)
//                HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> ServerError.GatewayTimeout(response)
//                else -> null
//            }
//    ​
//    sealed class HTTPResponseEvaluation<T>(val response: Response<T>) {
//        ​
//        /** Http-Status 200 - 299 **/
//        sealed class Success<T>(response: Response<T>) : HTTPResponseEvaluation<T>(response) {
//            ​
//            /** Http-Status 200 **/
//            class Ok<T>(response: Response<T>) : Success<T>(response)
//            ​
//            /** Http-Status 201 **/
//            class Created<T>(response: Response<T>) : Success<T>(response)
//            ​
//            /** Http-Status 202 **/
//            class Accepted<T>(response: Response<T>) : Success<T>(response)
//        }
//        ​
//        /** Http-Status 400 - 599 **/
//        sealed class Error<T>(response: Response<T>) : HTTPResponseEvaluation<T>(response) {
//            ​
//            /** Http-Status 400 - 499 **/
//            sealed class ClientError<T>(response: Response<T>) : Error<T>(response) {
//                /** Http-Status 400 **/
//                class BadRequest<T>(response: Response<T>) : ClientError<T>(response)
//                ​
//                /** Http-Status 401 **/
//                class Unauthorized<T>(response: Response<T>) : ClientError<T>(response)
//                ​
//                /** Http-Status 403 **/
//                class Forbidden<T>(response: Response<T>) : ClientError<T>(response)
//                ​
//                /** Http-Status 404 **/
//                class NotFound<T>(response: Response<T>) : ClientError<T>(response)
//            }
//            ​
//            /** Http-Status 500 - 599 **/
//            sealed class ServerError<T>(response: Response<T>) : Error<T>(response) {
//                /** Http-Status 500 **/
//                class InternalError<T>(response: Response<T>) : ServerError<T>(response)
//                ​
//                /** Http-Status 502 **/
//                class BadGateway<T>(response: Response<T>) : ServerError<T>(response)
//                ​
//                /** Http-Status 503 **/
//                class Unavailable<T>(response: Response<T>) : ServerError<T>(response)
//                ​
//                /** Http-Status 504 **/
//                class GatewayTimeout<T>(response: Response<T>) : ServerError<T>(response)
//            }
//            ​
//            /** Http-Status 600 and above **/
//            class UnknownError<T>(response: Response<T>) : Error<T>(response)
//        }
//    }
//}