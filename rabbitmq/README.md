### Install on Docker - Raspberry Pi
* **Documentação:** https://www.rabbitmq.com/kubernetes/operator/quickstart-operator.html
* **Connectar no raspberry pi:** 
  * ssh ubuntu@192.168.15.7
* **Criar o RabbitMQ:**
  * docker run -dit --restart unless-stopped -p 15672:15672 --hostname rabbit-mq --name some-rabbit rabbitmq:3
* **Entrar no container e habilitar o console:**
  * rabbitmq-plugins enable rabbitmq_management
* **Acessar o console externamente:** 
  * http://192.168.15.7:15672
  * guest/guest
* **Kubernetes:**
    * https://github.com/nickborges/springboot-base-rabbitmq/blob/master/kubernetes/rabbitmq.yaml