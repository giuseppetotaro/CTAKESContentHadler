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

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.impl.XCASSerializer;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.cas.impl.XmiSerializationSharedData;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;
import org.apache.uima.util.XmlCasSerializer;
import org.xml.sax.SAXException;

/**
 * This class provides methods to extract biomedical information from plain text
 * using {@see CTAKESContentHandler} that relies on Apache cTAKES.
 * 
 * <p>
 * Apache cTAKES is built on top of <a href="https://uima.apache.org/">Apache
 * UIMA</a> framework and <a href="https://opennlp.apache.org/">OpenNLP</a>
 * toolkit.
 * </p>
 *
 */
public class CTAKESUtils {
	// UIMA Analysis Engine
	private static AnalysisEngine ae = null;

	// JCas object for working with the CAS (Common Analysis System)
	private static JCas jcas = null;

	// UMLS username
	private final static String CTAKES_UMLS_USER = "ctakes.umlsuser";

	// UMLS password
	private final static String CTAKES_UMLS_PASS = "ctakes.umlspw";

	/**
	 * Returns a new UIMA Analysis Engine (AE). This method ensures that only
	 * one instance of an AE is created.
	 * 
	 * <p>
	 * An Analysis Engine is a component responsible for analyzing unstructured
	 * information, discovering and representing semantic content. Unstructured
	 * information includes, but is not restricted to, text documents.
	 * </p>
	 * 
	 * @param aeDescriptor
	 *            pathname for XML file including an AnalysisEngineDescription
	 *            that contains all of the information needed to instantiate and
	 *            use an AnalysisEngine.
	 * @param umlsUser
	 *            UMLS username for NLM database
	 * @param umlsPass
	 *            UMLS password for NLM database
	 * @return an Analysis Engine for analyzing unstructured information.
	 * @throws IOException
	 *             if any I/O error occurs.
	 * @throws InvalidXMLException
	 *             if the input XML is not valid or does not specify a valid
	 *             ResourceSpecifier.
	 * @throws ResourceInitializationException
	 *             if a failure occurred during production of the resource.
	 * @throws URISyntaxException
	 *             if URL of the resource is not formatted strictly according to
	 *             to RFC2396 and cannot be converted to a URI.
	 */
	public static AnalysisEngine getAnalysisEngine(String aeDescriptor,
			String umlsUser, String umlsPass) throws IOException,
			InvalidXMLException, ResourceInitializationException,
			URISyntaxException {
		if (ae == null) {
			// UMLS user ID and password.
			String aeDescriptorPath = CTAKESUtils.class
					.getResource(aeDescriptor).toURI().getPath();

			// get Resource Specifier from XML
			XMLInputSource aeIputSource = new XMLInputSource(aeDescriptorPath);
			ResourceSpecifier aeSpecifier = UIMAFramework.getXMLParser()
					.parseResourceSpecifier(aeIputSource);

			// UMLS user ID and password
			if ((umlsUser != null) && (!umlsUser.isEmpty())
					&& (umlsPass != null) && (!umlsPass.isEmpty())) {
				/*
				 * It is highly recommended that you change UMLS credentials in
				 * the XML configuration file instead of giving user and
				 * password using CTAKESConfig.
				 */
				System.setProperty(CTAKES_UMLS_USER, umlsUser);
				System.setProperty(CTAKES_UMLS_PASS, umlsPass);
			}

			// create AE
			ae = UIMAFramework.produceAnalysisEngine(aeSpecifier);
		}
		return ae;
	}

	/**
	 * Returns a new JCas () appropriate for the given Analysis Engine. This
	 * method ensures that only one instance of a JCas is created. A Jcas is a
	 * Java Cover Classes based Object-oriented CAS (Common Analysis System)
	 * API.
	 * 
	 * <p>
	 * Important: It is highly recommended that you reuse CAS objects rather
	 * than creating new CAS objects prior to each analysis. This is because CAS
	 * objects may be expensive to create and may consume a significant amount
	 * of memory.
	 * </p>
	 * 
	 * @param ae
	 *            AnalysisEngine used to create an appropriate JCas object.
	 * @return a JCas object appropriate for the given AnalysisEngine.
	 * @throws ResourceInitializationException
	 *             if a CAS could not be created because this AnalysisEngine's
	 *             CAS metadata (type system, type priorities, or FS indexes)
	 *             are invalid.
	 */
	public static JCas getJCas(AnalysisEngine ae)
			throws ResourceInitializationException {
		if (jcas == null) {
			jcas = ae.newJCas();
		}
		return jcas;
	}

	/**
	 * Serializes a CAS in the given format.
	 * 
	 * @param type
	 *            type of cTAKES (UIMA) serializer used to write CAS.
	 * @param prettyPrint
	 *            {@code true} to do pretty printing of output.
	 * @param stream
	 *            {@see OutputStream} object used to print out information
	 *            extracted by using cTAKES.
	 * @throws SAXException
	 *             if there was a SAX exception.
	 * @throws IOException
	 *             if any I/O error occurs.
	 */
	public static void serialize(CTAKESSerializer type, boolean prettyPrint,
			OutputStream stream) throws SAXException, IOException {
		if (type == CTAKESSerializer.XCAS) {
			XCASSerializer.serialize(jcas.getCas(), stream, prettyPrint);
		} else if (type == CTAKESSerializer.XMI) {
			XmiCasSerializer.serialize(jcas.getCas(), jcas.getTypeSystem(),
					stream, prettyPrint, new XmiSerializationSharedData());
		} else {
			XmlCasSerializer.serialize(jcas.getCas(), jcas.getTypeSystem(),
					stream);
		}
	}

	/**
	 * Resets cTAKES objects, if created. This method ensures that new cTAKES
	 * objects (a.k.a., Analysis Engine and JCas) will be created if getters of
	 * this class are called.
	 */
	public static void reset() {
		// Analysis Engine
		ae.destroy();
		ae = null;

		// JCas
		jcas.reset();
		jcas = null;
	}
}
