Advanced-API
=

### Maven

Add the following to your pom.xml

```xml

	<repositories>
		<repository>
			<id>Mert1602-Advanced-API-mvn-repo</id>
			<url>https://raw.githubusercontent.com/Mert1602/Advanced-API/mvn-repo/</url>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
			</snapshots>
		</repository>
	</repositories>
	
	<dependencies>
		<dependency>
			<groupId>me.mert1602</groupId>
			<artifactId>advanced-api</artifactId>
			<version>[1.0.0,2.0.0)</version>
		</dependency>
	</dependencies>
		
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-shade-plugin</artifactId>
				<version>2.4.3</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>shade</goal>
						</goals>
					</execution>
				</executions>
				<configuration>
					<artifactSet>
						<includes>
							<include>me.mert1602:advanced-api</include>
						</includes>
					</artifactSet>
				</configuration>
			</plugin>
		</plugins>
	</build>
```