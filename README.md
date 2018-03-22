# verify-xmldsig
Validating an XML Digital Signature

## Usage

### Docker

```
$ docker run --rm -it -v "$(pwd)":/data softleader/vxmldsig relative/path/to/signed.xml
```

- `relative/path/to/signed.xml` -  要驗證數位簽章的 xml 位置, **是對於 $(pwd) 的相對位置**

### Source

```
$ git clone git@github.com:softleader/verify-xmldsig.git
$ cd verify-xmldsig
$ mvn clean package
$ java -jar target/app.jar /absolute/path/to/signed.xml
```

- `/absolute/path/to/signed.xml` - 要驗證數位簽章的 xml 位置, 是在當前電腦上的**絕對位置**

## References

> https://docs.oracle.com/javase/8/docs/technotes/guides/security/xmldsig/Validate.java
