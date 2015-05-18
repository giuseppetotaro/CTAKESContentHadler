/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.sax;

import java.io.Serializable;

/*
 * Configuration for {@see CTAKESContentHandler}.
 * 
 * This class allows to enable cTAKES and set its parameters.
 * 
 */
public class CTAKESConfig implements Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -1599741171775528923L;
	
	// Path to XML descriptor for AnalysisEngine
	private String aeDescriptor = "/ctakes-core/desc/analysis_engine/SentencesAndTokensAggregate.xml"; // TODO enumeration for cTAKES engines
	
	// UMLS username
	private String UMLSUser = "";
	
	// UMLS password
	private String UMLSPass = "";
	
	// Enables formatted output
	private boolean prettyPrint = true; 
	
	// Type of cTAKES (UIMA) serializer
	private CTAKESSerializer serializerType = CTAKESSerializer.XMI;

	/**
	 * Default constructor.
	 */
	public CTAKESConfig() {
		// TODO init method to loads properties from InputStream
	}
	
	/**
	 * Returns the path to XML descriptor for AnalysisEngine.
	 * @return the path to XML descriptor for AnalysisEngine.
	 */
	public String getAeDescriptor() {
		return aeDescriptor;
	}
	
	/**
	 * Returns the UMLS username.
	 * @return the UMLS username.
	 */
	public String getUMLSUser() {
		return UMLSUser;
	}
	
	/**
	 * Returns the UMLS password.
	 * @return the UMLS password.
	 */
	public String getUMLSPass() {
		return UMLSPass;
	}
	
	/**
	 * Returns {@code true} if formatted output is enabled, {@code false} otherwise.
	 * @return {@code true} if formatted output is enabled, {@code false} otherwise.
	 */
	public boolean isPrettyPrint() {
		return prettyPrint;
	}
	
	/**
	 * Returns the type of cTAKES (UIMA) serializer used to write CAS.
	 * @return the type of cTAKES serializer.
	 */
	public CTAKESSerializer getSerializerType() {
		return serializerType;
	}

	/**
	 * Sets the path to XML descriptor for AnalysisEngine.
	 * @param aeDescriptor the path to XML descriptor for AnalysisEngine.
	 */
	public void setAeDescriptor(String aeDescriptor) {
		this.aeDescriptor = aeDescriptor;
	}

	/**
	 * Sets the UMLS username.
	 * @param uMLSUser the UMLS username.
	 */
	public void setUMLSUser(String uMLSUser) {
		this.UMLSUser = uMLSUser;
	}

	/**
	 * Sets the UMLS password.
	 * @param uMLSPass the UMLS password.
	 */
	public void setUMLSPass(String uMLSPass) {
		this.UMLSPass = uMLSPass;
	}

	/**
	 * Enables the formatted output for serializer.
	 * @param prettyPrint {@true} to enable formatted output, {@code false} otherwise.
	 */
	public void setPrettyPrint(boolean prettyPrint) {
		this.prettyPrint = prettyPrint;
	}

	/**
	 * Sets the type of cTAKES (UIMA) serializer used to write CAS. 
	 * @param serializerType the type of cTAKES serializer.
	 */
	public void setSerializerType(CTAKESSerializer serializerType) {
		this.serializerType = serializerType;
	}
}
