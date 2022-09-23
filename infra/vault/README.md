# Vault init
- Init Vault in dev mode
```
export VAULT_ADDR="http://localhost:8200"
export VAULT_TOKEN="***REMOVED***"
```

- Enable transit features
```
vault secrets enable transit
```
