package com.example.techevent.untils

object Constants {

    val userTypes = arrayListOf(
        "Participant",
        "Event Manager",
        "Sponsor"
    )


    const val KEY_COLLECTION_USERS = "users"
    const val KEY_NAME = "name"
    const val KEY_TEAM_NAME = "teamName"
    const val KEY_EMAIL = "email"
    const val KEY_CNIC = "cnic"
    const val KEY_PASSWORD = "password"
    const val KEY_PREFERENCE_NAME = "chatAppPreference"
    const val KEY_IS_SIGNED_IN = "isSignedIn"
    const val KEY_USER_ID = "userId"
    const val KEY_IMAGE = "image"
    const val KEY_USER_TYPE = "userType"
    const val KEY_COMPETITION = "competition"
    const val KEY_PHONE_NUMBER = "phoneNumber"
    const val KEY_TEAM_MEMBERS  = "teamMembers"
    const val KEY_AMOUNT = "amount"
    const val KEY_COLLECTION_PAYMENTS = "payments"
    const val KEY_COLLECTION_REGESTERED_COMPET = "RegisteredCompetition"
    const val KEY_PAYMENT_CONFIRMATION = "paymentConfirmation"

    const val KEY_STALL_BIDS = "stallBids"
    const val KEY_BID_TITLE = "bidTitle"
    const val KEY_BID_STATUS = "bidStatus"
    const val KEY_BIDDERS = "bidders"
    const val KEY_MAX_BID = "maxBid"
    const val KEY_MIN_BID = "minBid"
    const val KEY_DESCRIPTION_1 = "description_1"
    const val KEY_DESCRIPTION_2 = "description_2"
    const val KEY_DESCRIPTION_3 = "description_3"
    const val KEY_DESCRIPTION_4 = "description_4"
    const val KEY_STALL_BID = "stallBid"
    const val KEY_CURRENT_BID_ID ="currentBidId"
    const val KEY_PARTICIPANT = "Participant"
    const val KEY_SPONSOR = "Sponsor"
    const val KEY_EVENT_MANAGER = "Event Manager"
    const val KEY_TARGET_BIDS = "TargetBids"



    const val KEY_FCM_TOKEN = "fcmToken"
    const val KEY_USER = "user"
    const val KEY_COLLECTION_CHAT = "chat"
    const val KEY_SENDER_ID = "senderId"
    const val KEY_RECEIVER_ID = "receiverId"
    const val KEY_MESSAGE = "message"
    const val KEY_TIMESTAMP = "timestamp"
    const val KEY_COLLECTION_CONVERSATIONS = "conversations"
    const val KEY_SENDER_NAME = "senderName"
    const val KEY_RECEIVER_NAME = "receiverName"
    const val KEY_SENDER_IMAGE = "senderImage"
    const val KEY_RECEIVER_IMAGE = "receiverImage"
    const val KEY_LAST_MESSAGE = "lastMessage"
    const val KEY_AVAILABILITY = "availability"
    const val REMOTE_MSG_AUTHORIZATION = "Authorization"
    const val REMOTE_MSG_CONTENT_TYPE = "Content-Type"
    const val REMOTE_MSG_DATA = "data"
    const val REMOTE_MSG_REGISTRATION_IDS = "registration_ids"


    var remoteMsgHeaders: HashMap<String, String>? = null

    @JvmName("getRemoteMsgHeaders1")
    fun getRemoteMsgHeaders(): HashMap<String, String> {
        if (remoteMsgHeaders == null) {
            remoteMsgHeaders = HashMap()
            remoteMsgHeaders!![REMOTE_MSG_AUTHORIZATION] =
                "key=AAAAJCDwcwc:APA91bHvfrz40wnQLh8eyGkx9WBGkbPUypZFukvl8-5bUdzuALCh5wb159e3L5WVSghZNdg-oXcJeKiQ4shgF8RJgk-xRgtlab-PPT46H_kMbV22Y9_ju7VVVAtPJ0hFHVpuBURRLoGn"
            remoteMsgHeaders!![REMOTE_MSG_CONTENT_TYPE] = "application/json"
        }

        return remoteMsgHeaders as HashMap<String, String>
    }

}