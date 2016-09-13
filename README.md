# Kaggle Bimbo Spark

This project is a partial port of my solution for Kaggle Grupo Bimbo demand prediction challenge. The original
solution was implemented in Python and R entirely. Here I wanted to compare data processing speed of spark and py-spark
with non-distributed solutions to gain some understanding on how I can use Spark for data processing on my local cluster
and AWS.

## Notes

The project has all jar-deployment plugins configured, so submitting a job for a local cluster is easy. For the cluster
configuration I use `vshulyak/overfittr` Docker image. Local sbt tasks are preconfigured to work with it.

Also note that Spark in local cluster mode requires local files (file://...) to be accessible not only to workers and
executors, but for the driver itself which is run on your local machine. An intuitive solution is to wrap
sbt in another docker container with data directories mounted, but I chose to opt to a workaround here: docker-compose.yml
maps `pwd` to a folder with the same `pwd` inside the `overfittr` container. And it works!

## Usage

To run in cluster mode:
```
$ sbt scluster
```

To run in local[\*] mode:
```
$ sbt slocal
```
