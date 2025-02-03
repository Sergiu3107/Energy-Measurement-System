FROM rabbitmq:4-management

EXPOSE 5672 15672

CMD ["rabbitmq-server"]