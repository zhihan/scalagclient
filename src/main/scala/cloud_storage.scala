package org.mine.gclient.cloud_storage

/**
  Interface to Google cloud storage
  */

import com.google.api.services.storage.Storage
import com.google.api.client.http.ByteArrayContent
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

  /** Insert a byte array as raw data (application/octet-stream type). */
  def insertBytes(name: String, data: Array[Byte]) {
    val content = new ByteArrayContent("application/octet-stream", data)
    val req = storage.objects().insert(bucket, null, content).setName(name)
    req.getMediaHttpUploader().setDirectUploadEnabled(true);
    req.execute()
  }

  /** Insert a text file as plain text (plain/text type). */
  def insertBytes(name: String, data: String) {
    val content = new ByteArrayContent("text/plain", data.getBytes())
    val req = storage.objects().insert(bucket, null, content).setName(name)
    req.getMediaHttpUploader().setDirectUploadEnabled(true);
    req.execute()
  }


  def insertJson(name: String, j: String) {

  }

}
