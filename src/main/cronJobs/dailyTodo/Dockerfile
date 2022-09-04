FROM debian
RUN apt-get update && \
    apt-get install --yes --no-install-recommends curl ca-certificates
COPY ./createTodo.sh /opt/scripts/createTodo.sh
ENTRYPOINT ["/opt/scripts/createTodo.sh"]