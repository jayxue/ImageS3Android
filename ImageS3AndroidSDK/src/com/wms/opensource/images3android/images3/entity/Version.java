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

package com.wms.opensource.images3android.images3.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Version {

	@JsonProperty("templateName")
	private String templateName;
	@JsonProperty("originalImageId")
	private String originalImageId;

	/**
	*
	* @return
	* The templateName
	*/
	@JsonProperty("templateName")
	public String getTemplateName() {
		return templateName;
	}

	/**
	*
	* @param templateName
	* The templateName
	*/
	@JsonProperty("templateName")
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	*
	* @return
	* The originalImageId
	*/
	@JsonProperty("originalImageId")
	public String getOriginalImageId() {
		return originalImageId;
	}

	/**
	*
	* @param originalImageId
	* The originalImageId
	*/
	@JsonProperty("originalImageId")
	public void setOriginalImageId(String originalImageId) {
		this.originalImageId = originalImageId;
	}

}
