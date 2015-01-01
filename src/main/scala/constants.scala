package org.mine.gclient

/**
  Constants and other shared resources of the app.
  */
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport

/**
  Shared resources among all clients
  */
object Shared {
  val httpTransport = GoogleNetHttpTransport.newTrustedTransport();
  val jsonFactory = JacksonFactory.getDefaultInstance();
}

object Constants {
  val CLIENT_ID = "1022278465531-4251eu09npkop9ip88l3eov0us7cfkei.apps.googleusercontent.com"
  val CLIENT_SECRET = "6Jcsab2XoW_p0jxuG6DwCV8L"

  val APPLICATION_NAME = "scala google client"

  val scopes = List("https://www.googleapis.com/auth/userinfo.email",
    "https://www.googleapis.com/auth/fitness.activity.read")

  lazy val user = "zhi.han@gmail.com"
}
