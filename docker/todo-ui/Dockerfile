FROM python:3-slim as build

LABEL HARI KARTHIGASU <hariprasad.karthigasu@gmail.com>

WORKDIR /app
COPY requirements.txt todo-ui.py /app/

RUN pip3 install --upgrade pip && \
    pip3 install -r ./requirements.txt

FROM gcr.io/distroless/python3:debug
WORKDIR /app

ENV PYTHONPATH=/usr/local/lib/python3.8/site-packages
EXPOSE 8080

COPY --from=build /app /app
COPY --from=build /usr/local/lib/python3.8/site-packages /usr/local/lib/python3.8/site-packages

CMD ["todo-ui.py"]
