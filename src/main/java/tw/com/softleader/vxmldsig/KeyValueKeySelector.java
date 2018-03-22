package tw.com.softleader.vxmldsig;

import javax.xml.crypto.*;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import java.security.KeyException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;


/**
 * KeySelector which retrieves the public key out of the KeyValue element and returns it. NOTE: If
 * the key algorithm doesn't match signature algorithm, then the public key will be ignored.
 */
/**
 * KeySelector which retrieves the public key out of the KeyValue element and returns it. NOTE: If
 * the key algorithm doesn't match signature algorithm, then the public key will be ignored.
 */
public class KeyValueKeySelector extends KeySelector {
  public KeySelectorResult select(
      KeyInfo keyInfo,
      KeySelector.Purpose purpose,
      AlgorithmMethod method,
      XMLCryptoContext context)
      throws KeySelectorException {

    if (keyInfo == null) {
      throw new KeySelectorException("Null KeyInfo object!");
    }
    SignatureMethod sm = (SignatureMethod) method;
    List<?> list = keyInfo.getContent();

    for (int i = 0; i < list.size(); i++) {
      XMLStructure xmlStructure = (XMLStructure) list.get(i);
      if (xmlStructure instanceof X509Data) {
        PublicKey pk = null;
        List<?> l = ((X509Data) xmlStructure).getContent();
        if (l.size() > 0) {
          X509Certificate cert = (X509Certificate) l.get(1);
          pk = cert.getPublicKey();
          if (algEquals(sm.getAlgorithm(), pk.getAlgorithm())) {
            return new SimpleKeySelectorResult(pk);
          }
        }
      }
      if (xmlStructure instanceof KeyValue) {
        PublicKey pk = null;
        try {
          pk = ((KeyValue) xmlStructure).getPublicKey();
        } catch (KeyException ke) {
          throw new KeySelectorException(ke);
        }
        // make sure algorithm is compatible with method
        if (algEquals(sm.getAlgorithm(), pk.getAlgorithm())) {
          return new SimpleKeySelectorResult(pk);
        }
      }
    }
    throw new KeySelectorException("No KeyValue element found!");
  }

  // @@@FIXME: this should also work for key types other than DSA/RSA
  static boolean algEquals(String algURI, String algName) {
    if (algName.equalsIgnoreCase("DSA")) {
      return true;
    } else if (algName.equalsIgnoreCase("RSA")) {
      return true;
    } else {
      return false;
    }
  }
}
