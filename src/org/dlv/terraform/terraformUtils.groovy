package org.dlv.terraform

class terraformUtils {
    def stack

    terraformUtils(stack) {
        this.stack = stack
    }

    def formatCheck(Customer){
        stack.sh """
          cd ${stack.env.WORKSPACE}/stacks/${Customer}
          sudo terraform fmt -check
        """
    }
}