# moexiss-sdk

Java SDK for the [MOEX ISS API](https://iss.moex.com/iss/reference/).

- [Generated Maven site and Javadoc](https://drambluker.github.io/moexiss-sdk/)
- `./mvnw clean verify` runs deterministic tests.
- `./mvnw clean verify -Plive-contract-tests` additionally checks representative endpoints against the live MOEX ISS API.

The scheduled `MOEX ISS live contract` workflow runs the live checks daily. The
`Publish Maven site` workflow builds and deploys the documentation from `main`.
