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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.cache.ImageCache;
import com.wms.opensource.images3android.handler.HandlerMessage;
import com.wms.opensource.images3android.images3.ImageS3Service;
import com.wms.opensource.images3android.images3.entity.ImageCollection;
import com.wms.opensource.images3android.images3.entity.ImagePlant;
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

public class LoadImagePlantTask extends AsyncTask<String, Void, String> {

	private Context context = null;
	private Handler handler = null;

	private boolean shouldAutoRun = false;
	private int savedImageCount = -1;
	
	private ProgressDialog progressDialog = null;
	private ImagePlant imagePlant = null;
	private ImageCollection firstPageImageCollection = null;
	
	public LoadImagePlantTask(Context context, Handler handler, boolean shouldAutoRun, int savedImageCount) {
		this.context = context;
		this.handler = handler;
		this.shouldAutoRun = shouldAutoRun;
		this.savedImageCount = savedImageCount;
	}
	
	protected void onPreExecute() {
		if(!shouldAutoRun) {
			progressDialog = DialogUtil.showWaitingProgressDialog(context, ProgressDialog.STYLE_SPINNER, context.getString(R.string.loadingImages), false);
		}
    }
	
	@Override
	protected String doInBackground(String... params) {
		// params[0] is service url and params[1] is image plant id
		String imagePlantString = ImageS3Service.retrieveImagePlant(params[0], params[1]);
		if(imagePlantString != null) {
			FileUtil.writeStringToFile(imagePlantString, StorageUtil.getTempDirectory(context), PersistFileNameUtil.getImagePlantPersistFileName(params[1]), 
				context.getString(R.string.charSetName));
			try {
				imagePlant = (ImagePlant) JsonUtil.deserialize(imagePlantString, "", ImagePlant.class);

				// Get templates of this image plant
				String templateCollectionString = ImageS3Service.retrieveTemplateCollection(params[0], params[1]);
				if(templateCollectionString != null) {
					FileUtil.writeStringToFile(templateCollectionString, StorageUtil.getTempDirectory(context), PersistFileNameUtil.getTemplateCollectionFileName(params[1]), 
							context.getString(R.string.charSetName));
				}
				
				if(imagePlant.getNumberOfImages() > 0) {
					// Load the first page if there are any images
					String firstPageImageCollectionString = ImageS3Service.retrieveImages(params[0], params[1], "");
					if(firstPageImageCollectionString != null) {
			    		FileUtil.writeStringToFile(firstPageImageCollectionString, StorageUtil.getTempDirectory(context), PersistFileNameUtil.getImagesPersistFileName(params[1], 1), 
			    				context.getString(R.string.charSetName));

						firstPageImageCollection = (ImageCollection) JsonUtil.deserialize(firstPageImageCollectionString, "", ImageCollection.class);

						// Keep in cache. 
			    		ImageCache.putImageCollection(1, firstPageImageCollection);						
					}
				}
			}
			catch (JsonParseException e) {
				
			}
			catch (JsonMappingException e) {
				
			}
			catch (IOException e) {
				
			}
			return imagePlantString;
		}
		return null;
	}

    protected void onPostExecute(String result) { 
    	if(progressDialog != null && progressDialog.isShowing() == true) {
    		progressDialog.dismiss();
    	}
    	
    	if(result == null) {
    		DialogUtil.showExceptionAlertDialog(context, context.getString(R.string.loadImagePlantFailedTitle), context.getString(R.string.loadImagePlantFailed));
    	}
    	else {
    		if(shouldAutoRun) {
    			if(imagePlant.getNumberOfImages() != savedImageCount) {
    				MessageUtil.sendHandlerMessage(handler, HandlerMessage.IMAGES_COUNT_LOADED_ASK_FOR_RELOADING);
    			}
    		}
    		else {
    			MessageUtil.sendHandlerMessage(handler, HandlerMessage.IMAGES_COUNT_LOADED);
    		}
    	}

    }

	public ImagePlant getImagePlant() {
		return imagePlant;
	}
    
	public int getImagesCount() {
		return imagePlant.getNumberOfImages();
	}
	
	public String getSecondPageToken() {
		if(firstPageImageCollection != null) {
			return firstPageImageCollection.getPage().getNext();
		}
		else {
			return "";
		}
	}
	
}
