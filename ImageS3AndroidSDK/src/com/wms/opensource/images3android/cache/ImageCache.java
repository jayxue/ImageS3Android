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

package com.wms.opensource.images3android.cache;

import com.wms.opensource.images3android.images3.entity.ImageCollection;

import android.util.SparseArray;

public class ImageCache {

	private static SparseArray<ImageCollection> retrieveImageResponsesCache = new SparseArray<ImageCollection>();
	
	public static void putImageCollection(int page, ImageCollection collection) {
		retrieveImageResponsesCache.put(page, collection);
	}
	
	public static ImageCollection getRetrieveImageResponse(int page) {
		return retrieveImageResponsesCache.get(page);
	}
	
	public static boolean isCacheEmpty() {
		return retrieveImageResponsesCache.size() == 0;
	}
	
	public static boolean containsPage(int page) {		
		return retrieveImageResponsesCache.get(page) != null;
	}
	
	public static void clearCache() {
		retrieveImageResponsesCache.clear();		
	}

}
