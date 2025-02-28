# Commands

* Project Creation

~~~
mvn io.quarkus.platform:quarkus-maven-plugin:3.19.1:create "-DprojectGroupId=org.balhom" "-DprojectArtifactId=balhom-transactions-api" "-Dextensions=kotlin,rest-jackson"
~~~

* Install Yaml Config Reader

~~~
mvn quarkus:add-extension "-Dextensions=quarkus-config-yaml"
~~~
