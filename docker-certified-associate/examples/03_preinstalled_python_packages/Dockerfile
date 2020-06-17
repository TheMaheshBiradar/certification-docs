FROM python:3.7-slim-buster
COPY . .
RUN pip install --quiet -r packages.txt
ENTRYPOINT [ "python", server.py ]