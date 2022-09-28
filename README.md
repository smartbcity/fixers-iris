# Java Sdk to sign jwt and verifiable-claims

## Gradle

 * Test
 ```bash
make test 
```

 * Test
 ```bash
make package 
```

 * Publish
 ```bash
make push -e VERSION=0.0.0-SNAPSHOT
```

# DID Specs

## DID Registrar [Link](https://identity.foundation/did-registration) 
This specs define how a DID Registrar should be implemented.

## DID Authentication [Link](https://identity.foundation/working-groups/authentication.html)
Many docs about DID Authentication extending OIDC Connect protocol.

# DID Projects

## Universal Registrar [Link](https://github.com/decentralized-identity/universal-registrar)
This Universal Registrar is used to redirect a request towards the corresponding registrar, regarding the DID method called. 
The project also defines how a registrar should be implemented ([DID Registrar specs](https://identity.foundation/did-registration/#storesecrets-option)).

## EBSI Registrar [Link](https://github.com/decentralized-identity/uni-registrar-driver-did-ebsi)
The EBSI Registrar is an example of an implemented registrar written in NodeJs.
