#!/bin/bash

# Simple Maven installer

MAVEN_VERSION="3.9.11"
INSTALL_DIR="$HOME"
MAVEN_DIR="$INSTALL_DIR/apache-maven-$MAVEN_VERSION"

# Download and extract Maven
wget -nc https://dlcdn.apache.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz -P $INSTALL_DIR
tar -xzf $INSTALL_DIR/apache-maven-$MAVEN_VERSION-bin.tar.gz -C $INSTALL_DIR

# Set environment variables
echo "export M2_HOME=$MAVEN_DIR" >> ~/.zshrc
echo "export PATH=\$M2_HOME/bin:\$PATH" >> ~/.zshrc

# Reload shell
source ~/.zshrc

# Check installation
mvn -v
