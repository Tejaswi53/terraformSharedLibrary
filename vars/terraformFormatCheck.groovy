import org.dlv.terraform

def call(Map config = [:]) {
    def terraform = new TerraformUtils(this)

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
                    terraform.formatCheck(params.Customer)
                }
            }
        }
    }
}