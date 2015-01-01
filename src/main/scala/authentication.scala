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

import org.mine.gclient.Constants
import org.mine.gclient.Shared

object Auth {

  /** 
    Authorize the user using the installed application flow and return
    oauth2 interface object.
  */
  def authorize(user:String): Credential = {
    val flow = new GoogleAuthorizationCodeFlow.Builder(
      Shared.httpTransport, Shared.jsonFactory,
      Constants.CLIENT_ID, Constants.CLIENT_SECRET,
      Constants.scopes.toIterable).build()

    val credential = new AuthorizationCodeInstalledApp(
      flow, new LocalServerReceiver()).authorize(user);

    credential
  }

  def oauth2(credential: Credential) = {
    new Oauth2.Builder(Shared.httpTransport, Shared.jsonFactory,
      credential).setApplicationName(Constants.APPLICATION_NAME).build()
  }

  /** Getting the user info from an existing OAuth2 client */
  def userInfo(oauth2: Oauth2) = 
    oauth2.userinfo().get().execute()
  
}
