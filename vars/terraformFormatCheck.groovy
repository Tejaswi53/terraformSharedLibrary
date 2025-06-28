import org.dlv.terraform.terraformUtils

def call(Map config = [:]) {
    def terraform = new terraformUtils(this)

    pipeline {
        agent any

        parameters {
            string(name: 'Customer', defaultValue: config.customer ?: 'kasier', description: 'Enter the customer name')
            choice(name: 'ACTIONS', choices: ['plan', 'apply', 'destroy'], description: 'Select one choice to perform terraform action')
            choice(name: 'ENV', choices: ['dev', 'stage', 'prod'], description: 'Select the environment')
        }

        stages {

            stage('Git Checkout') {
                steps {
                    git branch: 'main', url: config.gitUrl ?: 'https://github.com/Tejaswi53/terraform_stack.git'
                }
            }

            stage('Terraform Format Check') {
                steps {
                    script {
                        terraform.formatCheck(params.Customer)
                    }
                }
            }

            stage('Terraform init') {
                steps {
                    script {
                        terraform.init(params.Customer)
                    }
                }
            }

            stage('Terraform validate') {
                steps {
                    script {
                        terraform.validate(params.Customer)
                    }
                }
            }

            stage('Terraform select workspace') {
                steps {
                    script {
                        terraform.selectWorkspace(params.Customer, params.ENV)
                    }
                }
            }
        }
    }
}