package org.mine.gclient.datastore

/**
  Interface fo Googole datastore for installed scala client.
  */
import com.google.api.services.datastore.client.{Datastore => GD}
import com.google.api.services.datastore.client.DatastoreException
import com.google.api.services.datastore.client.DatastoreFactory
import com.google.api.services.datastore.client.DatastoreOptions
import com.google.api.services.datastore.DatastoreV1.Entity
import com.google.api.services.datastore.DatastoreV1.CommitRequest
import com.google.api.services.datastore.DatastoreV1.Mutation
import com.google.api.services.datastore.DatastoreV1.LookupRequest
import com.google.api.services.datastore.DatastoreV1.Key
import com.google.api.services.datastore.client.DatastoreHelper._
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential

import com.google.api.client.json.GenericJson

import scala.collection.JavaConversions._
import java.io.File

import org.mine.gclient.auth.Auth
import org.mine.gclient.Constants
import org.mine.gclient.Shared

/**
   NOTE The Datastore client API needs a service account to access the 
  datastore.

  */

object Datastore {


  val dataset = "concise-emblem-811"

  private def initDatastore = {
    val serviceAccount = "1022278465531-s016mir6rkudo0lacvtfq1rakg6seeab@developer.gserviceaccount.com"
    val privateKeyFile = getClass.getResource("/scalagclient-51cea9b9032b.p12").getPath()
    val scopes = List("https://www.googleapis.com/auth/userinfo.email",
      "https://www.googleapis.com/auth/datastore")
    val credential = new GoogleCredential.Builder()
      .setTransport(Shared.httpTransport)
      .setJsonFactory(Shared.jsonFactory)
      .setServiceAccountId(serviceAccount)
      .setServiceAccountPrivateKeyFromP12File(new File(privateKeyFile))
      .setServiceAccountScopes(scopes.toIterable)
    .build();


    val option = new DatastoreOptions.Builder()
      .credential(credential)
      .dataset(dataset).build()
    DatastoreFactory.get().create(option)
  }

  val datastore = initDatastore

  def saveJson(obj:GenericJson) = {
    // A generic Json object has only a content field
    val builder = Entity.newBuilder().setKey(makeKey("json"))
    builder.addProperty(makeProperty("content",
      makeValue(obj.toString())));
    val req = CommitRequest.newBuilder()
      .setMode(CommitRequest.Mode.NON_TRANSACTIONAL)
      .setMutation(Mutation.newBuilder().addInsertAutoId(builder)).build()
    datastore.commit(req)
  }

  def getJson(id:Long) = {
    val key = Key.newBuilder()
      .addPathElement(
      Key.PathElement.newBuilder().setKind("json").setId(id)).build()
    val req = LookupRequest.newBuilder().addKey(key).build()
    datastore.lookup(req)
  }


}
