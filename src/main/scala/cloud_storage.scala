package org.mine.gclient.cloud_storage

/**
  Interface to Google cloud storage
  */

import com.google.api.services.storage.Storage
import com.google.api.client.http.ByteArrayContent
import com.google.api.client.http.json.JsonHttpContent
import scala.collection.JavaConversions._

import org.mine.gclient.auth.Auth
import org.mine.gclient.Constants
import org.mine.gclient.Shared

object CloudStorage {
  val project = "concise-emblem-811"
  val bucket = "zhihan_scalagclient"

  val credential = Auth.authorize(Constants.user)
  val storage = new Storage.Builder(
    Shared.httpTransport, Shared.jsonFactory, credential)
    .setApplicationName(Constants.APPLICATION_NAME)
    .build()

  def listObject =
    storage.objects().list(bucket).execute()

  /** Get the meta data of an object */
  def getObjectMetaData(name: String) =
    storage.objects().get(bucket, name).execute()

  /** Get the content of an object */
  def getObject(name: String): Array[Byte] = {
    val req = storage.objects().get(bucket, name)
    val out = new java.io.ByteArrayOutputStream()
    req.getMediaHttpDownloader().setDirectDownloadEnabled(true)
    req.executeMediaAndDownloadTo(out)
    out.toByteArray()
  }

  private def insert(name:String, content:ByteArrayContent) {
    val req = storage.objects().insert(bucket, null, content).setName(name)
    req.getMediaHttpUploader().setDirectUploadEnabled(true);
    req.execute()
  }

  /** Insert a byte array as raw data (application/octet-stream type). */
  def insertBytes(name: String, data: Array[Byte]) {
    val content = new ByteArrayContent("application/octet-stream", data)
    insert(name, content)
  }

  /** Insert a text file as plain text (plain/text type). */
  def insertBytes(name: String, data: String) {
    val content = new ByteArrayContent("text/plain", data.getBytes())
    insert(name, content)
  }

  /** Insert a Json object as json type (application/json) */
  def insertJson(name: String, j: Object) {
    val content = new ByteArrayContent("application/json",
      Shared.jsonFactory.toByteArray(j))
    insert(name, content)
  }

  def getJson[T](name:String, clazz:java.lang.Class[T]): T = {
    val bytes = getObject(name)
    Shared.jsonFactory.fromString(new String(bytes), clazz)
  }

}
