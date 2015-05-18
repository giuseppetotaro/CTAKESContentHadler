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

import java.io.OutputStream;

import org.apache.tika.sax.ContentHandlerDecorator;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.xml.sax.SAXException;

/**
 * Class used to extract biomedical information while parsing. 
 *
 * <p>
 * This class relies on <a href="http://ctakes.apache.org/">Apache cTAKES</a> 
 * that is a natural language processing system for extraction of information 
 * from electronic medical record clinical free-text.
 * </p>
 *
 */
public class CTAKESContentHandler extends ContentHandlerDecorator {
	// OutputStream object used to print out information extracted by cTAKES
	private OutputStream stream = null;
	
	// Configuration object for CTAKESContentHandler
	private CTAKESConfig config = null;
	
	// StringBuilder object used to build the clinical free-text for cTAKES
	private StringBuilder sb = null;
	
	/**
	 * Constructor for CTAKESContentHandler.
	 * @param stream {@see OutputStream} object used to print out information 
	 * extracted by cTAKES.
	 * @param config {@see CTAKESConfig} object used to configure cTAKES 
	 * analysis engine.
	 */
	public CTAKESContentHandler(OutputStream stream, CTAKESConfig config) {
		this.stream = stream;
		this.config = config;
		this.sb = new StringBuilder();
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}

	@Override
	public void endDocument() throws SAXException {
		try {
			// create an Analysis Engine
			AnalysisEngine ae = CTAKESUtils.getAnalysisEngine(config.getAeDescriptor(), config.getUMLSUser(), config.getUMLSPass());
			// create a JCas, given an AE
			JCas jcas = CTAKESUtils.getJCas(ae);
			// analyze a text
			jcas.setDocumentText(sb.toString());
			ae.process(jcas);
			// serialize data
			CTAKESUtils.serialize(config.getSerializerType(), config.isPrettyPrint(), this.stream);
		} catch (Exception e) {
			throw new SAXException(e.getMessage());
		}
	}
}
