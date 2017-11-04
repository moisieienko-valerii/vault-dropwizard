# vault-dropwizard

This repository is a demo of integration between Java application and HashiCorp Vault.

Dropwizard based microservice playes role of Java application.

## Dependencies

- Vault
- Summon


##Vault related files

The repository contains not only files related to Java/Dropwizard application,
but also files related to Vault. They are:
- Policy files:
  - example-service.hcl
  - example-service-admin.hcl
- secrets.yml
- summon-vault

###Policy files

There are two policies files. 
example-service.hcl defines application policy and example-service-admin.hcl defines admin policy.
These policy should be written to Vault and used for application token generation.

### secrets.yml
This file keeps secrets path and mapping them to ENV variables used by application.
File is not used by Vault directly, but is necessary for Summon.

### summon-vault
Summon's provider for Vault. This script should be placed to /usr/local/lib/summon/ with execution permissions.
It takes path:key string from secrets.yml file, parses it and queries corresponding secret from Vault. Returned value is placed to ENV variable and used by an application.

## Application Run

VAULT_TOKEN=<TOKEN> summon --provider summon-vault -f secrets.yml java -jar ./target/vault-dropwizard-1.0-SNAPSHOT.jar server src/main/resources/config.yml

<TOKEN> should have permissions to read secrets from corresponding path.
