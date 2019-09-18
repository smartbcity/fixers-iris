# Iris


# Connect with QRCODE
```
Browser -> Iris =>connect()
Phone -> Iris => connect()

Phone -> Iris => send(PublicKeyResponse)
Iris -> Phone => send(SignMessage)
Phone -> Iris => send(SignResponse)

Iris -> Browser => send(SignResponse)
```

## Build project

```
./gradlew build
```

```
make build tag-latest push -e VERSION=0.1.0  --always-make
```