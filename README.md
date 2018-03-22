# verify-xmldsig
Validating an XML Digital Signature

## Usage

### Docker

```shell
$ docker run --rm -it -v "$(pwd)":/data softleader/vxmldsig relative/path/to/signed.xml
```

- `relative/path/to/signed.xml` -  要驗證數位簽章的 xml 位置, **是對於 $(pwd) 的相對位置**

#### Example

```shell
16:15:53 ➜ tmp tree
.
└── xml
    └── D7JSA3.00002.ME.158_Payload.xml

1 directory, 1 file

16:15:54 ➜ tmp docker run --rm -it -v "$(pwd)":/data softleader/vxmldsig xml/D7JSA3.00002.ME.158_Payload.xml
Signature passed core validation
```

### Source

```shell
$ git clone git@github.com:softleader/verify-xmldsig.git
$ cd verify-xmldsig
$ mvn clean package
$ java -jar target/app.jar /absolute/path/to/signed.xml
```

- `/absolute/path/to/signed.xml` - 要驗證數位簽章的 xml 位置, 是在當前電腦上的**絕對位置**

## References

> https://docs.oracle.com/javase/8/docs/technotes/guides/security/xmldsig/Validate.java
