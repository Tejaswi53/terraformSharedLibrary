import org.dlv.terraform.terraformUtils

def call(Map config = [:]) {
    def terraform = new terraformUtils(this)

    pipeline {
        agent any

        parameters {
            string(name: 'Customer', defaultValue: config.customer ?: 'kasier', description: 'Enter the customer name')
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
        }
    }
}