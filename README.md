#### PT-BR
# EFI Bank Pix Integration
 Uma aplicação que integra as funções de criar chave pix, e criar cobrança pix gerando o código copia e cola juntamente com o QR Code do mesmo. Além de possuir um sistema de usuários com autenticação JWT e confirmação de cadastro via emails.

## Requisitos
### Banco EFI
- Primeiramente para rodarmos o projeto, necessitamos criar uma conta no Banco EFI **(https://sejaefi.com.br/)**
- Após criarmos  a conta necessitamos ir até a aba API, e selecionar a opção "Criar Aplicação", dar um nome, selecionar a opção API Pix e deixar dessa maneira:
- ![configs api pix](https://github.com/leocalheiros/payment-system-integration/assets/123272507/568faa42-fdac-4993-9a0f-430ea7d0aa4a)

- Com a aplicação criada, iremos precisar ir até a aba certificados, e criar um novo certificado (nome pode ser de sua escolha), e após isso baixar o mesmo, e criar uma pasta na raiz do projeto com o nome "certs"
- Feito isso, volte para a sua aplicação e guarde a chave "Client_ID" e "Client_Secret", pois usaremos elas para configurar as variáveis de ambiente.

- SDK de apoio: https://github.com/efipay/sdk-java-examples-apis-efi
- Documentação oficial: https://dev.efipay.com.br/docs/api-pix/credenciais/
### Email
- Você precisará criar uma conta no gmail, ou utilizar alguma sua existente (é ncessária a autenticação de 2 fatores ativada).
- Após criar, você necessitará ir até as configurações da sua conta e pesquisar por app passwords, como na imagem a seguir:
- ![app pass gmail](https://github.com/leocalheiros/payment-system-integration/assets/123272507/c8253416-b129-4370-9f55-da953faddcc8)
- Dê um nome para sua aplicação e prossiga, depois disso anote as credenciais que ele te informar, iremos colocar elas como variáveis de ambiente depois.

## Configuração

- No arquivo credentials.json na pasta resources, altere o campo "certificate" com o caminho e nome correto do seu certificado gerado acima.
- Nos arquivos Dockerfile e docker-compose.yml, troque as variáveis de ambiente por aquelas que anotamos na fase de requisitos e salve o arquivo.

## Como rodar o projeto
- Abra o terminal na pasta raiz do projeto e execute caso seja a primeira vez:
 ```
docker compose up --build
```
- Depois da primeira, utilize somente:
```
docker compose up
```
## Rotas
- Para mais informações sobre as rotas, acesse a documentação swagger do projeto:
  ```
  localhost:8080/swagger-ui.html
  ```

## Licença

[MIT](https://choosealicense.com/licenses/mit/)
