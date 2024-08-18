# Java Spring + JPA Data + DynamoDB のサンプルプロジェクト

DataStoreをDynamoDB, H2, InMemoryで切り替えられるようにしている。

## API 呼び出し例

```bash
curl -X PUT http://localhost:8080/test      -H "Content-Type: application/json"      -d '{"username": "yourUsername2", "password": "yourPassword"}'
```

```bash
curl http://localhost:8080/test?id=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```
