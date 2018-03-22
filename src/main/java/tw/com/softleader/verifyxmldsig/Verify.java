package tw.com.softleader.verifyxmldsig;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.util.Iterator;

public class Verify {

  public static void main(String[] args) throws Exception {

    if (args.length < 1) {
      throw new IllegalArgumentException("Signed xml path is required");
    }

    String signedXml = args[0];

    // Instantiate the document to be validated
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);
    Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(signedXml));

    // Find Signature element
    NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
    if (nl.getLength() == 0) {
      throw new Exception("Cannot find Signature element");
    }

    // Create a DOM XMLSignatureFactory that will be used to unmarshal the
    // document containing the XMLSignature
    XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

    // Create a DOMValidateContext and specify a KeyValue KeySelector
    // and document context
    DOMValidateContext valContext = new DOMValidateContext(new KeyValueKeySelector(), nl.item(0));

    // unmarshal the XMLSignature
    XMLSignature signature = fac.unmarshalXMLSignature(valContext);

    // Validate the XMLSignature (generated above)
    boolean coreValidity = signature.validate(valContext);

    // Check core validation status
    if (coreValidity == false) {
      System.err.println("Signature failed core validation");
      boolean sv = signature.getSignatureValue().validate(valContext);
      System.out.println("signature validation status: " + sv);
      // check the validation status of each Reference
      Iterator<?> i = signature.getSignedInfo().getReferences().iterator();
      for (int j = 0; i.hasNext(); j++) {
        boolean refValid = ((Reference) i.next()).validate(valContext);
        System.out.println("ref[" + j + "] validity status: " + refValid);
      }
    } else {
      System.out.println("Signature passed core validation");
    }
  }
}
