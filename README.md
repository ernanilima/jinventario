# jInventario
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/ernanilima/jinventario/blob/main/LICENSE)

![Gif day](https://github.com/ernanilima/ernanilima/blob/main/imagens/jinventario-android/video_dia_jinventario.gif)![Gif night](https://github.com/ernanilima/ernanilima/blob/main/imagens/jinventario-android/video_noite_jinventario.gif)

[Mais imagens](https://github.com/ernanilima/ernanilima/tree/main/imagens/jinventario-android)

<a href='https://play.google.com/store/apps/details?id=br.com.ernanilima.jinventario&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1' target="_blank"><img alt='Disponível no Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/pt-br_badge_web_generic.png' width="350"/></a>

# Sobre o projeto
Aplicação mobile desenvolvida com a linguagem Java.

jInventario foi construído com Java utilizando o padrão MVP, o objetivo dessa aplicação é auxiliar na coletagem de produtos para inventário de estoque.

Esta aplicação utiliza a o `Firebase` para login por e-mail e pelo `Google`.

Utilizado o `GreenDao` para gravar os dados localmente no banco de dados `SQLite`.

Aplicação possui a possibilidade de utilizar a `câmera como scanner`, essa aplicação utiliza a `camerax`, nas configurações é possível mudar o tipo de câmera que será utilizada ou até mesmo desativar esse recurso, também é possível `compartilhar` a contagem em formato `csv`.


### A aplicação disponibiliza de:
- **Login pelo Firebase**
    - Google
    - **E-mail e senha**
        - Login apenas para e-mail confirmado
        - Caso o usuário não tenha confirmado o e-mail, exibe uma opção para enviar um novo e-mail de confirmação
    - **Cadastrar**
        - Cadastrar usuário por e-mail e senha
        - Cadastro apenas ao aceitar os termos de privacidade
        - Envio de e-mail de confirmação ao cadastrar usuário
    - **Esqueceu a senha**
        - Envio de e-mail para alterar a senha
    - **Validações**
        - Uma validação interna envia um novo e-mail apenas 10 minutos depois do último e-mail enviado (para confirmação ou nova senha)
- **Nome do aparelho**
    - Necessário informar o nome do aparelho, esse nome será usado ao compartilhar um arquivo de contagem, assim o usuário saberá qual aparelho foi usado em determinada contagem
- **Contagem de estoque**
    - **Contagem individual**
        - Possível informar o `código de barras`, `quantidade de caixa(s)` e `quantidade de produtos por caixa`
        - Aplicativo calcula o `total de itens` com base nas quantidades informadas
        - Grava localmente todas as contagens de estoque
        - Possível editar/alterar e/ou excluir um produto coletado
        - Possivel `compartilhar` a contagem em formato `csv`
        - A contagem é gravada automaticamente ao ser alterada
    - **Todas as contagens**
        - É exibido o número da contagem, data de criação, data e hora da ultima alteração e total de produtos na contagem
        - Exibe no topo da lista a contagem que teve a alteração mais recente
        - Possível editar/alterar e/ou excluir uma contagem
        - A contagem é gravada automaticamente ao ser alterada
- **Menu lateral (Drawer Layout)**
    - Nome do aparelho
    - E-mail cadastrado
    - Início
    - Criar nova contagem
    - Visualizar política de privacidade
    - Configuração
    - Sair

# Mais
Este projeto faz parte do meu portfólio pessoal, então, ficarei feliz se você puder me fornecer algum feedback sobre o projeto, código, estrutura ou qualquer coisa que você possa relatar que possa me tornar um desenvolvedor melhor!

Contato através do [LinkedIn](https://www.linkedin.com/in/ernanilima)

Email: ernani.tecc@gmail.com

Além disso, você pode usar este projeto como quiser, seja para estudar ou fazer melhorias!

É grátis!

# Tecnologias utilizadas
- Java
- Firebase
- Google API
- [GreenDao](https://greenrobot.org/greendao)
- [Câmera ML KIT](https://developers.google.com/ml-kit)
- [Câmera ZXing](https://github.com/journeyapps/zxing-android-embedded)

### Banco de dados
- SQLite
- Realtime Database (Firebase)

## IDE utilizada
- Android Studio ([Site Oficial](https://developer.android.com/studio))


## Clonar repositório

Necessário vincular o projeto ao Firebase

```bash
git clone https://github.com/ernanilima/jinventario.git
```

# Autor

Ernani Lima - https://ernanilima.com.br/
