# A Dockerfile with everything that should be needed to run review
# This should be later used as basis for an image in Gitlab CI
# More info: https://gitlab.com/gitlab-org/gitlab-ce/issues/47955

# General documentation links:
# - https://docs.docker.com/get-started/part2/#introduction
# - https://docs.docker.com/develop/develop-images/dockerfile_best-practices/
# - https://blog.ubuntu.com/2018/07/09/minimal-ubuntu-released

# After installing docker you may need to:
#  sudo usermod -a -G docker $USER
# After that system restart is required.

FROM ubuntu:18.04
RUN apt-get update
RUN apt-get install -y openjdk-11-jdk-headless
COPY ./Group23.jar /
COPY ./graphs /graphs
ENTRYPOINT ["/bin/bash"]
