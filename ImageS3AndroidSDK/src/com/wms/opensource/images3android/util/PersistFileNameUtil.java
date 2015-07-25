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

package com.wms.opensource.images3android.util;

public class PersistFileNameUtil {

    public static String getImagesPersistFileName(String imagePlantId, int page) {
    	return imagePlantId + "-page" + page + ".json";
    }

    public static String getImagePlantPersistFileName(String imagePlantId) {
    	return imagePlantId + ".json";
    }

    public static String getImagesPersistFileNamePattern(String imagePlantId) {
    	return imagePlantId + "-page";
    }

    public static String getTemplateCollectionFileName(String imagePlantId) {
    	return imagePlantId + "-templates.json";    	
    }

}
