
default:
  just --list

# Useful commands for the project
build:
  ./mvnw clean package

mutation-testing:
  ./mvnw package pitest:mutationCoverage

setup-maven-wrapper:
  chmod +x ./mvnw

reset-maven-wrapper:
  chmod -x ./mvnw

# Convenience scripts for syncing the project fork with the template
setup fork:
  git remote add upstream git@github.com:stefreschke/hwr-oop-project-template.git
  git fetch upstream

sync fork:
	git switch main
	git pull --rebase upstream main
