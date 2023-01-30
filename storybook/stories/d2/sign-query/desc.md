

### Query

Type: `SignQuery`



  
<article>

***jsonLd*** `JsonLdObject` 

The json-LD to sign.

</article>
<article>

***method*** `String` 

Specify the signing method use: "rsa" or "transit" "rsa" uses a provided private key in the privateKey field "transit" uses a transit key which name is provided by the privateKey field

</article>
<article>

***pathToVerificationKey*** `String` 

The path to the public key to verify the signature.

</article>
<article>

***privateKey*** `Any` 

if method is "rsa", privateKey is privateKey value (only support #PKCS8 format). if method is "transit", privateKey is the name of the key in the vault.

</article>

