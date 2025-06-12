# BalHom Transactions API

BalHom Transactions API acts as a microservice for the BalHom infrastructure, providing functionalities to manage
transactions.

## Environment Variables

| Name                                               | Description                                                                                                    |
|----------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| KEYCLOAK_URL                                       | Keycloak instance url. Ex: http://localhost:7080                                                               |
| KEYCLOAK_REALM                                     | Keycloak instance realm name. Default: balhom-realm                                                            |
| KEYCLOAK_CLIENT_ID                                 | Keycloak instance client id. Default: balhom-client                                                            |
| KEYCLOAK_API_CLIENT_ID                             | Keycloak instance client id for the API to read user data. Default: balhom-api-client                          |
| KEYCLOAK_API_CLIENT_SECRET                         | Keycloak instance client secret for the API to read user data                                                  |
| POSTGRES_URL                                       | Postgres instance url                                                                                          |
| POSTGRES_USERNAME                                  | Postgres instance username. Optional                                                                           |
| POSTGRES_PASSWORD                                  | Postgres instance password. Optional                                                                           |
| S3_URL                                             | S3 url                                                                                                         |
| S3_REGION                                          | S3 region. Default: us-west-2                                                                                  |
| S3_ACCESS_KEY                                      | S3 access key                                                                                                  |
| S3_SECRET_KEY                                      | S3 secret key                                                                                                  |
| S3_BUCKET_NAME                                     | S3 bucket name. Default: balhom-bucket                                                                         |
| KAFKA_SERVERS                                      | Kafka server urls                                                                                              |
| CURRENCY_PROFILES_API_URL                          | Currency Profiles API instance url. Ex: http://balhom-currency-profiles-api:8081                               |
| QUARKUS_HTTP_CORS_ORIGINS                          | CORS origins                                                                                                   |
| QUARKUS_HTTP_CORS_ORIGINS                          | CORS origins. Optional                                                                                         |
| QUARKUS_HTTP_CORS_HEADERS                          | Headers allowed. Optional                                                                                      |
| QUARKUS_HTTP_CORS_EXPOSED_HEADERS                  | Headers exposed in responses. Optional                                                                         |
| QUARKUS_HTTP_CORS_ACCESS_CONTROL_MAX_AGE           | Informs the browser how long it can cache the results of a preflight request. Optional                         |
| QUARKUS_HTTP_CORS_ACCESS_CONTROL_ALLOW_CREDENTIALS | Tells browsers if front-end can be allowed to access credentials when the request’s credentials mode. Optional |

## Error Codes

| Code | Description                                |
|------|--------------------------------------------|
| 100  | "Transaction not found"                    |
| 101  | "Transaction document not found"           |
| 103  | "Transaction documents max number reached" |
| 200  | "Currency profile reference not found"     |

> **1 to 99** Generic errors \
> **100 to 199** Transaction related errors \
> **200 to 299** Currency Profile Changes related errors
