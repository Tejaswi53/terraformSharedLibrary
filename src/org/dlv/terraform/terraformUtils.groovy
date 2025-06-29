package org.dlv.terraform

class terraformUtils {
    def stack

    terraformUtils(stack) {
        this.stack = stack
    }

    def formatCheck(Customer) {
        stack.sh """
          cd ${stack.env.WORKSPACE}/stacks/${Customer}
          sudo terraform fmt -check
        """
    }

    def init(Customer) {
        stack.sh """
          cd ${stack.env.WORKSPACE}/stacks/${Customer}
          sudo terraform init -input=false
        """
    }

    def validate(Customer) {
        stack.sh """
          cd ${stack.env.WORKSPACE}/stacks/${Customer}
          sudo terraform validate
        """
    }

    def selectWorkspace(Customer, ENV) {
        stack.sh """
           cd ${stack.env.WORKSPACE}/stacks/${Customer}
           if terraform workspace list | grep -q "${ENV}"; then
                sudo terraform workspace select "${ENV}"
           else
                sudo terraform workspace new "${ENV}"
                sudo terraform workspace select "${ENV}"
            fi
        """
    }

    def plan(Customer, ENV) {
        stack.sh """
            cd ${stack.env.WORKSPACE}/stacks/${Customer}
            sudo terraform plan -input=false -out=tfplan -var-file="environments/${ENV}.tfvars"
        """
    }

    def apply(Customer, ENV) {
        stack.sh """
            cd ${stack.env.WORKSPACE}/stacks/${Customer}
            sudo terraform apply -input=false tfplan
        """
    }

    def destroy(Customer, ENV) {
        stack.sh """
            cd ${stack.env.WORKSPACE}/stacks/${Customer}
            sudo terraform workspace select "${ENV}"
            sudo terraform destroy -auto-approve 
        """
    }
}