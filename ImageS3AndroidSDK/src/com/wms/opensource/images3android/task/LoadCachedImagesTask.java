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

import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.cache.ImageCache;
import com.wms.opensource.images3android.handler.HandlerMessage;
import com.wms.opensource.images3android.images3.entity.Image;
import com.wms.opensource.images3android.images3.entity.ImageResult;
import com.wms.opensource.images3android.images3.entity.ImageCollection;
import com.wms.opensource.images3android.util.FileUtil;
import com.wms.opensource.images3android.util.JsonUtil;
import com.wms.opensource.images3android.util.MessageUtil;
import com.wms.opensource.images3android.util.PersistFileNameUtil;
import com.wms.opensource.images3android.util.StorageUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class LoadCachedImagesTask extends AsyncTask<Integer, Void, Void> {

	private Context context = null;
	private ProgressBar progressBar = null;
	private Handler handler = null;
	private String baseUrl = "";
	private String imagePlantId = "";
	private int page = 1;	
	
	private List<Image> images = new ArrayList<Image>();
	
	public LoadCachedImagesTask(Context context, Handler handler, ProgressBar progressBar, String baseUrl, String imagePlantId, int page) {
		this.context = context;
		this.handler = handler;
		this.progressBar = progressBar;
		this.baseUrl = baseUrl;
		this.imagePlantId = imagePlantId;
		this.page = page;
	}
	
	protected void onPreExecute() {
		progressBar.setVisibility(View.VISIBLE);
    }
	
	@Override
	protected Void doInBackground(Integer... params) {
		if(ImageCache.containsPage(page)) {
			ImageCollection response = ImageCache.getRetrieveImageResponse(page);
			List<ImageResult> imageResults = response.getResults();
			for(ImageResult result : imageResults) {
				images.add(new Image(baseUrl, result));
			}
			return null;
		}
		try {
			String jsonString = FileUtil.getStringFromFileInCache(StorageUtil.getTempDirectory(context), PersistFileNameUtil.getImagesPersistFileName(imagePlantId, page), context.getString(R.string.charSetName));
			ImageCollection imageCollection = (ImageCollection) JsonUtil.deserialize(jsonString, "", ImageCollection.class);
			for(ImageResult result : imageCollection.getResults()) {
				images.add(new Image(baseUrl, result));
			}

			// Keep in cache
    		ImageCache.putImageCollection(page, imageCollection);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {  
		progressBar.setVisibility(View.INVISIBLE);
		MessageUtil.sendHandlerMessage(handler, HandlerMessage.IMAGES_LOADED_FROM_CACHE);
	}

	public List<Image> getImages() {
		return images;
	}

}
