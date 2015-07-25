/*
 * Copyright 2015 Waterloo Mobile Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wms.opensource.images3android.images3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ImageS3Service {

	public static final int NUMBER_OF_IMAGES_PER_PAGE = 15;
	
	private static HttpClient client = null;	
	
	private static HttpClient getClient() {
		if (client == null) {
			client = new DefaultHttpClient();
		}
		return client;
	}
	  
	public static int addImage(String baseUrl, String imagePlantId, String filePath) {
		File file = new File(filePath);
		try {
			HttpClient client = getClient();

		    String url = baseUrl + imagePlantId + "/images";
		    HttpPost httppost = new HttpPost(url);
		    InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(file), -1);
		    reqEntity.setContentType("binary/octet-stream");
		    reqEntity.setChunked(true);
		    httppost.setEntity(reqEntity);
		    HttpResponse response = client.execute(httppost);
		    return response.getStatusLine().getStatusCode();
		}
		catch (Exception e) {
			return -1;
		}
	}
	
	public static String retrieveImages(String baseUrl, String imagePlantId, String pageToken) throws ClientProtocolException, IOException {
		String url = baseUrl + imagePlantId + "/images";
		if(!pageToken.isEmpty()) {
			url += "?page=" + pageToken;
		}
		return getResponse(url);
	}
	
	public static String retrieveImagePlant(String baseUrl, String imagePlantId) {
		String url = baseUrl + imagePlantId;
		return getResponse(url);
	}
	
	public static String retrieveTemplateCollection(String baseUrl, String imagePlantId) {
		String url = baseUrl + imagePlantId + "/templates?page=";
		return getResponse(url);
	}
	
	public static String getImageFileUrlInTemplate(String baseUrl, String imagePlantId, String imageId, String templateName) {
		return baseUrl + imagePlantId + "/imagefiles/" + imageId + "?template=" + templateName;
	}
	
	private static String getResponse(String url) {
		HttpClient client = getClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept", "application/json");
		HttpResponse response;
		try {
			response = client.execute(httpGet);
			int code = response.getStatusLine().getStatusCode();
			if(code == 200) {
				if(response.getEntity() != null) {
					HttpEntity resEntity = response.getEntity();
					return EntityUtils.toString(resEntity);
				}
			}
		} catch (ClientProtocolException e) {
			
		} catch (IOException e) {

		}
		
		return null;
	}
	
	public static boolean deleteImage(String baseUrl, String imagePlantId, String imageId) {
		HttpClient client = getClient();
		String url = baseUrl + imagePlantId + "/images/" + imageId;
		HttpDelete httpDelete = new HttpDelete(url);
		HttpResponse response;
		try {
			response = client.execute(httpDelete);
			int code = response.getStatusLine().getStatusCode();
			if(code == 204) {
				return true;
			}
		}
		catch(ClientProtocolException e) {
			
		}
		catch (IOException e) {

		}
		return false;
	}

}
