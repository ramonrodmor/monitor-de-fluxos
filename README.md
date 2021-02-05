# Monitor de fluxos

### Descrição do projeto
Ferramenta para monitoração de fluxos em Redes Definidas por Software.
Neste contexto, foram desenvolvidas duas APIs REST: uma delas é disponibilizada pelo controlador para que a ferramenta instale monitores de fluxo relacionados a determinados protocolos do nível de aplicação; a outra é disponibilizada pela ferramenta para que o controlador possa sinalizar o início e o fim de um fluxo monitorado.
Projeto desenvolvido em conjunto com Helcio Wagner da Silva (helcio@ufersa.edu.br) e David Anderson Bezerra Silva (david.anderson@ufersa.edu.br) durante a disciplina de Gerência de Redes no semestre letivo 2016.2 na UFERSA (www.ufersa.edu.br).

### Publicação em eventos
Trabalho publicado na X Escola Potiguar de Computação e suas Aplicações (EPOCA 2017).

### Tecnologias utilizadas
* O projeto da aplicação foi desenvolvido em java;
* Padrão cliente-servidor consumindo serviços de monitoramento disponíveis no controlador (URL de requisições HTML, endereços de e-mail de origem e destino em mensagems SMTP, nome e sentido de movimento (envio ou recebimento de arquivos em operações FTP);
* Utilização as rotinas de captura de dados desenvolvidas em python pelo professor (Helcio Wagner) como Controlador da aplicação.
