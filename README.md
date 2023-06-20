# 2waySSl
2 way SSL in spring boot
Data Transfer with SSL 

Use KEYTOOL to generate PKC12 (.p12) file which holds keys for server, do same for client A and client B
Create certificate .crt file from that PKC12 for each server.
Import server's crt into Client A's .p12 file and vise versa
Configure and enable SSL in application properties in each server. 

	Note: configure client-auth as need for server and as none for client.
For client-side, load the .p12 file in Keystore to create SslContext.
Use that sslContext in HttpClient for secure connection.

That's all for configuration.
  
When client A hits any endpoint of server, client checks if the server's public key is Trusted or not and vice versa, if it's not trusted then it throws an error with bad credentials else it gives access to it. 
	Note: Both server's and client's public key needs to be in each other's  trustedCred in .p12 in order to execute successful HandShake





Command to generate .p12 and crt file

SOME KEYTOOL COMMAND
Create keystore and generate keypair :
keytool -genkeypair -alias <my_keypair_alias> -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore <my_store_name>.p12 -validity 3650

Create public certificate file :
keytool -export -alias <my_keypair_alias> -file <my_cert_name>.crt -keystore <my_store_name>.p12 

Import Client Cert to Server jks File 
keytool -import -alias <my_keypair_alias -file <my_cert_name>.crt -keystore <my_store_name>.p12
 
View certificate
keytool -list -keystore <my_store_name>.p12
keytool -list -v -keystore <my_store_name>.p12













