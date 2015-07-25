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

package com.wms.opensource.images3android.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.activity.ImageListFragmentActivity;
import com.wms.opensource.images3android.cache.ImageCache;
import com.wms.opensource.images3android.handler.HandlerMessage;
import com.wms.opensource.images3android.images3.ImageS3Service;
import com.wms.opensource.images3android.images3.entity.Image;
import com.wms.opensource.images3android.images3.entity.ImageResult;
import com.wms.opensource.images3android.images3.entity.ImageCollection;
import com.wms.opensource.images3android.util.DialogUtil;
import com.wms.opensource.images3android.util.FileUtil;
import com.wms.opensource.images3android.util.JsonUtil;
import com.wms.opensource.images3android.util.MessageUtil;
import com.wms.opensource.images3android.util.PersistFileNameUtil;
import com.wms.opensource.images3android.util.StorageUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

public class LoadImagesTask extends AsyncTask<Void, Void, ImageCollection> {

	private Context context = null;
	private Handler handler = null;
	private String baseUrl = "";
	private String imagePlantId = "";
	private String pageToken = "";
	private int page = 1;	
	
	private ProgressDialog progressDialog = null;
	
	private List<Image> images = new ArrayList<Image>();
	
	public LoadImagesTask(Context context, Handler handler, String baseUrl, String imagePlantId, String pageToken, int page) {
		this.context = context;
		this.handler = handler;
		this.baseUrl = baseUrl;
		this.imagePlantId = imagePlantId;
		this.pageToken = pageToken;
		this.page = page;
	}

	protected void onPreExecute() {
		progressDialog = DialogUtil.showWaitingProgressDialog(context, ProgressDialog.STYLE_SPINNER, context.getString(R.string.loadingImages), false);
    }
	
	protected ImageCollection doInBackground(Void... params) {
		String imageCollectionString = "";
		ImageCollection imageCollection = null;
		try {
			if(page == 1) {
				imageCollectionString = ImageS3Service.retrieveImages(baseUrl, imagePlantId, "");
			}
			else {
				imageCollectionString = ImageS3Service.retrieveImages(baseUrl, imagePlantId, pageToken);
			}
			if(imageCollectionString != null) {
	    		FileUtil.writeStringToFile(imageCollectionString, StorageUtil.getTempDirectory(context), PersistFileNameUtil.getImagesPersistFileName(imagePlantId, page), 
	    				context.getString(R.string.charSetName));
				imageCollection = (ImageCollection) JsonUtil.deserialize(imageCollectionString, "", ImageCollection.class);			
				for(ImageResult result : imageCollection.getResults()) {
					Image image = new Image(baseUrl, result);
					images.add(image);
				}

				// Keep in cache
	    		ImageCache.putImageCollection(page, imageCollection);
			}
		}
		catch (ClientProtocolException e) {
			
		}
		catch (IOException e) {
			
		}

		return imageCollection;
	}

	@Override
	protected void onPostExecute(ImageCollection result) {
    	if(progressDialog != null && progressDialog.isShowing() == true)
    		progressDialog.dismiss();		

		if(result != null) {
	    	if(page == 1) {
				ImageListFragmentActivity.pageTokens.set(1, result.getPage().getNext());
			}
			else if(page == ImageListFragmentActivity.pageTokens.size()) {
				// Do nothing
			}
			else {
				ImageListFragmentActivity.pageTokens.set(page - 2, result.getPage().getPrev() == null ? "" : result.getPage().getPrev());
				ImageListFragmentActivity.pageTokens.set(page, result.getPage().getNext());
			}
	    	MessageUtil.sendHandlerMessage(handler, HandlerMessage.IMAGES_LOADED);
		}
		else {
			DialogUtil.showExceptionAlertDialog(context, context.getString(R.string.loadImagesFailedTitle), context.getString(R.string.loadImagesFailed));
		}
	}
	
	public List<Image> getImages() {
		return images;
	}
}
