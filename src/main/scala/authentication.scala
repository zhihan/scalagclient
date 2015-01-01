package org.mine.gclient.auth

/** 
  Authentication flow for Google JAVA client using Scala.
  */
import scala.collection.JavaConversions._

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver

import com.google.api.services.oauth2.Oauth2

object Constants {
  val CLIENT_ID = "1022278465531-4251eu09npkop9ip88l3eov0us7cfkei.apps.googleusercontent.com"
  val CLIENT_SECRET = "6Jcsab2XoW_p0jxuG6DwCV8L"

  val APPLICATION_NAME = "scala google client"
}

object Auth {
  val httpTransport = GoogleNetHttpTransport.newTrustedTransport();
  val jsonFactory = JacksonFactory.getDefaultInstance();

  private val scopes = List("https://www.googleapis.com/auth/userinfo.email")

  /** 
    Authorize the user using the installed application flow and return
    oauth2 interface object.
  */
  def authorize(user:String): Oauth2 = {
    val flow = new GoogleAuthorizationCodeFlow.Builder(
      httpTransport, jsonFactory,
      Constants.CLIENT_ID, Constants.CLIENT_SECRET,
      scopes.toIterable).build()

    val credential = new AuthorizationCodeInstalledApp(
      flow, new LocalServerReceiver()).authorize(user);

    new Oauth2.Builder(httpTransport, jsonFactory,
      credential).setApplicationName(Constants.APPLICATION_NAME).build()
  }
    
  /** Getting the user info from an existing OAuth2 client */
  def userInfo(oauth2: Oauth2) = 
    oauth2.userinfo().get().execute()
  
}
