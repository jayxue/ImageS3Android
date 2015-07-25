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

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MasterTemplate {
	
	@JsonProperty("isArchived")
	private Boolean isArchived;
	@JsonProperty("isRemovable")
	private Boolean isRemovable;
	@JsonProperty("resizingConfig")
	private ResizingConfig resizingConfig;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	/**
	*
	* @return
	* The isArchived
	*/
	@JsonProperty("isArchived")
	public Boolean getIsArchived() {
		return isArchived;
	}

	/**
	*
	* @param isArchived
	* The isArchived
	*/
	@JsonProperty("isArchived")
	public void setIsArchived(Boolean isArchived) {
		this.isArchived = isArchived;
	}

	/**
	*
	* @return
	* The isRemovable
	*/
	@JsonProperty("isRemovable")
	public Boolean getIsRemovable() {
		return isRemovable;
	}

	/**
	*
	* @param isRemovable
	* The isRemovable
	*/
	@JsonProperty("isRemovable")
	public void setIsRemovable(Boolean isRemovable) {
		this.isRemovable = isRemovable;
	}

	/**
	*
	* @return
	* The resizingConfig
	*/
	@JsonProperty("resizingConfig")
	public ResizingConfig getResizingConfig() {
		return resizingConfig;
	}

	/**
	*
	* @param resizingConfig
	* The resizingConfig
	*/
	@JsonProperty("resizingConfig")
	public void setResizingConfig(ResizingConfig resizingConfig) {
		this.resizingConfig = resizingConfig;
	}


	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
	
}
