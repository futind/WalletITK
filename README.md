# WalletITK

Test assignment for ITK company – a simple digital wallet service.

---

## Task

### Functionality
Implement a digital wallet with the following functionality:

- View wallet balance
- Deposit funds
- Withdraw funds

The service must handle concurrent requests safely and provide proper error responses when:

- Wallet does not exist
- Invalid request payload
- Insufficient funds

### Technological stack
#### Base
- Java 17
- Spring 3
- PostgreSQL
- Liquibase
- Docker
#### Testing
- JUnit 5
- Mockito
- Testcontainers

---

## API

### Deposit / Withdraw

**POST** `/api/v1/wallet`

Request body:

```json
{
  "walletId": "690955af-ee90-8325-9beb-d9dc896441f7",
  "operationType": "DEPOSIT" or "WITHDRAW",
  "amount": "1000.00"
}
```

### View balance

**GET** `/api/v1/wallets/{wallet_uuid}`

---

## Configuration
### Environment
Application and database configuration have to be provided using `.env` file.

**Example** `.env`:
```
# APP
WALLET_APP_OUTER_PORT=8081
WALLET_APP_INNER_PORT=8080
WALLET_APP_NAME=wallet-app

# DB
WALLET_DB_PORT=5433
WALLET_DB_NAME=wallet-db
WALLET_DB_USERNAME=user
WALLET_DB_PASSWORD=password
WALLET_DB_URL=jdbc:postgresql://wallet-db:5432/${WALLET_DB_NAME}
```
### Build and launch
Use `docker-compose`:
```shell
docker-compose build
docker-compose up -d
```
