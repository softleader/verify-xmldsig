FROM softleader/base

COPY target/app.jar app.jar
COPY vxmldsig.sh /vxmldsig.sh

WORKDIR /data

ENTRYPOINT ["/bin/sh", "/vxmldsig.sh"]
