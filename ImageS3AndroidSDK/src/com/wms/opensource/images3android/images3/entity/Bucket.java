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

public class Bucket {

	@JsonProperty("accessKey")
	private String accessKey;
	@JsonProperty("secretKey")
	private String secretKey;
	@JsonProperty("name")
	private String name;

	/**
	*
	* @return
	* The accessKey
	*/
	@JsonProperty("accessKey")
	public String getAccessKey() {
		return accessKey;
	}

	/**
	*
	* @param accessKey
	* The accessKey
	*/
	@JsonProperty("accessKey")
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	*
	* @return
	* The secretKey
	*/
	@JsonProperty("secretKey")
	public String getSecretKey() {
		return secretKey;
	}

	/**
	*
	* @param secretKey
	* The secretKey
	*/
	@JsonProperty("secretKey")
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	*
	* @return
	* The name
	*/
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	*
	* @param name
	* The name
	*/
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
	
}
