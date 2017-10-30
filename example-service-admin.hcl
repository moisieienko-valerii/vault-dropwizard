path "secret/service/example_service/v1/*" {
  capabilities = ["create", "read", "update", "delete", "list"]
}

path "auth/token/create/role.service.example-service" {
  capabilities = ["create", "update"]
}
