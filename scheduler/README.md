# Installation into an OpenShift Cluster

## Requirements

* Git installed
* The OC client installed

## Create Projects and CI CD Tooling

We will be using the Labs CI CD Repository to create the required objects in OpenShift.  The following repository contains the inventories that will be installed.

```
# clone the repository
$ git clone https://github.com/rht-labs/labs-ci-cd.git

# change to the repository directory
$ cd labs-ci-cd

# Switch to the latest Tag (v3.11.5 when this was written)
$ git checkout -b v3.11.5

```

### Install Required Roles:

```
ansible-galaxy install -r requirements.yml --roles-path=roles
```

### Create the OpenShift Projects/Namespaces:

ansible-playbook site.yml -e ci_cd_namespace=examples-scheduler-ci-cd -e dev_namespace=examples-scheduler-dev -e test_namespace=examples-scheduler-test -l bootstrap

### Create CI CD Tooling Deployments:

ansible-playbook site.yml -e ci_cd_namespace=examples-scheduler-ci-cd -e dev_namespace=examples-scheduler-dev -e test_namespace=examples-scheduler-test -l tools

## Create Scheduling Pipelines and Services

If you haven't already get this repository by cloning it.

```
$ git clone https://github.com/dwasinge/examples.git
```
### Create Pipeline and Build Scheduler Domain JAR

```
# change to the applier directory for the delivery-domain project
cd ${REPO_BASE_DIR}/scheduler/common/delivery-domain/.openshift-applier

# install required roles for applier
$ ansible-galaxy install -r requirements.yml --roles-path=roles

# run the ansible playbook on the delivery-domain inventory
$ ansible-playbook apply.yml -i inventory/
```

### Create the Pipeline and Build Scheduler Planning KJAR

```
# change to the applier directory for the delivery-domain project
cd ${REPO_BASE_DIR}/scheduler/business-automation/planner/delivery-schedule-planner/.openshift-applier

# install required roles for applier
$ ansible-galaxy install -r requirements.yml --roles-path=roles

# run the ansible playbook on the delivery-domain inventory
$ ansible-playbook apply.yml -i inventory/
```

### Create the Pipeline, Build, and Deployment for delivery-crew-service project

```
# change to the applier directory for the delivery-domain project
cd ${REPO_BASE_DIR}/scheduler/microservices/delivery-crew-service/.openshift-applier

# install required roles for applier
$ ansible-galaxy install -r requirements.yml --roles-path=roles

# run the ansible playbook on the delivery-domain inventory
$ ansible-playbook apply.yml -i inventory/
```

### Create the Pipeline, Build, and Deployment for delivery-schedule-service project

```
# change to the applier directory for the delivery-domain project
cd ${REPO_BASE_DIR}/scheduler/microservices/delivery-schedule-service/.openshift-applier

# install required roles for applier
$ ansible-galaxy install -r requirements.yml --roles-path=roles

# run the ansible playbook on the delivery-domain inventory
$ ansible-playbook apply.yml -i inventory/
```
