

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.CTAKESConfig;
import org.apache.tika.sax.CTAKESContentHandler;
import org.xml.sax.ContentHandler;

/**
 * Test class for {@see CTAKESContentHandler}.
 *
 */
public class TestCTAKESContentHandler {
	private static void usage() {
		System.err.println("Usage: " + TestCTAKESContentHandler.class.getName() + "-i /path/to/input -o /path/to/output [-u umlsUser -p umlsPassword] ");
		System.exit(1);
	}

	public static void main(String[] args) throws Exception {
		String inputPath = null;
		String outputPath = null;
		String umlsUser = null;
		String umlsPass = null;
		
		for (int i=0; i < args.length; i++) {
			if ("-i".equals(args[i])) {
				inputPath = args[++i];
			} else if ("-o".equals(args[i])) {
				outputPath = args[++i];
			} else if ("-u".equals(args[i])) {
				umlsUser = args[++i];
			} else if ("-p".equals(args[i])) {
				umlsPass = args[++i];
			} else {
				usage();
			}
		}
		
		if ((inputPath == null) || (outputPath == null)) {
			usage();
		}
		
		// create a new Tika parser
		Parser parser = new AutoDetectParser();
		
		// create objects for parsing
		InputStream inputStream = TikaInputStream.get(new File(inputPath));
		OutputStream outputStream = new FileOutputStream(new File(outputPath));
		CTAKESConfig config = new CTAKESConfig();
		config.setUMLSUser(umlsUser);
		config.setUMLSPass(umlsPass);
		config.setAeDescriptor("/ctakes-clinical-pipeline/desc/analysis_engine/AggregatePlaintextUMLSProcessor.xml");
		ContentHandler handler = new CTAKESContentHandler(outputStream, config);
		Metadata metadata = new Metadata();
		ParseContext context = new ParseContext();
		
		// perform parsing
		parser.parse(inputStream, handler, metadata, context);
		
		System.out.println("Process complete. Output: " + outputPath);
	}
}
