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

package com.wms.opensource.images3android.handler;

public class HandlerMessage {

	public static final int IMAGE_UPLOADED = 1000;
	
	public static final int IMAGES_LOADED = 2000;
	
	public static final int IMAGES_COUNT_LOADED_ASK_FOR_RELOADING = 3000;
	
	public static final int IMAGES_COUNT_LOADED = 3001;
	
	public static final int IMAGES_LOADED_FROM_CACHE = 4000;
	
	public static final int IMAGE_DELETED = 5000;
	
}
