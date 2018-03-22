package tw.com.softleader.verifyxmldsig;

import javax.xml.crypto.KeySelectorResult;
import java.security.Key;
import java.security.PublicKey;

public class SimpleKeySelectorResult implements KeySelectorResult {
  private PublicKey pk;

  SimpleKeySelectorResult(PublicKey pk) {
    this.pk = pk;
  }

  public Key getKey() {
    return pk;
  }
}
