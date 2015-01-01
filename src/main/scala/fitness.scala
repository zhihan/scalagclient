package org.mine.gclient.fitness

/** 
  Interface to Google Fitness API 
  */

import scala.collection.JavaConversions._

import com.google.api.services.fitness.{Fitness => GFit}

import org.mine.gclient.auth.Auth
import org.mine.gclient.Constants
import org.mine.gclient.Shared

object Fitness {
  val credential = Auth.authorize(Constants.user)
  val gFit = new GFit.Builder(
    Shared.httpTransport, Shared.jsonFactory,
    credential).build()


  object DataSources {
    def list = 
      gFit.users().dataSources().list("me").execute()
  }
}
